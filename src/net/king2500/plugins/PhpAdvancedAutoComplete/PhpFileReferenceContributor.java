package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.elements.impl.NewExpressionImpl;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.FileHelper;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 16.08.13
 * Time: 21:11
 */
public class PhpFileReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar psiReferenceRegistrar) {
        psiReferenceRegistrar.registerReferenceProvider(
                PlatformPatterns.psiElement(StringLiteralExpression.class).withLanguage(PhpLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
                        if(!(psiElement.getContext() instanceof ParameterList)) {
                            return new PsiReference[0];
                        }

                        ParameterList parameterList = (ParameterList)psiElement.getContext();

                        if (parameterList == null || !(parameterList.getContext() instanceof FunctionReference || parameterList.getContext() instanceof MethodReference || parameterList.getContext() instanceof NewExpressionImpl)) {
                            return new PsiReference[0];
                        }

                        String funcName = PhpHelper.getCanonicalFuncName(psiElement.getParent().getParent());
                        int paramIndex = PhpHelper.getParameterIndex(psiElement);

                        if(Arrays.asList(PhpCompletionTokens.fileFuncs).contains(funcName + ":" + paramIndex + ":f")) {
                            return new PsiReference[]{ new GenericFileReference((StringLiteralExpression)psiElement, FileHelper.TYPE_FILE ) };
                        }
                        else if(Arrays.asList(PhpCompletionTokens.fileFuncs).contains(funcName + ":" + paramIndex + ":d")) {
                            return new PsiReference[]{ new GenericFileReference((StringLiteralExpression)psiElement, FileHelper.TYPE_DIR ) };
                        }
                        else if(Arrays.asList(PhpCompletionTokens.fileFuncs).contains(funcName + ":" + paramIndex)) {
                            return new PsiReference[]{ new GenericFileReference((StringLiteralExpression)psiElement, FileHelper.TYPE_ALL ) };
                        }

                        return new PsiReference[0];
                    }
                }
        );
    }
}
