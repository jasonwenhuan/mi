package com.hyron.db.dao;

import java.util.Date;
import java.util.List;

import com.hyron.db.HibernateTestBase;
import com.hyron.db.domain.Appoint;
import com.hyron.presentation.utils.Page;
import com.hyron.presentation.utils.State;

public class AppointTest extends HibernateTestBase{
	
	State state = null;
	public AppointHibernateDao getDao(){
		initState();
		return (AppointHibernateDao) applicationContext.getBean("appointHibernateDao");
	}
	
	public void initState(){
		state = new State();
		Page page = new Page();
		page.setRowsPerPage(10);
		page.setRecordOffset(0);
		state.setPage(page);
	}
	
	/*public void testCreateAppoint(){
		Appoint t = new Appoint();
		Client c = new Client();
		Appoint tg = new Appoint();
		User user = new User();
		tg.setId(1);
		c.setId(1);
		t.setClient(c);
		user.setId(18);
		t.setCreateDate(new Date());
		t.setModifiDate(new Date());
		t.setDetail("这是一个预约");
		t.setName("预约");
		t.setAppoint(tg);
		t.setCreator(user);
		
		getDao().createAppoint(t);
	}*/
	
	
	/*public void testGetAppoints(){	
		List<Appoint> trs = getDao().getAppoints(state);
		assert(trs.size() > 0);
	}
	
	public void testGetAppointCount(){
		int count = getDao().getAppointCount(state, 0);
		assert(count > 0);
	}*/
	
	public void testGetAppointsByUser(){
		List<Appoint> trs = getDao().getAppointByCreator(state, 18);
		assert(trs.size() == 2);
	}
	
	public void testUpdateRecord(){
		Appoint t = getDao().getAppointById(3);
		t.setName("American");
		getDao().updateAppoint(t);
	}
	
	public void testDeleteRecord(){
		Appoint t = getDao().getAppointById(2);
		getDao().deleteAppoint(t);
	}
}
