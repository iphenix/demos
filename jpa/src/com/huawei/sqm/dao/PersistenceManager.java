package com.huawei.sqm.dao;

public interface PersistenceManager {
	public <T> void persist(T entity);

	public <T> T findById(Class<T> type, long id);
}
