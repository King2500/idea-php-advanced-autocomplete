package net.king2500.plugins.PhpAdvancedAutoComplete.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.PhpNamespace;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpMetaArgumentsSetReference extends PsiReferenceBase<PsiElement> implements PsiReference {

    private final String myArgumentsSet;

    public PhpMetaArgumentsSetReference(@NotNull StringLiteralExpression element) {
        super(element, element.getValueRange());

        myArgumentsSet = element.getText().substring(
            element.getValueRange().getStartOffset(),
            element.getValueRange().getEndOffset()
        );
    }

    @Override
    public @Nullable PsiElement resolve() {
        PhpNamespace root = PsiTreeUtil.getParentOfType(this.myElement, PhpNamespace.class);
        if (root == null || !"PHPSTORM_META".equals(root.getName())) {
            return null;
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

            if (arg0.getContents().equals(myArgumentsSet)) {
                return arg0;
            }
        }

        return null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Collection<String> argumentsSets = new ArrayList<>();

        PhpNamespace root = PsiTreeUtil.getParentOfType(this.myElement, PhpNamespace.class);
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
    public boolean isSoft() {
        return true;
    }
}
