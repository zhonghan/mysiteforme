package com.mysiteforme.admin.config;

import com.google.gson.Gson;
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
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter{
    /**
     * 登录session key
     */
    public final static String TOKEN_COOKIE_NAME = "account-token";
    private final static String TOKEN = "token";
    private final static String USER_SESSION_KEY = "USER_SESSION_KEY";
    private final static String ACCOUNT_SITE_CONTEXT_PATH = "http://127.0.0.1:8080/account";
    private final static String ACCOUNT_SITE_GET_USER_BY_TOKEN_URL = "http://127.0.0.1:8080/account/getUserInfoByToken";
    private final static String SERVICE_CONTEXT_PATH = "http://127.0.0.1:8081";
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/web/**");
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }
    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {

            HttpSession session = request.getSession();
            Object u = session.getAttribute(USER_SESSION_KEY);
            if(u != null) {
                User user = (User)u;
                UserHolder.set(user);
                return true;
            }
            String token = null;
            if(!StringUtils.isEmpty(request.getQueryString()) && request.getQueryString().contains(TOKEN)) {
                token = request.getQueryString().substring(TOKEN.length() + 1);
            }

            if(StringUtils.isEmpty(token)) {
                token = getToken(request);
            }

            if(!StringUtils.isEmpty(token)) {
                User user = getUserByToken(token);
                if(user != null){
                    UserHolder.set(user);
                    Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, token);
                    response.addCookie(cookie);
                    session.setAttribute(USER_SESSION_KEY, UserHolder.get());
                    return true;
                }
            }

            // 跳转登录
            String url = ACCOUNT_SITE_CONTEXT_PATH+"/login.html?app=13&redirect="+SERVICE_CONTEXT_PATH+"/callback";
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

    private User getUserByToken(String token) {
        URL url= null;
        try {
            url = new URL(ACCOUNT_SITE_GET_USER_BY_TOKEN_URL+"?"+TOKEN+"="+token);
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int code=urlConnection.getResponseCode();
            if (code==200) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }
                String str = buffer.toString();
                UserTokenResponse userTokenResponse = new Gson().fromJson(str, UserTokenResponse.class);
                UserTokenResponseObj userTokenResponseObj = new Gson().fromJson(userTokenResponse.getItems(), UserTokenResponseObj.class);
                if(userTokenResponse != null && userTokenResponse.getItems() != null) {
                    User user = new User();
                    user.setId(userTokenResponseObj.getUid());
                    user.setNickName(userTokenResponseObj.getUserName());
                    return user;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



}
