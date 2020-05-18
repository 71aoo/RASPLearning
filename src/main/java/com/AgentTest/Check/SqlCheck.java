package com.AgentTest.Check;

import com.AgentTest.HookHandler;
import com.AgentTest.Request.HttpServletRequest;
import com.baidu.openrasp.TokenGenerator;

import java.util.HashMap;
import java.util.Map;


public class SqlCheck {

    public static void sqlAttackCheck(Map<String, String> sqlMap){

        HttpServletRequest request = HookHandler.getRequestCache().get();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String query = sqlMap.get("query");

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()){

            String[] value = entry.getValue();

            for (int i = 0; i < value.length; i++) {

                if (query.contains(value[i])){

                    //执行语句token化
                    String[] queryToken = TokenGenerator.tokenize(query);

                    //query语句匹配去掉用户输入
                    String originalQuery = query.replace(value[i], "");

                    //原sql语句token化
                    String[] originalQueryToken = TokenGenerator.tokenize(originalQuery);

                    if (queryToken.length - originalQueryToken.length > 2){

                        System.out.println("危险，sql注入");
                    }
                }
            }

        }

    }

}
