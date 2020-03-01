package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

import com.intellij.psi.PsiElement;
import com.intellij.util.ObjectUtils;
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpCallInstruction;
import com.jetbrains.php.lang.PhpLangUtil;
import com.jetbrains.php.lang.psi.elements.FunctionReference;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpMetaUtil {
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
        return PhpLangUtil.equalsClassNames(reference.getName(), name) && "\\PHPSTORM_META\\".equals(reference.getNamespaceName());
    }
}
