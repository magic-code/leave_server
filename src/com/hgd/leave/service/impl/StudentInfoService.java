package com.hgd.leave.service.impl;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgd.leave.constants.Constants;
import com.hgd.leave.dao.IStudentInfoDao;

import com.hgd.leave.domain.StudentInfo;
import com.hgd.leave.exceptions.AccountLockedException;
import com.hgd.leave.exceptions.NoHeadImgException;
import com.hgd.leave.exceptions.NoThisFileException;
import com.hgd.leave.exceptions.UserExistException;
import com.hgd.leave.exceptions.UserNotExitException;
import com.hgd.leave.exceptions.UserPasswdErrorException;

import com.hgd.leave.service.IStudentInfoService;

public class StudentInfoService implements IStudentInfoService {

	private IStudentInfoDao studao;

	public IStudentInfoDao getStudao() {
		return studao;
	}

	@Resource
	public void setStudao(IStudentInfoDao studao) {
		this.studao = studao;
	}

	@Override
	public void registe(StudentInfo info) throws UserExistException, Exception {
		boolean flag;
		try {
			flag = studao.hasThisUname(info.getUname());
			if (flag) {
				throw new UserExistException();
			}
			studao.add(info);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void validateUnameAndPasswd(String uname, String passwd)
			throws Exception, UserNotExitException, UserPasswdErrorException {
		try {
			boolean flag = studao.hasThisUname(uname);
			if (!flag) {
				throw new UserNotExitException();
			}
			String upass = studao.selectPasswdByUname(uname);
			if (upass==null || !upass.equals(passwd)) {
				throw new UserPasswdErrorException();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void validateAccountState(String uname) throws Exception,
			AccountLockedException {
		try {
			int state = studao.getAccountState(uname);
			if (state == Constants.ACCOUNT_LOCKED) {
				throw new AccountLockedException();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void changeHimg(String uname, String filename) throws Exception {
		try {
			studao.updateHimg(uname, filename);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public File getHimg(String uname) throws Exception,NoHeadImgException, NoThisFileException {
		try {
			String filename = studao.getHimg(uname);
			if(filename==null || filename.trim().equals("")){
				throw new NoHeadImgException();
			}
			File path = new File(System.getProperty("user.home"), "/himgs");
			if (!path.exists())
				path.mkdirs();
			File file = new File(path, filename);
			if (!file.exists()) {
				throw new NoThisFileException();
			}
			return file;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public StudentInfo getStuInfo(String uname) throws Exception {
		return studao.queryByStuNum(uname);
	}

	@Override
	public void updatePasswd(String uname,String oldPass, String passwd) throws Exception,UserPasswdErrorException {
		String op= studao.selectPasswdByUname(uname);
		if(op==null || !op.equals(oldPass)){
			throw new UserPasswdErrorException();
		}
		studao.updatePasswd(uname, passwd);
	}

	@Override
	public StudentInfo getStuInfo(int id) throws Exception {
		return studao.select(id);
	}

}
