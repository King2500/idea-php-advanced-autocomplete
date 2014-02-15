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
    public PhpFunctionCompletionContributor() {
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

                    if(Arrays.asList(PhpCompletionTokens.iniFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpCompletionTokens.iniElements;
                    }

                    if(Arrays.asList(PhpCompletionTokens.dbConnectFuncs).contains(funcName) && paramIndex == 0) {
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

                    if(Arrays.asList(PhpCompletionTokens.dbConnectUserFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = DbHelper.getDbUsers(project, "mysql://");
                    }

                    if(Arrays.asList(PhpCompletionTokens.dbCharSetFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = PhpCompletionTokens.dbCharSets;
                        resultInfos = PhpCompletionTokens.dbCharSetsInfos;
                    }

                    if(Arrays.asList(PhpCompletionTokens.dbSelectDbFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = DbHelper.getDbNames(project, "mysql://");
                    }

                    if(Arrays.asList(PhpCompletionTokens.phpExtensionFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpCompletionTokens.phpExtensionElements;
                    }

/*
                    if(Arrays.asList(PhpCompletionTokens.fileFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = FileHelper.getRelativeFiles(parameters.getPosition().getContainingFile().getOriginalFile());
                    }
*/

                    if(Arrays.asList(PhpCompletionTokens.fileModeFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = PhpCompletionTokens.fileModeElements;
                        resultInfos = PhpCompletionTokens.fileModeInfos;
                        resultBold = true;
                    }

                    if(Arrays.asList(PhpCompletionTokens.dateFormatFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = PhpCompletionTokens.dateFormatTokens;
                        resultInfos = PhpCompletionTokens.dateFormatInfos;
                        resultBold = true;
                    }

                    if(Arrays.asList(PhpCompletionTokens.timeFormatFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = PhpCompletionTokens.timeFormatTokens;
                        resultInfos = PhpCompletionTokens.timeFormatInfos;
                        resultBold = true;
                    }

                    if(Arrays.asList(PhpCompletionTokens.htmlCharSetFuncs).contains(funcName) && paramIndex == 2) {
                        resultElements = PhpCompletionTokens.htmlCharSets;
                        resultCaseSensitivity = false;
                    }

                    if(Arrays.asList(PhpCompletionTokens.mbStringEncodingFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = PhpCompletionTokens.mbStringEncodingElements;
                        resultCaseSensitivity = false;
                    }

                    if(Arrays.asList(PhpCompletionTokens.mbStringInfoFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpCompletionTokens.mbStringInfoTypes;
                    }

                    if(Arrays.asList(PhpCompletionTokens.mbStringLanguageFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpCompletionTokens.mbStringLanguageElements;
                    }

                    if(Arrays.asList(PhpCompletionTokens.obHandlerFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpCompletionTokens.obHandlerElements;
                    }

                    if(Arrays.asList(PhpCompletionTokens.httpHeaderResponseFuncs).contains(funcName) && paramIndex == 0) {
                        String stringLiteral = parameters.getPosition().getText();
                        String stringPrefix = stringLiteral.substring(1, stringLiteral.indexOf("IntellijIdeaRulezzz"));

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
                            resultPostfix = ": ";
                            resultPostfixAlt = " ";
                            resultPostfixExceptions = new String[] { "HTTP/1.0", "HTTP/1.1" };
                        }
                    }

                    if(resultElements == null)
                        return;

                    for(int i=0; i < resultElements.length; i++) {
                        String postfix = Arrays.asList(resultPostfixExceptions).contains(resultElements[i]) ? resultPostfixAlt : resultPostfix;
                        LookupElementBuilder builder = LookupElementBuilder.create(resultElements[i] + postfix)
                                .withCaseSensitivity(resultCaseSensitivity)
                                .withPresentableText(resultElements[i])
                                //.withTailText(resultPostfix, true)
                                .withBoldness(resultBold);

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
            }
        );
    }
}
