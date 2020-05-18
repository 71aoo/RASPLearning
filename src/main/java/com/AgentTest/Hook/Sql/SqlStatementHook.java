package com.AgentTest.Hook.Sql;

import com.AgentTest.Check.SqlCheck;
import com.AgentTest.Hook.Hook;
import com.AgentTest.HookHandler;
import com.AgentTest.Request.HttpServletRequest;
import com.baidu.openrasp.TokenGenerator;
import javassist.CtClass;
import javassist.CtMethod;

import java.util.HashMap;
import java.util.Map;


public class SqlStatementHook implements Hook {

    @Override
    public boolean isMethod(String className) {

        if (className.equals("com/mysql/jdbc/StatementImpl")){

            return true;
        }
        return false;
    }

    public void hookSqlStatementMethod(CtClass ctClass) throws Exception {

        CtMethod execute = ctClass.getMethod("execute", "(Ljava/lang/String;)Z");
        CtMethod executeQuery = ctClass.getMethod("executeQuery", "(Ljava/lang/String;)Ljava/sql/ResultSet;");


        execute.insertBefore("com.AgentTest.Hook.Sql.SqlStatementHook.getSqlStatement($1);");
        executeQuery.insertBefore("com.AgentTest.Hook.Sql.SqlStatementHook.getSqlStatement($1);");



    }

    //获得sql语句
    public static void getSqlStatement(String sql){


        HashMap<String, String> sqlMap = new HashMap<String, String>();

        sqlMap.put("query", sql);

        //进入检查
        SqlCheck.sqlAttackCheck(sqlMap);


    }


}
