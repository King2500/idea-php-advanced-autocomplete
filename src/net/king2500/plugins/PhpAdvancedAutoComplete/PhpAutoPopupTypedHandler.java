package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.ObjectUtils;
import com.jetbrains.php.completion.PhpCompletionUtil;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.Statement;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpElementsUtil;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.StringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpAutoPopupTypedHandler extends TypedHandlerDelegate {
    @NotNull
    @Override
    public Result checkAutoPopup(char charTyped, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {

        if (!(file instanceof PhpFile)) {
            return Result.CONTINUE;
        }

        if (charTyped != '%') {
            return Result.CONTINUE;
        }

        int offset = editor.getCaretModel().getOffset();
        PsiElement psiElement = file.findElementAt(offset);

        ParameterList parameterList = PhpPsiUtil.getParentByCondition(psiElement, true, ParameterList.INSTANCEOF, Statement.INSTANCEOF);
        if (parameterList != null) {
            FunctionReference functionCall = ObjectUtils.tryCast(parameterList.getParent(), FunctionReference.class);
            String fqn = PhpElementsUtil.resolveFqn(functionCall);

            if (/*charTyped == '%' &&*/ PhpElementsUtil.isFormatFunction(fqn)) {
                if (StringUtil.getPrecedingCharNum(editor.getDocument().getCharsSequence(), offset, '%') % 2 == 0) {
                    PhpCompletionUtil.showCompletion(editor);
                }
            }
        }

        return Result.CONTINUE;
    }

}