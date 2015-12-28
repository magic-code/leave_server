package com.hgd.leave.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.hgd.leave.constants.Constants;
import com.hgd.leave.domain.LeaveNote;
import com.hgd.leave.domain.ResultMes;
import com.hgd.leave.exceptions.AccessRefuseException;
import com.hgd.leave.exceptions.HasNoteExistException;
import com.hgd.leave.exceptions.NoteHasUpdatedException;
import com.hgd.leave.service.ILeaveService;

public class LeaveAction {
	
	private ILeaveService leaveservice;
	private ResultMes mes;
	private ResultMes allowmes;
	private ResultMes waitmes;
	private ResultMes allowedmes;
	private ResultMes allmes;
	public ResultMes getWaitmes() {
		return waitmes;
	}
	public void setWaitmes(ResultMes waitmes) {
		this.waitmes = waitmes;
	}
	public ResultMes getAllowedmes() {
		return allowedmes;
	}
	public void setAllowedmes(ResultMes allowedmes) {
		this.allowedmes = allowedmes;
	}
	public ResultMes getAllmes() {
		return allmes;
	}
	public void setAllmes(ResultMes allmes) {
		this.allmes = allmes;
	}
	public ResultMes getAllowmes() {
		return allowmes;
	}
	public void setAllowmes(ResultMes allowmes) {
		this.allowmes = allowmes;
	}
	public ResultMes getMes() {
		return mes;
	}
	public void setMes(ResultMes mes) {
		this.mes = mes;
	}
	public ILeaveService getLeaveservice() {
		return leaveservice;
	}
	@Resource
	public void setLeaveservice(ILeaveService leaveservice) {
		this.leaveservice = leaveservice;
	}
	
	
	public String createNote(){	//创建新的记录   starttime,endtime,reason,location,locationstr
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mes = new ResultMes();
		HttpServletRequest requ = ServletActionContext.getRequest();
		HttpSession session = requ.getSession();
		String sstartTime = requ.getParameter("starttime");
		String sendTime = requ.getParameter("endtime");
		String sreason = requ.getParameter("reason");
		String slocation = requ.getParameter("location");
		String locationstr = requ.getParameter("locationstr");
		
		if(sstartTime==null || sendTime==null || sreason==null || slocation==null
				|| locationstr==null ){
			mes.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		Date starttime,endtime;
		try{
			starttime = format.parse(sstartTime);
			endtime = format.parse(sendTime);
		}catch(Exception e){
			e.printStackTrace();
			mes.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		int actor = (Integer) session.getAttribute("actor");
		if(actor==Constants.ACTOR__FDY || actor == Constants.ACTOR_SHUJI){
			mes.setCode(Constants.SERV_ERROR);
			return "error";
		}
		int college = (Integer)session.getAttribute("college");
		int sid = (Integer)session.getAttribute("id");
		LeaveNote note =  new LeaveNote();
		note.setCollege(college);
		note.setSid(sid);
		note.setReason(sreason);
		note.setStarttime(starttime);
		note.setEndtime(endtime);
		note.setState(0);
		note.setCreatetime(new Date());
		try {
			LeaveNote no = leaveservice.createNote(sid, note);
			HashMap map = new HashMap();
			map.put("info", no);
			mes.setData(map);
			mes.setCode(Constants.STATE_OK);	
			return "success";
		} catch (HasNoteExistException e) {
			e.printStackTrace();
			mes.setCode(Constants.NOTE_NOFINISH_EXIST);
			return "error";
		} catch (Exception e) {
			e.printStackTrace();
			mes.setCode(Constants.SERV_ERROR);
			return "error";
		}
	}
	
	public String allowNote(){	//同意或拒绝请假   id,allow,result
		allowmes = new ResultMes();
		HttpServletRequest requ = ServletActionContext.getRequest();
		HttpSession session = requ.getSession();
		String strid = requ.getParameter("id");
		String sallow = requ.getParameter("allow");
		String sresult = requ.getParameter("result");
		if(strid==null || sallow==null){
			allowmes.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		int allow=0,id=0;
		try{
			allow = Integer.parseInt(sallow);
			id = Integer.parseInt(strid);
		}catch(Exception e){
			e.printStackTrace();
			allowmes.setCode(Constants.PARAM_FORMAT_ERR);
		}
		int college = (Integer)session.getAttribute("college");
		int mid = (Integer)session.getAttribute("id");
		int actor = (Integer)session.getAttribute("actor");
		if (allow==Constants.ALLOW) {
			try {
				leaveservice.allowNote(actor, college, mid, id);
				allowmes.setCode(Constants.STATE_OK);
				return "success";
			} catch (AccessRefuseException e) {
				e.printStackTrace();
				allowmes.setCode(Constants.ACCESS_REFUSE);
				return "error";
			}catch(NoteHasUpdatedException e){
				e.printStackTrace();
				allowedmes.setCode(Constants.NOTE_HAS_UPDATED);
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
				allowmes.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else if(allow==Constants.REFUSE){
			if (sresult==null || sresult.trim().equals("")) {
				try {
					leaveservice.refuseNote(actor, college, mid, id);
					allowmes.setCode(Constants.STATE_OK);
					return "success";
				} catch (AccessRefuseException e) {
					e.printStackTrace();
					allowmes.setCode(Constants.ACCESS_REFUSE);
					return "error";
				}catch(NoteHasUpdatedException e){
					e.printStackTrace();
					allowedmes.setCode(Constants.NOTE_HAS_UPDATED);
					return "error";
				} catch (Exception e) {
					e.printStackTrace();
					allowmes.setCode(Constants.SERV_ERROR);
					return "error";
				}
			}else{
				try {
					leaveservice.refuseNote(actor, college, mid,id,sresult);
					allowmes.setCode(Constants.STATE_OK);
					return "success";
				} catch (AccessRefuseException e) {
					e.printStackTrace();
					allowmes.setCode(Constants.ACCESS_REFUSE);
					return "error";
				}catch(NoteHasUpdatedException e){
					e.printStackTrace();
					allowedmes.setCode(Constants.NOTE_HAS_UPDATED);
					return "error";
				} catch (Exception e) {
					e.printStackTrace();
					allowmes.setCode(Constants.SERV_ERROR);
					return "error";
				}
			}
		}else{
			allowmes.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		
		
	}
	
	public String getWaitAllowNote(){//取等待审核的记录 start,num
		waitmes = new ResultMes();
		HttpServletRequest requ = ServletActionContext.getRequest();
		HttpSession session = requ.getSession();
		String sstart = requ.getParameter("start");
		String snum = requ.getParameter("num");
		if(sstart==null || snum==null){
			waitmes.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		int start=0,num=0;
		try{
			start = Integer.parseInt(sstart);
			num = Integer.parseInt(snum);
		}catch(Exception e){
			e.printStackTrace();
			waitmes.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		int actor = (Integer)session.getAttribute("actor");
		if(actor==Constants.ACTOR_STUDENT){
			int sid = (Integer)session.getAttribute("id");
			try {
				List<LeaveNote> notes = leaveservice.getInitNoteBySid(sid);
				HashMap map = new HashMap();
				map.put("info",notes);
				waitmes.setData(map);
				waitmes.setCode(Constants.STATE_OK);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				waitmes.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else if(actor==Constants.ACTOR__FDY){
			int college = (Integer)session.getAttribute("college");
			try {
				List<LeaveNote> notes = leaveservice.getInitNote(college, start,num);
				HashMap map = new HashMap();
				map.put("info",notes);
				waitmes.setData(map);
				waitmes.setCode(Constants.STATE_OK);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				waitmes.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else if(actor==Constants.ACTOR_SHUJI){
			int college = (Integer)session.getAttribute("college");
			try {
				List<LeaveNote> notes = leaveservice.getFdyAllowNote(college, start, num);
				HashMap map = new HashMap();
				map.put("info",notes);
				waitmes.setData(map);
				waitmes.setCode(Constants.STATE_OK);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				waitmes.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else{
			waitmes.setCode(Constants.SERV_ERROR);
			return "error";
		}
	}
	
	public String getAllowedNote(){	//取已同意的记录 start num
		allowedmes = new ResultMes();
		HttpServletRequest requ = ServletActionContext.getRequest();
		HttpSession session = requ.getSession();
		String sstart = requ.getParameter("start");
		String snum = requ.getParameter("num");
		if(sstart==null || snum==null){
			allowedmes.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		int start=0,num=0;
		try{
			start = Integer.parseInt(sstart);
			num = Integer.parseInt(snum);
		}catch(Exception e){
			e.printStackTrace();
			allowedmes.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		int actor = (Integer)session.getAttribute("actor");
		if(actor==Constants.ACTOR_STUDENT){
			int sid = (Integer)session.getAttribute("id");
			try {
				List<LeaveNote> notes = leaveservice.getAllowedNoteBySid(sid,start,num);
				HashMap map = new HashMap();
				map.put("info",notes);
				allowedmes.setData(map);
				allowedmes.setCode(Constants.STATE_OK);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				allowedmes.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI){
			int college = (Integer)session.getAttribute("college");
			try {
				List<LeaveNote> notes = leaveservice.getAllowedNote(college, start, num);
				HashMap map = new HashMap();
				map.put("info",notes);
				allowedmes.setData(map);
				allowedmes.setCode(Constants.STATE_OK);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				allowedmes.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else{
			allowedmes.setCode(Constants.SERV_ERROR);
			return "error";
		}
		
	}
	
	public String getAllNote(){	//取全部记录  start,num
		allmes = new ResultMes();
		HttpServletRequest requ = ServletActionContext.getRequest();
		HttpSession session = requ.getSession();
		String sstart = requ.getParameter("start");
		String snum = requ.getParameter("num");
		if(sstart==null || snum==null){
			allmes.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		int start=0,num=0;
		try{
			start = Integer.parseInt(sstart);
			num = Integer.parseInt(snum);
		}catch(Exception e){
			e.printStackTrace();
			allmes.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		int actor = (Integer)session.getAttribute("actor");
		if(actor==Constants.ACTOR_STUDENT){
			int sid = (Integer)session.getAttribute("id");
			try {
				List<LeaveNote> notes = leaveservice.getAllNoteBySid(sid, start, num);
				HashMap map = new HashMap();
				map.put("info",notes);
				allmes.setData(map);
				allmes.setCode(Constants.STATE_OK);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				allmes.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI){
			int college = (Integer)session.getAttribute("college");
			try {
				List<LeaveNote> notes = leaveservice.getAllNote(college, start, num);
				HashMap map = new HashMap();
				map.put("info",notes);
				allmes.setData(map);
				allmes.setCode(Constants.STATE_OK);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				allmes.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else{
			allmes.setCode(Constants.SERV_ERROR);
			return "error";
		}
	}
	
}
