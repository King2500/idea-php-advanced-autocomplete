package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.lang.Language;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.light.LightElement;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.Statement;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpHeaderDocumentationProvider implements DocumentationProvider {

    @Override
    public @Nullable String getQuickNavigateInfo(PsiElement psiElement, PsiElement psiElement1) {
        return null;
    }

    @Override
    public @Nullable List<String> getUrlFor(PsiElement psiElement, PsiElement psiElement1) {
        return null;
    }

    @Override
    public @Nullable String generateDoc(PsiElement psiElement, @Nullable PsiElement psiElement1) {
        if (!(psiElement instanceof HeaderDocElement)) {
            return null;
        }


        String headerName = ((HeaderDocElement)psiElement).getHeaderName();

        if (!PhpCompletionTokens.httpHeaderFieldsDocumentation.containsKey(headerName)) {
            return null;
        }
        return PhpCompletionTokens.httpHeaderFieldsDocumentation.get(headerName);
    }

    @Override
    public @Nullable PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object o, PsiElement psiElement) {
        if (!(o instanceof String)) {
            return null;
        }

        FunctionReference function = PhpPsiUtil.getParentByCondition(psiElement, true, FunctionReference.INSTANCEOF, Statement.INSTANCEOF);
        if (function == null) {
            return null;
        }
        if (!"header".equals(function.getName())) {
            return null;
        }

        String headerName = StringUtils.strip((String)o, ": ");
        return new HeaderDocElement(psiManager, psiElement.getLanguage(), headerName);
    }

    @Override
    public @Nullable PsiElement getDocumentationElementForLink(PsiManager psiManager, String s, PsiElement psiElement) {
        return null;
    }


    private class HeaderDocElement extends LightElement {

        private final String headerName;

        protected HeaderDocElement(@NotNull final PsiManager manager, @NotNull final Language language, @NotNull final String headerName) {
            super(manager, language);
            this.headerName = headerName;
        }

        public String getHeaderName() {
            return headerName;
        }

        @Override
        public String toString() {
            return "HeaderDocElement for " + headerName;
        }
    }
}
