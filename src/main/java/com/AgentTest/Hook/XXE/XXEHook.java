package com.AgentTest.Hook.XXE;

import com.AgentTest.Check.XXECheck;
import com.AgentTest.Hook.Hook;
import javassist.CtClass;
import javassist.CtMethod;


public class XXEHook implements Hook {

    @Override
    public boolean isMethod(String className) {
        return "com/sun/org/apache/xerces/internal/impl/XMLEntityManager".equals(className);
    }

    public void hookMethod(CtClass ctClass) throws Exception {

        CtMethod expandSystemId = ctClass.getMethod("expandSystemId", "(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;");

        expandSystemId.insertBefore("com.AgentTest.Hook.XXE.XXEHook.checkXXE($1);");

    }

    public static void checkXXE(String expandSystemId) {

        if (expandSystemId != null){

            XXECheck.doCheck(expandSystemId);
        }
    }
}
