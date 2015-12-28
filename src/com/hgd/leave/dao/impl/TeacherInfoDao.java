package com.hgd.leave.dao.impl;

import java.io.File;

import org.hibernate.Query;

import com.hgd.leave.dao.ITeacherInfoDao;
import com.hgd.leave.domain.StudentInfo;
import com.hgd.leave.domain.TeacherInfo;
import com.hgd.leave.exceptions.AccountLockedException;
import com.hgd.leave.exceptions.NoThisFileException;
import com.hgd.leave.exceptions.UserExistException;
import com.hgd.leave.exceptions.UserNotExitException;
import com.hgd.leave.exceptions.UserPasswdErrorException;

public class TeacherInfoDao extends BaseDao<TeacherInfo> implements ITeacherInfoDao {

	@Override
	public int getAccountState(String uname) throws Exception {
		try {
			String hql = "select locked from TeacherInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			int state = (Integer) q.uniqueResult();
			return state;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String getHimg(String uname) throws Exception {
		try {
			String hql = "select headimg from TeacherInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			String filename = (String) q.uniqueResult();
			return filename;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean hasThisUname(String uname) throws Exception {
		try {
			String hql = "select count(id) from TeacherInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			Number number = (Number) q.uniqueResult();
			int count = number.intValue();
			if (count > 0)
				return true;
			return false;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public TeacherInfo queryByUname(String uname) throws Exception {
		try {
			String hql = "from TeacherInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			TeacherInfo info = null;
			info = (TeacherInfo) q.uniqueResult();
			return info;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String selectPasswdByUname(String uname) throws Exception {
		try {
			String hql = "select passwd from TeacherInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			String passwd = (String) q.uniqueResult();
			return passwd;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateHimg(String uname, String filename) throws Exception {
		try {
			String hql = "update TeacherInfo set headimg=? where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, filename);
			q.setParameter(1, uname);
			q.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updatePasswd(String uname, String passwd) throws Exception {
		try{
			String hql = "update TeacherInfo set passwd=? where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, passwd);
			q.setParameter(1, uname);
			q.executeUpdate();
		}catch(Exception e){
			throw e;
		}
	}


}
