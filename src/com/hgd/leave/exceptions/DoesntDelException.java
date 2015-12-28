package com.hgd.leave.exceptions;

public class DoesntDelException extends Exception {
	public DoesntDelException(){
		super("记录已经被管理员操作无法删除");
	}
}
