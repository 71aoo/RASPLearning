package com.AgentTest.Hook.File;

import com.AgentTest.Hook.Hook;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.File;
import java.io.IOException;

public class FileOutputStreamHook implements Hook {


    @Override
    public boolean isMethod(String className) {
        return false;
    }

    public void hookMethod(CtClass ctClass){

        CtConstructor constructor = null;
        try {

            constructor = ctClass.getConstructor("(Ljava/io/File;Z)V");

            constructor.insertBefore("com.AgentTest.Hook.File.FileOutputStreamHook.checkFile($1);");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void checkFile(File file) {

        if (file != null){

            String path;

            try {
                path = file.getCanonicalPath();
            } catch (IOException e) {
                path = file.getAbsolutePath();
            }

            System.out.println(path);
        }
    }

}
