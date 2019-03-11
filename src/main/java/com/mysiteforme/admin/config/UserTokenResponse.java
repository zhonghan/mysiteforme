package com.mysiteforme.admin.config;

public class UserTokenResponse {
    private String code;
    private String message;
    private String items;

    public String getCode() {
        return code;
    }

    public UserTokenResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UserTokenResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getItems() {
        return items;
    }

    public UserTokenResponse setItems(String items) {
        this.items = items;
        return this;
    }
}
