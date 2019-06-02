package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.openapi.util.Condition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.elements.impl.NewExpressionImpl;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpElementsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpFileReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar psiReferenceRegistrar) {
        psiReferenceRegistrar.registerReferenceProvider(
            PlatformPatterns
                .psiElement(StringLiteralExpression.class)
                .withParent(ParameterList.class)
                .withLanguage(PhpLanguage.INSTANCE),
            new PsiReferenceProvider() {
                @NotNull
                @Override
                public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
                    if (!(psiElement.getContext() instanceof ParameterList)) {
                        return new PsiReference[0];
                    }

                    ParameterList parameterList = (ParameterList) psiElement.getContext();

                    if (!(parameterList.getContext() instanceof FunctionReference || parameterList.getContext() instanceof NewExpressionImpl)) {
                        return new PsiReference[0];
                    }

                    String funcName = PhpElementsUtil.getCanonicalFuncName(psiElement.getParent().getParent());
                    int paramIndex = PhpElementsUtil.getParameterIndex(psiElement);

                    if (funcName == null) {
                        return new PsiReference[0];
                    }

                    if (Arrays.asList(PhpCompletionTokens.fileFuncs).contains(funcName + ":" + paramIndex + ":d")) {
                        return (new FileReferenceSet(psiElement) {
                            @Override
                            protected Condition<PsiFileSystemItem> getReferenceCompletionFilter() {
                                return FileReferenceSet.DIRECTORY_FILTER;
                            }
                        }).getAllReferences();
                    }
                    else if (Arrays.asList(PhpCompletionTokens.fileFuncs).contains(funcName + ":" + paramIndex)) {
                        return (new FileReferenceSet(psiElement)).getAllReferences();
                    }

                    String text = ((StringLiteralExpression) psiElement).getContents();
                    String prefix1 = "Location: /";
                    String prefix2 = "Content-Location: /";

                    if (funcName.equals("header")) {
                        if (text.startsWith(prefix1)) {
                            return getFileReferenceAfterPrefix(psiElement, prefix1);
                        }
                        if (text.startsWith(prefix2)) {
                            return getFileReferenceAfterPrefix(psiElement, prefix2);
                        }
                    }

                    return new PsiReference[0];
                }

                @NotNull
                private PsiReference[] getFileReferenceAfterPrefix(@NotNull PsiElement psiElement, String prefix) {
                    FileReferenceSet referenceSet = new FileReferenceSet(((StringLiteralExpression) psiElement).getContents(), psiElement, prefix.length() + 1, null, false) {
                        @Override
                        protected boolean isUrlEncoded() {
                            return true;
                        }

                        @Override
                        public boolean isEndingSlashNotAllowed() {
                            return false;
                        }
                    };
                    referenceSet.addCustomization(FileReferenceSet.DEFAULT_PATH_EVALUATOR_OPTION, FileReferenceSet.ABSOLUTE_TOP_LEVEL);
                    return referenceSet.getAllReferences();
                }
            }
        );
    }
}
