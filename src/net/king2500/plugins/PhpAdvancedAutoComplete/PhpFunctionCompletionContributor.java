package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.jetbrains.php.lang.parser.PhpElementTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class PhpFunctionCompletionContributor extends CompletionContributor {
    public PhpFunctionCompletionContributor() {
        PsiElementPattern.Capture<PsiElement> stringInFuncCall = PlatformPatterns.psiElement(PhpElementTypes.STRING)
            .withParent(PlatformPatterns.psiElement(PhpElementTypes.PARAMETER_LIST)
                    .withParent(PlatformPatterns.psiElement(PhpElementTypes.FUNCTION_CALL)
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

                    String funcCall = parameters.getPosition().getParent().getParent().getParent().getText();
                    String funcName = funcCall.substring(0, funcCall.indexOf('(')).trim();
                    String[] resultElements = {};
                    int paramIndex = getParameterIndex(parameters.getPosition().getParent());

                    if(Arrays.asList(PhpFunctionCompletionTokens.iniFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpFunctionCompletionTokens.iniElements;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.dbConnectFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpFunctionCompletionTokens.dbConnectElements;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.phpExtensionFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpFunctionCompletionTokens.phpExtensionElements;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.fileModeFuncs).contains(funcName) && paramIndex == 1) {
                        resultElements = PhpFunctionCompletionTokens.fileModeElements;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.dateFormatFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpFunctionCompletionTokens.dateFormatTokens;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.htmlCharSetFuncs).contains(funcName) && paramIndex == 2) {
                        resultElements = PhpFunctionCompletionTokens.htmlCharSets;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.mbStringEncodingFuncs).contains(funcName + ":" + paramIndex)) {
                        resultElements = PhpFunctionCompletionTokens.mbStringEncodingElements;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.mbStringInfoFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpFunctionCompletionTokens.mbStringInfoTypes;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.mbStringLanguageFuncs).contains(funcName) && paramIndex == 0) {
                        resultElements = PhpFunctionCompletionTokens.mbStringLanguageElements;
                    }

                    if(Arrays.asList(PhpFunctionCompletionTokens.httpHeaderResponseFuncs).contains(funcName) && paramIndex == 0) {
                        String stringLiteral = parameters.getPosition().getText();
                        String stringPrefix = stringLiteral.substring(1, stringLiteral.indexOf("IntellijIdeaRulezzz"));

                        if(stringPrefix.startsWith("Allow:")) {
                            resultElements = PhpFunctionCompletionTokens.httpMethods;
                        }
                        else if(stringPrefix.startsWith("Accept-Ranges:") || stringPrefix.startsWith("Content-Range:")) {
                            resultElements = PhpFunctionCompletionTokens.httpRangeTypes;
                        }
                        else if(stringPrefix.startsWith("Cache-Control:")) {
                            resultElements = PhpFunctionCompletionTokens.httpCacheControlDirectives;
                        }
                        else if(stringPrefix.startsWith("Connection:")) {
                            resultElements = PhpFunctionCompletionTokens.httpConnectionOptions;
                        }
                        else if(stringPrefix.startsWith("Content-Encoding:")) {
                            resultElements = PhpFunctionCompletionTokens.httpEncodingTokens;
                        }
                        else if(stringPrefix.startsWith("Content-Language:")) {
                            resultElements = PhpFunctionCompletionTokens.isoLanguageCodes;
                        }
                        else if(stringPrefix.startsWith("Content-Location:") || stringPrefix.startsWith("Location:")) {
                            // TODO: possible locations (.php or .html)
                        }
                        else if(stringPrefix.startsWith("Content-Disposition:")) {
                            resultElements = PhpFunctionCompletionTokens.httpContentDispositionTokens;
                        }
                        else if(stringPrefix.startsWith("Content-Type:") && !stringPrefix.contains(";")) {
                            resultElements = PhpFunctionCompletionTokens.mimeTypes;
                        }
                        else if(stringPrefix.startsWith("Content-Type:") && stringPrefix.trim().endsWith(";")) {
                            resultElements = prefixArray("charset=", PhpFunctionCompletionTokens.httpCharSets);
                        }
                        else if(stringPrefix.startsWith("Content-Type:") && stringPrefix.trim().endsWith("charset=")) {
                            resultElements = PhpFunctionCompletionTokens.httpCharSets;
                        }
                        else if(stringPrefix.startsWith("Pragma:")) {
                            resultElements = PhpFunctionCompletionTokens.httpPragmaDirectives;
                        }
                        else if(stringPrefix.startsWith("Proxy-Authenticate:") || stringPrefix.startsWith("WWW-Authenticate:")) {
                            resultElements = PhpFunctionCompletionTokens.httpAuthenticationTypes;
                        }
                        else if(stringPrefix.startsWith("Status:") || stringPrefix.startsWith("HTTP/1.0 ") || stringPrefix.startsWith("HTTP/1.1 ")) {
                            resultElements = PhpFunctionCompletionTokens.httpStatusCodes;

                            if(stringPrefix.startsWith("HTTP/1.1 ")) {
                                resultElements = concatArrays(resultElements, PhpFunctionCompletionTokens.httpStatusCodes11);
                            }
                        }
                        else if(stringPrefix.startsWith("Trailer:")) {
                            resultElements = PhpFunctionCompletionTokens.httpHeaderRequestFields;
                        }
                        else if(stringPrefix.startsWith("Transfer-Encoding:")) {
                            resultElements = PhpFunctionCompletionTokens.httpTransferEncodingValues;
                        }
                        else if(stringPrefix.startsWith("Vary:")) {
                            resultElements = concatArrays(new String[] {"*"}, PhpFunctionCompletionTokens.httpHeaderRequestFields);
                        }
                        else if(stringPrefix.startsWith("X-Frame-Options:")) {
                            resultElements = PhpFunctionCompletionTokens.httpXFrameOptions;
                        }
                        else if(stringPrefix.startsWith("Content-Security-Policy:") || stringPrefix.startsWith("X-Content-Security-Policy:") || stringPrefix.startsWith("X-WebKit-CSP:")) {
                            resultElements = PhpFunctionCompletionTokens.httpCSP;
                        }
                        else if(stringPrefix.startsWith("X-Content-Type-Options:")) {
                            resultElements = PhpFunctionCompletionTokens.httpXContentTypeOptions;
                        }
                        else if(stringPrefix.startsWith("X-UA-Compatible:")) {
                            resultElements = PhpFunctionCompletionTokens.httpXUACompatibleValues;
                        }
                        else if(stringPrefix.startsWith("X-Robots-Tag:")) {
                            resultElements = PhpFunctionCompletionTokens.httpXRobotsTagDirectives;
                        }
                        else if(!stringPrefix.contains(":")) {
                            resultElements = PhpFunctionCompletionTokens.httpHeaderResponseFields;
                        }
                    }

                    for(int i=0; i < resultElements.length; i++) {
                        resultSet.addElement(LookupElementBuilder.create(resultElements[i]));
                    }
                }

                private int getParameterIndex(PsiElement paramElement) {
                    int index = 0;
                    PsiElement element = paramElement;

                    while(element.getPrevSibling() != null) {
                        String elementClass = element.getPrevSibling().getClass().getSimpleName();

                        if(elementClass.equals("LeafPsiElement")) {
                            index++;
                        }

                        element = element.getPrevSibling();
                    }

                    return index;
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
