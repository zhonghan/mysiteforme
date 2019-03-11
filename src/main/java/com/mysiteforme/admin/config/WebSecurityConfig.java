package com.mysiteforme.admin.config;

import com.mysiteforme.admin.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter{
    /**
     * 登录session key
     */
    public final static String TOKEN_COOKIE_NAME = "account-token";
    private final static String TOKEN = "token";
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }
    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {

            if(!StringUtils.isEmpty(getToken(request))) {
                setUserToHolder();
                return true;
            }
            if(!StringUtils.isEmpty(request.getQueryString())) {
                if (request.getQueryString().contains(TOKEN)) {
                    String token = request.getQueryString().substring("token=".length());
                    setUserToHolder();
                    Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, token);
                    response.addCookie(cookie);
                    return true;
                }
            }


            // 跳转登录
            String url = "http://127.0.0.1:8080/account/login.html?app=13&redirect=http://127.0.0.1:8081/callback";
            response.sendRedirect(url);
            return false;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            UserHolder.clear();
            super.postHandle(request, response, handler, modelAndView);
        }

    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";

    }

    private void setUserToHolder() {
        User user = new User();
        user.setNickName("test");
        user.setId(1L);
        UserHolder.set(user);
    }


}
