package com.hgd.leave.exceptions;

public class UserNotExitException extends Exception{
	public UserNotExitException() {
		super("用户名不存在");
	}
}