package com.AgentTest.Agent;

import com.AgentTest.Transform.MyTransform;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.jar.JarFile;

public class Agent {



    public static void premain(String Args, Instrumentation inst) {

        try {

            inst.appendToBootstrapClassLoaderSearch(new JarFile(getLocalJarPath()));

            MyTransform myTransform = new MyTransform();
            inst.addTransformer(myTransform,true);

            Class[] classes = inst.getAllLoadedClasses();
            for (Class clazz: classes){
                if (clazz.getName().equals("org.apache.catalina.core.ApplicationFilterChain")){
                    System.out.println("已找到");
                    inst.retransformClasses(clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void agentmain(String Args, Instrumentation inst) {

        try {

            inst.appendToBootstrapClassLoaderSearch(new JarFile(getLocalJarPath()));

            MyTransform myTransform = new MyTransform();
            inst.addTransformer(myTransform,true);

            Class[] classes = inst.getAllLoadedClasses();
            for (Class clazz: classes){
                if (clazz.getName().equals("apache.catalina.core.ApplicationFilterChain")){
                    System.out.println("已找到");
                    inst.retransformClasses(clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static String getLocalJarPath() {

        URL url = Agent.class.getProtectionDomain().getCodeSource().getLocation();
        String path = null;

        try {

            path = URLDecoder.decode(url.getFile().replace("+", "%2B"), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return path;
    }
}
