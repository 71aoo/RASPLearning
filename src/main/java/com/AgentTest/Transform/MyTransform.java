package com.AgentTest.Transform;

import com.AgentTest.Hook.Sql.SqlStatementHook;
import com.AgentTest.Hook.server.ApplicationFilterChainHook;
import javassist.*;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyTransform implements ClassFileTransformer {



    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        SqlStatementHook sstm = new SqlStatementHook();

        //获取sql执行语句
        if (sstm.isMethod(className)){

            ClassPool classPool = new ClassPool(true);

            try {

                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                sstm.hookSqlStatementMethod(ctClass);

                classfileBuffer = ctClass.toBytecode();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        ApplicationFilterChainHook chainHook = new ApplicationFilterChainHook();

        if (chainHook.isMethod(className)){

            try {
                ClassPool classPool = new ClassPool(true);

                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                chainHook.hookMethod(ctClass);

                classfileBuffer = ctClass.toBytecode();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return classfileBuffer;
    }

}
