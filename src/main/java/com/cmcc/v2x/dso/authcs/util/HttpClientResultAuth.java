package com.cmcc.v2x.dso.authcs.util;

import java.io.Serializable;

/**
 * Description: ��װhttpClient��Ӧ���
 * 
 * @author JourWon
 * @date Created on 2018��4��19��
 */
public class HttpClientResultAuth implements Serializable {

	private static final long serialVersionUID = 5588359768768031640L;
	private int result;
	private  String timestamp;

	public HttpClientResultAuth(int result, String timestamp) {
		this.result = result;
		this.timestamp = timestamp;
	}

	public HttpClientResultAuth() {

	}
	public HttpClientResultAuth(int result){
		this.result = result;
	}
	public HttpClientResultAuth(String timestamp){
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

	/*private static final long serialVersionUID = 2168152194164783950L;

	*//**
	 * ��Ӧ״̬��
	 *//*
	private int code;

	*//**
	 * ��Ӧ����
	 *//*
	private String content;

	public HttpClientResultAuth1() {
	}

	public HttpClientResultAuth1(int code) {
		this.code = code;
	}

	public HttpClientResultAuth1(String content) {
		this.content = content;
	}

	public HttpClientResultAuth1(int code, String content) {
		this.code = code;
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "HttpClientResult [code=" + code + ", content=" + content + "]";
	}*/

}
