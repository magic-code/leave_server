package com.hgd.leave.exceptions;

public class HasNoteExistException extends Exception {
	public HasNoteExistException(){
		super("存在未完成的记录，无法新建记录");
	}
}
