package com.chinaunicom.filterman.ui.wsclient;

import com.chinaunicom.filterman.comm.vo.AppVO;
import com.chinaunicom.filterman.comm.vo.GroupVO;
import com.chinaunicom.filterman.comm.vo.FilterVO;
import com.chinaunicom.filterman.comm.vo.IntervalVO;
import com.chinaunicom.filterman.comm.vo.ModifiedVO;
import com.chinaunicom.filterman.comm.vo.PagerVO;
import com.chinaunicom.filterman.comm.vo.PolicyVO;
import com.chinaunicom.filterman.comm.vo.RelatedPhoneVO;
import com.chinaunicom.filterman.comm.vo.RequestVO;
import com.chinaunicom.filterman.comm.vo.SearchVO;
import com.chinaunicom.filterman.comm.vo.KeyValueVO;
import com.chinaunicom.filterman.comm.vo.UserVO;
import com.chinaunicom.filterman.comm.vo.WBListVO;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import javax.ws.rs.core.MediaType;

import java.util.List;

/**
 * User: larry
 */
public class WebServiceClient {

    private String rootPath;

    private ApacheHttpClient4 client;

    private WebResource wr;

    private ClientResponse clientResponse;

    public WebServiceClient() {
        ClientConfig clientConfig = new DefaultApacheHttpClient4Config();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getProperties().put(ApacheHttpClient4Config.PROPERTY_DISABLE_COOKIES, false);
        final ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();

        //TODO just to avoid deadlock
        threadSafeClientConnManager.setDefaultMaxPerRoute(100);
        threadSafeClientConnManager.setMaxTotal(1000);

        clientConfig.getProperties().put(ApacheHttpClient4Config.PROPERTY_CONNECTION_MANAGER, threadSafeClientConnManager);
        client = ApacheHttpClient4.create(clientConfig);
        client.getClientHandler().getHttpClient().getParams().setBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, false);

    }

    public List<UserVO> getUser() {
        wr = client.resource(getRootPath());

        clientResponse = wr.path("/users/list")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        List<UserVO> result = clientResponse.getEntity(new GenericType<List<UserVO>>(){});

        return result;
    }

    public UserVO getUserByKey(UserVO userVO) {
        wr = client.resource(getRootPath());

        clientResponse = wr.path("/users/validate")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, userVO);

        UserVO result = clientResponse.getEntity(new GenericType<UserVO>() {});

        return result;
    }

    public List<SearchVO> getResultByFilter(FilterVO filterVO) {
    	wr = client.resource(getRootPath());

        clientResponse = wr.path("/search/getData")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, filterVO);

        List<SearchVO> searchVO = clientResponse.getEntity(new GenericType<List<SearchVO>>(){});

        return searchVO;
	}
    
    public Boolean updateDetail(String changedData) {
    	wr = client.resource(getRootPath());
        Gson gson = new Gson();
        String str = gson.toJson(changedData);
        clientResponse = wr.path("/search/update")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, str);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        
        return result;
	}
    
    public List<KeyValueVO> getAppKeyValuePair() {
        wr = client.resource(getRootPath());

        clientResponse = wr.path("/search/appKeyValue")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        List<KeyValueVO> result = clientResponse.getEntity(new GenericType<List<KeyValueVO>>(){});

        return result;
	}

    public List<GroupVO> getAllAppGroups() {
        wr = client.resource(getRootPath());

        clientResponse = wr.path("/group/allGroups")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        List<GroupVO> result = clientResponse.getEntity(new GenericType<List<GroupVO>>(){});

        return result;
	}
    
    public Boolean addAppGroup(GroupVO groupData) {
        wr = client.resource(getRootPath());

        clientResponse = wr.path("/group/saveGroup")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, groupData);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
    
    public Boolean deleteAppGroup(List<String> groupIds) {
        wr = client.resource(getRootPath());

        Gson gson = new Gson();
        String str = gson.toJson(groupIds);

        clientResponse = wr.path("/group/removeGroups")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, str);

		Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
		return result;
	}
    
    public List<AppVO> getAllApps(int pageOffset, int pageSize) {
        wr = client.resource(getRootPath());
        PagerVO pv = new PagerVO();
		pv.setRowsPerPage(pageSize);
		pv.setPageOffset(pageOffset);
        
        clientResponse = wr.path("/group/allApps")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, pv);

        List<AppVO> result = clientResponse.getEntity(new GenericType<List<AppVO>>(){});
	    return result;
    }
    
    public List<AppVO> getAppsByGroupId(int recordOffset, int pageSize, String groupId, String appName) {
        wr = client.resource(getRootPath());
        PagerVO pv = new PagerVO();
        pv.setRowsPerPage(pageSize);
		pv.setPageOffset(recordOffset);
		pv.setGroupId(groupId);
        pv.setAppName(appName);
		
        clientResponse = wr.path("/group/appsRefGroup")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, pv);

        List<AppVO> result = clientResponse.getEntity(new GenericType<List<AppVO>>(){});
	    return result;
    }
    
    public Boolean updateApps(List<AppVO> apps) {
        wr = client.resource(getRootPath());

        clientResponse = wr.path("/group/updateApps")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, apps);
        
        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
	    return result;
    }
    
	public List<PolicyVO> getPolicyDataByGroupId(String groupId, String groupLevel) {
		wr = client.resource(getRootPath());

		PolicyVO policyData = new PolicyVO();
		policyData.setGroupId(groupId);
		policyData.setLevel(groupLevel);

        clientResponse = wr.path("/policy/policyData")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, policyData);
        
        List<PolicyVO> result = clientResponse.getEntity(new GenericType<List<PolicyVO>>(){});
	    return result;
	}
	
	public List<PolicyVO> getAllPolicy() {
		wr = client.resource(getRootPath());
        
        clientResponse = wr.path("/policy/allPolicy")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        
        List<PolicyVO> result = clientResponse.getEntity(new GenericType<List<PolicyVO>>(){});
	    return result;
	}
	
	public Boolean removePolicyFromGroup(String groupPolicyId, String policyId, String policyLevel, String groupId) {
		wr = client.resource(getRootPath());
        PolicyVO policyData = new PolicyVO();
        policyData.setGroupPolicyKey(groupPolicyId);
        policyData.setPolicyId(policyId);
        policyData.setLevel(policyLevel);
        policyData.setGroupId(groupId);

        clientResponse = wr.path("/policy/removePolicyFromGroup")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, policyData);
        
        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public Boolean addPolicyToGroup(String groupId, String groupName, String groupLevel, String groupType, String policyId) {
		wr = client.resource(getRootPath());
        PolicyVO policyData = new PolicyVO();
        policyData.setGroupId(groupId);
        policyData.setGroupName(groupName);
        policyData.setGroupType(groupType);
        policyData.setPolicyId(policyId);
        policyData.setLevel(groupLevel);

        clientResponse = wr.path("/policy/addPolicyToGroup")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, policyData);
        
        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public Boolean updatePolicyLevel(String groupId, String groupLevel) {
		
		wr = client.resource(getRootPath());
        PolicyVO policyData = new PolicyVO();
        policyData.setGroupId(groupId);
        policyData.setLevel(groupLevel);
        
        clientResponse = wr.path("/policy/updatePolicyLevel")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, policyData);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public Boolean createBlackList(WBListVO data) {
		
		wr = client.resource(getRootPath());
        
        clientResponse = wr.path("/blacklist/save")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, data);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public Boolean createWhiteList(WBListVO data) {
		
		wr = client.resource(getRootPath());
        
        clientResponse = wr.path("/whitelist/save")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, data);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public void removeWhiteList(List<String> delWhitelist) {
		
		wr = client.resource(getRootPath());
        
        Gson gson = new Gson();
        String str = gson.toJson(delWhitelist);
        
        clientResponse = wr.path("/whitelist/remove")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, str);

        clientResponse.getEntity(new GenericType<String>(){});
	}
	
	public void removeBlackList(List<String> delBlacklist) {
		
		wr = client.resource(getRootPath());
        
        Gson gson = new Gson();
        String str = gson.toJson(delBlacklist);
        
        clientResponse = wr.path("/blacklist/remove")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, str);

        clientResponse.getEntity(new GenericType<String>(){});
	}
	
	public List<WBListVO> getBlackList(String blacklistId, int pageOffset, int pageSize) {
		
		wr = client.resource(getRootPath());
		PagerVO pageVO = new PagerVO();
		pageVO.setWblistId(blacklistId);
		pageVO.setPageOffset(pageOffset);
		pageVO.setRowsPerPage(pageSize);
        
        clientResponse = wr.path("/blacklist/info")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, pageVO);

        List<WBListVO> result = clientResponse.getEntity(new GenericType<List<WBListVO>>(){});
        return result;
	}
	
	public List<WBListVO> getWhiteList(String whitelistId, int pageOffset, int pageSize) {
		wr = client.resource(getRootPath());
		
		PagerVO pageVO = new PagerVO();
		pageVO.setWblistId(whitelistId);
		pageVO.setPageOffset(pageOffset);
		pageVO.setRowsPerPage(pageSize);
        
        clientResponse = wr.path("/whitelist/info")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, pageVO);

        List<WBListVO> result = clientResponse.getEntity(new GenericType<List<WBListVO>>(){});
        return result;
	}
	
	public Boolean validateIsInBlacklist(String keyId) {
		wr = client.resource(getRootPath());
        
        Gson gson = new Gson();
        String str = gson.toJson(keyId);
        
        clientResponse = wr.path("/blacklist/check")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, str);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public List<RequestVO> getAllRequests(String orderIdOrPhone, int pageOffset, int rowsPerPage) {
		wr = client.resource(getRootPath());
		PagerVO pv = new PagerVO();
		pv.setRowsPerPage(rowsPerPage);
		pv.setPageOffset(pageOffset);
		pv.setOtherMessage(orderIdOrPhone);
		Gson gson = new Gson();
        String str = gson.toJson(pv);
        
        clientResponse = wr.path("/request/all")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, str);

        List<RequestVO> result = clientResponse.getEntity(new GenericType<List<RequestVO>>(){});
        return result;
	}
	
	
	public ModifiedVO getAccountPhoneDeviceDetailByKey(String account,
			String device, String phone, String appId, String appName, String changedField,
			String changedTable) {
		wr = client.resource(getRootPath());
		ModifiedVO request = new ModifiedVO();
		request.setAccount(account);
		request.setDevice(device);
		request.setPhone(phone);
		request.setAppId(appId);
		request.setAppName(appName);
		request.setChangedField(changedField);
		request.setChangedTable(changedTable);
        
        clientResponse = wr.path("/request/apdDetailByKey")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, request);

        ModifiedVO result = clientResponse.getEntity(new GenericType<ModifiedVO>(){});
        return result;
	}
	
	public Boolean updateAccountPhoneDevice(String account, String device,
			String phone, String appId, String appName, String changedField, String changedTable) {
		wr = client.resource(getRootPath());
		ModifiedVO request = new ModifiedVO();
		request.setAccount(account);
		request.setDevice(device);
		request.setPhone(phone);
		request.setAppId(appId);
		request.setAppName(appName);
		request.setChangedField(changedField);
		request.setChangedTable(changedTable);
        
        clientResponse = wr.path("/request/updateAccountPhoneDevice")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, request);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public List<RelatedPhoneVO> getAllRelatedPhonePolicy() {
		wr = client.resource(getRootPath());
        
        clientResponse = wr.path("/policyImprove/allRelatedPhoneRule")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        List<RelatedPhoneVO> result = clientResponse.getEntity(new GenericType<List<RelatedPhoneVO>>(){});
        return result;
	}
	
	public List<RelatedPhoneVO> getRulesByInterval(String interval) {
		wr = client.resource(getRootPath());
        
        clientResponse = wr.path("/policyImprove/getRulesByInterval")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, interval);

        List<RelatedPhoneVO> result = clientResponse.getEntity(new GenericType<List<RelatedPhoneVO>>(){});
        return result;
	}
	
	public Boolean updateRelatedPhoneRule(String ruleId,
			String interval, String limit) {
		wr = client.resource(getRootPath());
		RelatedPhoneVO rpVO = new RelatedPhoneVO();
		rpVO.setKey(ruleId);
		rpVO.setTimeInterval(interval);
		rpVO.setTotalNumber(limit);
        clientResponse = wr.path("/policyImprove/updateRelatedPhoneRule")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, rpVO);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public Boolean createPhoneRule(String interval, String limit) {
		wr = client.resource(getRootPath());
		RelatedPhoneVO rpVO = new RelatedPhoneVO();
		rpVO.setTimeInterval(interval);
		rpVO.setTotalNumber(limit);
        clientResponse = wr.path("/policyImprove/createPhoneRule")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, rpVO);

        Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public Boolean deleteRelatedPhoneRules(List<String> relatedPhoneRules) {
		wr = client.resource(getRootPath());
		
		Gson gson = new Gson();
        String str = gson.toJson(relatedPhoneRules);

        clientResponse = wr.path("/policyImprove/deleteRelatedPhoneRules")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, str);

		Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
		return result;
	}
	
	public List<IntervalVO> getAllIntervalPolicy() {
		wr = client.resource(getRootPath());
        
        clientResponse = wr.path("/policyImprove/allIntervalRules")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        List<IntervalVO> result = clientResponse.getEntity(new GenericType<List<IntervalVO>>(){});
        return result;
	}
	
	public List<IntervalVO> getIntervalPoliciesByInterval(String interval) {
		wr = client.resource(getRootPath());
        
        clientResponse = wr.path("/policyImprove/getIntervalPoliciesByInterval")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, interval);

        List<IntervalVO> result = clientResponse.getEntity(new GenericType<List<IntervalVO>>(){});
        return result;
	}
	
	public boolean createIntervalRule(String interval, String limit) {
		wr = client.resource(getRootPath());
        IntervalVO intVO = new IntervalVO();
        intVO.setTimeInterval(interval);
        intVO.setTotalAccount(limit);
        clientResponse = wr.path("/policyImprove/createIntervalRule")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, intVO);

        boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
        return result;
	}
	
	public Boolean updateIntervalRule(String ruleId, String interval,
			String limit) {
		IntervalVO intVO = new IntervalVO();
		intVO.setKey(ruleId);
		intVO.setTimeInterval(interval);
		intVO.setTotalAccount(limit);
		
		clientResponse = wr.path("/policyImprove/updateIntervalRule")
        .type(MediaType.APPLICATION_JSON_TYPE)
        .accept(MediaType.APPLICATION_JSON_TYPE)
        .post(ClientResponse.class, intVO);

		boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
		return result;
	}
	
	public Boolean deleteIntervalRules(List<String> intervalRules) {
		wr = client.resource(getRootPath());
		
		Gson gson = new Gson();
        String str = gson.toJson(intervalRules);

        clientResponse = wr.path("/policyImprove/deleteIntervalRules")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, str);

		Boolean result = clientResponse.getEntity(new GenericType<Boolean>(){});
		return result;
	}

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
