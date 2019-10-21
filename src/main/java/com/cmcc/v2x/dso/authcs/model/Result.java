package com.cmcc.v2x.dso.authcs.model;


public class Result {
    private int result;
    private int  randomnum;
    private  String token;
    private  String tokenvalidtime;
    private  String key;
    private  String keyvalidtime;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getRandomnum() {
        return randomnum;
    }

    public void setRandomnum(int randomnum) {
        this.randomnum = randomnum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenvalidtime() {
        return tokenvalidtime;
    }

    public void setTokenvalidtime(String tokenvalidtime) {
        this.tokenvalidtime = tokenvalidtime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyvalidtime() {
        return keyvalidtime;
    }

    public void setKeyvalidtime(String keyvalidtime) {
        this.keyvalidtime = keyvalidtime;
    }
}
