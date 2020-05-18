package com.AgentTest.Check;

import com.AgentTest.HookHandler;
import com.AgentTest.Request.HttpServletRequest;
import com.AgentTest.Request.HttpServletResponse;

import java.util.Arrays;
import java.util.Map;

public class ServletRequestCheck {



    /**
     * 包装 request和 response
     *
     * @param request   request对象
     * @param response  response对象
     */
    public static void checkRequest(Object request, Object response){

        HookHandler.checkRequest(request, response);
    }

//    public static void
}
