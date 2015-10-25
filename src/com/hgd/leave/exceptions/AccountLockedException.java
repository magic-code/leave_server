package com.hgd.leave.exceptions;

public class AccountLockedException extends Exception {
	public AccountLockedException() {
		super("用户已经被锁定，暂不可使用");
	}
}
