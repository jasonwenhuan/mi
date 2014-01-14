package com.chinaunicom.filterman.core.bl;

import com.chinaunicom.filterman.core.db.entity.AccountEntity;

public interface IAccountBL {
    public AccountEntity getAccount4Two(String key, String appId);
    public AccountEntity getAccount4One(String key, String appId);
    public void updateAccount4Two(AccountEntity entity);
    public void updateAccount4One(AccountEntity entity);

    public AccountEntity getAccountUuid4Two(String key, String uuid);
    public AccountEntity getAccountUuid4One(String key, String uuid);
    public void updateAccountUuid4Two(AccountEntity entity);
    public void updateAccountUuid4One(AccountEntity entity);
}
  