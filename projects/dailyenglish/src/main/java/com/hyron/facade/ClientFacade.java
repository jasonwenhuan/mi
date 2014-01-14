package com.hyron.facade;

import java.util.List;

import com.hyron.db.dao.ClientHibernateDao;
import com.hyron.db.domain.Client;
import com.hyron.presentation.utils.State;

public class ClientFacade {
    private ClientHibernateDao clientHibernateDao;
    
    public Client createClient(Client t){
    	return clientHibernateDao.createClient(t);
    }
    
    public List<Client> getClient(State state, String filterQuery){
    	return clientHibernateDao.getClients(state, filterQuery);
    }
    
    public List<Client> getClientByCreator(State state, int userId, String filterQuery){
    	return clientHibernateDao.getClientByCreator(state, userId, filterQuery);
    }
    
    public Integer getClientCount(State state, int userId, String filterQuery){
    	return clientHibernateDao.getClientCount(state, userId, filterQuery);
    }
    
    public Client getClientById(int id){
    	return clientHibernateDao.getClientById(id);
    }
    
    public Client getClientByName(String name){
    	return clientHibernateDao.getClientByName(name);
    }
    
    public void updateClient(Client post){
    	clientHibernateDao.updateClient(post);
    }
    
    public void deleteClient(Client post){
    	clientHibernateDao.deleteClient(post);
    }

	public ClientHibernateDao getClientHibernateDao() {
		return clientHibernateDao;
	}

	public void setClientHibernateDao(ClientHibernateDao clientHibernateDao) {
		this.clientHibernateDao = clientHibernateDao;
	}
}
