package com.hgd.leave.exceptions;

public class NoteHasUpdatedException extends Exception {
	public NoteHasUpdatedException(){
		super("该记录已经被其它管理员操作，更新失败");
	}
}
