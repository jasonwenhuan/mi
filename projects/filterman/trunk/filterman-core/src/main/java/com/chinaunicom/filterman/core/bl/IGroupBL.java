package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.GroupEntity;

public interface IGroupBL {
    public void saveGroup(GroupEntity group);

	public List<GroupEntity> getGroups();

    public GroupEntity getGroupById(String id);

	public void removeGroup(String groupId);

    public List<GroupEntity> getGroupsByType(String type);
}
