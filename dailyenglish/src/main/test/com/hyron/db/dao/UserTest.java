package com.hyron.db.dao;

import com.hyron.db.HibernateTestBase;
import com.hyron.db.domain.User;

public class UserTest extends HibernateTestBase{
    private UserHibernateDao getDao() throws Exception {
        return (UserHibernateDao) applicationContext.getBean("userHibernateDao");
    }
/*    public void testCreateNewUser() throws Exception{
    	User user = new User();
    	user.setNickname("wenhuan");
    	user.setPassword("123");
    	user.setEmail("Jason@163.com");
        user.setRole(Role.Role_Admin);
    	getDao().createNewUser(user);
    }*/
    
    /*public void testValidateForUserLoginIn() throws Exception{
    	User user = new User();
    	user.setPassword("123");
    	user.setEmail("1005149089@qq.com");
    	getDao().validateForUserLoginIn("1005149089@qq.com", "123");
    }*/
    
    /*public void testGetUserById() throws Exception{
    	User user = getDao().getUserById(3);
    	System.out.println(user);
    }*/
}
