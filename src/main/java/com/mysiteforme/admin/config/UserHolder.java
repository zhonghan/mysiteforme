package com.mysiteforme.admin.config;

import com.mysiteforme.admin.entity.User;

public class UserHolder {
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();
    public static User get(){
        return userThreadLocal.get();
    }
    public static void set(User user) {
        userThreadLocal.set(user);
    }
    public static void clear() {
        userThreadLocal.remove();;
    }
}
