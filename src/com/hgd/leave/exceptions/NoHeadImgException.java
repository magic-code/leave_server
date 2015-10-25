package com.hgd.leave.exceptions;

public class NoHeadImgException extends Exception {
	public NoHeadImgException() {
		super("用户没有上传头像");
	}
}
