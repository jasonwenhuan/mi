package com.chinaunicom.filterman.ws.validate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.chinaunicom.filterman.comm.xml.PayMessage;
import com.chinaunicom.filterman.core.bl.IAppBL;
import com.chinaunicom.filterman.core.bl.IGroupBL;
import com.chinaunicom.filterman.core.bl.IPolicyBL;
import com.chinaunicom.filterman.core.db.entity.AppEntity;
import com.chinaunicom.filterman.core.db.entity.GroupEntity;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.chinaunicom.filterman.core.bl.IRequestBL;
import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.entity.PolicyEntity;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;

import com.chinaunicom.filterman.utilities.Logging;

/**
 *  by wenhuan
 */
public class RequestValidate {

	@Autowired
	private IPolicyBL policyBL;

	@Autowired
	private IDefenseBL three1Defense;

	@Autowired
	private IDefenseBL three2Defense;

    @Autowired
    private IDefenseBL zonePayDefense;

    @Autowired
    private IDefenseBL intervalDefense;
	
	@Autowired
    private IDefenseBL relatedPhoneDefense;

	@Autowired
	private IDefenseBL blackListDefense;

    @Autowired
    private IDefenseBL whiteListDefense;
    
    @Autowired
    private IDefenseBL suspectedUserDefense;
    
    @Autowired
    private IAppBL appBL;

    @Autowired
    private IGroupBL groupBL;

    @Autowired
    private IRequestBL requestBL;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");

    public Boolean validate(String xml) throws RequestException {
        RequestEntity message = null;

        try {
            PayMessage entity = (PayMessage)JaxbUtils.parseXmlDataObject(PayMessage.class, xml);

            if (entity == null) {
                return false;
            }

            message = convertPayMessageToEntity(entity);
        }
        catch (Exception e)
        {
            String str = String.format("Requested XML data is [%s]", xml);
            throw new RequestException(e.getMessage() + "\n" + str);
        }

        return validate(message);
    }

    public boolean validate(RequestEntity entity) throws RequestException {
        FilterLog log = new FilterLog(entity);

        String groupId = "";
        if (entity.getAppid() != null && !"".equals(entity.getAppid().replace(" ", ""))) {
            AppEntity appEntity = appBL.getAppByIdAndName(entity.getAppid(), entity.getAppName());
            if (appEntity == null) {
                appEntity = new AppEntity();
                appEntity.setAppName(entity.getAppName());
                appEntity.setAppId(entity.getAppid());
                appEntity.setUpdateDate(new Date());
                appEntity.setUpdateUser("RequestValidate.validate");
                appBL.saveApp(appEntity);
            } else {
                groupId = appEntity.getGroupId();
                if (groupId == null) groupId = "";
            }
        }
        
        List<IDefenseBL> defenses = getAvailableDefenses(groupId);
        String availedPolicy = StringUtils.collectionToDelimitedString(defenses, " > ");
        log.setPolicy(availedPolicy);
        entity.setEnabledPolicy(availedPolicy);

        IDefenseBL defense = null;
        try {
            for(IDefenseBL defenseBL : defenses) {
                defense = defenseBL;
                defenseBL.execute(entity);
            }
        } catch (PolicyException e) {
            log.setExplanation(e.getMessage());

            entity.setExplanation(e.getMessage());

            if (defense != whiteListDefense) {
                entity.setCheckFlg(false);
                log.setCheckFlg(false);
            }
        }

        //save requested entity to db.
        requestBL.saveRequestedMessage(entity);

        Logging.logRequestAction(log);

        return log.getCheckFlg();
    }

	public RequestEntity convertPayMessageToEntity(PayMessage message) throws Exception {

		RequestEntity requestEntity = new RequestEntity();

        if(message.getCheckOrderIdRsp() != null){
            requestEntity.setCheckOutIdRsp(message.getCheckOrderIdRsp());
        }
        if(message.getAppname() != null){
            requestEntity.setAppName(message.getAppname());
        }
        if(message.getFeename() != null){
            requestEntity.setBillid(message.getFeename());
        }
        if(message.getPayfee() != null){
        	String fee = message.getPayfee().replaceAll("[^0-9^\\.]", "");
        	String intFee = fee;
        	if(fee.indexOf(".") != -1){
        		intFee = fee.substring(0, intFee.indexOf("."));
        	}
        	
            requestEntity.setBill(intFee);
            requestEntity.setPayfee(intFee);
        }
        if(message.getAppdeveloper() != null){
            requestEntity.setAppDeveloper(message.getAppdeveloper());
        }
        if(message.getGameaccount() != null){
            requestEntity.setAccount(message.getGameaccount());
        }
        if(message.getMacaddress() != null){
            String device = message.getMacaddress().replaceAll(" ", "").
                replaceAll(":", "").toUpperCase();
            requestEntity.setMac(device);
        }
        if(message.getAppid() != null){
            requestEntity.setAppid(message.getAppid());
        }
        if(message.getIpaddress() != null){
            requestEntity.setIp(message.getIpaddress());
        }
        if(message.getServiceid() != null){
            requestEntity.setBillLocation(message.getServiceid());
        }
        if(message.getChannelid() != null){
            requestEntity.setBillWay(message.getChannelid());
        }
        if(message.getCpid() != null){
            requestEntity.setCp(message.getCpid());
        }

        if (message.isTest()) {
            requestEntity.setTimestamp(dateFormat.parse(message.getOrdertime()));
        } else {
            requestEntity.setTimestamp(new Date());
        }

        if(message.getImei() != null){
            requestEntity.setImei(message.getImei());
        }
        if(message.getAppversion() != null){
            requestEntity.setAppVersion(message.getAppversion());
        }
        if(message.getUsercode() != null){
            requestEntity.setPhone(message.getUsercode());
        }
        if(message.getImsi() != null){
            requestEntity.setImsi(message.getImsi());
        }

        if(message.getOrderid() != null){
            requestEntity.setOrderid(message.getOrderid());
        }

        if(message.getPaytype() != null) {
            requestEntity.setPayType(message.getPaytype());
        }

        return requestEntity;
	}
	
	public RequestEntity convertRequestToEntity(String request) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
	    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(request, RequestEntity.class);

	}

	//thread safe
	private List<IDefenseBL> getAvailableDefenses(String groupId){

		List<IDefenseBL> defenses = new ArrayList<IDefenseBL>();

        PolicyEntity entity = policyBL.getAvailablePolicy(groupId);
        if (entity == null) {
        //using PinTai policy when the groupId has no policy.
            List<GroupEntity> groupEntities = groupBL.getGroupsByType("0");
            if (groupEntities != null && !groupEntities.isEmpty()) {
                GroupEntity groupEntity = groupEntities.get(0);
                entity = policyBL.getAvailablePolicy(groupEntity.getId());
            }
        }

        if (entity != null && entity.getPolicyIds() != null) {
            String[] myPolicies = entity.getPolicyIds().split(",");

            List<Integer> policies = new ArrayList<Integer>(myPolicies.length);
            for(String policy : myPolicies){
                policies.add(Integer.parseInt(policy));
            }

            Collections.sort(policies);
            for (int i = (policies.size() - 1); i >= 0; i--) {
                if(policies.get(i) == 10){
                    defenses.add(three1Defense);
                }else if(policies.get(i) == 20){
                    defenses.add(three2Defense);
                }else if(policies.get(i) == 30){
                    defenses.add(zonePayDefense);
                }else if(policies.get(i) == 40){
                    defenses.add(intervalDefense);
                }else if(policies.get(i) == 60){
                	defenses.add(relatedPhoneDefense);
                }else if(policies.get(i) == 70){
                	defenses.add(suspectedUserDefense);
                }else if(policies.get(i) == 80){
                    defenses.add(blackListDefense);
                }else if(policies.get(i) == 90){
                	defenses.add(whiteListDefense);
                }
            }
        }

		return defenses;
	}

    class FilterLog {
        private RequestEntity entity;
        private String policy;
        private String explanation;
        private boolean checkFlg = true;

        FilterLog(RequestEntity entity) {
            this.entity = entity;
        }

        String getPolicy() {
            return policy;
        }

        void setPolicy(String policy) {
            this.policy = policy;
        }

        RequestEntity getEntity() {
            return entity;
        }

        void setEntity(RequestEntity entity) {
            this.entity = entity;
        }

        String getExplanation() {
            return explanation;
        }

        void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        boolean getCheckFlg() {
            return checkFlg;
        }

        void setCheckFlg(boolean checkFlg) {
            this.checkFlg = checkFlg;
        }

        @Override
        public String toString() {
            return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
                    entity.getOrderid(),
                    entity.getAppid(),
                    entity.getAppName(),
                    policy,
                    checkFlg,
                    explanation,
                    entity.getAccount(),
                    entity.getPhone(),
                    entity.getMac(),
                    entity.getAppDeveloper());
        }
    }

}
