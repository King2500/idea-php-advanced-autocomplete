package net.king2500.plugins.PhpAdvancedAutoComplete.reference;

import com.intellij.codeInsight.highlighting.HighlightUsagesHandlerBase;
import com.intellij.codeInsight.highlighting.HighlightUsagesHandlerFactoryBase;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.ObjectUtils;
import com.jetbrains.php.codeInsight.PhpCodeInsightUtil;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.Function;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.Statement;
import one.util.streamex.StreamEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpHighlightPackParametersUsagesHandlerFactory extends HighlightUsagesHandlerFactoryBase {
    @Override
    public @Nullable HighlightUsagesHandlerBase createHighlightUsagesHandler(@NotNull Editor editor, @NotNull PsiFile file, @NotNull PsiElement target) {

        ParameterList parameterList = PhpPsiUtil.getParentByCondition(target, true, ParameterList.INSTANCEOF, Statement.INSTANCEOF);
        if (parameterList == null) {
            return null;
        }

        FunctionReference functionCall = ObjectUtils.tryCast(parameterList.getParent(), FunctionReference.class);
        String fqn = resolveFqn(functionCall);
        if (!"\\pack".equals(fqn)) {
            return null;
        }

        PsiElement[] parameters = parameterList.getParameters();
        PsiElement selectedParameter = StreamEx.of(parameters).findFirst((p) -> p.getTextRange().containsOffset(editor.getCaretModel().getOffset())).orElse(null);
        if (selectedParameter == null) {
            return null;
        }

        int selectedIndex = PhpCodeInsightUtil.getParameterIndex(selectedParameter);
        if (selectedIndex < 0 || selectedIndex >= parameters.length) {
            return null;
        }
        return new PhpHighlightPackParametersUsagesHandler(editor, file, 0, selectedIndex, parameters);
    }

    @Nullable
    private static String resolveFqn(@Nullable FunctionReference reference) {
        Function function = reference != null ? ObjectUtils.tryCast(reference.resolve(), Function.class) : null;
        return function != null ? function.getFQN() : null;
    }
}
