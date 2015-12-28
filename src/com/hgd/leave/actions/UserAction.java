package com.hgd.leave.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
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
import com.opensymphony.xwork2.ActionSupport;


public class UserAction extends ActionSupport{
	
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
	private ResultMes updatePass;
	private ResultMes info;
	
	private File file;
	private String fileFileName;
	private String fileContentType;
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public void setInfo(ResultMes info) {
		this.info = info;
	}
	public ResultMes getUpdatePass() {
		return updatePass;
	}
	public void setUpdatePass(ResultMes updatePass) {
		this.updatePass = updatePass;
	}

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
		String clazz = req.getParameter("clazz");
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
					parentname==null || ssex==null || clazz==null){
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
			stu.setClazz(clazz);
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
	
	/*
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
								System.out.println("33333333333333");
								e.printStackTrace();
								loadimgResult.setCode(Constants.SERV_ERROR);
								return "error";
							}
						}else{
							System.out.println("55555555555555");
						}
					}
				}else{
					System.out.println("000000000000");
					loadimgResult.setCode(Constants.NO_FILE);
					return "error";
				}
			} catch (FileUploadException e) {
				System.out.println("222222222");
				e.printStackTrace();
				loadimgResult.setCode(Constants.FILE_UPLOAD_FAIL);
				return "error";
			}
			
		}else{
			System.out.println("-------------");
			loadimgResult.setCode(Constants.NO_FILE);
			return "error";
		}
		System.out.println("******");
		loadimgResult.setCode(Constants.NO_FILE);
		return "error";

	}
	*/
	
	
	
	
	public String uploadHimg(){
		HttpServletRequest req = ServletActionContext.getRequest();
		loadimgResult = new ResultMes();
		HttpSession session = req.getSession();
		String uname = (String) session.getAttribute("uname");
		int actor = (Integer) session.getAttribute("actor");
		File path = new File(System.getProperty("user.home"),"/himgs");
		if(!path.exists())
			path.mkdirs();
		String extra = fileFileName.substring(fileFileName.lastIndexOf("."));
		String filename = System.currentTimeMillis()+extra;
        InputStream is = null;
        OutputStream os = null;
		try {
			is = new FileInputStream(file);
			os = new FileOutputStream(new File(path, filename));
		} catch (FileNotFoundException e1) {
		}
        
        
        System.out.println("fileFileName: " + filename);

        // 因为file是存放在临时文件夹的文件，我们可以将其文件名和文件路径打印出来，看和之前的fileFileName是否相同
        System.out.println("file: " + file.getName());
        System.out.println("file: " + file.getPath());
        
        byte[] buffer = new byte[500];
        int length = 0;
        
        try {
			while(-1 != (length = is.read(buffer, 0, buffer.length)))
			{
			    os.write(buffer,0,length);
			}
			os.close();
	        is.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        try{
	        if(actor==Constants.ACTOR_STUDENT)
				stuservice.changeHimg(uname, filename);
			else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI)
				teacservice.changeHimg(uname, filename);
			loadimgResult.setCode(Constants.STATE_OK);
			HashMap map = new HashMap();
			map.put("filename", filename);
			loadimgResult.setData(map);
			return "success";
        }catch (Exception e) {
        	e.printStackTrace();
        	loadimgResult.setCode(Constants.SERV_ERROR);
        	return "error";
		}
        
        
	}
	
	
	public String getInfo(){
		info = new ResultMes();
		HttpServletRequest req = ServletActionContext.getRequest();
		String uname = req.getParameter("uname");
		String sactor = req.getParameter("actor");
		String sid = req.getParameter("id");
		if(uname==null && sid==null && sactor==null){
			info.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		int actor = 0;
		int id = 0;
		try{
			actor = Integer.parseInt(sactor);
			if (sid!=null) {
				id = Integer.parseInt(sid);
			}
		}catch(Exception e){
			info.setCode(Constants.PARAM_FORMAT_ERR);
			return "error";
		}
		if (sid!=null) {
			if (actor==Constants.ACTOR_STUDENT) {
				try {
					StudentInfo stu = stuservice.getStuInfo(id);
					stu.setPasswd("***");
					HashMap<String, Object> map = new HashMap<String,Object>();
					map.put("info", stu);
					info.setData(map);
				} catch (Exception e) {
					e.printStackTrace();
					info.setCode(Constants.SERV_ERROR);
					return "error";
				}
			}else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI){
				try {
					TeacherInfo teac = teacservice.getTeacherInfo(id);
					teac.setPasswd("***");
					HashMap<String, Object> map = new HashMap();
					map.put("info", teac);
					info.setData(map);
				} catch (Exception e) {
					e.printStackTrace();
					info.setCode(Constants.SERV_ERROR);
					return "error";
				}
			}else{
				info.setCode(Constants.NO_THIS_ACTOR);
				return "error";
			}
		}else if(uname!=null){
			if (actor==Constants.ACTOR_STUDENT) {
				try {
					StudentInfo stu = stuservice.getStuInfo(id);
					stu.setPasswd("***");
					HashMap<String, Object> map = new HashMap<String,Object>();
					map.put("info", stu);
					info.setData(map);
				} catch (Exception e) {
					e.printStackTrace();
					info.setCode(Constants.SERV_ERROR);
					return "error";
				}
			}else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI){
				try {
					TeacherInfo teac = teacservice.getTeacherInfo(id);
					teac.setPasswd("***");
					HashMap<String, Object> map = new HashMap();
					map.put("info", teac);
					info.setData(map);
				} catch (Exception e) {
					e.printStackTrace();
					info.setCode(Constants.SERV_ERROR);
					return "error";
				}
			}else{
				info.setCode(Constants.NO_THIS_ACTOR);
				return "error";
			}
		}else{
			info.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		info.setCode(Constants.STATE_OK);
		return "success";
		
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
				session.setAttribute("college", info.getCollege());
				session.setAttribute("id", info.getId());
				info.setPasswd("***");
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
				session.setAttribute("college", info.getCollege());
				session.setAttribute("id", info.getId());
				info.setPasswd("***");
				loginResult.setCode(Constants.STATE_OK);
				HashMap datas = new HashMap();
				datas.put("info", info);
				loginResult.setData(datas);
				return "success";
			} catch (UserNotExitException e) {	//用户不存在
				e.printStackTrace();
				loginResult.setCode(Constants.NO_UNAME);
				return "error";
			} catch (UserPasswdErrorException e) {	//用户密码错误
				e.printStackTrace();
				loginResult.setCode(Constants.PASSWD_ERROR);
				return "error";
			}catch(AccountLockedException e){	//账号被锁定
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
			} catch (NoHeadImgException e) {	//用户没有上传头像
				e.printStackTrace();
				getimgResult.setCode(Constants.NO_HEADIMG);
				return "error";
			} catch (NoThisFileException e) {	//用户上传了头像，但文件丢失了
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
			} catch (NoHeadImgException e) {	//用户没有上传头像
				e.printStackTrace();
				getimgResult.setCode(Constants.NO_HEADIMG);
				return "error";
			} catch (NoThisFileException e) {	//用户上传了头像，但文件丢失了
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
	public String updatePasswd(){
		updatePass = new ResultMes();
		HttpServletRequest requ = ServletActionContext.getRequest();
		String uname = requ.getParameter("uname");
		String opass = requ.getParameter("oldpass");
		String npass = requ.getParameter("newpass");
		String sactor = requ.getParameter("actor");
		int actor;
		if(sactor!=null && !sactor.trim().equals("")){
			try{
				actor = Integer.valueOf(sactor);
			}catch(Exception e){	//sactor 不是整数形式
				updatePass.setCode(Constants.PARAM_FORMAT_ERR);
				return "error";
			}
		}else{
			updatePass.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
		
		if(uname!=null && !uname.trim().equals("")&& opass!=null 
				&& !opass.trim().equals("") && npass!=null && 
				!npass.trim().equals("")){
			if(npass.matches("[\\w]{1}[\\w\\S]{5,19}")){
				if(actor==Constants.ACTOR_STUDENT){
					try {
						stuservice.updatePasswd(uname, opass, npass);
						//修改成功
						updatePass.setCode(Constants.STATE_OK);
						return "success";
					} catch (UserPasswdErrorException e) {	//旧密码错误
						e.printStackTrace();
						updatePass.setCode(Constants.PASSWD_ERROR);
						return "error";
					} catch (Exception e) {	//其它错误
						e.printStackTrace();
						updatePass.setCode(Constants.SERV_ERROR);
						return "error";
					}
				}else if(actor==Constants.ACTOR__FDY || actor==Constants.ACTOR_SHUJI){
					try {
						teacservice.updatePasswd(uname, opass, npass);
						//修改成功
						updatePass.setCode(Constants.STATE_OK);
						return "success";
					} catch (UserPasswdErrorException e) {	//旧密码错误
						e.printStackTrace();
						updatePass.setCode(Constants.PASSWD_ERROR);
						return "error";
					} catch (Exception e) {	//其它错误
						e.printStackTrace();
						updatePass.setCode(Constants.SERV_ERROR);
						return "error";
					}
				}else{
					updatePass.setCode(Constants.NO_THIS_ACTOR);
					return "error";
				}
			}else{	//新密码格式不匹配
				updatePass.setCode(Constants.PARAM_FORMAT_ERR);
				return "error";
			}
		}else{	//参数不全
			updatePass.setCode(Constants.INFO_NOT_FULL);
			return "error";
		}
	}
}
