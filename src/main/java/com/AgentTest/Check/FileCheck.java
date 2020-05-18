package com.AgentTest.Check;

import java.util.Map;
import java.util.regex.Pattern;



public class FileCheck {

    private static String DOTFILE = ".(7z|tar|gz|bz2|xz|rar|zip|sql|db|sqlite)$";

    private static String NONUSERDIRECTORY = "^/(proc|sys|root)";

    private static String NTFSREGEX = "::\\$(DATA|INDEX)$";

    private static String SCRIPTFILEREGEX = "\\.(aspx?|jspx?|php[345]?|phtml)\\.?$";

    // 目录探针 - webshell 查看频次最高的目录
    private static String[] UNWANTEDDIRECTORY = {
            "/",
            "/home",
            "/var/log",
            "/private/var/log",
            "/proc",
            "/sys",
            "C:\\",
            "D:\\",
            "E:\\"
    };

    // webdav 文件探针 - 最常被下载的文件
    private static String[] UNWANTEDFILENAMES = {
            // user files
            ".DS_Store",
            "id_rsa", "id_rsa.pub", "known_hosts", "authorized_keys",
            ".bash_history", ".csh_history", ".zsh_history", ".mysql_history",

            // project files
            ".htaccess", ".user.ini",

            "web.config", "web.xml", "build.property.xml", "bower.json",
            "Gemfile", "Gemfile.lock",
            ".gitignore",
            "error_log", "error.log", "nohup.out",
    };

    // 文件探针 - webshell 查看频次最高的文件
    private static String[] ABSOLUTEPATHS = {
            "/etc/shadow",
            "/etc/passwd",
            "/etc/hosts",
            "/etc/apache2/apache2.conf",
            "/root/.bash_history",
            "/root/.bash_profile",
            "c:\\windows\\system32\\inetsrv\\metabase.xml",
            "c:\\windows\\system32\\drivers\\etc\\hosts"
    };

    private Map<String,Object> params;

    public FileCheck(){}

    public FileCheck(Map<String,Object> params){

        this.params = params;
    }


    public void fileList(){

        String path = (String) params.get("path");

        String realPath = (String) params.get("realPath");

        if (realPath != null){

            for (String directory : UNWANTEDDIRECTORY){

                if (realPath.equals(directory)){

                    System.out.println("危险，目录遍历");
                }
            }
        }

        if (path != null){

            String path2 = path.replace("\\", "/");

            int left = path2.indexOf("/../");
            int right = path2.lastIndexOf("/../");

            if (left != -1 && right != -1 && left != right){

                System.out.println("危险，目录遍历");
            }

        }
    }

    public void fileDelete(){

        System.out.println("未实现");
    }

    public void readFile(){

        String realPathLower = ((String) params.get("realPath")).toLowerCase();

        String fileName = getFileName(realPathLower);

        if (Pattern.compile(DOTFILE).matcher(fileName).find()){

            System.out.println("危险");

        }else {
            for (String uwFile : UNWANTEDFILENAMES){
                if (fileName.equals(uwFile)){

                    System.out.println("危险");
                }
            }
        }

        for (String abPath: ABSOLUTEPATHS){

            if (realPathLower.equals(abPath)){
                System.out.println("危险");
            }
        }

    }

    public void writeFile(){

        String realPath = (String) params.get("realPath");

        if (Pattern.compile(NTFSREGEX, Pattern.CASE_INSENSITIVE).matcher(realPath).find()){

            System.out.println("危险");
        }
        
        if (Pattern.compile(SCRIPTFILEREGEX, Pattern.CASE_INSENSITIVE).matcher(realPath).find()){

            System.out.println("危险");
        }

    }

    // 从路径中获得目标文件名
    // /xx/xx/1.txt ---> 1.txt
    public String getFileName(String path){

        int i = path.lastIndexOf("/");

        return path.substring(i + 1);
    }

}