package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.util.ObjectUtils;
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpCallInstruction;
import com.jetbrains.php.lang.PhpLangUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import org.jetbrains.annotations.NotNull;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpMetaUtil {
    private static final String META_FILENAME = ".phpstorm.meta.php";
    private static final String META_NAMESPACE_PREFIX = "\\PHPSTORM_META\\";

    public static boolean isMetaFilename(CharSequence fileName) {
        return StringUtil.equals(fileName, META_FILENAME);
    }

    public static @NotNull String getMemberFQN(@NotNull String memberName) {
        return META_NAMESPACE_PREFIX + memberName;
    }

    public static FunctionReference getMetaFunctionReferenceWithName(PhpCallInstruction instruction, String name) {
        FunctionReference reference = instruction.getFunctionReference();
        if (!metaFunctionWithName(reference, name)) {
            return null;
        } else {
            PsiElement[] parameters = reference.getParameters();
            return parameters.length < 1 ? null : ObjectUtils.tryCast(parameters[0], FunctionReference.class);
        }
    }

    private static boolean metaFunctionWithName(FunctionReference reference, String name) {
        return PhpLangUtil.equalsClassNames(reference.getName(), name) && META_NAMESPACE_PREFIX.equals(reference.getNamespaceName());
    }

    public static int getArgumentIndexValue(PsiElement argumentIndex) {
        try {
            return Integer.parseInt(argumentIndex.getText());
        } catch (NumberFormatException var2) {
            return -1;
        }
    }
}
