package com.AgentTest.tools;

import java.lang.reflect.Method;

public class Rflection {


    /**
     * 根据方法名调用对象的某一个方法
     *
     * @param object        调用方法的对象
     * @param methodName    调用方法名
     * @param paramTypes    调用方法参数类型数组
     * @param parameters    用方法传入参数数组
     * @return              调用方法返回值
     */
    public static Object invokeMethod(Object object, String methodName, Class[] paramTypes, Object... parameters){

        if (object == null){
            return null;
        }

        return invokeMethod(object, object.getClass(), methodName, paramTypes, parameters);
    }

    /**
     * 根据方法名调用对象的某一个方法
     *
     * @param object        调用方法的对象
     * @param clazz         调用方法的类
     * @param methodName    调用方法名
     * @param paramTypes    调用方法参数类型数组
     * @param parameters    调用方法传入参数数组
     * @return              调用方法返回值
     */
    public static Object invokeMethod(Object object, Class clazz, String methodName,
                                      Class[] paramTypes, Object... parameters){
        try {
            //从类获取反射方法
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);

            //设置可反射执行权限
            if (!method.isAccessible()){
                method.setAccessible(true);
            }

            return method.invoke(object,parameters);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

}
