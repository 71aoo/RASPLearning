package com.AgentTest.Hook.File;

import com.AgentTest.Check.FileCheck;
import com.AgentTest.Hook.Hook;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FIleDeleteHook implements Hook {

    @Override
    public boolean isMethod(String className) {
        return "java/io/File".equals(className);
    }


    public void hookMethod(CtClass ctClass){

        try {

            CtMethod delete = ctClass.getMethod("delete", "()Z");

            if (delete != null){

                delete.insertBefore("com.AgentTest.Hook.File.FileHook.checkFile($0);");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void checkFile(File file) {

        HashMap<String, Object> paramsMap = new HashMap<String, Object>();

        if (file != null){

            paramsMap.put("path",file.getPath());

            String realPath;

            try {
                realPath = file.getCanonicalPath();

            } catch (IOException e) {
                realPath = file.getAbsolutePath();
            }

            paramsMap.put("realPath",realPath);

            FileCheck fileCheck = new FileCheck(paramsMap);

            fileCheck.fileDelete();
        }
    }
}
