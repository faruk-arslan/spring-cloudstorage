package com.udacity.jwdnd.course1.cloudstorage.entity;

public class Credential {
    private int credentialid;
    private String url;
    private String username;
    private String key2;
    private String password;
    private int userid;

    public int getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(int credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getkey2() {
        return key2;
    }

    public void setkey2(String key2) {
        this.key2 = key2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
