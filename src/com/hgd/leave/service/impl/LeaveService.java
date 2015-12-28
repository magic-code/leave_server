package com.hgd.leave.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.StaleObjectStateException;

import com.hgd.leave.constants.Constants;
import com.hgd.leave.dao.ILeaveNoteDao;
import com.hgd.leave.domain.LeaveNote;
import com.hgd.leave.exceptions.AccessRefuseException;
import com.hgd.leave.exceptions.DoesntDelException;
import com.hgd.leave.exceptions.HasNoteExistException;
import com.hgd.leave.exceptions.NoteHasUpdatedException;
import com.hgd.leave.service.ILeaveService;
import com.sun.org.apache.xml.internal.utils.Hashtree2Node;

public class LeaveService implements ILeaveService {
	private ILeaveNoteDao leavedao;
	
	public ILeaveNoteDao getLeavedao() {
		return leavedao;
	}
	@Resource
	public void setLeavedao(ILeaveNoteDao leavedao) {
		this.leavedao = leavedao;
	}

	@Override
	public LeaveNote createNote(int sid, LeaveNote note)
			throws HasNoteExistException, Exception {
		int c = leavedao.getNoFinishCount(sid);
		if(c>=1)
			throw new HasNoteExistException();
		leavedao.add(note);
		LeaveNote n = leavedao.getNewestNote(sid);
		return n;
	}

	@Override
	public void delNote(int id) throws DoesntDelException,Exception {
		LeaveNote note = leavedao.select(id);
		int state = note.getState();
		if (state==0) {
			leavedao.delNoteById(id);
		}else{
			throw new DoesntDelException();
		}
	}

	@Override
	public List<LeaveNote> getAllowedNote(int college, int first, int max)
			throws Exception {
		return leavedao.getAllowNote(college, first, max);
	}

	@Override
	public List<LeaveNote> getAllowedNoteBySid(int sid,int first,int num) throws Exception {
		return leavedao.getAllowNoteBySid(sid,first,num);
	}

	@Override
	public List<LeaveNote> getDefinedNoteBySid(int sid,int first,int num) throws Exception {
		return leavedao.getDefinedNoteBySid(sid,first,num);
	}

	@Override
	public List<LeaveNote> getFdyAllowNote(int college, int first, int max)
			throws Exception {
		return leavedao.getFdyAllowNote(college, first, max);
	}

	@Override
	public List<LeaveNote> getInitNote(int college, int first, int max)
			throws Exception {
		return leavedao.getAllAllowingNote(college, first, max);
	}

	@Override
	public List<LeaveNote> getInitNoteBySid(int sid) throws Exception {
		return leavedao.getAllAllowingNoteBySid(sid);
	}

	@Override
	public List<LeaveNote> getNoFinishBySid(int sid) throws Exception {
		return leavedao.getNoFinishNoteBySid(sid);
	}

	@Override
	public List<LeaveNote> getNoFinishNote(int college, int first, int max)
			throws Exception {
		return leavedao.getNoFinishNote(college, first, max);
	}

	@Override
	public void updateNoteState(int mid,int id, int state,int agent) throws NoteHasUpdatedException,Exception {
		try{
			leavedao.updateNoteState(mid,id, state,agent);
		}catch(StaleObjectStateException e){
			throw new NoteHasUpdatedException();
		}
	}

	@Override
	public void updateNoteState(int mid,int id, int state, String result,int agent)
			throws NoteHasUpdatedException,Exception {
		try{
			leavedao.updateNoteState(mid,id, state,result,agent);
		}catch(StaleObjectStateException e){
			throw new NoteHasUpdatedException();
		}
	}
	@Override
	public List<LeaveNote> getAllNoteBySid(int sid, int first, int max)
			throws Exception {
		return leavedao.getNoteBySid(sid, first, max);
	}
	@Override
	public void allowNote(int actor,int college, int mid, int id)
			throws AccessRefuseException,NoteHasUpdatedException, Exception {
		LeaveNote tnote = leavedao.select(id);
		if(tnote.getCollege()!=college){
			throw new AccessRefuseException();
		}
		int agen=0,sta=0;
		if (actor==Constants.ACTOR__FDY) {
			agen=1;
			sta=2;
		}else if(actor==Constants.ACTOR_SHUJI){
			agen = 2;
			sta=4;
		}else{
			throw new Exception();
		}
		updateNoteState(mid, id, sta, agen);
		
	}
	@Override
	public void refuseNote(int actor,int college, int mid, int id)
			throws AccessRefuseException,NoteHasUpdatedException, Exception {
		LeaveNote tnote = leavedao.select(id);
		if(tnote.getCollege()!=college){
			throw new AccessRefuseException();
		}
		int agen=0,sta=0;
		if (actor==Constants.ACTOR__FDY) {
			agen=1;
			sta=1;
		}else if(actor==Constants.ACTOR_SHUJI){
			agen = 2;
			sta=3;
		}else{
			throw new Exception();
		}
		updateNoteState(mid, id, sta, agen);
		
	}
	@Override
	public void refuseNote(int actor,int college, int mid, int id, String result)
			throws AccessRefuseException,NoteHasUpdatedException, Exception {
		LeaveNote tnote = leavedao.select(id);
		if(tnote.getCollege()!=college){
			throw new AccessRefuseException();
		}
		int agen=0,sta=0;
		if (actor==Constants.ACTOR__FDY) {
			agen=1;
			sta=1;
		}else if(actor==Constants.ACTOR_SHUJI){
			agen = 2;
			sta=3;
		}else{
			throw new Exception();
		}
		updateNoteState(mid, id, sta,result ,agen);
	}
	@Override
	public List<LeaveNote> getAllNote(int college, int first, int num)
			throws Exception {
		return leavedao.getAllAllowingNote(college, first, num);
	}

}
