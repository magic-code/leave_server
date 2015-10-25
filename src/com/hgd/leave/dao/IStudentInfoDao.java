package com.hgd.leave.dao;

import com.hgd.leave.domain.StudentInfo;

public interface IStudentInfoDao extends IBaseDao<StudentInfo>{
	public StudentInfo queryByStuNum(String uname)throws Exception;
	public String selectPasswdByUname(String uname)throws Exception;
	public boolean hasThisUname(String uname) throws Exception;
	public int getAccountState(String uname)throws Exception;
	public void updateHimg(String uname,String filename)throws Exception;
	public String getHimg(String uname)throws Exception;
}
