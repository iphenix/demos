/**
 * 
 */
package com.huawei.pqm.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huawei.pqm.security.dao.UserDAO;
import com.huawei.pqm.security.model.Role;
import com.huawei.pqm.security.model.User;

/**
 * @author phenix
 * 
 */
@Component
public class SqmRealm extends AuthorizingRealm {
	protected UserDAO userDAO = null;

	public SqmRealm() {
		setName("SqmRealm"); // This name must match the name in the User
								// class's getPrincipals() method
		setCredentialsMatcher(new Sha256CredentialsMatcher());
	}

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userDAO.findUser(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getId(),
					user.getPassword(), getName());
		} else {
			return null;
		}
	}

	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Long userId = (Long) principals.fromRealm(getName()).iterator().next();
		User user = userDAO.getUser(userId);
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (Role role : user.getRoles()) {
				info.addRole(role.getName());
				//info.addStringPermissions(role.getPermissions());
			}
			return info;
		} else {
			return null;
		}
	}

}
