package net.king2500.plugins.PhpAdvancedAutoComplete.index;

import com.intellij.psi.PsiElement;
import com.intellij.util.ObjectUtils;
import com.jetbrains.php.codeInsight.PhpCodeInsightUtil;
import com.jetbrains.php.codeInsight.controlFlow.PhpControlFlowUtil;
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpCallInstruction;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import net.king2500.plugins.PhpAdvancedAutoComplete.index.PhpInjectFileReference.RelativeMode;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpMetaUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpInjectFileReferenceCollector extends PhpControlFlowUtil.PhpRecursiveInstructionProcessor {
    private static final String INJECT_FILE_REFERENCE_NAME = "xAdvancedInjectFileReference";
    private final Map<String, PhpInjectFileReference> map;

    PhpInjectFileReferenceCollector(Map<String, PhpInjectFileReference> map) {
        this.map = map;
    }

    @Override
    public boolean processPhpCallInstruction(PhpCallInstruction instruction) {
        FunctionReference targetFunctionReference = PhpMetaUtil.getMetaFunctionReferenceWithName(instruction, INJECT_FILE_REFERENCE_NAME);
        if (targetFunctionReference == null) {
            return true;
        }

        PsiElement[] parameters = instruction.getFunctionReference().getParameters();
        if (parameters.length < 2) {
            return true;
        }

        String fqn = getFQN(targetFunctionReference);
        if (fqn != null) {
            int argumentIndex = getArgumentIndexValue(parameters[1]);
            PhpInjectFileReference injectFileReference = this.getInjectFileReference(Arrays.copyOfRange(parameters, 2, parameters.length), argumentIndex);

            if (injectFileReference != null) {
                this.map.putIfAbsent(fqn, injectFileReference);
            }
        }

        return super.processPhpCallInstruction(instruction);
    }

    private PhpInjectFileReference getInjectFileReference(PsiElement[] parameters, int argumentIndex) {
        if (argumentIndex < 0) {
            return null;
        }

        RelativeMode mode = RelativeMode.AUTO;
        if (parameters.length > 0) {
            if (parameters[0] instanceof StringLiteralExpression) {
                String relativeString = ((StringLiteralExpression)parameters[0]).getContents();
                if ("/".equals(relativeString)) {
                    mode = RelativeMode.TOP_LEVEL;
                }
                else if (".".equals(relativeString)) {
                    mode = RelativeMode.CURRENT_FILE;
                }
                if (parameters.length == 1) {
                    return new PhpInjectFileReference(argumentIndex, mode);
                }
            }
        }

        if (parameters.length > 1) {
            if (parameters[1] instanceof StringLiteralExpression) {
                String prefixString = ((StringLiteralExpression)parameters[1]).getContents();
                return new PhpInjectFileReference(argumentIndex, mode, prefixString);
            }
        }

        return new PhpInjectFileReference(argumentIndex);
    }

    @Nullable
    private static String getFQN(FunctionReference targetFunctionReference) {
        if (targetFunctionReference instanceof MethodReference) {
            PhpReference classReference = ObjectUtils.tryCast(((MethodReference)targetFunctionReference).getClassReference(), PhpReference.class);
            if (classReference != null) {
                return PhpCodeInsightUtil.getImmediateFQN(classReference) + "." + targetFunctionReference.getName();
            }
        }

        return PhpCodeInsightUtil.getImmediateFQN(targetFunctionReference);
    }

    private static int getArgumentIndexValue(PsiElement argumentIndex) {
        try {
            return Integer.parseInt(argumentIndex.getText());
        } catch (NumberFormatException var2) {
            return -1;
        }
    }
}
