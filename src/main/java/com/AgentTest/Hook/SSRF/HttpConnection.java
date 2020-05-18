package com.AgentTest.Hook.SSRF;

import com.AgentTest.Check.SSRFCheck;
import com.AgentTest.Hook.Hook;

import javassist.CtClass;
import javassist.CtMethod;


import java.net.URL;
import java.net.URLConnection;

public class HttpConnection implements Hook {


    @Override
    public boolean isMethod(String className) {

        return "sun/net/www/protocol/http/HttpURLConnection".equals(className);
    }

    public void HttpConnectionHook(CtClass ctClass) throws Exception {

        CtMethod connect = ctClass.getMethod("connect", "()V");

        connect.insertBefore("");

    }

    public static void checkUrl(URLConnection urlConnection){

        URL url = null;
        String host = null;
        String port = null;

        url = urlConnection.getURL();

        if (url != null){

            SSRFCheck.SSRFMap.put("url",url);
            host = url.getHost();

            SSRFCheck.SSRFMap.put("hostname",host);
            int temp = url.getPort();
            if (temp > 0){
                port = temp + "";
                SSRFCheck.SSRFMap.put("port",port);
            }
        }
    }


}
