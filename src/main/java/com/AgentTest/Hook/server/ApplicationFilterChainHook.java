package com.AgentTest.Hook.server;

import com.AgentTest.Check.ServletRequestCheck;
import com.AgentTest.Hook.Hook;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @Author: Playwi0
 * @Date: 2020/5/8 15:49
 * @Version 1.0
 */
public class ApplicationFilterChainHook implements Hook {

    @Override
    public boolean isMethod(String className) {
        return "org/apache/catalina/core/ApplicationFilterChain".equals(className);
    }


    public void hookMethod(CtClass ctClass){

        System.out.println("已经hook");

        try {

            CtMethod doFilter = ctClass.getMethod("doFilter", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;)V");

//            System.out.println(doFilter.getMethodInfo());
            doFilter.insertBefore("com.AgentTest.Hook.server.ApplicationFilterChainHook.checkRequest($$);");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void checkRequest(Object request,Object response){

        ServletRequestCheck.checkRequest(request, response);
    }

}
