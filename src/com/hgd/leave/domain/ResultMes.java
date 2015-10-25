package com.hgd.leave.domain;

import java.util.HashMap;

public class ResultMes {
	public int code;
	public HashMap<String,Object> data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public HashMap<String, Object> getData() {
		return data;
	}
	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
}
