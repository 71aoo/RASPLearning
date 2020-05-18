package com.AgentTest.Hook.File;

import com.AgentTest.Check.FileCheck;
import com.AgentTest.Hook.Hook;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileInputStreamHook implements Hook {

    @Override
    public boolean isMethod(String className) {
        return "java/io/FileInputStream".equals(className);
    }

    public void hookMethod(CtClass ctClass){
        try {

            CtConstructor constructor = ctClass.getConstructor("(Ljava/io/File;)V");
            constructor.insertBefore("com.AgentTest.Hook.File.FileInputStreamHook.checkFile($1);");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkFile(File file) {

        if (file != null){

            if (!file.exists()){
                return;
            }
            HashMap<String, Object> params = new HashMap();

            params.put("path",file.getPath());

            String realPath;

            try {
                realPath = file.getCanonicalPath();
            } catch (IOException e) {
                realPath = file.getAbsolutePath();
            }

            params.put("realPath",realPath);

            if (realPath.endsWith(".class")){
                return;
            }

            FileCheck fileCheck = new FileCheck(params);

            fileCheck.readFile();

        }
    }
}
