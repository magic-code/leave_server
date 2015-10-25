package com.hgd.leave.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.servlet.tags.ParamAware;

import com.hgd.leave.dao.IBaseDao;

public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T>{
	
	private SessionFactory sessionFactoryOverride;
	
	
	
	public SessionFactory getSessionFactoryOverride() {
		return sessionFactoryOverride;
	}
	@Resource
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public Class<T> getActualClass(){
		Type t = (Type) getClass().getGenericSuperclass();
		Type[] p = ((ParameterizedType)t).getActualTypeArguments();
		return (Class<T>)p[0];
	}

	@Override
	public void add(T t)throws Exception {
		try{
		getHibernateTemplate().save(t);
		}catch(Exception e){
			throw e;
		}
		
	}

	@Override
	public void delete(T t)throws Exception {
		try {
			getHibernateTemplate().delete(t);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public T select(int id)throws Exception {
		try {
			return (T)getHibernateTemplate().load(getActualClass(),id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void update(T t)throws Exception {
		try {
			getHibernateTemplate().update(t);
		} catch (Exception e) {
			throw e;
		}
	}
	
}
