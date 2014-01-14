package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import com.chinaunicom.filterman.core.bl.IAppBL;
import com.chinaunicom.filterman.core.db.dao.AppDao;
import com.chinaunicom.filterman.core.db.entity.AppEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class AppBL implements IAppBL {
	@Autowired
    private AppDao appDao;

    @Override
    public List<AppEntity> getAllApps(int PageOffset, int pageSize) {
        return appDao.getAllApps(PageOffset, pageSize);
    }

	@Override
	public List<AppEntity> getAppsByGroupId(String groupId, int pageOffset, int rowsPerPage) {
		return appDao.getAppsByGroupId(groupId, pageOffset, rowsPerPage);
	}

    @Override
    public List<AppEntity> getAppsByAppName(String appName, int pageOffset, int pageSize) {
        return appDao.getAppsByAppName(appName, pageOffset, pageSize);
    }

    @Override
    public AppEntity getAppById(String id) {
        return appDao.getAppById(id);
    }

    @Override
    public AppEntity getAppByIdAndName(String appId, String appName) {
        return appDao.getAppByIdAndName(appId,  appName);
    }

    @Override
    public void updateApp(AppEntity entity) {
        appDao.updateApp(entity);
    }

	@Override
	public void saveApp(AppEntity entity) {
		appDao.saveApp(entity);
	}

	@Override
	public List<AppEntity> getAppByAppId(String appId) {
		return appDao.getAppsByAppId(appId);
	}

	@Override
	public void resetAppGroup(String groupId) {
		appDao.resetAppGroup(groupId);
	}

	@Override
	public Long getAppsCount() {
		return appDao.getAppsCount();
	}

	@Override
	public Long getAppsCountByAppName(String appName) {
		return appDao.getAppsCountByAppName(appName);
	}

	@Override
	public Long getAppsCountByGroupId(String groupId) {
		return appDao.getAppsCountByGroupId(groupId);
	}

}
