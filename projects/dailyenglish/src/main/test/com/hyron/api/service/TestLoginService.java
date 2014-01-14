package com.hyron.api.service;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

public class TestLoginService extends ApiTestBase{
	private LoginService loginService;
    boolean boundSession;
    Session session;
    HttpServletRequest request;
    
	@Override
	protected void initTestCase() {
		loginService = (LoginService) applicationContext.getBean("loginService");
	    request = new MockMultipartHttpServletRequest();

	    boundSession = false;
	    Session session = null;
	 /* cDao = budgetApi.getBudgetBusiness().getAdwordsBusiness().getCityDao();
	        if (TransactionSynchronizationManager.getResource(cDao
	                .getSessionFactory()) == null) {
	            session = SessionFactoryUtils.getSession(cDao.getSessionFactory(),
	                    true);
	            TransactionSynchronizationManager.bindResource(cDao
	                    .getSessionFactory(), new SessionHolder(session));
	            boundSession = true;
	     }*/
	}

	@Override
	protected void cleanUpTestCase() {
		/* if (boundSession) {
	            TransactionSynchronizationManager.unbindResource(cDao
	                    .getSessionFactory());
	            SessionFactoryUtils.releaseSession(session, cDao
	                    .getSessionFactory());
	     }*/
	}
    
	public void testLogin() throws Exception {
		initTestCase();
        String username = "huan";
        String password = "123";
        //Boolean res = loginService.userLoginIn(username, password, request);
        //System.out.println(res.toString());
    }
}
