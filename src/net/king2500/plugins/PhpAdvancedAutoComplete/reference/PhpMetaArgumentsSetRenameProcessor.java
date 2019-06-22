package net.king2500.plugins.PhpAdvancedAutoComplete.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.PhpNamespace;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpMetaArgumentsSetRenameProcessor extends RenamePsiElementProcessor {
    @Override
    public boolean canProcessElement(@NotNull PsiElement psiElement) {
        if (!(psiElement instanceof StringLiteralExpression)) {
            return false;
        }
        FunctionReference function = PsiTreeUtil.getParentOfType(psiElement, FunctionReference.class);

        return function != null && "registerArgumentsSet".equals(function.getName());
    }

    @Override
    public @NotNull Collection<PsiReference> findReferences(@NotNull PsiElement element) {
        final Collection<PsiReference> references = super.findReferences(element);

        PhpNamespace root = PsiTreeUtil.getParentOfType(element, PhpNamespace.class);
        if (root == null || !"PHPSTORM_META".equals(root.getName())) {
            return references;
        }

        Collection<ParameterList> arguments = PsiTreeUtil.findChildrenOfType(root, ParameterList.class);
        for (ParameterList args : arguments) {
            PsiElement parent = args.getParent();
            if (!(parent instanceof FunctionReference) || (!"registerArgumentsSet".equals(((FunctionReference)parent).getName()) && !"argumentsSet".equals(((FunctionReference)parent).getName()))) {
                continue;
            }

            StringLiteralExpression arg0 = PsiTreeUtil.findChildOfType(args, StringLiteralExpression.class);
            if (arg0 == null) {
                continue;
            }

            if (arg0.getContents().equals(((StringLiteralExpression)element).getContents())) {
                references.add(new PhpMetaArgumentsSetReference(arg0));
            }
        }

        return references;
    }
}
