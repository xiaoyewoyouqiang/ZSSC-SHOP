package com.qf.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    /**
     * 对密码加密
     * @param password
     * @return
     */
    public static String encode(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    /**
     * 密码比对
     * @param password 用户输入的密码
     * @param dbpassword 从数据库中查询出的密文
     * @return
     */
    public static boolean checkpw(String password,String dbpassword){
        return BCrypt.checkpw(password,dbpassword);
    }
}
