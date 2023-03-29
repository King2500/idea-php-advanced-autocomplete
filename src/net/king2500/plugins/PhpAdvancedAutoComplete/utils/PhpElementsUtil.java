package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveResult;
import com.intellij.util.ObjectUtils;
import com.jetbrains.php.codeInsight.PhpCodeInsightUtil;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.*;
import net.king2500.plugins.PhpAdvancedAutoComplete.PhpCompletionTokens;
import one.util.streamex.StreamEx;
import org.jetbrains.annotations.Nullable;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpElementsUtil {

    public static int getParameterIndex(PsiElement paramElement) {
        int index = 0;
        PsiElement element = paramElement;

        while (element != null && element.getPrevSibling() != null) {
            String elementClass = element.getPrevSibling().getClass().getSimpleName();

            if (elementClass.equals("LeafPsiElement")) {
                index++;
            }

            element = element.getPrevSibling();
        }

        return index;
    }

    public static String getCanonicalFuncName(PsiElement caller) {
        if (caller.getReference() instanceof MethodReference) {
            return getMethodName(caller);
        }
        else if (caller.getReference() instanceof FunctionReference) {
            return getFuncName(caller);
        }
        else if (caller instanceof NewExpression) {
            return getClassConstructName(caller);
        }

        return null;
    }

    private static String getFuncName(PsiElement caller) {

        PsiReference psiReference = caller.getReference();

        if (psiReference == null) {
            return null;
        }

        PsiElement resolvedReference = psiReference.resolve();

        if (!(resolvedReference instanceof Function)) {
            return null;
        }

        Function function = (Function)resolvedReference;

        return function.getName();
    }

    private static String getMethodName(PsiElement caller) {

        PsiReference psiReference = caller.getReference();

        if (psiReference == null) {
            return null;
        }

        PsiElement resolvedReference = psiReference.resolve();

        if (!(resolvedReference instanceof Method)) {
            return null;
        }

        Method method = (Method)resolvedReference;
        PhpClass methodClass = method.getContainingClass();

        if (methodClass == null) {
            return null;
        }

        String className = methodClass.getName();
        String methodName = method.getName();

        return className + "::" + methodName;
    }

    private static String getClassConstructName(PsiElement caller) {

        PsiElement[] children = caller.getChildren();

        if (children.length == 0) {
            return null;
        }

        PsiReference psiReference = children[0].getReference();

        if (!(psiReference instanceof ClassReference)) {
            return null;
        }

        PsiElement resolvedReference = psiReference.resolve();

        if (!(resolvedReference instanceof Method)) {
            return null;
        }

        Method method = (Method)resolvedReference;

        if (!method.getName().equals("__construct")) {
            return null;
        }

        PhpClass methodClass = method.getContainingClass();

        if (methodClass == null) {
            return null;
        }

        String className = methodClass.getName();
        return className + "::__construct";
    }


    public static boolean isFormatFunction(String fqn) {
        return StreamEx.of(PhpCompletionTokens.formatFuncFqns).has(fqn);
    }

    @Nullable
    public static String resolveFqn(@Nullable FunctionReference reference) {
        Function function = reference != null ? ObjectUtils.tryCast(reference.resolve(), Function.class) : null;
        return function != null ? function.getFQN() : null;
    }

    @Nullable
    public static Function getFunction(PsiElement position) {
        FunctionReference functionReference = PhpPsiUtil.getParentByCondition(position, true, FunctionReference.INSTANCEOF, Statement.INSTANCEOF);
        if (functionReference != null) {
            return getFunction(functionReference);
        } else {
            NewExpression newExpression = PhpPsiUtil.getParentByCondition(position, true, NewExpression.INSTANCEOF, Statement.INSTANCEOF);
            if (newExpression != null) {
                ClassReference classReference = newExpression.getClassReference();
                if (classReference != null) {
                    return getFunction(classReference);
                }
            }

            return null;
        }
    }

    @Nullable
    private static Function getFunction(PhpReference reference) {
        return StreamEx.of(reference.multiResolve(false)).map(ResolveResult::getElement).select(Function.class).findFirst().orElse(null);
    }

    @Nullable
    public static String getFQN(FunctionReference targetFunctionReference) {
        if (targetFunctionReference instanceof MethodReference) {
            PhpReference classReference = ObjectUtils.tryCast(((MethodReference)targetFunctionReference).getClassReference(), PhpReference.class);
            if (classReference != null) {
                return PhpCodeInsightUtil.getImmediateFQN(classReference) + "." + targetFunctionReference.getName();
            }
        }

        return PhpCodeInsightUtil.getImmediateFQN(targetFunctionReference);
    }
}
