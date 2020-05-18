package com.AgentTest.Hook.Request;

import com.AgentTest.Check.SqlCheck;
import com.AgentTest.Hook.Hook;
import javassist.CtClass;
import javassist.CtMethod;

public class Request implements Hook {

    @Override
    public boolean isMethod(String className) {

        if (className.equals("com/AgentTest/MyTest/sqlExecute")){

            return true;
        }

        return false;
    }

    public void hookSqlStatementMethod(CtClass ctClass) throws Exception {

        CtMethod executeQuery = ctClass.getMethod("forth", "(Ljava/lang/String;)V");

        executeQuery.insertBefore("com.AgentTest.Hook.Request.Request.getStatement($1);");

    }

    //获得sql语句
    public static void getStatement(String param){

//        SqlCheck.hashMap.put("parameter",SqlCheck.tokenize(param));
        System.out.println(param);
    }
}
