package com.hgd.leave.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.hgd.leave.dao.ILeaveNoteDao;
import com.hgd.leave.domain.LeaveNote;

public class LeaveNoteDao extends BaseDao<LeaveNote> implements ILeaveNoteDao {
	
	@Override
	public List<LeaveNote> getAllAllowingNoteBySid(int sid) throws Exception {
		//state==0
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where sid=? and state in(0,2)";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, sid);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

	@Override
	public List<LeaveNote> getAllowNoteBySid(int sid,int first,int num) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where sid=? and state>4";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, sid);
		q.setFirstResult(first).setMaxResults(num);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

	@Override
	public List<LeaveNote> getDefinedNoteBySid(int sid,int first,int num) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where sid=? and state in(1,3)";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, sid);
		q.setFirstResult(first).setMaxResults(num);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

/*	@Override
	public List<LeaveNote> getFdyAllowNote(int sid) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where sid=? and state=2";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, sid);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}*/

	@Override
	public List<LeaveNote> getNoteBySid(int sid) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where sid=?";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, sid);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}


	@Override
	public void delNoteById(int id) throws Exception {
		String hql="delete from  LeaveNote where id=?";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, id);
		q.executeUpdate();
	}

	@Override
	public List<LeaveNote> getAllAllowingNote(int college,int first,int max) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where state=0 and college=? order by createtime desc";
		Query q = this.getSession().createQuery(hql);
		q.setFirstResult(first).setMaxResults(max);
		q.setParameter(0, college);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

	@Override
	public List<LeaveNote> getAllowNote(int college,int first,int max) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where state>4 and college=? order by createtime desc";
		Query q = this.getSession().createQuery(hql);
		q.setFirstResult(first).setMaxResults(max);
		q.setParameter(0, college);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

	@Override
	public List<LeaveNote> getDefinedNote(int college,int first,int max) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where state in(1,3) and college=? order by createtime desc";
		Query q = this.getSession().createQuery(hql);
		q.setFirstResult(first).setMaxResults(max);
		q.setParameter(0, college);
		
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

	@Override
	public List<LeaveNote> getFdyAllowNote(int college,int first,int max) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where state=2 and college=? order by createtime desc";
		Query q = this.getSession().createQuery(hql);
		q.setFirstResult(first).setMaxResults(max);
		q.setParameter(0, college);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

	@Override
	public List<LeaveNote> getNoFinishNoteBySid(int sid) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where sid=? and state=6";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, sid);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

	@Override
	public List<LeaveNote> getNoFinishNote(int college,int first,int max) throws Exception {
		ArrayList<LeaveNote> list;
		String hql = "select * from LeaveNote where state=6 and college=? order by createtime desc";
		Query q = this.getSession().createQuery(hql);
		q.setFirstResult(first).setMaxResults(max);
		q.setParameter(0, college);
		list = (ArrayList<LeaveNote>) q.list();
		return list;
	}

	@Override
	public void updateNoteState(int mid,int id, int state,int agent) throws Exception {
		String hql;
		if(agent==1){
			 hql = "update LeaveNote set state=?,agent1=? where id=?";
		}else{
			hql = "update LeaveNote set state=?,agent2=? where id=?";
		}
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, state);
		q.setParameter(1, mid);
		q.setParameter(2, id);
		q.executeUpdate();
	}

	@Override
	public void updateNoteState(int mid,int id, int state, String result,int agent)
			throws Exception {
		String hql;
		if(agent==1){
			 hql = "update LeaveNote set state=?,agent1=?,result=? where id=?";
		}else{
			hql = "update LeaveNote set state=?,agent2=?,result=? where id=?";
		}
		
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, state);
		q.setParameter(1, mid);
		q.setParameter(2, result);
		q.setParameter(3, id);
		q.executeUpdate();
	}

	@Override
	public int getNoFinishCount(int sid) throws Exception {
		String hql = "select count(*) from LeaveNote where sid=? and state not in(1,3,6,7)";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, sid);
		int count = ((Number)(q.uniqueResult())).intValue();
		return count;
	}

	@Override
	public List<LeaveNote> getNoteBySid(int sid, int first, int max)
			throws Exception {
		String hql ="select * from LeaveNote where sid=? order by createtime desc";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, sid);
		q.setFirstResult(first).setMaxResults(max);
		return q.list();
	}

	@Override
	public LeaveNote getNewestNote(int sid) throws Exception {
		String hql="select * from LeaveNote where sid=? order by createtime desc";
		Query q = this.getSession().createQuery(hql);
		q.setFirstResult(0).setMaxResults(1);
		return (LeaveNote)q.uniqueResult();
	}

	@Override
	public List<LeaveNote> getAAllNote(int college, int first, int num)
			throws Exception {
		String hql ="select * from LeaveNote where college=? order by createtime desc";
		Query q = this.getSession().createQuery(hql);
		q.setParameter(0, college);
		q.setFirstResult(first).setMaxResults(num);
		return q.list();
	}

}
