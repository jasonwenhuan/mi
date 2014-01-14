package com.chinaunicom.filterman.comm.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.chinaunicom.filterman.constants.PolicyType;

public class PolicyTypeVO implements Serializable {
	private int policyTypeId;
	private String policyTypeName;

	public int getPolicyTypeId() {
		return policyTypeId;
	}

	public void setPolicyTypeId(int policyTypeId) {
		this.policyTypeId = policyTypeId;
	}

	public String getPolicyTypeName() {
		return policyTypeName;
	}

	public void setPolicyTypeName(String policyTypeName) {
		this.policyTypeName = policyTypeName;
	}

	public List<PolicyTypeVO> getAllPolicyType() {
		List<PolicyTypeVO> allPolicyTypeVOType = new ArrayList<PolicyTypeVO>();
		for (PolicyType policyType : PolicyType.values()) {
			PolicyTypeVO policyTypeVO = new PolicyTypeVO();
			policyTypeVO.setPolicyTypeId(policyType.getPolicyId());
			policyTypeVO.setPolicyTypeName(policyType.getPolicyName());
			allPolicyTypeVOType.add(policyTypeVO);
		}
		return allPolicyTypeVOType;
	}

}
