package com.hgd.leave.exceptions;

public class NoThisFileException extends Exception {
	public NoThisFileException() {
		super("没有这个文件");
	}
}
