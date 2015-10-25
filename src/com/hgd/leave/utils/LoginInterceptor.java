package com.hgd.leave.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.hgd.leave.constants.Constants;
import com.hgd.leave.domain.ResultMes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor {
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		ActionContext context = invocation.getInvocationContext();
		HttpServletRequest req = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
		String uri = req.getRequestURI();
		System.out.println("uri"+uri);
		String uname = (String) req.getSession().getAttribute("uname");
		if(uname==null){
			ResultMes mes = new ResultMes();
			mes.setCode(Constants.NO_LOGIN);
			context.put("mes", mes);
			return "nologin";
		}
		return invocation.invoke();
	}

}
