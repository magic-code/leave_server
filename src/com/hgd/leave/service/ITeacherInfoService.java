package com.hgd.leave.service;

import java.io.File;

import com.hgd.leave.domain.TeacherInfo;
import com.hgd.leave.exceptions.AccountLockedException;
import com.hgd.leave.exceptions.NoHeadImgException;
import com.hgd.leave.exceptions.NoThisFileException;
import com.hgd.leave.exceptions.UserExistException;
import com.hgd.leave.exceptions.UserNotExitException;
import com.hgd.leave.exceptions.UserPasswdErrorException;

public interface ITeacherInfoService {
	public void registe(TeacherInfo info) throws UserExistException, Exception;
	public void validateUnameAndPasswd(String uname,String passwd) throws Exception,UserNotExitException,UserPasswdErrorException;
	public void validateAccountState(String uname) throws Exception,AccountLockedException;
	public void changeHimg(String uname,String filename)throws Exception;
	public File getHimg(String uname) throws Exception,NoHeadImgException,NoThisFileException;
	public TeacherInfo getTeacherInfo(String uname)throws Exception;
	public TeacherInfo getTeacherInfo(int id) throws Exception;
	public void updatePasswd(String uname ,String oldPass,String passwd)throws Exception,UserPasswdErrorException;
}
