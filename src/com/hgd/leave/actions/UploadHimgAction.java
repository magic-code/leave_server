package com.hgd.leave.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.hgd.leave.constants.Constants;
import com.hgd.leave.domain.ResultMes;
import com.hgd.leave.service.IStudentInfoService;
import com.hgd.leave.service.ITeacherInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class UploadHimgAction extends ActionSupport {

	private File upfile ; //�����ϴ��ļ��� ���� , ָ����ʱĿ¼�е���ʱ�ļ�  
    private String upfileFileName ;  // �ϴ��ļ������� ,FileName �̶���д��  
    private String upfileContentType ; //�ϴ��ļ������ͣ� ContentType �̶���д��  
    
    private IStudentInfoService stuservice;
	public File getUpfile() {
		return upfile;
	}
	public void setUpfile(File upfile) {
		this.upfile = upfile;
	}
	public String getUpfileFileName() {
		return upfileFileName;
	}
	public void setUpfileFileName(String upfileFileName) {
		this.upfileFileName = upfileFileName;
	}
	public String getUpfileContentType() {
		return upfileContentType;
	}
	public void setUpfileContentType(String upfileContentType) {
		this.upfileContentType = upfileContentType;
	}
	private ITeacherInfoService teacservice;
	private ResultMes loadimgResult;
    
    public IStudentInfoService getStuservice() {
		return stuservice;
	}
	public void setStuservice(IStudentInfoService stuservice) {
		this.stuservice = stuservice;
	}
	public ITeacherInfoService getTeacservice() {
		return teacservice;
	}
	public void setTeacservice(ITeacherInfoService teacservice) {
		this.teacservice = teacservice;
	}
	
	
   
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		loadimgResult = new ResultMes();
		HttpSession session = req.getSession();
		String uname = (String) session.getAttribute("uname");
		int actor = (Integer) session.getAttribute("actor");
		File path = new File(System.getProperty("user.home"),"/himgs");
		if(!path.exists())
			path.mkdirs();
		String extra = upfileFileName.substring(upfileFileName.lastIndexOf("."));
		String filename = System.currentTimeMillis()+extra;
        InputStream is = null;
        OutputStream os = null;
		try {
			is = new FileInputStream(upfile);
			os = new FileOutputStream(new File(path, filename));
		} catch (FileNotFoundException e1) {
		}
        
        
        System.out.println("fileFileName: " + filename);

        // ��Ϊfile�Ǵ������ʱ�ļ��е��ļ������ǿ��Խ����ļ������ļ�·����ӡ����������֮ǰ��fileFileName�Ƿ���ͬ
        System.out.println("file: " + upfile.getName());
        System.out.println("file: " + upfile.getPath());
        
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
        	loadimgResult.setCode(Constants.SERV_ERROR);
        	return "error";
		}
	}
	public ResultMes getLoadimgResult() {
		return loadimgResult;
	}
	public void setLoadimgResult(ResultMes loadimgResult) {
		this.loadimgResult = loadimgResult;
	}

}
