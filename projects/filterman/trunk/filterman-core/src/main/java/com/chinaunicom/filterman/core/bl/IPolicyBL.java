package com.chinaunicom.filterman.core.bl;

import java.util.List;

import com.chinaunicom.filterman.core.db.entity.PolicyEntity;

public interface IPolicyBL {
    public void savePolicy(PolicyEntity entity);
    public List<PolicyEntity> getPolicies(String groupId, String groupLevel);
    public PolicyEntity getPolicy(String id);
    public PolicyEntity getAvailablePolicy(String groupId);
    public void updatePolicy(PolicyEntity entity);
    public void resetPolicyStatus(String groupId);
    public void removePolicyByGroupId(String groupId);
}
