package com.hgd.leave.dao.impl;

import org.hibernate.Query;

import com.hgd.leave.dao.IStudentInfoDao;
import com.hgd.leave.domain.StudentInfo;

public class StudentInfoDao extends BaseDao<StudentInfo> implements
		IStudentInfoDao {

	@Override
	public boolean hasThisUname(String uname) throws Exception {
		try {
			String hql = "select count(id) from StudentInfo where uname=?";
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
	public StudentInfo queryByStuNum(String uname) throws Exception {
		try {
			String hql = "from StudentInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			StudentInfo info = null;
			info = (StudentInfo) q.uniqueResult();
			return info;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String selectPasswdByUname(String uname) throws Exception {
		try {
			String hql = "select passwd from StudentInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			String passwd = (String) q.uniqueResult();
			return passwd;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getAccountState(String uname) throws Exception {
		try {
			String hql = "select locked from StudentInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			int state = (Integer) q.uniqueResult();
			return state;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateHimg(String uname, String filename) throws Exception {
		try {
			String hql = "update StudentInfo set headimg=? where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, filename);
			q.setParameter(1, uname);
			q.executeUpdate();
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public String getHimg(String uname) throws Exception {
		try {
			String hql = "select headimg from StudentInfo where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, uname);
			String filename = (String) q.uniqueResult();
			return filename;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updatePasswd(String uname, String passwd) throws Exception {
		try{
			String hql = "update SutdentInfo set passwd=? where uname=?";
			Query q = this.getSession().createQuery(hql);
			q.setParameter(0, passwd);
			q.setParameter(1, uname);
			q.executeUpdate();
		}catch(Exception e){
			throw e;
		}
	}

}
