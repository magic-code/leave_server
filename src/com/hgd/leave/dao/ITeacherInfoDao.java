package com.hgd.leave.dao;


import com.hgd.leave.domain.TeacherInfo;


public interface ITeacherInfoDao extends IBaseDao<TeacherInfo>{
	public TeacherInfo queryByUname(String uname)throws Exception;
	public String selectPasswdByUname(String uname)throws Exception;
	public boolean hasThisUname(String uname) throws Exception;
	public int getAccountState(String uname)throws Exception;
	public void updateHimg(String uname,String filename)throws Exception;
	public String getHimg(String uname)throws Exception;
	public void updatePasswd(String uname,String passwd) throws Exception;
}
