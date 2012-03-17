/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.huawei.pqm.security.dao;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.huawei.pqm.security.model.User;

import java.util.List;

import javax.persistence.TypedQuery;

@Repository("userDAO")
@SuppressWarnings("unchecked")
public class HibernateUserDAO extends HibernateDao implements UserDAO {

	public User getUser(Long userId) {

		return getEntityManager().find(User.class, userId);
	}

	public User findUser(String username) {
		Assert.hasText(username);
		TypedQuery<User> query = getEntityManager().createNamedQuery(
				"QueryByName", User.class);
		query.setParameter("username", username);
		return query.getSingleResult();
	}

	public void createUser(User user) {
		getEntityManager().persist(user);
	}

	public List<User> getAllUsers() {
		TypedQuery<User> query = getEntityManager().createNamedQuery(
				"QueryAll", User.class);
		return query.getResultList();

	}

	public void deleteUser(Long userId) {
		User user = getUser(userId);
		if (user != null) {
			getEntityManager().remove(user);
		}
	}

	public void updateUser(User user) {
		getEntityManager().merge(user);
	}

}
