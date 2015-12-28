package com.hgd.leave.dao;

import java.util.List;

import com.hgd.leave.domain.LeaveNote;

public interface ILeaveNoteDao extends IBaseDao<LeaveNote>{
	/**获取最新一条记录*/
	public LeaveNote getNewestNote(int sid)throws Exception;
	/**通过学生ID获取记录*/
	public List<LeaveNote> getNoteBySid(int sid)throws Exception;
	
	/**通过学生ID获取记录*/
	public List<LeaveNote> getNoteBySid(int sid,int first,int max)throws Exception;
	/**学生获取拒绝的记录*/
	public List<LeaveNote> getDefinedNoteBySid(int sid,int first,int num)throws Exception;
	/**领导获取拒绝的记录*/
	public List<LeaveNote> getDefinedNote(int college,int first,int max)throws Exception;
	/**学生获取同意的记录*/
	public List<LeaveNote> getAllowNoteBySid(int sid,int first,int num)throws Exception;
	/**领导获取同意的记录*/
	public List<LeaveNote> getAllowNote(int college,int first,int max)throws Exception;
	/**获取 辅导员同意的记录*/
	public List<LeaveNote> getFdyAllowNote(int college,int first,int max)throws Exception;
	/**学生获取等待同意的记录*/
	public List<LeaveNote> getAllAllowingNoteBySid(int sid)throws Exception;
	/**领导获取等待同意的记录*/
	public List<LeaveNote> getAllAllowingNote(int college,int first,int max)throws Exception;
	/**学生获取没有销假的记录*/
	public List<LeaveNote> getNoFinishNoteBySid(int sid)throws Exception;
	/**领导获取没有销假的记录*/
	public List<LeaveNote> getNoFinishNote(int college,int first,int max)throws Exception;
	/**更改记录状态*/
	public void updateNoteState(int mid,int id,int state,int agent)throws Exception;
	/**更改记录状态并插入reason*/
	public void updateNoteState(int mid,int id,int state,String result,int agent)throws Exception;

	/**通过id删除记录  */
	public void delNoteById(int id)throws Exception;
	
	/**获取没有完成的记录的 数目即  state!=1,3,7*/
	public int getNoFinishCount(int sid)throws Exception;
	/**领导获取记录*/
	public List<LeaveNote> getAAllNote(int college,int first,int num)throws Exception;
}
