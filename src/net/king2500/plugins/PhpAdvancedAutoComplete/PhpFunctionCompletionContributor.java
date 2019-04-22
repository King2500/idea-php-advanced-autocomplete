package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.completion.PhpCompletionUtil;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.jetbrains.php.lang.parser.PhpElementTypes;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.DatabaseUtil;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.DateTimeUtil;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpElementsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

                    String funcName = PhpElementsUtil.getCanonicalFuncName(parameters.getPosition().getParent().getParent().getParent());
                    Project project = parameters.getPosition().getProject();
                    String[] resultElements = {};
                    String[] resultInfos = {};
                    String[] resultParams = {};
                    String resultPostfix = "";
                    String resultPostfixAlt = "";
                    String[] resultPostfixExceptions = {};
                    boolean resultBold = false;
                    boolean resultCaseSensitivity = true;
                    String[] deprecatedElements = {};
                    boolean allowMultiple = false;
                    String splitter = ",";
                    boolean overwriteExistingCompletions = false;

                    int paramIndex = PhpElementsUtil.getParameterIndex(parameters.getPosition().getParent());

                    if(funcName == null) {
                        return;
                    }

                    String stringLiteral = parameters.getPosition().getText();
                    String stringPrefix = "";

                    if(stringLiteral.contains(CompletionUtil.DUMMY_IDENTIFIER)) {
                        stringPrefix = stringLiteral.substring(0, stringLiteral.indexOf(CompletionUtil.DUMMY_IDENTIFIER));
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.dbConnectFuncs, 0)) {
                        if(funcName.startsWith("PDO::")) {
                            resultElements = DatabaseUtil.getPdoDSNs(project, "mysql://");

                            if(resultElements == null) {
                                resultElements = prefixArray("mysql:dbname=;host=", PhpCompletionTokens.dbConnectElements);
                            }

                            resultElements = concatArrays(resultElements, prefixArray("mysql:dbname=;host=", PhpCompletionTokens.dbConnectElements));
                        }
                        else {
                            resultElements = DatabaseUtil.getDbHostnames(project, "mysql://");
                            if(resultElements == null) {
                                resultElements = PhpCompletionTokens.dbConnectElements;
                            }

                            resultElements = concatArrays(resultElements, PhpCompletionTokens.dbConnectElements);
                        }
                    }

                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.dbConnectUserFuncs)) {
                        resultElements = DatabaseUtil.getDbUsers(project, "mysql://");
                    }

                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.dbCharSetFuncs)) {
                        resultElements = PhpCompletionTokens.dbCharSets;
                        resultInfos = PhpCompletionTokens.dbCharSetsInfos;
                    }

                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.dbSelectDbFuncs)) {
                        resultElements = DatabaseUtil.getDbNames(project, "mysql://");
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.phpExtensionFuncs, 0)) {
                        resultElements = PhpCompletionTokens.phpExtensionElements;
                    }

//                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.fileFuncs)) {
//                        resultElements = FileUtil.getRelativeFiles(parameters.getPosition().getContainingFile().getOriginalFile());
//                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.fileModeFuncs)) {
                        resultElements = PhpCompletionTokens.fileModeElements;
                        resultInfos = PhpCompletionTokens.fileModeInfos;
                        resultBold = true;
                        overwriteExistingCompletions = true;
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.dateFormatFuncs)) {
                        resultElements = PhpCompletionTokens.dateFormatTokens;
                        resultInfos = PhpCompletionTokens.dateFormatInfos;
                        resultBold = true;
                        resultParams = new String[resultElements.length];

                        for (int i = 0; i < PhpCompletionTokens.dateFormatTokens.length; i++) {
                            String format = PhpCompletionTokens.dateFormatTokens[i];
                            String dateTimeText = DateTimeUtil.formatPhpDateTime(format);
                            resultParams[i] = !dateTimeText.isEmpty() ? (" = " + dateTimeText) : "";
                        }
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.timeFormatFuncs)) {
                        resultElements = PhpCompletionTokens.timeFormatTokens;
                        resultInfos = PhpCompletionTokens.timeFormatInfos;
                        resultBold = true;
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.dateTimeParserFuncs)) {
                        stringPrefix = stringPrefix.toLowerCase();
                        resultCaseSensitivity = false;

                        // "1" without trailing space
                        if (patternMatches(DATETIME_NUMBER_ONE, stringPrefix)) {
                            resultElements = PhpCompletionTokens.dateTimeUnits;
                            result.stopHere();
                        }
                        // numbers like 2, 17 without trailing space
                        else if(patternMatches(DATETIME_NUMBERS, stringPrefix)) {
                            resultElements = PhpCompletionTokens.dateTimeUnits2;
                            result.stopHere();
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

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.htmlCharSetFuncs, 2)) {
                        resultElements = PhpCompletionTokens.htmlCharSets;
                        resultCaseSensitivity = false;
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.mbStringEncodingFuncs)) {
                        resultElements = PhpCompletionTokens.mbStringEncodingElements;
                        resultCaseSensitivity = false;
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.mbStringInfoFuncs, 0)) {
                        resultElements = PhpCompletionTokens.mbStringInfoTypes;
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.mbStringLanguageFuncs, 0)) {
                        resultElements = PhpCompletionTokens.mbStringLanguageElements;
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.obHandlerFuncs, 0)) {
                        resultElements = PhpCompletionTokens.obHandlerElements;
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.socketFuncs)) {
                        resultElements = PhpCompletionTokens.socketTransports;
                        resultParams = new String[resultElements.length];
                        Arrays.fill(resultParams, funcName.equals("stream_socket_client") ? "<host>:<port>" : "<host>");
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.envFuncs)) {
                        resultElements = PhpCompletionTokens.envNames;
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.httpHeaderResponseFuncs, 0)) {
                        boolean isFullHeader = funcName.equals("header");
                        if (!stringPrefix.contains(":") && !stringPrefix.startsWith("HTTP/1.0") && !stringPrefix.startsWith("HTTP/1.1")) {
                            resultElements = PhpCompletionTokens.httpHeaderResponseFields;
                            if (isFullHeader) {
                                resultPostfix = ": ";
                            }
                            resultBold = true;
                            resultPostfixAlt = " ";
                            resultPostfixExceptions = new String[] { "HTTP/1.0", "HTTP/1.1" };
                            deprecatedElements = PhpCompletionTokens.httpHeaderDeprecatedFields;
                            overwriteExistingCompletions = true;

                            resultParams = new String[resultElements.length];

                            for (int i = 0; i < resultElements.length; i++) {
                                String header = resultElements[i];
                                resultParams[i] = "";
                                if (PhpCompletionTokens.httpHeaderResponseFieldsSyntax.containsKey(header)) {
                                    resultParams[i] = PhpCompletionTokens.httpHeaderResponseFieldsSyntax.get(header);
                                }
                            }
                        }
                        else if (isFullHeader) {
                            result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.contains(": ") ? stringPrefix.indexOf(": ") + 2 : stringPrefix.indexOf(":") + 1));

                            if(stringPrefix.startsWith("Allow: ") || stringPrefix.startsWith("Access-Control-Allow-Methods: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpMethods;
                            }
                            else if(stringPrefix.startsWith("Accept-CH: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpClientHintDirectives;
                            }
                            else if(stringPrefix.startsWith("Accept-Ranges: ")) {
                                resultElements = PhpCompletionTokens.httpRangeTypes;
                            }
                            else if(stringPrefix.startsWith("Access-Control-Allow-Credentials: ")) {
                                resultElements = PhpCompletionTokens.httpACAllowCredentials;
                            }
                            else if(stringPrefix.startsWith("Access-Control-Allow-Headers: ")) {
                                allowMultiple = true;
                                List<String> alwaysAllowed = Arrays.asList(PhpCompletionTokens.httpACAllowHeadersAlways);
                                List<String> filteredHeaderFields = Arrays.stream(PhpCompletionTokens.httpHeaderRequestFields).filter(((Predicate<String>) alwaysAllowed::contains).negate()).collect(Collectors.toList());
                                resultElements = new String[filteredHeaderFields.size()];
                                resultElements = filteredHeaderFields.toArray(resultElements);
                            }
                            else if(stringPrefix.startsWith("Access-Control-Allow-Origin: ")) {
                                resultElements = PhpCompletionTokens.httpACAllowOrigin;
                            }
                            else if(stringPrefix.startsWith("Access-Control-Expose-Headers: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpHeaderResponseFields;
                            }
                            else if(stringPrefix.startsWith("Cache-Control: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpCacheControlDirectives;
                            }
                            else if(stringPrefix.startsWith("Clear-Site-Data: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpClearSiteDataDirectives;
                            }
                            else if(stringPrefix.startsWith("Connection: ")) {
                                resultElements = PhpCompletionTokens.httpConnectionOptions;
                            }
                            else if(stringPrefix.startsWith("Content-Disposition: ")) {
                                resultElements = PhpCompletionTokens.httpContentDispositionTokens;
                            }
                            else if(stringPrefix.startsWith("Content-Encoding: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpEncodingTokens;
                                resultCaseSensitivity = false;
                            }
                            else if(stringPrefix.startsWith("Content-Language: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.isoLanguageCodes;
                            }
                            else if(stringPrefix.startsWith("Content-Location: ") || stringPrefix.startsWith("Location: ")) {
//                                resultElements = prefixArray("/", FileUtil.getProjectFiles(project));
//                                resultElements = concatArrays(new String[] { "/" }, resultElements);
                            }
                            else if(stringPrefix.startsWith("Content-Range: ")) {
                                resultElements = new String[] { PhpCompletionTokens.httpRangeTypes[0] };
                                resultPostfix = " ";
                            }
                            else if(stringPrefix.startsWith("Content-Security-Policy: ") || stringPrefix.startsWith("X-Content-Security-Policy: ") || stringPrefix.startsWith("X-WebKit-CSP: ") || stringPrefix.startsWith("Content-Security-Policy-Report-Only: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpCSP;
                                splitter = ";";
                            }
                            else if(stringPrefix.startsWith("Content-Type: ")) {
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
                            else if(stringPrefix.startsWith("Cross-Origin-Resource-Policy: ")) {
                                resultElements = PhpCompletionTokens.httpCORS;
                            }
                            else if(stringPrefix.startsWith("Expect-CT: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpExpectCT;
                            }
                            else if(stringPrefix.startsWith("Feature-Policy: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpFeaturePolicyDirectives;
                                splitter = ";";
                            }
                            else if(stringPrefix.startsWith("Keep-Alive: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpKeepAliveDirectives;
                            }
                            else if(stringPrefix.startsWith("Pragma: ")) {
                                resultElements = PhpCompletionTokens.httpPragmaDirectives;
                            }
                            else if(stringPrefix.startsWith("Proxy-Authenticate: ") || stringPrefix.startsWith("WWW-Authenticate: ")) {
                                resultElements = PhpCompletionTokens.httpAuthenticationTypes;
                            }
                            else if(stringPrefix.startsWith("Public-Key-Pins: ") || stringPrefix.startsWith("Public-Key-Pins-Report-Only: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpPublicKeyPinsDirectives;
                                splitter = ";";
                            }
                            else if(stringPrefix.startsWith("Referrer-Policy: ")) {
                                resultElements = PhpCompletionTokens.httpReferrerPolicyDirectives;
                            }
                            else if(stringPrefix.startsWith("Set-Cookie: ") && stringPrefix.contains(";")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpSetCookieDirectives;
                                splitter = ";";
                            }
                            else if(stringPrefix.startsWith("Strict-Transport-Security: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpStrictTransportSecurityDirectives;
                                splitter = ";";
                            }
                            else if(stringPrefix.startsWith("Status: ") || stringPrefix.startsWith("HTTP/1.0 ") || stringPrefix.startsWith("HTTP/1.1 ")) {
                                resultElements = PhpCompletionTokens.httpStatusCodes;

                                if(stringPrefix.startsWith("HTTP/1.1 ")) {
                                    resultElements = concatArrays(resultElements, PhpCompletionTokens.httpStatusCodes11);
                                }
                            }
                            else if(stringPrefix.startsWith("Trailer: ")) {
                                allowMultiple = true;
                                List<String> notAllowed = Arrays.asList(PhpCompletionTokens.httpHeaderResponseFieldsNotInTrailer);
                                List<String> filteredHeaderFields = Arrays.stream(PhpCompletionTokens.httpHeaderResponseFields).filter(((Predicate<String>) notAllowed::contains).negate()).collect(Collectors.toList());
                                resultElements = new String[filteredHeaderFields.size()];
                                resultElements = filteredHeaderFields.toArray(resultElements);
                            }
                            else if(stringPrefix.startsWith("Transfer-Encoding: ")) {
                                resultElements = PhpCompletionTokens.httpTransferEncodingValues;
                            }
                            else if(stringPrefix.startsWith("Vary: ")) {
                                allowMultiple = true;
                                resultElements = concatArrays(new String[] {"*"}, PhpCompletionTokens.httpHeaderRequestFields);
                            }
                            else if(stringPrefix.startsWith("Via: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpViaProtocols;
                                resultPostfix = " ";
                            }
                            else if(stringPrefix.startsWith("Warning: ")) {
                                resultElements = PhpCompletionTokens.httpWarningCodes;
                                resultInfos = PhpCompletionTokens.httpWarningTexts;
                                resultPostfix = " ";
                            }
                            else if(stringPrefix.startsWith("X-Content-Type-Options: ")) {
                                resultElements = PhpCompletionTokens.httpXContentTypeOptions;
                            }
                            else if(stringPrefix.startsWith("X-DNS-Prefetch-Control: ")) {
                                resultElements = PhpCompletionTokens.httpXDnsPrefetchControlDirectives;
                            }
                            else if(stringPrefix.startsWith("X-Frame-Options: ")) {
                                resultElements = PhpCompletionTokens.httpXFrameOptions;
                            }
                            else if(stringPrefix.startsWith("X-UA-Compatible: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpXUACompatibleValues;
                                splitter = ";";
                            }
                            else if(stringPrefix.startsWith("X-Robots-Tag: ")) {
                                allowMultiple = true;
                                resultElements = PhpCompletionTokens.httpXRobotsTagDirectives;
                            }
                            else if(stringPrefix.startsWith("X-XSS-Protection: ")) {
                                resultElements = PhpCompletionTokens.httpXXssProtectionValues;
                            }
                        }
                    }

                    if(resultElements == null)
                        return;

                    if (allowMultiple && stringPrefix.contains(splitter+" ")) {
                        result = result.withPrefixMatcher(stringPrefix.substring(stringPrefix.lastIndexOf(splitter+" ") + 2));
                    }

                    if (overwriteExistingCompletions) {
                        result.stopHere();
                    }

                    for(int i=0; i < resultElements.length; i++) {

                        if (allowMultiple && stringPrefix.contains(resultElements[i] + splitter)) {
                            continue;
                        }
                        String postfix = Arrays.asList(resultPostfixExceptions).contains(resultElements[i]) ? resultPostfixAlt : resultPostfix;
                        LookupElementBuilder builder = LookupElementBuilder.create(resultElements[i] + postfix)
                                .withCaseSensitivity(resultCaseSensitivity)
                                .withPresentableText(resultElements[i])
//                                .withTailText(resultPostfix, true)
                                .withBoldness(resultBold)
                                .withLookupString(resultElements[i].toLowerCase() + postfix)
                        ;

                        if (Arrays.asList(deprecatedElements).contains(resultElements[i])) {
                            builder = builder.withStrikeoutness(true);
                        }

                        if (i < resultParams.length) {
                            builder = builder.withTailText(resultParams[i], true);
                        }
                        if (i < resultInfos.length) {
                            builder = builder.withTypeText(resultInfos[i]);
                        }

                        InsertHandler<LookupElement> insertHandler = null;

                        if ((resultElements[i] + postfix).endsWith(" ")) {
                            insertHandler = InvokeCompletionInsertHandler.getInstance();
                        }

                        if (insertHandler != null) {
                            builder = builder.withInsertHandler(insertHandler);
                        }

                        //LookupElement element = builder.withAutoCompletionPolicy(AutoCompletionPolicy.ALWAYS_AUTOCOMPLETE);
                        //result.addElement(element);
                        result.addElement(builder);
                    }
                }

                private String[] concatArrays(String[] A, String[] B) {
                    int aLen = A.length;
                    int bLen = B.length;
                    String[] C = new String[aLen+bLen];
                    System.arraycopy(A, 0, C, 0, aLen);
                    System.arraycopy(B, 0, C, aLen, bLen);
                    return C;
                }

                private String[] prefixArray(String prefix, String[] array) {
                    String[] B = new String[array.length];
                    for(int i=0; i < B.length; i++) {
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
            }
        );
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
