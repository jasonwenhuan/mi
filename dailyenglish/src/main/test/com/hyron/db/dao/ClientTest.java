package com.hyron.db.dao;

import java.util.List;

import com.hyron.db.HibernateTestBase;
import com.hyron.db.domain.Client;
import com.hyron.db.domain.Client;
import com.hyron.presentation.utils.Page;
import com.hyron.presentation.utils.State;

public class ClientTest extends HibernateTestBase{
	State state = null;
	
	public ClientHibernateDao getDao(){
		return (ClientHibernateDao) applicationContext.getBean("clientHibernateDao");
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
	
	/*public void testCreateClient(){
		Client t = new Client();
		t.setAge(44);
		t.setCreator(getUserDao().getUserById(18));
		t.setEmail("njupt_wenhuan@163.com");
		t.setName("闻欢");
		t.setRemark("这是一个客户");
		getDao().createClient(t);
	}*/
	
	/*public void testGetClients(){	
		List<Client> trs = getDao().getClients(state);
		assert(trs.size() > 0);
	}
	
	public void testGetClientCount(){
		int count = getDao().getClientCount(state, 0);
		assert(count > 0);
	}
	
	public void testGetClientsByUser(){
		List<Client> trs = getDao().getClientByCreator(state, 18);
		assert(trs.size() == 3);
	}*/
	
	public void testUpdateRecord(){
		Client t = getDao().getClientById(3);
		t.setName("American");
		getDao().updateClient(t);
	}
	
	public void testDeleteRecord(){
		Client t = getDao().getClientById(2);
		getDao().deleteClient(t);
	}
}
