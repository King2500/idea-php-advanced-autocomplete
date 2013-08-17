package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 16.08.13
 * Time: 21:59
 */
public class GenericFileLookupElement extends LookupElement {
    private String fileName;
    private PsiFileSystemItem psiFile;
    private PsiElement psiElement = null;

    @Nullable
    private InsertHandler<LookupElement> insertHandler = null;

    public GenericFileLookupElement(String fileName, PsiFileSystemItem psiFile) {
        this.fileName = fileName;
        this.psiFile = psiFile;
    }

    public GenericFileLookupElement(String fileName, PsiFileSystemItem psiFile, PsiElement psiElement, InsertHandler<LookupElement> insertHandler) {
        this.fileName = fileName;
        this.psiFile = psiFile;
        this.insertHandler = insertHandler;
        this.psiElement = psiElement;
    }

    @NotNull
    @Override
    public String getLookupString() {
        return fileName;
    }

    @NotNull
    public Object getObject() {
        return this.psiElement != null ? this.psiElement : super.getObject();
    }

    public void handleInsert(InsertionContext context) {
        if (this.insertHandler != null) {
            this.insertHandler.handleInsert(context, this);
        }
    }

    public void renderElement(LookupElementPresentation presentation) {
        presentation.setItemText(getLookupString());
        presentation.setIcon(psiFile.getIcon(0));
        //presentation.setTypeText(VfsUtil.getRelativePath(psiFile.getContainingDirectory().getVirtualFile(), psiFile.getProject().getBaseDir(), '/'));
        //presentation.setTypeGrayed(true);
    }
}
