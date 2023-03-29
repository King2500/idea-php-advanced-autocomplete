package net.king2500.plugins.PhpAdvancedAutoComplete.index;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.codeInsight.controlFlow.PhpControlFlowUtil;
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpCallInstruction;
import com.jetbrains.php.lang.psi.elements.*;
import net.king2500.plugins.PhpAdvancedAutoComplete.index.PhpInjectFileReference.RelativeMode;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpElementsUtil;
import net.king2500.plugins.PhpAdvancedAutoComplete.utils.PhpMetaUtil;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpMetaCompletionCollector extends PhpControlFlowUtil.PhpRecursiveInstructionProcessor {
    private static final String COMPLETION_NAME = "xAdvancedCompletion";
    private final Map<String, PhpMetaCompletion> map;

    PhpMetaCompletionCollector(Map<String, PhpMetaCompletion> map) {
        this.map = map;
    }

    @Override
    public boolean processPhpCallInstruction(PhpCallInstruction instruction) {
        FunctionReference targetFunctionReference = PhpMetaUtil.getMetaFunctionReferenceWithName(instruction, COMPLETION_NAME);
        if (targetFunctionReference == null) {
            return true;
        }

        PsiElement[] parameters = instruction.getFunctionReference().getParameters();
        if (parameters.length < 2) {
            return true;
        }

        String fqn = PhpElementsUtil.getFQN(targetFunctionReference);
        if (fqn != null) {
            int argumentIndex = PhpMetaUtil.getArgumentIndexValue(parameters[1]);
            PhpMetaCompletion metaCompletion = this.getMetaCompletion(parameters[2], argumentIndex);

            if (metaCompletion != null) {
                this.map.putIfAbsent(fqn + ":" + argumentIndex, metaCompletion);
            }
        }

        return super.processPhpCallInstruction(instruction);
    }

    private PhpMetaCompletion getMetaCompletion(PsiElement parameter, int argumentIndex) {
        if (argumentIndex < 0) {
            return null;
        }

        if (parameter instanceof StringLiteralExpression) {
            String completionList = ((StringLiteralExpression)parameter).getContents();
            return new PhpMetaCompletion(argumentIndex, completionList);
        }

        return null;
    }
}
