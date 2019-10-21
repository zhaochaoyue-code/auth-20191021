package com.cmcc.v2x.dso.authcs.model;


public enum Error {
    NO_TOKEN_FOUND(1, "终端不合法"),
    NO_VALID_TOKEN(2, "报文不能识别");

    public final int errorCode;
    public final String errorMessage;

    private Error(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() { return errorCode; }

    public String getErrorMessage() { return errorMessage; }
}