package com.hyron.db.dao;

import java.util.List;

import com.hyron.db.HibernateTestBase;
import com.hyron.db.domain.Tourgroup;
import com.hyron.presentation.utils.Page;
import com.hyron.presentation.utils.State;

public class TourgroupTest extends HibernateTestBase{
	State state = null;
	
	public TourgroupHibernateDao getDao(){
		initState();
		return (TourgroupHibernateDao) applicationContext.getBean("tourgroupHibernateDao");
	}
	public UserHibernateDao getUserDao(){
		return (UserHibernateDao) applicationContext.getBean("userHibernateDao");
	}
	
	public void initState(){
		state = new State();
		Page page = new Page();
		page.setRowsPerPage(10);
		page.setRecordOffset(0);
		state.setPage(page);
	}
	
	/*public void testCreateTourgroup(){
		Tourgroup t = new Tourgroup();
		t.setName("韩国");
		//t.setCreator(getUserDao().getUserById(3));
		t.setCreator(getUserDao().getUserById(18));
		t.setDetail("新创建的旅游团");
		getDao().createTourgroup(t);
	}*/
	
	/*public void testGetTourgroups(){	
		List<Tourgroup> trs = getDao().getTourgroups(state);
		assert(trs.size() > 0);
	}
	
	public void testGetTourgroupCount(){
		int count = getDao().getTourgroupCount(state, 0);
		assert(count > 0);
	}
	
	public void testGetTourgroupsByUser(){
		List<Tourgroup> trs = getDao().getTourgroupByCreator(state, 18);
		assert(trs.size() == 2);
	}*/
	
	public void testUpdateRecord(){
		Tourgroup t = getDao().getTourgroupById(3);
		t.setName("American");
		getDao().updateTourgroup(t);
	}
	
	public void testDeleteRecord(){
		Tourgroup t = getDao().getTourgroupById(2);
		getDao().deleteTourgroup(t);
	}
}
