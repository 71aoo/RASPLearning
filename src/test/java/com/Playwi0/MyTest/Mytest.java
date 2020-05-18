package com.Playwi0.MyTest;

import com.baidu.openrasp.TokenGenerator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mytest {

    private static String NTFSREGEX = "::\\$(DATA|INDEX)$";

    public static void main(String[] args) {

//        String realPath = "1123::$DATA";
//
//        if (Pattern.compile(NTFSREGEX, Pattern.CASE_INSENSITIVE).matcher(realPath).find()){
//
//            System.out.println("危险");

        getSql();
//        }

    }

    public static void getSql(){

        String sql = "SELECT * FROM users WHERE id = 'abc--')/*!UNION/**/ALL*//*!SELECT*//*!USER*/(),/*!user*//*!FROM*/users#";

        String[] tokenize = TokenGenerator.tokenize(sql);

        System.out.println(Arrays.toString(tokenize));
    }
}
