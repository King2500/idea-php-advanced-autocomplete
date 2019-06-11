package net.king2500.plugins.PhpAdvancedAutoComplete.reference;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.PhpNamespace;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpElementsUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpMetaArgumentsSetReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {
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
                        return PsiReference.EMPTY_ARRAY;
                    }

                    ParameterList parameterList = (ParameterList) psiElement.getContext();

                    if (!(parameterList.getContext() instanceof FunctionReference)) {
                        return PsiReference.EMPTY_ARRAY;
                    }

                    String funcName = PhpElementsUtil.getCanonicalFuncName(psiElement.getParent().getParent());
                    if (funcName == null || !funcName.equals("argumentsSet")) {
                        return PsiReference.EMPTY_ARRAY;
                    }

                    PhpNamespace root = PsiTreeUtil.getParentOfType(psiElement, PhpNamespace.class);
                    if (root == null || !"PHPSTORM_META".equals(root.getName())) {
                        return PsiReference.EMPTY_ARRAY;
                    }

                    return new PsiReference[] { new PhpMetaArgumentsSetReference((StringLiteralExpression)psiElement) };
                }
            }
        );
    }
}
