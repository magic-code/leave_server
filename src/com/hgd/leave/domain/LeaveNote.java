package com.hgd.leave.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
@Entity
@Table(name="leavenote")
public class LeaveNote {
	private int id;
	private int sid;
	private Date createtime;
	private Date starttime;
	private Date endtime;
	private String reason;
	private String location;	//坐标经纬
	private int state;
	private String result;	//反馈结果
	private int agent1;
	private int agent2;
	private String locationstr;	//坐标地点
	private int college;
	@Version
	private int version;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getCollege() {
		return college;
	}
	public void setCollege(int college) {
		this.college = college;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getAgent1() {
		return agent1;
	}
	public void setAgent1(int agent1) {
		this.agent1 = agent1;
	}
	public int getAgent2() {
		return agent2;
	}
	public void setAgent2(int agent2) {
		this.agent2 = agent2;
	}
	public String getLocationstr() {
		return locationstr;
	}
	public void setLocationstr(String locationstr) {
		this.locationstr = locationstr;
	}
	
	
}
