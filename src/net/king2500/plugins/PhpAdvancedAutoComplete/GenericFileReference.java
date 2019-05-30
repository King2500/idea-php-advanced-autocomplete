package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Schulz <mail@king2500.net>
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
        Map<String, PsiFileSystemItem> filesByName = FileUtil.getRelativeFilesByName(getElement().getContainingFile(), fileType);

        return filesByName.get(fileName);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        List<LookupElement> results = new ArrayList<LookupElement>();

        Map<String, PsiFileSystemItem> filesByName = FileUtil.getRelativeFilesByName(getElement().getContainingFile(), fileType);
        for (Map.Entry<String, PsiFileSystemItem> entry : filesByName.entrySet()) {
            results.add(
                new GenericFileLookupElement(entry.getKey(), entry.getValue())
            );
        }

        return results.toArray();
    }
}
