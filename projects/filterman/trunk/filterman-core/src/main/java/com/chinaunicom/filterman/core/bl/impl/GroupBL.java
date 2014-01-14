package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import com.chinaunicom.filterman.core.bl.IGroupBL;
import com.chinaunicom.filterman.core.db.dao.GroupDao;
import com.chinaunicom.filterman.core.db.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupBL implements IGroupBL {
	@Autowired
    private GroupDao groupDao;
	
	@Override
	public void saveGroup(GroupEntity entity) {
		groupDao.saveGroup(entity);
	}
	
	@Override
	public List<GroupEntity> getGroups(){
		return groupDao.getGroups();
	}

    @Override
    public List<GroupEntity> getGroupsByType(String type) {
        return groupDao.getGroupsByType(type);
    }

    @Override
	public void removeGroup(String groupId) {
		groupDao.removeGroup(groupId);
	}

    @Override
    public GroupEntity getGroupById(String id) {
        return groupDao.getGroupById(id);
    }
}
