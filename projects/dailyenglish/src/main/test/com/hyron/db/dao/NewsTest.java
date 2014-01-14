package com.hyron.db.dao;

import com.hyron.db.HibernateTestBase;





public class NewsTest extends HibernateTestBase{
    private NewsHibernateDao getDao() throws Exception {
        return (NewsHibernateDao) applicationContext.getBean("newsHibernateDao");
    }
/*    public void testCreateNewUser() throws Exception{
    	User user = new User();
    	user.setNickname("wenhuan");
    	user.setPassword("123");
    	user.setEmail("Jason@163.com");
        user.setRole(Role.Role_Admin);
    	getDao().createNewUser(user);
    }*/
    
    public void testDisplayTopNews() throws Exception{
    	getDao().displayTopNews();
    }
}
