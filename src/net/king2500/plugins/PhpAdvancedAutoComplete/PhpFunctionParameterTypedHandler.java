package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.Statement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class PhpFunctionParameterTypedHandler extends TypedHandlerDelegate {
    @Override
    public @NotNull Result beforeCharTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file, @NotNull FileType fileType) {

        if (!(file instanceof PhpFile)) {
            return Result.CONTINUE;
        }

        if (c != '"') {
            return Result.CONTINUE;
        }

        int offset = editor.getCaretModel().getOffset();
        PsiDocumentManager.getInstance(project).commitDocument(editor.getDocument());
        PsiElement currElement = file.findElementAt(offset);

        if (currElement == null) {
            return Result.CONTINUE;
        }

        if (!(currElement.getParent() instanceof StringLiteralExpression)) {
            return Result.CONTINUE;
        }

        FunctionReference function = PhpPsiUtil.getParentByCondition(currElement, true, FunctionReference.INSTANCEOF, Statement.INSTANCEOF);
        if (function != null && "header".equals(function.getName())) {
            if (StringUtils.countMatches(((StringLiteralExpression) currElement.getParent()).getContents(), "\"") % 2 == 0) {
                // insert " behind caret: "<caret>"
                EditorModificationUtil.insertStringAtCaret(editor, "\"\"");
                editor.getCaretModel().moveCaretRelatively(-1, 0, false, false, false);
                PsiDocumentManager.getInstance(project).commitDocument(editor.getDocument());
                return Result.STOP;
            }
        }

        return Result.CONTINUE;
    }

    @Override
    public @NotNull Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        return super.charTyped(c, project, editor, file);
    }
}
