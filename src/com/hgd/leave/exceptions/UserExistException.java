package com.hgd.leave.exceptions;

public class UserExistException extends Exception{
	public UserExistException(){
		super("用户名已经存在");
	}
}