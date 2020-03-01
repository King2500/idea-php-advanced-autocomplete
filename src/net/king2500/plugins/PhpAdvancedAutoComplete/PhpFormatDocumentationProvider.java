package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.intellij.lang.Language;
import com.intellij.lang.documentation.DocumentationProviderEx;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.light.LightElement;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.Statement;
import io.netty.util.internal.StringUtil;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpElementsUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpFormatDocumentationProvider extends DocumentationProviderEx {

    private static final String PHP_FORMAT_URL = "https://www.php.net/manual/en/function.{functionName}.php";

    @Override
    public @Nullable List<String> getUrlFor(PsiElement element, PsiElement originalElement) {
        if (!(element instanceof FormatTokenDocElement)) {
            return null;
        }
        String functionName = ((FormatTokenDocElement)element).getFunctionName();
        return Collections.singletonList(PHP_FORMAT_URL.replace("{functionName}", functionName));
    }

    @Override
    public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (!(element instanceof FormatTokenDocElement)) {
            return null;
        }

        String tokenText = ((FormatTokenDocElement)element).getTokenText();
        String functionName = ((FormatTokenDocElement)element).getFunctionName();

        if (Arrays.asList(PhpCompletionTokens.scanFormatFuncs).contains(functionName + ":1")) {
            return PhpCompletionTokens.scanFormatTokensDoc.getOrDefault(tokenText, "");
        }
        else {
            return PhpCompletionTokens.formatTokensDoc.getOrDefault(tokenText, "");
        }
    }

    @Override
    public @Nullable PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement psiElement) {
        if (!(object instanceof String)) {
            return null;
        }

        String fqn = getCallToFormatFunc(psiElement);
        if (StringUtil.isNullOrEmpty(fqn)) {
            return null;
        }

        String tokenText = (String)object;

        if ("%%".equals(tokenText)) {
            tokenText = "%";
        }
        else if (!"%".equals(tokenText)) {
            tokenText = StringUtils.strip((String)object, "%");
        }
        String functionName = StringUtils.strip(fqn, "\\");
        return new FormatTokenDocElement(psiManager, psiElement.getLanguage(), tokenText, functionName);
    }

    private String getCallToFormatFunc(PsiElement psiElement) {
        FunctionReference function = PhpPsiUtil.getParentByCondition(psiElement, true, FunctionReference.INSTANCEOF, Statement.INSTANCEOF);
        if (function == null) {
            return null;
        }
        return PhpElementsUtil.resolveFqn(function);
    }

    private static class FormatTokenDocElement extends LightElement {

        private final String tokenText;
        private final String functionName;

        protected FormatTokenDocElement(@NotNull final PsiManager manager, @NotNull final Language language, @NotNull final String tokenText, @NotNull final String functionName) {
            super(manager, language);
            this.tokenText = tokenText;
            this.functionName = functionName;
        }

        public String getTokenText() {
            return tokenText;
        }

        @Override
        public String getText() {
            return functionName;
        }

        public String getFunctionName() {
            return functionName;
        }

        @Override
        public String toString() {
            return "FormatTokenDocElement for " + tokenText;
        }
    }
}
