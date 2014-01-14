package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.core.bl.IAccountBL;
import com.chinaunicom.filterman.core.db.dao.AccountDao;
import com.chinaunicom.filterman.core.db.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountBL implements IAccountBL {
	
	@Autowired
    private AccountDao accountDao;

	@Override
	public AccountEntity getAccountUuid4Two(String key, String appId) {
		return accountDao.getAccountUuid4Two(key, appId);
	}

	@Override
	public void updateAccountUuid4Two(AccountEntity entity) {
		accountDao.updateAccountUuid4Two(entity);
	}
	
	@Override
	public AccountEntity getAccountUuid4One(String key, String appId) {
		return accountDao.getAccountUuid4One(key, appId);
	}

	@Override
	public void updateAccountUuid4One(AccountEntity entity) {
		accountDao.updateAccountUuid4One(entity);
	}

    @Override
    public AccountEntity getAccount4Two(String key, String appId) {
        return accountDao.getAccount4Two(key, appId);
    }

    @Override
    public void updateAccount4Two(AccountEntity entity) {
        accountDao.updateAccount4Two(entity);
    }

    @Override
    public AccountEntity getAccount4One(String key, String appId) {
        return accountDao.getAccount4One(key, appId);
    }

    @Override
    public void updateAccount4One(AccountEntity entity) {
        accountDao.updateAccount4One(entity);
    }
}
