package com.mysiteforme.admin.base;

import com.mysiteforme.admin.config.UserHolder;
import com.mysiteforme.admin.entity.User;

/**
 * Created by wangl on 2017/11/25.
 * todo:
 */
public class MySysUser {
    /**
     * 取出Shiro中的当前用户LoginName.
     */
    public static String icon() {
        return ShiroUser().getIcon();
    }

    public static Long id() {
        return ShiroUser().getId();
    }

    public static String loginName() {
        return ShiroUser().getNickName();
    }

    public static String nickName(){
        return ShiroUser().getNickName();
    }

    public static User ShiroUser() {
        return UserHolder.get();
    }
}
