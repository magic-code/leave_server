package com.hgd.leave.service;

import java.util.List;

import com.hgd.leave.domain.LeaveNote;
import com.hgd.leave.exceptions.AccessRefuseException;
import com.hgd.leave.exceptions.DoesntDelException;
import com.hgd.leave.exceptions.HasNoteExistException;
import com.hgd.leave.exceptions.NoteHasUpdatedException;

public interface ILeaveService {
	/**创建记录*/
	public LeaveNote createNote(int sid,LeaveNote note)throws HasNoteExistException,Exception;
	/**学生获取没有 审核的记录*/
	public List<LeaveNote> getInitNoteBySid(int sid)throws Exception;
	/**辅导员获取没有审核的记录*/
	public List<LeaveNote> getInitNote(int college,int first,int max)throws Exception;
	/**书记获取没有审核的记录*/
	public List<LeaveNote> getFdyAllowNote(int college,int first,int max)throws Exception;
	/**学生获取审核没通过的记录*/
	public List<LeaveNote> getDefinedNoteBySid(int sid,int first,int num)throws Exception;
	/**学生获取 通过审核的记录*/
	public List<LeaveNote> getAllowedNoteBySid(int sid,int first,int num)throws Exception;
	/**领导获取通过审核的记录*/
	public List<LeaveNote> getAllowedNote(int college,int first,int max)throws Exception;
	/**学生获取未销假的记录*/
	public List<LeaveNote> getNoFinishBySid(int sid)throws Exception;
	/**领导获取未销假的记录*/
	public List<LeaveNote> getNoFinishNote(int college,int first,int max)throws Exception;
	/**更改记录状态*/
	public void updateNoteState(int mid,int id,int state,int agent)throws NoteHasUpdatedException,Exception;
	/**更改记录状态和原因*/
	public void updateNoteState(int mid,int id,int state,String result,int agent)throws NoteHasUpdatedException,Exception;
	/**学生删除记录,只允许删除初始状态的记录，被管理操作后不可删除*/
	public void delNote(int id)throws DoesntDelException,Exception;
	/**学生获取所有的记录 */
	public List<LeaveNote> getAllNoteBySid(int sid,int first,int max)throws Exception;
	
	/**领导获取所有的 记录 */
	public List<LeaveNote> getAllNote(int college,int first,int num)throws Exception;
	/**同意请假*/
	public void allowNote(int actor,int college,int mid,int id)throws AccessRefuseException,NoteHasUpdatedException,Exception;
	/**拒绝请假*/
	public void refuseNote(int actor,int college,int mid,int id)throws AccessRefuseException,NoteHasUpdatedException,Exception;
	/**拒绝请假*/
	public void refuseNote(int actor,int college,int mid,int id,String result)throws AccessRefuseException,NoteHasUpdatedException,Exception;
}
