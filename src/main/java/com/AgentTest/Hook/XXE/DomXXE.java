package com.AgentTest.Hook.XXE;

import com.AgentTest.Hook.Hook;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.reflect.Method;

public class DomXXE implements Hook {

    private static final String Feature = "http://apache.org/xml/features/disallow-doctype-decl";

    @Override
    public boolean isMethod(String className) {
        return "com/sun/org/apache/xerces/internal/parsers/DOMParser".equals(className);
    }

    public void hookMethod(CtClass ctClass) throws Exception {

        CtMethod parse = ctClass.getMethod("parse","(Lorg/xml/sax/InputSource;)V");

        parse.insertBefore("com.AgentTest.Hook.XXE.DomXXE.setFeature($0);");

    }

    public static void setFeature(Object parse) throws Exception {

        System.out.println("reflection ok");

        if (parse != null){

            Method setFeature = parse.getClass().getMethod("setFeature", String.class, boolean.class);

            setFeature.setAccessible(true);

            setFeature.invoke(parse, Feature, true);

        }
    }

}
