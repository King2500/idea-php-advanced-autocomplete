package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.codeInsight.completion.CompletionPhase;
import com.intellij.codeInsight.completion.CompletionProgressIndicator;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.completion.impl.CompletionServiceImpl;
import com.intellij.codeInsight.editorActions.CompletionAutoPopupHandler;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.ide.PowerSaveMode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.jetbrains.php.lang.psi.PhpFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PhpAutoPopupSpaceTypedHandler extends TypedHandlerDelegate {
    @Override
    public Result checkAutoPopup(char charTyped, Project project, Editor editor, PsiFile file) {

        if (!(file instanceof PhpFile)) {
            return TypedHandlerDelegate.Result.CONTINUE;
        }

//        scheduleAutoPopup(project, editor, null);
//
//        if ((charTyped != ' ')) {
//            return TypedHandlerDelegate.Result.STOP;
//        }

        return TypedHandlerDelegate.Result.CONTINUE;
    }

    @Override
    public Result charTyped(char c, Project project, @NotNull Editor editor, @NotNull PsiFile file) {

        if (!(file instanceof PhpFile)) {
            return TypedHandlerDelegate.Result.CONTINUE;
        }

        if ((c != ' ')) {
            return TypedHandlerDelegate.Result.CONTINUE;
        }

        PsiElement psiElement = file.findElementAt(editor.getCaretModel().getOffset()-2);
        if (psiElement == null || !(PlatformPatterns.psiElement(PhpTokenTypes.STRING_LITERAL).accepts(psiElement) || PlatformPatterns.psiElement(PhpTokenTypes.STRING_LITERAL_SINGLE_QUOTE).accepts(psiElement))) {
            return TypedHandlerDelegate.Result.CONTINUE;
        }

        scheduleAutoPopup(project, editor, null);

        return TypedHandlerDelegate.Result.CONTINUE;
    }

    /**
     * PhpTypedHandler.scheduleAutoPopup but use SMART since BASIC is blocked
     */
    public void scheduleAutoPopup(final Project project, final Editor editor, @Nullable final Condition<PsiFile> condition) {
        if (ApplicationManager.getApplication().isUnitTestMode() && !CompletionAutoPopupHandler.ourTestingAutopopup) {
            return;
        }

        if (!CodeInsightSettings.getInstance().AUTO_POPUP_COMPLETION_LOOKUP) {
            return;
        }
        if (PowerSaveMode.isEnabled()) {
            return;
        }

        if (!CompletionServiceImpl.isPhase(CompletionPhase.CommittingDocuments.class, CompletionPhase.NoCompletion.getClass())) {
            return;
        }

        final CompletionProgressIndicator currentCompletion = CompletionServiceImpl.getCompletionService().getCurrentCompletion();
        if (currentCompletion != null) {
            currentCompletion.closeAndFinish(true);
        }

        final CompletionPhase.CommittingDocuments phase = new CompletionPhase.CommittingDocuments(null, editor);
        CompletionServiceImpl.setCompletionPhase(phase);

        CompletionAutoPopupHandler.runLaterWithCommitted(project, editor.getDocument(), new Runnable() {
            @Override
            public void run() {
                CompletionAutoPopupHandler.invokeCompletion(CompletionType.BASIC, true, project, editor, 0, false);
            }
        });
    }

}
