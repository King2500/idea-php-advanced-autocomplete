package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.jetbrains.php.lang.psi.elements.*;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 07.07.13
 * Time: 21:08
 */
public class PhpHelper {

    public static int getParameterIndex(PsiElement paramElement) {
        int index = 0;
        PsiElement element = paramElement;

        while(element != null && element.getPrevSibling() != null) {
            String elementClass = element.getPrevSibling().getClass().getSimpleName();

            if(elementClass.equals("LeafPsiElement")) {
                index++;
            }

            element = element.getPrevSibling();
        }

        return index;
    }

    public static String getCanonicalFuncName(PsiElement caller) {
        if(caller.getReference() instanceof MethodReference) {
            return getMethodName(caller);
        }
        else if(caller.getReference() instanceof FunctionReference) {
            return getFuncName(caller);
        }
        else if(caller instanceof NewExpression) {
            return getClassConstructName(caller);
        }

        return null;
    }

    private static String getFuncName(PsiElement caller) {

        PsiReference psiReference = caller.getReference();

        if(psiReference == null)
            return null;

        PsiElement resolvedReference = psiReference.resolve();

        if(!(resolvedReference instanceof Function))
            return null;

        Function function = (Function)resolvedReference;

        return function.getName();
    }

    private static String getMethodName(PsiElement caller) {

        PsiReference psiReference = caller.getReference();

        if(psiReference == null)
            return null;

        PsiElement resolvedReference = psiReference.resolve();

        if(!(resolvedReference instanceof Method))
            return null;

        Method method = (Method)resolvedReference;
        PhpClass methodClass = method.getContainingClass();

        if(methodClass == null)
            return null;

        String className = methodClass.getName();
        String methodName = method.getName();

        return className + "::" + methodName;
    }

    private static String getClassConstructName(PsiElement caller) {

        PsiElement[] children = caller.getChildren();

        if(children.length == 0)
            return null;

        PsiReference psiReference = children[0].getReference();

        if(!(psiReference instanceof ClassReference))
            return null;

        PsiElement resolvedReference = psiReference.resolve();

        if(!(resolvedReference instanceof Method))
            return null;

        Method method = (Method)resolvedReference;

        if(!method.getName().equals("__construct"))
            return null;

        PhpClass methodClass = method.getContainingClass();

        if(methodClass == null)
            return null;

        String className = methodClass.getName();
        return className + "::__construct";
    }

}
