package com.AgentTest;

import com.AgentTest.Check.ServletRequestCheck;
import com.AgentTest.Request.HttpServletRequest;
import com.AgentTest.Request.HttpServletResponse;

import java.util.Arrays;
import java.util.Map;

public class HookHandler {

    private static ThreadLocal<HttpServletRequest> requestCache = new ThreadLocal<HttpServletRequest>();

    private static Map<String, String[]> formMap = null;


    /**
     * 包装 request和 response
     *
     * @param request   request对象
     * @param response  response对象
     */
    public static void checkRequest(Object request, Object response){

        if (request != null || response != null){

            HttpServletRequest httpServletRequest = new HttpServletRequest(request);

            HttpServletResponse httpServletResponse = new HttpServletResponse(response);

            requestCache.set(httpServletRequest);

            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();


        }
    }

    public static ThreadLocal<HttpServletRequest> getRequestCache() {
        return requestCache;
    }

}
