package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.*;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.FileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 16.08.13
 * Time: 21:29
 */
public class GenericFileReference extends PsiReferenceBase<PsiElement> implements PsiReference {

    private String fileName;
    private int fileType;

    public GenericFileReference(@NotNull StringLiteralExpression element, int type) {
        super(element);

        fileName = element.getText().substring(
                element.getValueRange().getStartOffset(),
                element.getValueRange().getEndOffset()
        );

        fileType = type;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        Map<String, PsiFileSystemItem> filesByName = FileHelper.getRelativeFilesByName(getElement().getContainingFile(), fileType);

        return filesByName.get(fileName);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        List<LookupElement> results = new ArrayList<LookupElement>();

        Map<String, PsiFileSystemItem> filesByName = FileHelper.getRelativeFilesByName(getElement().getContainingFile(), fileType);
        for (Map.Entry<String, PsiFileSystemItem> entry : filesByName.entrySet()) {
            results.add(
                   new GenericFileLookupElement(entry.getKey(), entry.getValue())
            );
        }

        return results.toArray();
    }
}
