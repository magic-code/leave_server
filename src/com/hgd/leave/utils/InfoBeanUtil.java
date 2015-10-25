package com.hgd.leave.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.hgd.leave.constants.Constants;
import com.hgd.leave.domain.StudentInfo;

public class InfoBeanUtil {
	
	public static boolean validateInfo(String uname,String passwd,String idcard,String phone,
			String parentphone,String address,String college,String ssex) throws Exception{
		if(uname!=null && !uname.matches("[\\w]{4,14}")){
			System.out.println(uname);
			System.out.println("1");
			return false;
		}
		if(passwd!=null && !passwd.matches("[\\w]{1}[\\w\\S]{5,19}")){
			System.out.println("2");
			return false;
		}
		if(idcard!=null && !idcard.matches("[\\d]{14}[Xx\\d]{1,4}")){
			System.out.println("3");
			return false;
		}
		if(phone!=null && !phone.matches("[\\d]{1,11}")){
			System.out.println("4");
			return false;
		}
		if(parentphone!=null && !parentphone.matches("[\\d]{1,11}")){
			System.out.println("5");
			return false;
		}
		if(college!=null){
			try{
				Integer.valueOf(college);
			}catch(Exception e){
				System.out.println("6");
				throw e;
			}
		}
		if(ssex!=null){
			try{
				Integer.valueOf(ssex);
			}catch(Exception e){
				System.out.println("6");
				throw e;
			}
		}
		
		return true;
		
	}
}
