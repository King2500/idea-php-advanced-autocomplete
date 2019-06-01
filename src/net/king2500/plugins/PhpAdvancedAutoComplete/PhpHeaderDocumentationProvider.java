package net.king2500.plugins.PhpAdvancedAutoComplete;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.lang.Language;
import com.intellij.lang.documentation.DocumentationProviderEx;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.light.LightElement;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.Statement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpHeaderDocumentationProvider extends DocumentationProviderEx {

    private static final String MDN_URL_PREFIX = "https://developer.mozilla.org/docs/Web/HTTP/Headers/";
    private static final String HEADERS_DOC_JSON = "/com/intellij/ws/rest/client/headers/headers-doc.json";

    private static Map<String, String> httpHeaderDescriptions = null;

    @Override
    public @Nullable List<String> getUrlFor(PsiElement element, PsiElement originalElement) {
        if (!(element instanceof HeaderDocElement)) {
            return null;
        }
        String headerName = ((HeaderDocElement)element).getHeaderName();
        return Collections.singletonList(MDN_URL_PREFIX + headerName);
    }

    @Override
    public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (!(element instanceof HeaderDocElement)) {
            return null;
        }

        String headerName = ((HeaderDocElement)element).getHeaderName();
        StringBuilder buffer = new StringBuilder();

        buffer.append("<div class='definition'><pre><b>").append(headerName).append("</b>");
        String syntax = PhpCompletionTokens.httpHeaderResponseFieldsSyntax.getOrDefault(headerName, ": ");
        buffer.append(StringEscapeUtils.escapeHtml(syntax));
        buffer.append("</pre></div>");

        buffer.append("<div class='content'>");
        addHeaderDescription(buffer, headerName);
        buffer.append("</div>");

        return buffer.toString();
    }

    private void addHeaderDescription(StringBuilder buffer, String headerName) {
        String description = getHeaders().get(headerName);
        if (description == null) {
            return;
        }
        buffer.append(description);
    }

    @Override
    public @Nullable PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement psiElement) {
        if (!(object instanceof String)) {
            return null;
        }

        if (!isCallToHeaderFunc(psiElement)) {
            return null;
        }

        String headerName = StringUtils.strip((String)object, ": ");
        if (!isHeaderName(headerName)) {
            return null;
        }
        return new HeaderDocElement(psiManager, psiElement.getLanguage(), headerName);
    }

    private boolean isCallToHeaderFunc(PsiElement psiElement) {
        FunctionReference function = PhpPsiUtil.getParentByCondition(psiElement, true, FunctionReference.INSTANCEOF, Statement.INSTANCEOF);
        if (function == null) {
            return false;
        }
        return "header".equals(function.getName());
    }

    @Override
    public @Nullable PsiElement getCustomDocumentationElement(@NotNull Editor editor, @NotNull PsiFile file, @Nullable PsiElement contextElement) {
        if (!isCallToHeaderFunc(contextElement)) {
            return null;
        }

        if (!(contextElement instanceof StringLiteralExpression)) {
            contextElement = PhpPsiUtil.getParentByCondition(contextElement, true, StringLiteralExpression.INSTANCEOF);
        }

        if (contextElement instanceof StringLiteralExpression) {
            String contents = ((StringLiteralExpression)contextElement).getContents();
            if (!contents.contains(":")) {
                return null;
            }
            String headerName = StringUtils.substringBefore(contents, ":");
            if (headerName.isEmpty() || !isHeaderName(headerName)) {
                return null;
            }
            return new HeaderDocElement(contextElement.getManager(), contextElement.getLanguage(), headerName);
        }
        return null;
    }

    private static boolean isHeaderName(String headerName) {
        return Arrays.asList(PhpCompletionTokens.httpHeaderResponseFields).contains(headerName)
            || Arrays.asList(PhpCompletionTokens.httpHeaderRequestFields).contains(headerName);
    }

    @NotNull
    private static synchronized Map<String, String> getHeaders() {
        if (httpHeaderDescriptions == null) {
            httpHeaderDescriptions = readHeaderDescriptions();
        }

        return httpHeaderDescriptions;
    }

    @NotNull
    private static Map<String, String> readHeaderDescriptions() {
        Map<String, String> result = ContainerUtil.newHashMap();

        // Re-use docs from JB HTTP Client plugin
        InputStream stream = PhpHeaderDocumentationProvider.class.getResourceAsStream(HEADERS_DOC_JSON);

        try {
            String file = stream != null ? FileUtil.loadTextAndClose(stream) : "";
            if (StringUtil.isNotEmpty(file)) {
                JsonElement root = (new JsonParser()).parse(file);
                if (root.isJsonArray()) {
                    JsonArray array = root.getAsJsonArray();

                    for (JsonElement element : array) {
                        if (element.isJsonObject()) {
                            JsonObject obj = element.getAsJsonObject();
                            String name = getAsString(obj, "name");
                            if (!StringUtil.isNotEmpty(name)) {
                                continue;
                            }
                            String description = getAsString(obj, "descr");
                            if (!StringUtil.isNotEmpty(description)) {
                                continue;
                            }
                            result.put(name, description);
                        }
                    }
                }
            }
        } catch (IOException ignored) {
        }

        return result;
    }

    @NotNull
    private static String getAsString(@NotNull JsonObject obj, @NotNull String name) {
        JsonElement element = obj.get(name);
        return element != null && element.isJsonPrimitive() ? element.getAsString() : "";
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
        public String getText() {
            return headerName;
        }

        @Override
        public String toString() {
            return "HeaderDocElement for " + headerName;
        }
    }
}
