package com.hgd.leave.dao;

import java.util.List;

import com.hgd.leave.domain.LeaveNote;

public interface ILeaveNoteDao extends IBaseDao<LeaveNote>{
	/**��ȡ����һ����¼*/
	public LeaveNote getNewestNote(int sid)throws Exception;
	/**ͨ��ѧ��ID��ȡ��¼*/
	public List<LeaveNote> getNoteBySid(int sid)throws Exception;
	
	/**ͨ��ѧ��ID��ȡ��¼*/
	public List<LeaveNote> getNoteBySid(int sid,int first,int max)throws Exception;
	/**ѧ����ȡ�ܾ��ļ�¼*/
	public List<LeaveNote> getDefinedNoteBySid(int sid,int first,int num)throws Exception;
	/**�쵼��ȡ�ܾ��ļ�¼*/
	public List<LeaveNote> getDefinedNote(int college,int first,int max)throws Exception;
	/**ѧ����ȡͬ��ļ�¼*/
	public List<LeaveNote> getAllowNoteBySid(int sid,int first,int num)throws Exception;
	/**�쵼��ȡͬ��ļ�¼*/
	public List<LeaveNote> getAllowNote(int college,int first,int max)throws Exception;
	/**��ȡ ����Աͬ��ļ�¼*/
	public List<LeaveNote> getFdyAllowNote(int college,int first,int max)throws Exception;
	/**ѧ����ȡ�ȴ�ͬ��ļ�¼*/
	public List<LeaveNote> getAllAllowingNoteBySid(int sid)throws Exception;
	/**�쵼��ȡ�ȴ�ͬ��ļ�¼*/
	public List<LeaveNote> getAllAllowingNote(int college,int first,int max)throws Exception;
	/**ѧ����ȡû�����ٵļ�¼*/
	public List<LeaveNote> getNoFinishNoteBySid(int sid)throws Exception;
	/**�쵼��ȡû�����ٵļ�¼*/
	public List<LeaveNote> getNoFinishNote(int college,int first,int max)throws Exception;
	/**���ļ�¼״̬*/
	public void updateNoteState(int mid,int id,int state,int agent)throws Exception;
	/**���ļ�¼״̬������reason*/
	public void updateNoteState(int mid,int id,int state,String result,int agent)throws Exception;

	/**ͨ��idɾ����¼  */
	public void delNoteById(int id)throws Exception;
	
	/**��ȡû����ɵļ�¼�� ��Ŀ��  state!=1,3,7*/
	public int getNoFinishCount(int sid)throws Exception;
	/**�쵼��ȡ��¼*/
	public List<LeaveNote> getAAllNote(int college,int first,int num)throws Exception;
}
