package com.hgd.leave.service;

import java.io.File;

import com.hgd.leave.domain.StudentInfo;
import com.hgd.leave.exceptions.AccountLockedException;
import com.hgd.leave.exceptions.NoHeadImgException;
import com.hgd.leave.exceptions.NoThisFileException;
import com.hgd.leave.exceptions.UserExistException;
import com.hgd.leave.exceptions.UserNotExitException;
import com.hgd.leave.exceptions.UserPasswdErrorException;


public interface IStudentInfoService {
	public void registe(StudentInfo info) throws UserExistException, Exception;
	public void validateUnameAndPasswd(String uname,String passwd) throws Exception,UserNotExitException,UserPasswdErrorException;
	public void validateAccountState(String uname) throws Exception,AccountLockedException;
	public void changeHimg(String uname,String filename)throws Exception;
	public File getHimg(String uname) throws Exception,NoHeadImgException,NoThisFileException;
	public StudentInfo getStuInfo(String uname)throws Exception;
	public StudentInfo getStuInfo(int id)throws Exception;
	public void updatePasswd(String uname ,String oldPass,String passwd)throws Exception,UserPasswdErrorException;
}
