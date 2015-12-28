package com.hgd.leave.service;

import java.util.List;

import com.hgd.leave.domain.LeaveNote;
import com.hgd.leave.exceptions.AccessRefuseException;
import com.hgd.leave.exceptions.DoesntDelException;
import com.hgd.leave.exceptions.HasNoteExistException;
import com.hgd.leave.exceptions.NoteHasUpdatedException;

public interface ILeaveService {
	/**������¼*/
	public LeaveNote createNote(int sid,LeaveNote note)throws HasNoteExistException,Exception;
	/**ѧ����ȡû�� ��˵ļ�¼*/
	public List<LeaveNote> getInitNoteBySid(int sid)throws Exception;
	/**����Ա��ȡû����˵ļ�¼*/
	public List<LeaveNote> getInitNote(int college,int first,int max)throws Exception;
	/**��ǻ�ȡû����˵ļ�¼*/
	public List<LeaveNote> getFdyAllowNote(int college,int first,int max)throws Exception;
	/**ѧ����ȡ���ûͨ���ļ�¼*/
	public List<LeaveNote> getDefinedNoteBySid(int sid,int first,int num)throws Exception;
	/**ѧ����ȡ ͨ����˵ļ�¼*/
	public List<LeaveNote> getAllowedNoteBySid(int sid,int first,int num)throws Exception;
	/**�쵼��ȡͨ����˵ļ�¼*/
	public List<LeaveNote> getAllowedNote(int college,int first,int max)throws Exception;
	/**ѧ����ȡδ���ٵļ�¼*/
	public List<LeaveNote> getNoFinishBySid(int sid)throws Exception;
	/**�쵼��ȡδ���ٵļ�¼*/
	public List<LeaveNote> getNoFinishNote(int college,int first,int max)throws Exception;
	/**���ļ�¼״̬*/
	public void updateNoteState(int mid,int id,int state,int agent)throws NoteHasUpdatedException,Exception;
	/**���ļ�¼״̬��ԭ��*/
	public void updateNoteState(int mid,int id,int state,String result,int agent)throws NoteHasUpdatedException,Exception;
	/**ѧ��ɾ����¼,ֻ����ɾ����ʼ״̬�ļ�¼������������󲻿�ɾ��*/
	public void delNote(int id)throws DoesntDelException,Exception;
	/**ѧ����ȡ���еļ�¼ */
	public List<LeaveNote> getAllNoteBySid(int sid,int first,int max)throws Exception;
	
	/**�쵼��ȡ���е� ��¼ */
	public List<LeaveNote> getAllNote(int college,int first,int num)throws Exception;
	/**ͬ�����*/
	public void allowNote(int actor,int college,int mid,int id)throws AccessRefuseException,NoteHasUpdatedException,Exception;
	/**�ܾ����*/
	public void refuseNote(int actor,int college,int mid,int id)throws AccessRefuseException,NoteHasUpdatedException,Exception;
	/**�ܾ����*/
	public void refuseNote(int actor,int college,int mid,int id,String result)throws AccessRefuseException,NoteHasUpdatedException,Exception;
}
