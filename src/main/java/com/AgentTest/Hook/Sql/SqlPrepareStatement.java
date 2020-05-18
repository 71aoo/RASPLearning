package com.AgentTest.Hook.Sql;

import com.AgentTest.Hook.Hook;

public class SqlPrepareStatement implements Hook {

    @Override
    public boolean isMethod(String className){


        if (className.equals("com/mysql/jdbc/ConnectionImpl")){

            return true;
        }

        return false;
    }
}
