package com.mysiteforme.admin.config;

public class UserTokenResponseObj {
    private Long uid;
    private String sessionId;
    private Long tenantId;
    private String userName;
    private Long timestamp;

    public Long getUid() {
        return uid;
    }

    public UserTokenResponseObj setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UserTokenResponseObj setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public UserTokenResponseObj setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserTokenResponseObj setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public UserTokenResponseObj setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
