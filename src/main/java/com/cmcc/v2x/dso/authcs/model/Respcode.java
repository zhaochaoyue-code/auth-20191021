package com.cmcc.v2x.dso.authcs.model;

public class Respcode {//错误码定义
    public  static final int OK_CODE = 10200;//正常码
    public  static final int HASHERROR_CODE =10500;//hasinfo值错误码
    public  static final int MAC_CODE =10501;//mac值错误码
    public  static final int ERROR_CODE =10502;//值错误码
    public  static final int NO_CODE =10503;//反馈报错终端不合法
    public  static final int NOTKNOW_CODE =10504;//反馈报错报文不能识别
    public  static final int NOBACK_CODE =10505;//没有反馈时间信
    public  static final int V2X_CODE =10506;//内部错误
    public  static final int REQ_CODE =10507;//在请求中 v2x 还没有返回重试
    public  static final int TIME_CODE =10201;//处理完成,没有过期,提示用户还有多少时间过期
    public  static final String OK_MESS="OK";// ok 信息
    public  static final String HASHERROR_MES ="HASH_CHECK_ERROR";// hasherror信息
    public  static final String MAC_MES ="终端mac信息，请检查原因";
    public  static final String ERROR_MES ="ERROR";
    public  static final String NO_MES ="反馈报错终端不合法";
    public  static final String NOTKNOW_MES ="反馈报错报文不能识别";
    public  static final String NOBACK_MES ="没有反馈信息";
    public  static final String TIME_MES ="距离过期还有";
    public  static final String V2X_MES ="系统内部发生错误";
    public  static final String REQ_MES ="正在请求中，请稍等重试";
    private int code;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static int getOkCode() {
        return OK_CODE;
    }

    public static int getHasherrorCode() {
        return HASHERROR_CODE;
    }

    public static String getOkMess() {
        return OK_MESS;
    }

    public static String getHasherrorMes() {
        return HASHERROR_MES;
    }
}
