package com.hgd.leave.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import com.hgd.leave.constants.Constants;
import com.hgd.leave.domain.ResultMes;
import com.hgd.leave.domain.StudentInfo;
import com.hgd.leave.domain.TeacherInfo;
import com.hgd.leave.exceptions.AccountLockedException;
import com.hgd.leave.exceptions.NoHeadImgException;
import com.hgd.leave.exceptions.NoThisFileException;
import com.hgd.leave.exceptions.UserExistException;
import com.hgd.leave.exceptions.UserNotExitException;
import com.hgd.leave.exceptions.UserPasswdErrorException;
import com.hgd.leave.service.IStudentInfoService;
import com.hgd.leave.service.ITeacherInfoService;
import com.hgd.leave.utils.InfoBeanUtil;


public class UserAction {
	
	private IStudentInfoService stuservice;
	private ITeacherInfoService teacservice;
	
	public ITeacherInfoService getTeacservice() {
		return teacservice;
	}
	@Resource
	public void setTeacservice(ITeacherInfoService teacservice) {
		this.teacservice = teacservice;
	}
	@Resource
	public void setStuservice(IStudentInfoService stuservice) {
		this.stuservice = stuservice;
	}
	public IStudentInfoService getStuservice() {
		return stuservice;
	}
	
	private ResultMes regResult;
	private ResultMes loginResult;
	private ResultMes loadimgResult;
	private ResultMes getimgResult;
	private InputStream imageStream;
	public InputStream getImageStream() {
		return imageStream;
	}
	public void setImageStream(InputStream imageStream) {
		this.imageStream = imageStream;
	}
	public ResultMes getRegResult() {
		return regResult;
	}
	public void setRegResult(ResultMes regResult) {
		this.regResult = regResult;
	}
	public ResultMes getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(ResultMes loginResult) {
		this.loginResult = loginResult;
	}
	public ResultMes getLoadimgResult() {
		return loadimgResult;
	}
	public void setLoadimgResult(ResultMes loadimgResult) {
		this.loadimgResult = loadimgResult;
	}
	public ResultMes getGetimgResult() {
		return getimgResult;
	}
	public void setGetimgResult(ResultMes getimgResult) {
		this.getimgResult = getimgResult;
	}

	

	
	
	public String regist() throws UnsupportedEncodingException{
		HttpServletRequest req = ServletActionContext.getRequest();
		regResult = new ResultMes();
		String uname = req.getParameter("uname");
		String passwd = req.getParameter("passwd");
		String name = req.getParameter("name");
		String idcard = req.getParameter("idcard");
		String phone = req.getParameter("phone");
		String parentphone = req.getParameter("parentphone");
		String address = req.getParameter("address");
		String tcol = req.getParameter("college");
		String parentname = req.getParameter("parentname");
		String ssex = req.getParameter("sex");
		System.out.println(name+"****"+uname);
		String sactor = req.getParameter("actor");
		int actor =1;
		if(sactor==null || sactor.trim().equals("")){
			regResult.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		try{
			actor = Integer.valueOf(sactor);
		}catch(Exception e){
			e.printStackTrace();
			regResult.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		if(actor==Constants.ACTOR_STUDENT){	//学生
			if(uname==null || passwd==null || name==null || idcard==null || phone==null ||
					parentphone==null || address==null || tcol==null ||
					parentname==null || ssex==null){
				regResult.setCode(Constants.INFO_NOT_FULL);
				return "error";
			}
		}else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI){//书记 辅导员
			if(uname==null || passwd==null || name==null || phone==null || tcol==null || ssex==null){
				regResult.setCode(Constants.INFO_NOT_FULL);
				return "error";
			}
		}else{
			regResult.setCode(Constants.NO_THIS_ACTOR);
			return "error";
		}
		try {
			if(!InfoBeanUtil.validateInfo(uname, passwd, idcard, phone, parentphone, address, tcol,ssex)){
				regResult.setCode(Constants.PARAM_FORMAT_ERR);
				return "error";
			}
		} catch (Exception e1) {
			regResult.setCode(Constants.PARAM_FORMAT_ERR);
			e1.printStackTrace();
			return "error";
		}
		int college = Integer.valueOf(tcol);
		int sex = Integer.valueOf(ssex);
		if(actor==Constants.ACTOR_STUDENT){
			StudentInfo stu = new StudentInfo();
			stu.setUname(uname);
			stu.setPasswd(passwd);
			stu.setIdcard(idcard);
			stu.setAddress(address);
			stu.setName(name);
			stu.setPhone(phone);
			stu.setParentname(parentname);
			stu.setParentphone(parentphone);
			stu.setCollege(college);
			stu.setSex(sex);
			stu.setActor(Constants.ACTOR_STUDENT);
			try {
				stuservice.registe(stu);
			} catch (UserExistException e) {
				e.printStackTrace();
				regResult.setCode(Constants.UNAME_EXISITED);
				return "error";
			}catch(Exception e){
				e.printStackTrace();
				regResult.setCode(Constants.SERV_ERROR);
				return "error";
			}
			regResult.setCode(Constants.STATE_OK);
			return "success";
		}else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI){
			TeacherInfo tinfo = new TeacherInfo();
			tinfo.setUname(uname);
			tinfo.setPasswd(passwd);
			tinfo.setPhone(phone);
			tinfo.setCollege(college);
			tinfo.setActor(actor);
			tinfo.setName(name);
			tinfo.setSex(sex);
			
			try {
				teacservice.registe(tinfo);
			} catch (UserExistException e) {
				e.printStackTrace();
				regResult.setCode(Constants.UNAME_EXISITED);
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
				regResult.setCode(Constants.SERV_ERROR);
				return "error";
			}
			regResult.setCode(Constants.STATE_OK);
			return "success";
		}
		regResult.setCode(Constants.NO_THIS_ACTOR);
		return "error";
	}
	
	public String uploadHimg(){
		HttpServletRequest req = ServletActionContext.getRequest();
		loadimgResult = new ResultMes();
		HttpSession session = req.getSession();
		String uname = (String) session.getAttribute("uname");
		int actor = (Integer) session.getAttribute("actor");
		if(ServletFileUpload.isMultipartContent(req)){
			DiskFileItemFactory factory = new DiskFileItemFactory();
			File temp = new File(System.getProperty("user.home"),"/temp");
			if(!temp.exists())
				temp.mkdirs();
			factory.setRepository(temp);
			factory.setSizeThreshold(1024*1024*2);//2mb buf
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setFileSizeMax(1024*1024*2);
			fileUpload.setSizeMax(1024*1024*3);
			List<FileItem> items;
			try {
				items = fileUpload.parseRequest(req);
				if(items!=null){
					for(FileItem item:items){
						if(!item.isFormField()){
							File path = new File(System.getProperty("user.home"),"/himgs");
							if(!path.exists())
								path.mkdirs();
							String fname = item.getName();
							String extra = fname.substring(fname.lastIndexOf("."));
							String filename = uname+System.currentTimeMillis()+extra;
							try {
								item.write(new File(path,filename));
								if(actor==Constants.ACTOR_STUDENT)
									stuservice.changeHimg(uname, filename);
								else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI)
									teacservice.changeHimg(uname, filename);
								loadimgResult.setCode(Constants.STATE_OK);
								HashMap map = new HashMap();
								map.put("filename", filename);
								loadimgResult.setData(map);
								return "success";
							} catch (Exception e) {
								e.printStackTrace();
								loadimgResult.setCode(Constants.SERV_ERROR);
								return "error";
							}
						}
					}
				}else{
					loadimgResult.setCode(Constants.NO_FILE);
					return "error";
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
				loadimgResult.setCode(Constants.FILE_UPLOAD_FAIL);
				return "error";
			}
			
		}else{
			loadimgResult.setCode(Constants.NO_FILE);
			return "error";
		}
		loadimgResult.setCode(Constants.NO_FILE);
		return "error";

	}
	
	public String login(){
		loginResult = new ResultMes();
		HttpServletRequest req = ServletActionContext.getRequest();
		String uname = req.getParameter("uname");
		String passwd = req.getParameter("passwd");
		String sactor = req.getParameter("actor");
		if(uname==null){
			loginResult.setCode(Constants.NO_UNAME);
			return "error";
		}
		if (passwd==null) {
			loginResult.setCode(Constants.PASSWD_ERROR);
			return "error";
		}
		int actor=Constants.ACTOR_STUDENT;
		try{
			actor = Integer.valueOf(sactor);
		}catch(Exception e){
			loginResult.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		if(actor==Constants.ACTOR_STUDENT){	//学生登陆
			try {
				stuservice.validateUnameAndPasswd(uname, passwd);
				//登陆成功
				stuservice.validateAccountState(uname);	//验证账号是否是锁定状态
				StudentInfo info = stuservice.getStuInfo(uname);
				HttpSession session = req.getSession();
				session.setAttribute("uname", uname);	//记录登陆的用户名信息
				session.setAttribute("actor",info.getActor());	//记录登陆的用户角色信息
				loginResult.setCode(Constants.STATE_OK);
				HashMap datas = new HashMap();
				datas.put("info", info);
				loginResult.setData(datas);
				return "success";
			} catch (UserNotExitException e) {
				e.printStackTrace();
				loginResult.setCode(Constants.NO_UNAME);
				return "error";
			} catch (UserPasswdErrorException e) {
				e.printStackTrace();
				loginResult.setCode(Constants.PASSWD_ERROR);
				return "error";
			}catch(AccountLockedException e){
				e.printStackTrace();
				loginResult.setCode(Constants.ERR_ACCOUNT_LOCKED);
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
				loginResult.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI){	//老师 辅导员 书记 登陆 
			try {
				teacservice.validateUnameAndPasswd(uname, passwd);
				//登陆成功
				teacservice.validateAccountState(uname);	//验证账号是否是锁定状态
				TeacherInfo info = teacservice.getTeacherInfo(uname);
				HttpSession session = req.getSession();
				session.setAttribute("uname", uname);	//记录登陆的用户名信息
				session.setAttribute("actor",info.getActor());	//记录登陆的用户角色信息
				loginResult.setCode(Constants.STATE_OK);
				HashMap datas = new HashMap();
				datas.put("info", info);
				loginResult.setData(datas);
				return "success";
			} catch (UserNotExitException e) {
				e.printStackTrace();
				loginResult.setCode(Constants.NO_UNAME);
				return "error";
			} catch (UserPasswdErrorException e) {
				e.printStackTrace();
				loginResult.setCode(Constants.PASSWD_ERROR);
				return "error";
			}catch(AccountLockedException e){
				e.printStackTrace();
				loginResult.setCode(Constants.ERR_ACCOUNT_LOCKED);
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
				loginResult.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}
		loginResult.setCode(Constants.NO_THIS_ACTOR);
		return "error";
	}
	
	public String getHimg(){	//get
		getimgResult = new ResultMes();
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse respon = ServletActionContext.getResponse();
		String uname = req.getParameter("uname");
		String sactor = req.getParameter("actor"); 
		if(uname==null || sactor==null){
			getimgResult.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		try{
			Integer.valueOf(sactor);
		}catch(Exception e){
			getimgResult.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		int actor = Integer.valueOf(sactor);
		if(actor==1){
			try {
				File file = stuservice.getHimg(uname);
				imageStream = new FileInputStream(file);
				return "success";
			} catch (NoHeadImgException e) {
				e.printStackTrace();
				getimgResult.setCode(Constants.NO_HEADIMG);
				return "error";
			} catch (NoThisFileException e) {
				e.printStackTrace();
				getimgResult.setCode(Constants.NO_HEADIMG);
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
				getimgResult.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}else{
			try {
				File file = teacservice.getHimg(uname);
				imageStream = new FileInputStream(file);
				return "success";
			} catch (NoHeadImgException e) {
				e.printStackTrace();
				getimgResult.setCode(Constants.NO_HEADIMG);
				return "error";
			} catch (NoThisFileException e) {
				e.printStackTrace();
				getimgResult.setCode(Constants.NO_HEADIMG);
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
				getimgResult.setCode(Constants.SERV_ERROR);
				return "error";
			}
		}
	}
}
