package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.AppEntity;

public interface IAppBL {
    public List<AppEntity> getAppsByGroupId(String groupId, int pageOffset, int rowsPerPage);
    public List<AppEntity> getAppsByAppName(String appName, int pageOffset, int rowsPerPage);
    public AppEntity getAppById(String id);
    public AppEntity getAppByIdAndName(String appId, String appName);
    public void updateApp(AppEntity entity);
    public void saveApp(AppEntity entity);
    public List<AppEntity> getAllApps(int pageOffset, int rowsPerPage);
    public List<AppEntity> getAppByAppId(String appId);
    public void resetAppGroup(String groupId);
	public Long getAppsCount();
	public Long getAppsCountByAppName(String appName);
	public Long getAppsCountByGroupId(String groupId);
}
