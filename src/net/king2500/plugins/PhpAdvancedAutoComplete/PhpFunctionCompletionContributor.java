package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.jetbrains.php.lang.parser.PhpElementTypes;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.DbHelper;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.FileHelper;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class PhpFunctionCompletionContributor extends CompletionContributor {
    private static final String CARET_MAGIC_IDENTIFIER = "IntellijIdeaRulezzz";

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
                public void addCompletions(@NotNull CompletionParameters parameters,
                                           ProcessingContext context,
                                           @NotNull CompletionResultSet resultSet) {

                    String funcName = PhpHelper.getCanonicalFuncName(parameters.getPosition().getParent().getParent().getParent());
                    Project project = parameters.getPosition().getProject();
                    String[] resultElements = {};
                    String[] resultInfos = {};
                    String resultPostfix = "";
                    String resultPostfixAlt = "";
                    String[] resultPostfixExceptions = {};
                    boolean resultBold = false;
                    boolean resultCaseSensitivity = true;

                    int paramIndex = PhpHelper.getParameterIndex(parameters.getPosition().getParent());

                    if(funcName == null) {
                        return;
                    }

                    String stringLiteral = parameters.getPosition().getText();
                    String stringPrefix = "";

                    if(stringLiteral.contains(CARET_MAGIC_IDENTIFIER)) {
                        stringPrefix = stringLiteral.substring(0, stringLiteral.indexOf(CARET_MAGIC_IDENTIFIER));
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.iniFuncs, 0)) {
                        resultElements = PhpCompletionTokens.iniElements;
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.dbConnectFuncs, 0)) {
                        if(funcName.startsWith("PDO::")) {
                            resultElements = DbHelper.getPdoDSNs(project, "mysql://");

                            if(resultElements == null) {
                                resultElements = prefixArray("mysql:dbname=;host=", PhpCompletionTokens.dbConnectElements);
                            }

                            resultElements = concatArrays(resultElements, prefixArray("mysql:dbname=;host=", PhpCompletionTokens.dbConnectElements));
                        }
                        else {
                            resultElements = DbHelper.getDbHostnames(project, "mysql://");
                            if(resultElements == null) {
                                resultElements = PhpCompletionTokens.dbConnectElements;
                            }

                            resultElements = concatArrays(resultElements, PhpCompletionTokens.dbConnectElements);
                        }
                    }

                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.dbConnectUserFuncs)) {
                        resultElements = DbHelper.getDbUsers(project, "mysql://");
                    }

                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.dbCharSetFuncs)) {
                        resultElements = PhpCompletionTokens.dbCharSets;
                        resultInfos = PhpCompletionTokens.dbCharSetsInfos;
                    }

                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.dbSelectDbFuncs)) {
                        resultElements = DbHelper.getDbNames(project, "mysql://");
                    }

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.phpExtensionFuncs, 0)) {
                        resultElements = PhpCompletionTokens.phpExtensionElements;
                    }

//                    if(methodMatches(funcName, paramIndex, PhpCompletionTokens.fileFuncs)) {
//                        resultElements = FileHelper.getRelativeFiles(parameters.getPosition().getContainingFile().getOriginalFile());
//                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.fileModeFuncs)) {
                        resultElements = PhpCompletionTokens.fileModeElements;
                        resultInfos = PhpCompletionTokens.fileModeInfos;
                        resultBold = true;
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.dateFormatFuncs)) {
                        resultElements = PhpCompletionTokens.dateFormatTokens;
                        resultInfos = PhpCompletionTokens.dateFormatInfos;
                        resultBold = true;
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.timeFormatFuncs)) {
                        resultElements = PhpCompletionTokens.timeFormatTokens;
                        resultInfos = PhpCompletionTokens.timeFormatInfos;
                        resultBold = true;
                    }

                    if (methodMatches(funcName, paramIndex, PhpCompletionTokens.dateTimeParserFuncs)) {
                        stringPrefix = stringPrefix.toLowerCase();
                        resultCaseSensitivity = false;

                        if (stringPrefix.isEmpty()) {
                            resultElements = concatArrays(PhpCompletionTokens.dateTimeRelativeFormats, PhpCompletionTokens.dateTimeDayNames);
                        } else if (stringPrefix.endsWith(" ")) {
                            // yesterday, today, tomorrow
                            if (Arrays.asList(PhpCompletionTokens.dateTimeDayRelTexts).contains(stringPrefix.trim())) {
                                resultElements = concatArrays(PhpCompletionTokens.dateTimeDaytimeTexts, PhpCompletionTokens.dateTimeHourTexts);
                            }
                            // back of, front of
                            else if (stringPrefix.equals("back of ") || stringPrefix.equals("front of ")) {
                                resultElements = PhpCompletionTokens.dateTimeHourTexts;
                            }
                            // first day of, last day of
                            else if (stringPrefix.equals("first day of ") || stringPrefix.equals("last day of ")) {
                                resultElements = concatArrays(PhpCompletionTokens.dateTimeMonthNames, PhpCompletionTokens.dateTimeMonthRelTexts);
                            }
                            // first mon of, last friday of, ...
                            else if (Arrays.asList(PhpCompletionTokens.dateTimeDayOfTexts).contains(stringPrefix)) {
                                resultElements = concatArrays(PhpCompletionTokens.dateTimeMonthNames, PhpCompletionTokens.dateTimeMonthRelTexts);
                            }
                            // first, last, next, this
                            else if (Arrays.asList(PhpCompletionTokens.dateTimeOrdinal).contains(stringPrefix.trim())) {
                                resultElements = concatArrays(PhpCompletionTokens.dateTimeDayNames, PhpCompletionTokens.dateTimeUnits);
                            }
                            // back, front, first day, last day, first monday, ...
                            else if (Arrays.asList(PhpCompletionTokens.dateTimeOfPrefixes).contains(stringPrefix)) {
                                resultElements = new String[] { "of " };
                            }
                            // monday, friday, ...
                            else if (Arrays.asList(PhpCompletionTokens.dateTimeDayNames).contains(stringPrefix.trim())) {
                                resultElements = PhpCompletionTokens.dateTimeRelWeek;
                            }
                            // -1, +1
                            else if (stringPrefix.matches("^[-+]?1 ")) {
                                resultElements = PhpCompletionTokens.dateTimeUnits;
                            }
                            // -2, +7
                            else if (stringPrefix.matches("^[-+]?\\d+ ")) {
                                resultElements = PhpCompletionTokens.dateTimeUnits2;
                            }
                            // 5 days, 3 weeks
                            else if (stringPrefix.matches("[^-+]?\\d+[ ]?(sec(ond)?|min(ute)?|hour|day|forth?night|month|year|week(day)?)s? $")) {
                                resultElements = PhpCompletionTokens.dateTimeAgo;
                            }
                        }
                        // "1" without trailing space
                        else if (stringPrefix.matches("^[-+]?1$")) {
                            resultElements = PhpCompletionTokens.dateTimeUnits;
                            resultSet.stopHere();
                        }
                        // numbers like 2, 17 without trailing space
                        else if(stringPrefix.matches("^[-+]?\\d+$")) {
                            resultElements = PhpCompletionTokens.dateTimeUnits2;
                            resultSet.stopHere();
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

                    if(methodMatchesAt(funcName, paramIndex, PhpCompletionTokens.httpHeaderResponseFuncs, 0)) {
                        if(stringPrefix.startsWith("Allow:")) {
                            resultElements = PhpCompletionTokens.httpMethods;
                        }
                        else if(stringPrefix.startsWith("Accept-Ranges:") || stringPrefix.startsWith("Content-Range:")) {
                            resultElements = PhpCompletionTokens.httpRangeTypes;
                        }
                        else if(stringPrefix.startsWith("Cache-Control:")) {
                            resultElements = PhpCompletionTokens.httpCacheControlDirectives;
                        }
                        else if(stringPrefix.startsWith("Connection:")) {
                            resultElements = PhpCompletionTokens.httpConnectionOptions;
                        }
                        else if(stringPrefix.startsWith("Content-Encoding:")) {
                            resultElements = PhpCompletionTokens.httpEncodingTokens;
                            resultCaseSensitivity = false;
                        }
                        else if(stringPrefix.startsWith("Content-Language:")) {
                            resultElements = PhpCompletionTokens.isoLanguageCodes;
                        }
                        else if(stringPrefix.startsWith("Content-Location:") || stringPrefix.startsWith("Location:")) {
                            resultElements = prefixArray("/", FileHelper.getProjectFiles(project));
                            resultElements = concatArrays(new String[] { "/" }, resultElements);
                        }
                        else if(stringPrefix.startsWith("Content-Disposition:")) {
                            resultElements = PhpCompletionTokens.httpContentDispositionTokens;
                        }
                        else if(stringPrefix.startsWith("Content-Type:") && !stringPrefix.contains(";")) {
                            resultElements = PhpCompletionTokens.mimeTypes;
                        }
                        else if(stringPrefix.startsWith("Content-Type:") && stringPrefix.trim().endsWith(";")) {
                            resultElements = prefixArray("charset=", PhpCompletionTokens.httpCharSets);
                            resultCaseSensitivity = false;
                        }
                        else if(stringPrefix.startsWith("Content-Type:") && stringPrefix.trim().endsWith("charset=")) {
                            resultElements = PhpCompletionTokens.httpCharSets;
                            resultCaseSensitivity = false;
                        }
                        else if(stringPrefix.startsWith("Pragma:")) {
                            resultElements = PhpCompletionTokens.httpPragmaDirectives;
                        }
                        else if(stringPrefix.startsWith("Proxy-Authenticate:") || stringPrefix.startsWith("WWW-Authenticate:")) {
                            resultElements = PhpCompletionTokens.httpAuthenticationTypes;
                        }
                        else if(stringPrefix.startsWith("Status:") || stringPrefix.startsWith("HTTP/1.0 ") || stringPrefix.startsWith("HTTP/1.1 ")) {
                            resultElements = PhpCompletionTokens.httpStatusCodes;

                            if(stringPrefix.startsWith("HTTP/1.1 ")) {
                                resultElements = concatArrays(resultElements, PhpCompletionTokens.httpStatusCodes11);
                            }
                        }
                        else if(stringPrefix.startsWith("Trailer:")) {
                            resultElements = PhpCompletionTokens.httpHeaderRequestFields;
                        }
                        else if(stringPrefix.startsWith("Transfer-Encoding:")) {
                            resultElements = PhpCompletionTokens.httpTransferEncodingValues;
                        }
                        else if(stringPrefix.startsWith("Vary:")) {
                            resultElements = concatArrays(new String[] {"*"}, PhpCompletionTokens.httpHeaderRequestFields);
                        }
                        else if(stringPrefix.startsWith("X-Frame-Options:")) {
                            resultElements = PhpCompletionTokens.httpXFrameOptions;
                        }
                        else if(stringPrefix.startsWith("Content-Security-Policy:") || stringPrefix.startsWith("X-Content-Security-Policy:") || stringPrefix.startsWith("X-WebKit-CSP:")) {
                            resultElements = PhpCompletionTokens.httpCSP;
                        }
                        else if(stringPrefix.startsWith("X-Content-Type-Options:")) {
                            resultElements = PhpCompletionTokens.httpXContentTypeOptions;
                        }
                        else if(stringPrefix.startsWith("X-UA-Compatible:")) {
                            resultElements = PhpCompletionTokens.httpXUACompatibleValues;
                        }
                        else if(stringPrefix.startsWith("X-Robots-Tag:")) {
                            resultElements = PhpCompletionTokens.httpXRobotsTagDirectives;
                        }
                        else if(!stringPrefix.contains(":")) {
                            resultElements = PhpCompletionTokens.httpHeaderResponseFields;
                            resultPostfix = ":";
                            resultPostfixAlt = " ";
                            resultPostfixExceptions = new String[] { "HTTP/1.0", "HTTP/1.1" };
                        }
                    }

                    if(resultElements == null)
                        return;

//                    InsertHandler<LookupElement> handler = new InsertHandler<LookupElement>() {
//                        @Override
//                        public void handleInsert(InsertionContext context, LookupElement lookupElement) {
//                            PsiElement element = PsiUtilCore.getElementAtOffset(context.getFile(), context.getStartOffset());
//                        }
//                    };

                    for(int i=0; i < resultElements.length; i++) {
                        String postfix = Arrays.asList(resultPostfixExceptions).contains(resultElements[i]) ? resultPostfixAlt : resultPostfix;
                        LookupElementBuilder builder = LookupElementBuilder.create(resultElements[i] + postfix)
                                .withCaseSensitivity(resultCaseSensitivity)
                                .withPresentableText(resultElements[i])
//                                .withTailText(resultPostfix, true)
                                .withBoldness(resultBold)
                                .withLookupString(resultElements[i].toLowerCase())
//                                .withInsertHandler(handler)
                        ;

                        if(resultInfos.length > 0)
                            builder = builder.withTypeText(resultInfos[i]);

                        //LookupElement element = builder.withAutoCompletionPolicy(AutoCompletionPolicy.ALWAYS_AUTOCOMPLETE);
                        //resultSet.addElement(element);
                        resultSet.addElement(builder);
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

                private Boolean methodMatches(String methodName, int paramIndex, String[] tokens) {
                    return Arrays.asList(tokens).contains(methodName + ":" + paramIndex);
                }

                private Boolean methodMatchesAt(String methodName, int paramIndex, String[] tokens, int expectedParamIndex) {
                    return Arrays.asList(tokens).contains(methodName) && paramIndex == expectedParamIndex;
                }
            }
        );
    }

    @Override
    public boolean invokeAutoPopup(@NotNull PsiElement position, char typeChar) {
        if(typeChar == ' ')
            return true;

        return super.invokeAutoPopup(position, typeChar);
    }
}
