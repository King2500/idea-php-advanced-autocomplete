package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.completion.PhpCompletionUtil;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.jetbrains.php.lang.parser.PhpElementTypes;
import com.jetbrains.php.lang.psi.elements.*;
import net.king2500.plugins.PhpAdvancedAutoComplete.index.PhpMetaCompletion;
import net.king2500.plugins.PhpAdvancedAutoComplete.index.PhpMetaCompletionIndex;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpFunctionCompletionContributor extends CompletionContributor {

    public PhpFunctionCompletionContributor() {
        //noinspection unchecked
        PsiElementPattern.Capture<PsiElement> stringInFuncCall = PlatformPatterns.psiElement(PhpElementTypes.STRING)
            .withParent(PlatformPatterns.psiElement(PhpElementTypes.PARAMETER_LIST)
                .withParent(
                    PlatformPatterns.or(
                        PlatformPatterns.psiElement(PhpElementTypes.FUNCTION_CALL),
                        PlatformPatterns.psiElement(PhpElementTypes.METHOD_REFERENCE),
                        PlatformPatterns.psiElement(PhpElementTypes.NEW_EXPRESSION)
                    )
                )
            );
        //noinspection unchecked
        extend(CompletionType.BASIC,
            PlatformPatterns.or(
                PlatformPatterns
                    .psiElement(PhpTokenTypes.STRING_LITERAL)
                    .withParent(stringInFuncCall)
                    .withLanguage(PhpLanguage.INSTANCE),
                PlatformPatterns
                    .psiElement(PhpTokenTypes.STRING_LITERAL_SINGLE_QUOTE)
                    .withParent(stringInFuncCall)
                    .withLanguage(PhpLanguage.INSTANCE)
            ),
            new CompletionProvider<CompletionParameters>() {
                final Pattern DATETIME_NUMBER_ONE = Pattern.compile("^[-+]?1$");
                final Pattern DATETIME_NUMBERS = Pattern.compile("^[-+]?\\d+$");
                final Pattern DATETIME_PREFIX_NUMBER_ONE = Pattern.compile("^[-+]?1 ");
                final Pattern DATETIME_PREFIX_NUMBERS = Pattern.compile("^[-+]?\\d+ ");
                final Pattern DATETIME_PREFIX_NUMBERS_UNITS = Pattern.compile("[^-+]?\\d+[ ]?(sec(ond)?|min(ute)?|hour|day|forth?night|month|year|week(day)?)s? ");

                public void addCompletions(@NotNull CompletionParameters parameters,
                                           ProcessingContext context,
                                           @NotNull CompletionResultSet result) {

                    Project project = parameters.getPosition().getProject();
                    ParameterList parameterList = (ParameterList)parameters.getOriginalPosition().getParent().getParent();

                    String funcName = PhpElementsUtil.getCanonicalFuncName(parameterList.getParent());
                    String[] resultElements = {};
                    String[] resultInfos = {};
                    String[] resultParams = {};
                    String resultPrefix = "";
                    String resultPostfix = "";
                    String resultPostfixAlt = "";
                    String resultTailText = null;
                    String[] resultPostfixExceptions = {};
                    boolean resultBold = false;
                    boolean resultCaseSensitivity = true;
                    String[] deprecatedElements = {};
                    boolean allowMultiple = false;
                    boolean allowRepeat = false;
                    String splitter = ",";
                    String splitterSpace = " ";
                    boolean overwriteExistingCompletions = false;
                    InsertHandler<LookupElement> insertHandler = null;

                    int paramIndex = PhpElementsUtil.getParameterIndex(parameters.getPosition().getParent());

                    if (funcName == null) {
                        return;
                    }

                    String stringLiteral = parameters.getPosition().getText();
                    String stringPrefix = "";

                    if (stringLiteral.contains(CompletionUtil.DUMMY_IDENTIFIER)) {
                        stringPrefix = stringLiteral.substring(0, stringLiteral.indexOf(CompletionUtil.DUMMY_IDENTIFIER));
                    }

                    Function function = PhpElementsUtil.getFunction(parameterList);

                    PhpMetaCompletion metaCompletion = null;

                    if (function != null) {
                        metaCompletion = PhpMetaCompletionIndex.getMetaCompletion(project, function, paramIndex);
                    }

                    if (methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.dbConnectFuncs, 0)) {
                        if (funcName.startsWith("PDO::")) {
                            resultElements = DatabaseUtil.getPdoDSNs(project, "mysql://");

                            if (resultElements == null) {
                                resultElements = prefixArray("mysql:dbname=;host=", PhpCompletionTokens.dbConnectElements);
                            }

                            resultElements = concatArrays(resultElements, prefixArray("mysql:dbname=;host=", PhpCompletionTokens.dbConnectElements));
                        }
                        else {
                            resultElements = DatabaseUtil.getDbHostnames(project, "mysql://");
                            if (resultElements == null) {
                                resultElements = PhpCompletionTokens.dbConnectElements;
                            }

                            resultElements = concatArrays(resultElements, PhpCompletionTokens.dbConnectElements);
                        }
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.dbConnectUserFuncs)) {
                        resultElements = DatabaseUtil.getDbUsers(project, "mysql://");
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.dbCharSetFuncs)) {
                        resultElements = PhpCompletionTokens.dbCharSets;
                        resultInfos = PhpCompletionTokens.dbCharSetsInfos;
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.dbSelectDbFuncs)) {
                        resultElements = DatabaseUtil.getDbNames(project, "mysql://");
                    }

                    if (isCompletionList(metaCompletion, "php_extension")) {
                        resultElements = PhpCompletionTokens.phpExtensionElements;
                    }

//                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.fileFuncs)) {
//                        resultElements = FileUtil.getRelativeFiles(parameters.getPosition().getContainingFile().getOriginalFile());
//                    }

                    if (isCompletionList(metaCompletion, "file_mode")) {
                        resultElements = PhpCompletionTokens.fileModeElements;
                        resultInfos = PhpCompletionTokens.fileModeInfos;
                        resultBold = true;
                        overwriteExistingCompletions = true;
                    }

                    if (isCompletionList(metaCompletion, "date_format")) {
                        resultElements = PhpCompletionTokens.dateFormatTokens;
                        resultInfos = PhpCompletionTokens.dateFormatInfos;
                        resultBold = true;
                        resultParams = new String[resultElements.length];

                        for (int i = 0; i < PhpCompletionTokens.dateFormatTokens.length; i++) {
                            String format = PhpCompletionTokens.dateFormatTokens[i];
                            String dateTimeText = DateTimeUtil.formatPhpDateTime(format, Locale.ENGLISH);
                            resultParams[i] = !dateTimeText.isEmpty() ? (" = " + dateTimeText) : "";
                        }
                    }

                    if (isCompletionList(metaCompletion, "strftime_format")) {
                        resultElements = PhpCompletionTokens.timeFormatTokens;
                        resultInfos = PhpCompletionTokens.timeFormatInfos;
                        resultBold = true;
                    }

                    if (isCompletionList(metaCompletion,"datetime_token")) {
                        stringPrefix = stringPrefix.toLowerCase();
                        resultCaseSensitivity = false;
                        overwriteExistingCompletions = true;

                        // "1" without trailing space
                        if (patternMatches(DATETIME_NUMBER_ONE, stringPrefix)) {
                            resultElements = PhpCompletionTokens.dateTimeUnits;
                        }
                        // numbers like 2, 17 without trailing space
                        else if (patternMatches(DATETIME_NUMBERS, stringPrefix)) {
                            resultElements = PhpCompletionTokens.dateTimeUnits2;
                        }
                        else if (stringPrefix.contains(" ")) {
                            result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.lastIndexOf(" ") + 1));
                            String[] prefixParts = stringPrefix.split(" ");
                            int numParts = prefixParts.length;

                            // yesterday, today, tomorrow
                            if (numParts >= 1 && numParts <= 2 && Arrays.asList(PhpCompletionTokens.dateTimeDayRelTexts).contains(prefixParts[0])) {
                                resultElements = concatArrays(PhpCompletionTokens.dateTimeDaytimeTexts, PhpCompletionTokens.dateTimeHourTexts);
                            }
                            // back of, front of
                            else if (numParts <= 3 && (stringPrefix.startsWith("back of ") || stringPrefix.startsWith("front of "))) {
                                resultElements = PhpCompletionTokens.dateTimeHourTexts;
                            }
                            // first day of, last day of
                            else if (numParts <= 5 && (stringPrefix.startsWith("first day of ") || stringPrefix.startsWith("last day of "))) {
                                result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.lastIndexOf("day of ") + 7));
                                resultElements = concatArrays(PhpCompletionTokens.dateTimeMonthNames, PhpCompletionTokens.dateTimeMonthRelTexts);
                            }
                            // first mon of, last friday of, ...
                            else if (numParts <= 5 && stringPrefix.contains("of ") && Arrays.asList(PhpCompletionTokens.dateTimeDayOfTexts).contains(stringPrefix.substring(0, stringPrefix.indexOf("of ") + 3))) {
                                result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.lastIndexOf("of ") + 3));
                                resultElements = concatArrays(PhpCompletionTokens.dateTimeMonthNames, PhpCompletionTokens.dateTimeMonthRelTexts);
                            }
                            // first, last, next, this
                            else if ((numParts == 1 || (numParts == 2 && !stringPrefix.endsWith(" "))) && Arrays.asList(PhpCompletionTokens.dateTimeOrdinal).contains(prefixParts[0])) {
                                resultElements = concatArrays(PhpCompletionTokens.dateTimeDayNames, PhpCompletionTokens.dateTimeUnits);
                            }
                            // back, front, first day, last day, first monday, ...
                            else if (numParts >= 1 && Arrays.asList(PhpCompletionTokens.dateTimeOfPrefixes).contains(prefixParts[0] + " ")
                                || numParts >= 2 && Arrays.asList(PhpCompletionTokens.dateTimeOfPrefixes).contains(prefixParts[0] + " " + prefixParts[1] + " ")) {
                                resultElements = new String[]{"of "};
                            }
                            // monday, friday, ...
                            else if (numParts >= 1 && Arrays.asList(PhpCompletionTokens.dateTimeDayNames).contains(prefixParts[0])) {
                                result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.indexOf(" ") + 1));
                                resultElements = PhpCompletionTokens.dateTimeRelWeek;
                            }
                            // -1, +1
                            else if ((numParts == 1 || (numParts == 2 && !stringPrefix.endsWith(" "))) && patternMatches(DATETIME_PREFIX_NUMBER_ONE, stringPrefix)) {
                                resultElements = PhpCompletionTokens.dateTimeUnits;
                            }
                            // -2, +7
                            else if ((numParts == 1 || (numParts == 2 && !stringPrefix.endsWith(" "))) && patternMatches(DATETIME_PREFIX_NUMBERS, stringPrefix)) {
                                resultElements = PhpCompletionTokens.dateTimeUnits2;
                            }
                            // 5 days, 3 weeks
                            else if (numParts <= 3 && patternMatches(DATETIME_PREFIX_NUMBERS_UNITS, stringPrefix) && !stringPrefix.contains(" ago ")) {
                                resultElements = PhpCompletionTokens.dateTimeAgo;
                            }
                        }
                        else {
                            resultElements = concatArrays(PhpCompletionTokens.dateTimeRelativeFormats, PhpCompletionTokens.dateTimeDayNames);
                        }
                    }

                    if (isCompletionList(metaCompletion, "html_charset")) {
                        resultElements = PhpCompletionTokens.htmlCharSets;
                        resultCaseSensitivity = false;
                    }

                    if (isCompletionList(metaCompletion, "mb_encoding")) {
                        resultElements = PhpCompletionTokens.mbStringEncodingElements;
                        resultCaseSensitivity = false;
                    }

                    if (isCompletionList(metaCompletion, "mb_info_type")) {
                        resultElements = PhpCompletionTokens.mbStringInfoTypes;
                    }

                    if (isCompletionList(metaCompletion, "mb_language")) {
                        resultElements = PhpCompletionTokens.mbStringLanguageElements;
                    }

                    if (isCompletionList(metaCompletion, "ob_handler") && parameters.getInvocationCount() <= 1) {
                        resultElements = PhpCompletionTokens.obHandlerElements;
                        overwriteExistingCompletions = true;
                    }

                    if (isCompletionList(metaCompletion, "socket_transport")) {
                        resultElements = PhpCompletionTokens.socketTransports;
                        resultParams = new String[resultElements.length];
                        Arrays.fill(resultParams, funcName.equals("stream_socket_client") ? "<host>:<port>" : "<host>");
                    }

                    if (isCompletionList(metaCompletion, "env_var")) {
                        resultElements = PhpCompletionTokens.envNames;
                    }

                    if (isCompletionList(metaCompletion, "pack_format_code") && !stringPrefix.contains("*")) {
                        resultElements = PhpCompletionTokens.packCodes;
                        resultInfos = PhpCompletionTokens.packCodesInfos;
                        allowMultiple = true;
                        allowRepeat = true;
                        splitter = "";
                    }

                    if (isCompletionList(metaCompletion, "unpack_format_code") && !stringPrefix.contains("*")) {
                        resultElements = PhpCompletionTokens.packCodes;
                        resultInfos = PhpCompletionTokens.packCodesInfos;
                        allowMultiple = true;
                        allowRepeat = true;
                        splitter = "/";
                        splitterSpace = "";
                    }

                    if (isCompletionList(metaCompletion, "format_spec") || isCompletionList(metaCompletion, "scan_format_spec")) {
                        boolean isScanFormat = isCompletionList(metaCompletion, "scan_format_spec");
                        FormatSpecification formatSpec = getFormatSpecification(stringPrefix);
                        boolean insideFormat = formatSpec != null;

                        if (insideFormat || parameters.getInvocationCount() > 0) {
                            if (!insideFormat) {
                                resultPrefix = "%";
                            }
                            Collection<String> specifiers = new ArrayList<>();
                            Collection<String> specifiersInfos = new ArrayList<>();
                            String[] formatTokens = isScanFormat ? PhpCompletionTokens.scanFormatTokens : PhpCompletionTokens.formatTokens;
                            String[] formatInfos = isScanFormat ? PhpCompletionTokens.scanFormatInfos : PhpCompletionTokens.formatInfos;

                            for (int i = 0; i < formatTokens.length; i++) {
                                if (formatSpec != null) {
                                    if (formatTokens[i].equals("%") && !"%".equals(formatSpec.getText())) {
                                        continue;
                                    }

                                    if (formatSpec.hasPrecision() && formatInfos[i].equals("integer")) {
                                        continue;
                                    }
                                }

                                specifiers.add(formatTokens[i]);
                                specifiersInfos.add(formatInfos[i]);
                            }

                            if (formatSpec != null && formatSpec.isInsideFlags()) {
                                for (String flag : PhpCompletionTokens.formatFlags) {
                                    if (formatSpec.getText().contains(flag)) {
                                        continue;
                                    }

                                    specifiers.add(flag);
                                    specifiersInfos.add("");
                                }
                            }

                            resultElements = specifiers.toArray(new String[0]);
                            resultInfos = specifiersInfos.toArray(new String[0]);
                            allowMultiple = true;
                            allowRepeat = true;
                            splitter = "";
                        }
                    }

                    if (isCompletionList(metaCompletion, "http_response_header_name") || isCompletionList(metaCompletion, "http_response_header_string")) {
                        boolean isFullHeader = isCompletionList(metaCompletion, "http_response_header_string");
                        if (!stringPrefix.contains(":") && !stringPrefix.startsWith("HTTP/1.0") && !stringPrefix.startsWith("HTTP/1.1")) {
                            resultElements = PhpCompletionTokens.httpHeaderResponseFields;
                            resultPostfixAlt = " ";
                            resultPostfixExceptions = new String[]{"HTTP/1.0", "HTTP/1.1"};
                            deprecatedElements = PhpCompletionTokens.httpHeaderDeprecatedFields;
                            overwriteExistingCompletions = true;
                            if (isFullHeader) {
                                resultBold = true;
                                resultPostfix = ": ";
                                resultParams = new String[resultElements.length];

                                for (int i = 0; i < resultElements.length; i++) {
                                    String header = resultElements[i];
                                    resultParams[i] = "";
                                    if (PhpCompletionTokens.httpHeaderResponseFieldsSyntax.containsKey(header)) {
                                        resultParams[i] = PhpCompletionTokens.httpHeaderResponseFieldsSyntax.get(header);
                                    }
                                }
                            }
                        }
                        else if (isFullHeader) {
                            result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.contains(": ") ? stringPrefix.indexOf(": ") + 2 : stringPrefix.indexOf(":") + 1));

                            if (stringPrefix.startsWith("Allow: ") || stringPrefix.startsWith("Access-Control-Allow-Methods: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpMethods;
                            }
                            else if (stringPrefix.startsWith("Accept-CH: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpClientHintDirectives;
                            }
                            else if (stringPrefix.startsWith("Accept-Ranges: ")) {
                                resultElements = PhpCompletionTokens.httpRangeTypes;
                            }
                            else if (stringPrefix.startsWith("Access-Control-Allow-Credentials: ")) {
                                resultElements = PhpCompletionTokens.httpACAllowCredentials;
                            }
                            else if (stringPrefix.startsWith("Access-Control-Allow-Headers: ")) {
                                allowMultiple = true;
                                List<String> alwaysAllowed = Arrays.asList(PhpCompletionTokens.httpACAllowHeadersAlways);
                                List<String> filteredHeaderFields = Arrays.stream(PhpCompletionTokens.httpHeaderRequestFields).filter(((Predicate<String>)alwaysAllowed::contains).negate()).collect(Collectors.toList());
                                resultElements = new String[filteredHeaderFields.size()];
                                resultElements = filteredHeaderFields.toArray(resultElements);
                            }
                            else if (stringPrefix.startsWith("Access-Control-Allow-Origin: ")) {
                                resultElements = PhpCompletionTokens.httpACAllowOrigin;
                            }
                            else if (stringPrefix.startsWith("Access-Control-Expose-Headers: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpHeaderResponseFields;
                            }
                            else if (stringPrefix.startsWith("Cache-Control: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpCacheControlDirectives;
                            }
                            else if (stringPrefix.startsWith("Clear-Site-Data: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpClearSiteDataDirectives;
                            }
                            else if (stringPrefix.startsWith("Connection: ")) {
                                resultElements = PhpCompletionTokens.httpConnectionOptions;
                            }
                            else if (stringPrefix.startsWith("Content-Disposition: ")) {
                                resultElements = PhpCompletionTokens.httpContentDispositionTokens;
                            }
                            else if (stringPrefix.startsWith("Content-Encoding: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpEncodingTokens;
                                resultCaseSensitivity = false;
                            }
                            else if (stringPrefix.startsWith("Content-Language: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.isoLanguageCodes;
                            }
                            else if (stringPrefix.startsWith("Content-Location: /") || stringPrefix.startsWith("Location: /")) {
                                overwriteExistingCompletions = true;
                                resultElements = prefixArray("/", FileUtil.getProjectFiles(project));
                                resultElements = concatArrays(new String[]{"/"}, resultElements);
                            }
                            else if ((stringPrefix.equals("Content-Location: ") || stringPrefix.equals("Location: ")
                                || stringPrefix.startsWith("Content-Location: h") || stringPrefix.startsWith("Location: h"))
                                && !stringPrefix.contains("://")) {
                                resultElements = PhpCompletionTokens.httpLocationBaseUrls;
                                insertHandler = InvokeCompletionInsertHandler.getInstance();
                                resultTailText = "...";
                            }
                            else if (stringPrefix.startsWith("Content-Range: ")) {
                                resultElements = new String[]{PhpCompletionTokens.httpRangeTypes[0]};
                                resultPostfix = " ";
                            }
                            else if (stringPrefix.startsWith("Content-Security-Policy: ") || stringPrefix.startsWith("X-Content-Security-Policy: ") || stringPrefix.startsWith("X-WebKit-CSP: ") || stringPrefix.startsWith("Content-Security-Policy-Report-Only: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpCSP;
                                splitter = ";";
                            }
                            else if (stringPrefix.startsWith("Content-Type: ")) {
                                if (!stringPrefix.contains(";")) {
                                    resultElements = PhpCompletionTokens.mimeTypes;
                                }
                                else if (!stringPrefix.contains("charset=")) {
                                    result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.contains("; ") ? stringPrefix.lastIndexOf("; ") + 2 : stringPrefix.lastIndexOf(";") + 1));
                                    resultElements = prefixArray("charset=", PhpCompletionTokens.httpCharSets);
                                }
                                else /*if(stringPrefix.contains("charset="))*/ {
                                    result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.lastIndexOf("charset=") + 8));
                                    resultElements = PhpCompletionTokens.httpCharSets;
                                }
                            }
                            else if (stringPrefix.startsWith("Cross-Origin-Resource-Policy: ")) {
                                resultElements = PhpCompletionTokens.httpCORS;
                            }
                            else if (stringPrefix.startsWith("Expect-CT: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpExpectCT;
                            }
                            else if (stringPrefix.startsWith("Feature-Policy: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpFeaturePolicyDirectives;
                                splitter = ";";
                            }
                            else if (stringPrefix.startsWith("Keep-Alive: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpKeepAliveDirectives;
                            }
                            else if (stringPrefix.startsWith("Pragma: ")) {
                                resultElements = PhpCompletionTokens.httpPragmaDirectives;
                            }
                            else if (stringPrefix.startsWith("Proxy-Authenticate: ") || stringPrefix.startsWith("WWW-Authenticate: ")) {
                                resultElements = PhpCompletionTokens.httpAuthenticationTypes;
                            }
                            else if (stringPrefix.startsWith("Public-Key-Pins: ") || stringPrefix.startsWith("Public-Key-Pins-Report-Only: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpPublicKeyPinsDirectives;
                                splitter = ";";
                            }
                            else if (stringPrefix.startsWith("Referrer-Policy: ")) {
                                resultElements = PhpCompletionTokens.httpReferrerPolicyDirectives;
                            }
                            else if (stringPrefix.startsWith("Set-Cookie: ") && stringPrefix.contains(";")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpSetCookieDirectives;
                                splitter = ";";
                            }
                            else if (stringPrefix.startsWith("Strict-Transport-Security: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpStrictTransportSecurityDirectives;
                                splitter = ";";
                            }
                            else if (stringPrefix.startsWith("Status: ") || stringPrefix.startsWith("HTTP/1.0 ") || stringPrefix.startsWith("HTTP/1.1 ")) {
                                resultElements = PhpCompletionTokens.httpStatusCodes;

                                if (stringPrefix.startsWith("HTTP/1.1 ")) {
                                    resultElements = concatArrays(resultElements, PhpCompletionTokens.httpStatusCodes11);
                                }
                            }
                            else if (stringPrefix.startsWith("Trailer: ")) {
                                allowMultiple = true;
                                List<String> notAllowed = Arrays.asList(PhpCompletionTokens.httpHeaderResponseFieldsNotInTrailer);
                                List<String> filteredHeaderFields = Arrays.stream(PhpCompletionTokens.httpHeaderResponseFields).filter(((Predicate<String>)notAllowed::contains).negate()).collect(Collectors.toList());
                                resultElements = new String[filteredHeaderFields.size()];
                                resultElements = filteredHeaderFields.toArray(resultElements);
                            }
                            else if (stringPrefix.startsWith("Transfer-Encoding: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpTransferEncodingValues;
                            }
                            else if (stringPrefix.startsWith("Vary: ")) {
                                allowMultiple = true;
                                resultElements = concatArrays(new String[]{"*"}, PhpCompletionTokens.httpHeaderRequestFields);
                            }
                            else if (stringPrefix.startsWith("Via: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpViaProtocols;
                                resultPostfix = " ";
                            }
                            else if (stringPrefix.startsWith("Warning: ")) {
                                resultElements = PhpCompletionTokens.httpWarningCodes;
                                resultInfos = PhpCompletionTokens.httpWarningTexts;
                                resultPostfix = " ";
                            }
                            else if (stringPrefix.startsWith("X-Content-Type-Options: ")) {
                                resultElements = PhpCompletionTokens.httpXContentTypeOptions;
                            }
                            else if (stringPrefix.startsWith("X-DNS-Prefetch-Control: ")) {
                                resultElements = PhpCompletionTokens.httpXDnsPrefetchControlDirectives;
                            }
                            else if (stringPrefix.startsWith("X-Frame-Options: ")) {
                                resultElements = PhpCompletionTokens.httpXFrameOptions;
                            }
                            else if (stringPrefix.startsWith("X-UA-Compatible: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpXUACompatibleValues;
                                splitter = ";";
                            }
                            else if (stringPrefix.startsWith("X-Robots-Tag: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpXRobotsTagDirectives;
                            }
                            else if (stringPrefix.startsWith("X-XSS-Protection: ")) {
                                resultElements = PhpCompletionTokens.httpXXssProtectionValues;
                            }
                        }
                    }

                    if (isCompletionList(metaCompletion, "http_request_header_name")) {
                        resultElements = PhpCompletionTokens.httpHeaderRequestFields;
                        overwriteExistingCompletions = true;
                    }

                    if (isCompletionList(metaCompletion, "http_method")) {
                        resultElements = PhpCompletionTokens.httpMethods;
                    }

                    if (isCompletionList(metaCompletion, "http_cachecontrol_directive")) {
                        resultElements = PhpCompletionTokens.httpCacheControlDirectives;
                    }

                    if (isCompletionList(metaCompletion, "http_encoding_token")) {
                        resultElements = PhpCompletionTokens.httpEncodingTokens;
                    }

                    if (isCompletionList(metaCompletion, "http_charset")) {
                        resultElements = PhpCompletionTokens.httpCharSets;
                    }

                    if (isCompletionList(metaCompletion, "http_statuscode_text")) {
                        resultElements = PhpCompletionTokens.httpStatusCodes;
                    }

                    if (isCompletionList(metaCompletion, "iso_language_code")) {
                        resultElements = PhpCompletionTokens.isoLanguageCodes;
                    }

                    if (isCompletionList(metaCompletion, "mime_type")) {
                        resultElements = PhpCompletionTokens.mimeTypes;
                    }

                    if (isCompletionList(metaCompletion, "phpstorm_meta_arguments_set")) {
                        resultElements = collectMetaArgumentsSets(parameters.getPosition());
                    }

                    if (resultElements == null) {
                        return;
                    }

                    // ", "
                    String split = splitter + splitterSpace;
                    if (allowMultiple && !splitter.isEmpty() && stringPrefix.contains(split)) {
                        result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.lastIndexOf(split) + split.length()));
                    }
                    else if (allowMultiple && splitter.isEmpty()) {
                        result = result.withPrefixMatcher("");
                    }

                    if (overwriteExistingCompletions) {
                        result.stopHere();
                    }

                    for (int i = 0; i < resultElements.length; i++) {

                        if (allowMultiple && !allowRepeat && stringPrefix.contains(resultElements[i] + splitter)) {
                            continue;
                        }
                        String postfix = Arrays.asList(resultPostfixExceptions).contains(resultElements[i]) ? resultPostfixAlt : resultPostfix;
                        LookupElementBuilder builder = LookupElementBuilder.create(resultPrefix + resultElements[i] + postfix)
                            .withCaseSensitivity(resultCaseSensitivity)
                            .withPresentableText(resultPrefix + resultElements[i])
//                                .withTailText(resultPostfix, true)
                            .withBoldness(resultBold)
                            .withLookupString(resultPrefix + resultElements[i].toLowerCase() + postfix);

                        if (Arrays.asList(deprecatedElements).contains(resultElements[i])) {
                            builder = builder.withStrikeoutness(true);
                        }

                        if (i < resultParams.length) {
                            builder = builder.withTailText(resultParams[i], true);
                        }
                        else if (resultTailText != null) {
                            builder = builder.withTailText(resultTailText, true);
                        }
                        if (i < resultInfos.length) {
                            builder = builder.withTypeText(resultInfos[i]);
                        }

                        InsertHandler<LookupElement> handler = insertHandler;

                        if (handler == null && (resultElements[i] + postfix).endsWith(" ")) {
                            handler = InvokeCompletionInsertHandler.getInstance();
                        }

                        if (handler != null) {
                            builder = builder.withInsertHandler(handler);
                        }

                        //LookupElement element = builder.withAutoCompletionPolicy(AutoCompletionPolicy.ALWAYS_AUTOCOMPLETE);
                        //result.addElement(element);
                        result.addElement(builder);
                    }
                }

                private String[] concatArrays(String[] A, String[] B) {
                    int aLen = A.length;
                    int bLen = B.length;
                    String[] C = new String[aLen + bLen];
                    System.arraycopy(A, 0, C, 0, aLen);
                    System.arraycopy(B, 0, C, aLen, bLen);
                    return C;
                }

                private String[] prefixArray(String prefix, String[] array) {
                    String[] B = new String[array.length];
                    for (int i = 0; i < B.length; i++) {
                        B[i] = prefix + array[i];
                    }
                    return B;
                }

                private boolean methodMatches(String methodName, int paramIndex, String[] tokens) {
                    return Arrays.asList(tokens).contains(methodName + ":" + paramIndex);
                }

                private boolean methodMatchesAt(String methodName, int paramIndex, String[] tokens, int expectedParamIndex) {
                    return Arrays.asList(tokens).contains(methodName) && paramIndex == expectedParamIndex;
                }

                private boolean patternMatches(Pattern pattern, @NotNull String string) {
                    return pattern.matcher(string).lookingAt();
                }

                private boolean isCompletionList(PhpMetaCompletion completion, @NotNull String list) {
                    if (completion == null) {
                        return false;
                    }

                    return list.equals(completion.getCompletionList());
                }
            }
        );
    }

    private FormatSpecification getFormatSpecification(String stringPrefix) {
        if (stringPrefix.isEmpty()) {
            return null;
        }
        FormatSpecification spec = new FormatSpecification();
        char lastChar = stringPrefix.charAt(stringPrefix.length() - 1);

        if (lastChar == '%' && StringUtil.getPrecedingCharNum(stringPrefix, stringPrefix.length() - 1, '%') % 2 == 0) {
            spec.setText("%");
            spec.setInsideFlags(true);
            return spec;
        }

        if (lastChar == '$' || Arrays.asList(PhpCompletionTokens.formatFlags).contains(Character.toString(lastChar))) {
            spec.setInsideFlags(true);
        }

        // Syntax: %[argnum$][flags][width][.precision]specifier

        char[] chars = stringPrefix.toCharArray();

        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];

            // width or precision
            if (Character.isDigit(c)) {
                continue;
            }
            // .precision
            if (c == '.' && i < chars.length - 1 && Character.isDigit(chars[i + 1])) {
                spec.setPrecision();
                continue;
            }
            // '-' flag requires width
            if (c == '-' && i < chars.length - 1 && Character.isDigit(chars[i + 1])) {
                continue;
            }
            // '(char) flag requires at least one char after it
            if (c == '\'' && i < chars.length - 1) {
                continue;
            }
            // other flags
            if (c == '+' || c == ' ' || c == '0') {
                continue;
            }
            // $ for argnum
            if (c == '$' && i > 0 && Character.isDigit(chars[i - 1])) {
                continue;
            }

            if (c == '%') {
                if (StringUtil.getPrecedingCharNum(stringPrefix, i, '%') % 2 == 0) {
                    spec.setText(stringPrefix.substring(i));
                    return spec;
                }
            }

            break;
        }

        return null;
    }

    private static class FormatSpecification {
        private String text;
        private boolean precision;
        private boolean insideFlags;

        FormatSpecification() {
        }
        FormatSpecification(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        void setText(String text) {
            this.text = text;
        }

        public boolean hasPrecision() {
            return precision;
        }

        void setPrecision() {
            this.precision = true;
        }

        public boolean isInsideFlags() {
            return insideFlags;
        }

        public void setInsideFlags(boolean insideFlags) {
            this.insideFlags = insideFlags;
        }
    }

    private String[] collectMetaArgumentsSets(PsiElement position) {
        Collection<String> argumentsSets = new ArrayList<>();

        PhpNamespace root = PsiTreeUtil.getParentOfType(position, PhpNamespace.class);
        if (root == null || !"PHPSTORM_META".equals(root.getName())) {
            return new String[0];
        }

        Collection<ParameterList> arguments = PsiTreeUtil.findChildrenOfType(root, ParameterList.class);
        for (ParameterList args : arguments) {
            PsiElement parent = args.getParent();
            if (!(parent instanceof FunctionReference) || !"registerArgumentsSet".equals(((FunctionReference)parent).getName())) {
                continue;
            }

            StringLiteralExpression arg0 = PsiTreeUtil.findChildOfType(args, StringLiteralExpression.class);
            if (arg0 == null) {
                continue;
            }

            argumentsSets.add(arg0.getContents());
        }

        return argumentsSets.toArray(new String[0]);
    }

    @Override
    public boolean invokeAutoPopup(@NotNull PsiElement position, char typeChar) {
        if (typeChar == ' ' || typeChar == '-') {
            return true;
        }

        return super.invokeAutoPopup(position, typeChar);
    }

    static class InvokeCompletionInsertHandler implements InsertHandler<LookupElement> {

        private static final InvokeCompletionInsertHandler instance = new InvokeCompletionInsertHandler();

        @Override
        public void handleInsert(InsertionContext context, LookupElement lookupElement) {
            PhpCompletionUtil.showCompletion(context);
        }

        public static InvokeCompletionInsertHandler getInstance() {
            return instance;
        }
    }
}
