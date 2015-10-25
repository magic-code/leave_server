package com.hgd.leave.dao;

public interface IBaseDao<T> {
	public void add(T t)throws Exception;
	public void update(T t)throws Exception;
	public void delete(T t)throws Exception;
	public T select(int id)throws Exception;
}
