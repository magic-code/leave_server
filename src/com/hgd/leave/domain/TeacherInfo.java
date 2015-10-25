package com.hgd.leave.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="teacherinfo")
public class TeacherInfo {
	private int id;
	/**用户名*/
	private String uname;
	/**密码*/
	private String passwd;
	/**院系*/
	private int college;
	/**角色*/
	private int actor;
	/**真实姓名*/
	private String name;
	/**电话*/
	private String phone;
	/**账号锁定状态*/
	private int locked;
	/**头像路径*/
	private String headimg;
	/**性别*/
	private int sex;
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
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
	public int getCollege() {
		return college;
	}
	public void setCollege(int college) {
		this.college = college;
	}
	public int getActor() {
		return actor;
	}
	public void setActor(int actor) {
		this.actor = actor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
}
