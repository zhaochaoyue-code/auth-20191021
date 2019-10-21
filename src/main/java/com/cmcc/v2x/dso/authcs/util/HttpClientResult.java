package com.cmcc.v2x.dso.authcs.util;

import java.io.Serializable;

public class HttpClientResult implements Serializable {

    private static final long serialVersionUID = 5588359768768031640L;
    private int result;
    private  String timestamp;


    public HttpClientResult(int result, String timestamp) {
        this.result = result;
        this.timestamp = timestamp;
    }

    public HttpClientResult() {

    }
    public HttpClientResult(int result){
        this.result = result;
    }
    public HttpClientResult(String timestamp){
        this.timestamp = timestamp;
    }

   /* @Override
    public String toString() {
        return "HttpClientResultAuth{" +
                "result=" + result +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }*/
    @Override
    public String toString() {
        return "HttpClientResultAuth [result=" + result + ", timestamp=" + timestamp + "]";
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
