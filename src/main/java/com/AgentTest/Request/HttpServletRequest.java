package com.AgentTest.Request;

import com.AgentTest.tools.Rflection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {

    private Object request;

    protected static final Class[] EMPTY_CLASS = new Class[]{};

    private Map<String, String[]> formMap = null;

    public HttpServletRequest(Object request) {
        this.request = request;
    }

    /**
     * 获取 request 请求参数，转为 hashMap
     *
     * @return
     */
    public Map<String, String[]> getParameterMap(){

        Map<String, String[]> parameterMap = null;
        formMap = new HashMap();

        parameterMap = (Map<String, String[]>) Rflection.invokeMethod(request, "getParameterMap", EMPTY_CLASS);

        return mergeToHashMap(parameterMap, formMap);
    }

    /**
     * 遍历 parameterMap，把相同的 key 的 value 合并为数组存到 HashMap
     *
     * @param parameterMap  参数map
     * @param formMap       空的formMap
     * @return              formMap
     */
    private Map<String, String[]> mergeToHashMap(Map<String, String[]> parameterMap, Map<String, String[]> formMap){

        if (parameterMap == null) return null;

        for (Map.Entry<String, String[]> entry: parameterMap.entrySet()){

            if (formMap.containsKey(entry.getKey())){

                formMap.put(entry.getKey(), mergeArray(entry.getValue(), formMap.get(entry.getKey())));

            }else {

                formMap.put(entry.getKey(), entry.getValue());
            }
        }

        return formMap;
    }

    /**
     * 将两个string数组和并为一个string数组
     *
     * @param s1    string数组1
     * @param s2    string数组2
     * @return      string数组
     */
    private String[] mergeArray(String[] s1, String[] s2){

        int str1Length = s1.length;
        int str2length = s2.length;
        s1 = Arrays.copyOf(s1, str1Length + str2length);
        System.arraycopy(s2, 0, s1, str1Length, str2length);
        return s1;

    }




}
