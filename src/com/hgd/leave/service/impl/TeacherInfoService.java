package com.hgd.leave.service.impl;

import java.io.File;

import javax.annotation.Resource;

import com.hgd.leave.constants.Constants;
import com.hgd.leave.dao.ITeacherInfoDao;
import com.hgd.leave.domain.TeacherInfo;
import com.hgd.leave.exceptions.AccountLockedException;
import com.hgd.leave.exceptions.NoHeadImgException;
import com.hgd.leave.exceptions.NoThisFileException;
import com.hgd.leave.exceptions.UserExistException;
import com.hgd.leave.exceptions.UserNotExitException;
import com.hgd.leave.exceptions.UserPasswdErrorException;
import com.hgd.leave.service.ITeacherInfoService;

public class TeacherInfoService implements ITeacherInfoService {

	private ITeacherInfoDao teacdao;
	
	public ITeacherInfoDao getTeacdao() {
		return teacdao;
	}
	@Resource
	public void setTeacdao(ITeacherInfoDao teacdao) {
		this.teacdao = teacdao;
	}

	@Override
	public void changeHimg(String uname, String filename) throws Exception {
		try {
			teacdao.updateHimg(uname, filename);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public File getHimg(String uname) throws Exception,NoHeadImgException, NoThisFileException {
		try {
			String filename = teacdao.getHimg(uname);
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
	public TeacherInfo getTeacherInfo(String uname) throws Exception {
		try{
			return teacdao.queryByUname(uname);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public void registe(TeacherInfo info) throws UserExistException, Exception {
		boolean flag;
		try {
			flag = teacdao.hasThisUname(info.getUname());
			if (flag) {
				throw new UserExistException();
			}
			teacdao.add(info);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void validateAccountState(String uname) throws Exception,
			AccountLockedException {
		try {
			int state = teacdao.getAccountState(uname);
			if (state == Constants.ACCOUNT_LOCKED) {
				throw new AccountLockedException();
			}
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void validateUnameAndPasswd(String uname, String passwd)
			throws Exception, UserNotExitException, UserPasswdErrorException {
		try {
			boolean flag = teacdao.hasThisUname(uname);
			if (!flag) {
				throw new UserNotExitException();
			}
			String upass = teacdao.selectPasswdByUname(uname);
			if (upass==null || !upass.equals(passwd)) {
				throw new UserPasswdErrorException();
			}
		} catch (Exception e) {
			throw e;
		}

	}
	@Override
	public void updatePasswd(String uname, String oldPass, String passwd)
			throws Exception, UserPasswdErrorException {
		String op= teacdao.selectPasswdByUname(uname);
		if(op==null || !op.equals(oldPass)){
			throw new UserPasswdErrorException();
		}
		teacdao.updatePasswd(uname, passwd);
	}
	@Override
	public TeacherInfo getTeacherInfo(int id) throws Exception {
		return teacdao.select(id);
	}

}
