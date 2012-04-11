package com.huawei.sqm.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class PersistenceManagerBase implements PersistenceManager {

	@PersistenceContext
	private EntityManager em;

	@Override
	public <T> void persist(T entity) {
		em.persist(entity);
	}

	@Override
	public <T> T findById(Class<T> type, long id) {
		return em.find(type, id);
	}

}
