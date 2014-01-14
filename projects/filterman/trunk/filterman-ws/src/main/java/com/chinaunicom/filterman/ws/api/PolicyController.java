package com.chinaunicom.filterman.ws.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.comm.vo.PolicyVO;
import com.chinaunicom.filterman.constants.PolicyType;
import com.chinaunicom.filterman.core.bl.IGroupBL;
import com.chinaunicom.filterman.core.bl.IPolicyBL;
import com.chinaunicom.filterman.core.db.entity.GroupEntity;
import com.chinaunicom.filterman.core.db.entity.PolicyEntity;
import com.chinaunicom.filterman.utilities.Logging;

@Controller
@RequestMapping(value = "/policy")
public class PolicyController {
	@Autowired
    private IPolicyBL policyBL;
	
	@Autowired
    private IGroupBL groupBL;
	
	@RequestMapping(value = "/policyData", method = RequestMethod.POST)
    @ResponseBody
    public List<PolicyVO> getPoliciesByGroupId(@RequestBody PolicyVO pv) {
		List<PolicyVO> policyData = new ArrayList<PolicyVO>();
		try{
			String groupId = pv.getGroupId();
			String groupLevel = pv.getLevel();
			
            List<PolicyEntity> policies = policyBL.getPolicies(groupId, groupLevel);
            
            if(policies == null){
                return null;
            }

            GroupEntity groupEntity = groupBL.getGroupById(groupId);
			if(groupEntity == null){
				return policyData;
			}
			
			String groupType = groupEntity.getType();
			PolicyEntity policyEntity = policies.get(0);
			String policyIds = policyEntity.getPolicyIds();
			String[] pIds = policyIds.split(",");
			//if pids === "", no policy, policyName will be ""
			for(int i=0; i<pIds.length; i++){
				PolicyVO pData = new PolicyVO();
				String policyName = "";
				if(!pIds[i].equals("")){
					policyName = PolicyType.getPolicyByValue(pIds[i]).getPolicyName();
				}
				pData.setGroupPolicyKey(policyEntity.getId());
				pData.setGroupId(policyEntity.getGroupId());
				pData.setGroupType(groupType);
				
				if(policyEntity.getGroupName() != null){
					pData.setGroupName(policyEntity.getGroupName());
				}
				
				if(policyEntity.getLevel() != null){
					pData.setLevel(policyEntity.getLevel());
				}
				
				pData.setPolicyId(pIds[i]);
				
				if(policyName != null){
					pData.setPolicyName(policyName);
				}else{
					pData.setPolicyName("");
				}
				
				if(policyEntity.getUpdateDate() != null){
					pData.setUpdateDate(policyEntity.getUpdateDate());
				}

				if(policyEntity.getUpdateUser() != null){
					pData.setUpdateUser(policyEntity.getUpdateUser());
				}
				policyData.add(pData);
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyController.getPoliciesByGroupId error", e);
		}
		return policyData;
	}
	
	@RequestMapping(value = "/allPolicy", method = RequestMethod.GET)
    @ResponseBody
    public List<PolicyVO> getAllPolicy() {
		List<PolicyVO> allPolicies = new ArrayList<PolicyVO>();
		
		PolicyType []types =  PolicyType.getAllPolicies();
		for(int i = 0; i < types.length; i++){
			PolicyType policyType = types[i];
			PolicyVO policyVO = new PolicyVO();
			policyVO.setPolicyId(String.valueOf(policyType.getPolicyId()));
			policyVO.setPolicyName(String.valueOf(policyType.getPolicyName()));
			allPolicies.add(policyVO);
		}
		
		return allPolicies;
	}
	
	@RequestMapping(value = "/removePolicyFromGroup", method = RequestMethod.POST)
    @ResponseBody
    public Boolean removePolicyFromGroup(@RequestBody PolicyVO groupPolicy) {
		String groupPolicyId = groupPolicy.getGroupPolicyKey();
		String level = groupPolicy.getLevel();
		String waitRemovePolicy = groupPolicy.getPolicyId();
		
		PolicyEntity groupPolicyEntity = policyBL.getPolicy(groupPolicyId);
		if(groupPolicyEntity == null){
			Logging.logError("Doing removePolicyFromGroup error, can not get PolicyEntity by groupPolicyId");
		    return false;
		}
		
		String policies = groupPolicyEntity.getPolicyIds();
		String[] policyArr = policies.split(",");
		if(policyArr.length == 1 && policyArr[0].equals("")){
			Logging.logError("Doing removePolicyFromGroup error, can not get PolicyEntity by groupPolicyId");
		    return false;
		}
		
		List<String> newPolicyList  = new ArrayList<String>();
		for(int i = 0; i < policyArr.length; i++){
			if(policyArr[i].equalsIgnoreCase(waitRemovePolicy)){
				continue;
			}
			newPolicyList.add(policyArr[i]);
		}
		
		String newPolicyStr = "";
		for(int j=0;j<newPolicyList.size();j++){
			newPolicyStr += newPolicyList.get(j);
			if(j != (newPolicyList.size()-1)){
				newPolicyStr += ",";
			}
		}
		
		PolicyEntity newGroupPolicy = new PolicyEntity();
		newGroupPolicy.setId(groupPolicyId);
		newGroupPolicy.setPolicyIds(newPolicyStr);
		newGroupPolicy.setLevel(level);
		policyBL.updatePolicy(newGroupPolicy);
		
		return false;
	}
	
	@RequestMapping(value = "/addPolicyToGroup", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addPolicyToGroup(@RequestBody PolicyVO groupPolicy) {
		String groupId = groupPolicy.getGroupId();
		String groupName = groupPolicy.getGroupName();
		String level = groupPolicy.getLevel();
		String waitAddPolicy = groupPolicy.getPolicyId();
		
		List<PolicyEntity> groupPolicyEntities = new ArrayList<PolicyEntity>();
		
		if(level.equals("")){
			PolicyEntity generalPe = policyBL.getAvailablePolicy(groupId);
			if(generalPe != null){
				groupPolicyEntities.add(generalPe);
			}
		}else{
			groupPolicyEntities	= policyBL.getPolicies(groupId, level);
		}
		
		
		PolicyEntity newGroupPolicy = new PolicyEntity();
		 
		policyBL.resetPolicyStatus(groupId);
		
		if(groupPolicyEntities.size() == 0 || groupPolicyEntities == null){
			
			newGroupPolicy.setGroupId(groupId);
			newGroupPolicy.setGroupName(groupName);
			newGroupPolicy.setLevel(level);
			newGroupPolicy.setPolicyIds(waitAddPolicy);
			newGroupPolicy.setStatus("0");
			newGroupPolicy.setUpdateDate(new Date());
			newGroupPolicy.setUpdateUser("");
			
			policyBL.savePolicy(newGroupPolicy);
		}else{
			PolicyEntity groupPolicyEntity = groupPolicyEntities.get(0);
			
			String policies = groupPolicyEntity.getPolicyIds();
			newGroupPolicy.setId(groupPolicyEntity.getId());
			newGroupPolicy.setLevel(level);
			if(policies.equals("")){
				newGroupPolicy.setPolicyIds(waitAddPolicy);
			}else{
				String newPolicy = policies + "," + waitAddPolicy;
				newGroupPolicy.setPolicyIds(newPolicy);
			}
			policyBL.updatePolicy(newGroupPolicy);
		}
		return true;
	}
	
	@RequestMapping(value = "/updatePolicyLevel", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updatePolicyLevel(@RequestBody PolicyVO groupPolicy) {
		try{
			String groupId = groupPolicy.getGroupId();
			String groupLevel = groupPolicy.getLevel();
			
			List<PolicyEntity> pes = policyBL.getPolicies(groupId, groupLevel);
			
			if(pes.size() == 0 && groupLevel.equals("0")){
				policyBL.resetPolicyStatus(groupId);
			}
			if(pes.size() > 0){
				policyBL.resetPolicyStatus(groupId);
				PolicyEntity newPolicyEntity = pes.get(0);
				policyBL.updatePolicy(newPolicyEntity);
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyController.updatePolicyLevel error", e);
			return false;
		}
		return true;
	}
}
