package com.hgd.leave.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="stuinfo")
public class StudentInfo {
	private int id;
	/**学号（用户名）*/
	private String uname;
	/**密码*/
	private String passwd;
	/**真实姓名*/
	private String name;
	/**身份证号*/
	private String idcard;
	/**电话*/
	private String phone;
	/**父母电话*/
	private String parentphone;
	/**家庭住址*/
	private String address;
	/**院系*/
	private int college;
	/**账号锁定状态*/
	private int locked;
	/**头像路径*/
	private String headimg;
	
	/**父母姓名*/
	private String parentname;
	/**性别*/
	private int sex;
	/**角色*/
	private int actor;
	
	public int getActor() {
		return actor;
	}
	public void setActor(int actor) {
		this.actor = actor;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getParentphone() {
		return parentphone;
	}
	public void setParentphone(String parentphone) {
		this.parentphone = parentphone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCollege() {
		return college;
	}
	public void setCollege(int college) {
		this.college = college;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
}
