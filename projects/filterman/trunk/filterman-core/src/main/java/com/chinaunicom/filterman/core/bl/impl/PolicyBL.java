package com.chinaunicom.filterman.core.bl.impl;

import java.util.List;

import com.chinaunicom.filterman.core.bl.IPolicyBL;
import com.chinaunicom.filterman.core.db.entity.PolicyEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.db.dao.PolicyDao;

public class PolicyBL implements IPolicyBL {
	@Autowired
    private PolicyDao policyDao;
	
	@Override
	public void savePolicy(PolicyEntity entity) {
		policyDao.savePolicy(entity);
	}

	@Override
	public List<PolicyEntity> getPolicies(String groupId, String groupLevel) {
		return policyDao.getPolicies(groupId, groupLevel);
	}

	@Override
	public PolicyEntity getPolicy(String id) {
		return policyDao.getPolicy(id);
	}

    @Override
    public PolicyEntity getAvailablePolicy(String groupId) {
        return policyDao.getAvailablePolicy(groupId);
    }

    @Override
	public void updatePolicy(PolicyEntity entity) {
		policyDao.updatePolicy(entity);
	}

	@Override
	public void resetPolicyStatus(String groupId) {
		policyDao.resetPolicyStatus(groupId);
	}

	@Override
	public void removePolicyByGroupId(String groupId) {
		policyDao.removePoliciesByGroupId(groupId);
	}
}
