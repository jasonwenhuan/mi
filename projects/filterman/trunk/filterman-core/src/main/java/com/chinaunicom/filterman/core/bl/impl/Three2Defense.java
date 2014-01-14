package com.chinaunicom.filterman.core.bl.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.chinaunicom.filterman.core.bl.IDefenseBL;
import com.chinaunicom.filterman.core.bl.exceptions.PolicyException;
import com.chinaunicom.filterman.core.bl.exceptions.RequestException;
import com.chinaunicom.filterman.core.db.dao.AccountDao;
import com.chinaunicom.filterman.core.db.dao.AppDao;
import com.chinaunicom.filterman.core.db.dao.DeviceDao;
import com.chinaunicom.filterman.core.db.dao.OrderInfoDao;
import com.chinaunicom.filterman.core.db.dao.PhoneDao;
import com.chinaunicom.filterman.core.db.entity.AccountEntity;
import com.chinaunicom.filterman.core.db.entity.AppEntity;
import com.chinaunicom.filterman.core.db.entity.DeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import com.chinaunicom.filterman.core.db.entity.PhoneEntity;
import org.springframework.util.StringUtils;

public class Three2Defense implements IDefenseBL {

    private final static String POLICY_NAME = "2-2-2";

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private PhoneDao phoneDao;

    @Autowired
    private AppDao appDao;

    @Autowired
    private OrderInfoDao orderInfoDao;

	private final int DEFENSESTRATEGY = 2;
    private final static String EXP_ACCOUNT_PHONE = "Trigger " + POLICY_NAME + ". The phone[%s] has been bound on account[%s].";
    private final static String EXP_ACCOUNT_DEVICE = "Trigger " + POLICY_NAME + ". The device[%s] has been bound on account[%s].";
    private final static String EXP_PHONE_ACCOUNT = "Trigger " + POLICY_NAME + ". The account[%s] has been bound on phone[%s].";
    private final static String EXP_PHONE_DEVICE = "Trigger " + POLICY_NAME + ". The device[%s] has been bound on phone[%s].";
    private final static String EXP_DEVICE_ACCOUNT = "Trigger " + POLICY_NAME + ". The account[%s] has been bound on device[%s].";
    private final static String EXP_DEVICE_PHONE = "Trigger " + POLICY_NAME + ". The phone[%s] has been bound on device[%s].";

	@Override
	public boolean execute(final RequestEntity requestEntity) throws PolicyException, RequestException {

        if(!StringUtils.hasText(requestEntity.getAppid())) {
            throw new RequestException("The appid is blank.", requestEntity);
        }

        if(!requestEntity.getPayType().equals("2") && !StringUtils.hasText(requestEntity.getAccount())) {
            throw new RequestException("The account is blank.", requestEntity);
        }

        if(!StringUtils.hasText(requestEntity.getPhone())) {
            throw new RequestException("The phone is blank.", requestEntity);
        }

        RequestEntity request = new RequestEntity();
        request.setMac(requestEntity.getMac());
        request.setAccount(requestEntity.getAccount());
        request.setPhone(requestEntity.getPhone());
        request.setAppid(requestEntity.getAppid());
        request.setAppName(requestEntity.getAppName());
        request.setOrderid(requestEntity.getOrderid());

        String uuid = orderInfoDao.getUuid(request.getOrderid());
        boolean hasUuidVal = false;
        if (uuid != null) {
            hasUuidVal = true;
            request.setMac(uuid);
        }

		Date timestamp = new Date();
		
		boolean rightMark = true;
        boolean hasAccountVal = true;
        if (request.getAccount() == null || "".equals(request.getAccount()))
        	hasAccountVal = false;
        
        boolean hasDeviceVal = true;
        if (request.getMac() == null || "".equals(request.getMac()))
        	hasDeviceVal = false;
        
        boolean hasPhoneVal = true;
        if (request.getPhone() == null || "".equals(request.getPhone()))
        	hasPhoneVal = false;

        String appId = "";
        AppEntity appEntity = appDao.getAppByIdAndName(request.getAppid(), request.getAppName());
        if (appEntity == null) {
            return false;
        } else {
            appId = appEntity.getId();
        }

        Map<String,Object> entityMap = new HashMap<String,Object>();
        entityMap.put("newAccountEntity", null);
        entityMap.put("newDeviceEntity", null);
        entityMap.put("newPhoneEntity", null);
        entityMap.put("timestamp", timestamp);
        entityMap.put("request", null);
        entityMap.put("rightMark", rightMark);
        entityMap.put("isSamePhone", false);
        entityMap.put("isSameAccount", false);

        if(hasAccountVal){
            entityMap = checkAccountEntity(request, entityMap, hasDeviceVal, hasPhoneVal, appId, hasUuidVal);
            if (entityMap == null) return false;
        }

        if(hasPhoneVal){
            entityMap = checkPhoneEntity(request, entityMap, hasAccountVal, hasDeviceVal, appId, hasUuidVal);
            if (entityMap == null) return false;
        }

        if(!((Boolean)entityMap.get("isSamePhone")
                && (Boolean)entityMap.get("isSameAccount"))
                && hasDeviceVal){
            entityMap = checkDeviceEntity(request, entityMap, hasAccountVal, hasPhoneVal, appId, hasUuidVal);
            if (entityMap == null) return false;
        }

        rightMark = (Boolean) entityMap.get("rightMark");

        if(rightMark){
            if(hasAccountVal){
                if (hasUuidVal) {
                    accountDao.updateAccountUuid4Two((AccountEntity) entityMap.get("newAccountEntity"));
                } else {
                    accountDao.updateAccount4Two((AccountEntity) entityMap.get("newAccountEntity"));
                }
            }

            if(hasDeviceVal){
                if (hasUuidVal) {
                    deviceDao.updateDeviceUuid4Two((DeviceEntity) entityMap.get("newDeviceEntity"));
                } else {
                    deviceDao.updateDevice4Two((DeviceEntity) entityMap.get("newDeviceEntity"));
                }
            }

            if(!((Boolean)entityMap.get("isSamePhone")
                    && (Boolean)entityMap.get("isSameAccount"))
                    && hasPhoneVal){
                if (hasUuidVal) {
                    phoneDao.updatePhoneUuid4Two((PhoneEntity) entityMap.get("newPhoneEntity"));
                } else {
                    phoneDao.updatePhone4Two((PhoneEntity) entityMap.get("newPhoneEntity"));
                }
            }
        }

		return rightMark;
	}
	
	private Map<String,Object> checkRequest(String originalRequest,
                                            String receivedRequest,
                                            Map<String,Object> map) {
		if (map.get("rightMark") != null && (Boolean) map.get("rightMark")) {
			String newRequest = "";
			boolean status = true;
			if (receivedRequest != null && !"".equalsIgnoreCase(receivedRequest.trim())) {
                if (originalRequest != null && !"".equals(originalRequest)) {
                    String[] oldIds = originalRequest.split(",");
                    for (String id : oldIds) {
                        if (id != null && !"".equals(id)) {
                            if (!"".equals(newRequest)) {
                                newRequest += "," + id;
                            } else {
                                newRequest += id;
                            }
                        }
                    }

                    int RequestLength = newRequest.split(",").length;
                    if (RequestLength > DEFENSESTRATEGY) {
                        status = false;
                    } else if (newRequest.indexOf(receivedRequest) < 0) {
                        if (RequestLength < DEFENSESTRATEGY) {
                            newRequest += "," + receivedRequest;
                        } else {
                            status = false;
                        }
                    }
                } else {
                    newRequest = receivedRequest;
                }
			}

			map.put("request", newRequest);
			map.put("rightMark", status);
		}

		return map;
	}
	
	private Map<String, Object> checkAccountEntity(RequestEntity request,
                                                   Map<String,Object> entityMap,
                                                   boolean hasDeviceVal,
                                                   boolean hasPhoneVal,
                                                   String appId,
                                                   boolean hasUuidVal) throws PolicyException {

        AccountEntity newAccountEntity = null;
        if (hasUuidVal) {
            newAccountEntity = accountDao.getAccountUuid4Two(request.getAccount(), appId);
        } else {
            newAccountEntity = accountDao.getAccount4Two(request.getAccount(), appId);
        }

        if (newAccountEntity != null) {
            newAccountEntity.setTimestamp((Date) entityMap.get("timestamp"));

            if (hasPhoneVal) {
                if (newAccountEntity.getPhones().indexOf(request.getPhone()) > -1) {
                    entityMap.put("isSamePhone", true);
                }

                entityMap = checkRequest(newAccountEntity.getPhones(), request.getPhone(), entityMap);
                if(!(Boolean)entityMap.get("rightMark")) {
                    throw new PolicyException(String.format(EXP_ACCOUNT_PHONE, newAccountEntity.getPhones(), newAccountEntity.getKey()));
                }

                String newphones = entityMap.get("request").toString();
                newAccountEntity.setPhones(newphones);
            }

            if (!(Boolean)entityMap.get("isSamePhone") && hasDeviceVal) {
                entityMap = checkRequest(newAccountEntity.getDevices(), request.getMac(), entityMap);
                if(!(Boolean)entityMap.get("rightMark")) {
                    throw new PolicyException(String.format(EXP_ACCOUNT_DEVICE, newAccountEntity.getDevices(), newAccountEntity.getKey()));
                }

                String newdevices = entityMap.get("request").toString();
                newAccountEntity.setDevices(newdevices);
            }
        } else {
            newAccountEntity = new AccountEntity(
                    request.getAccount(),
                    request.getPhone(),
                    request.getMac(),
                    (Date) entityMap.get("timestamp"),
                    appId);
        }

        entityMap.put("newAccountEntity", newAccountEntity);

		return entityMap;
	}
	
	private Map<String,Object> checkDeviceEntity(RequestEntity Request,
                                                 Map<String,Object> entityMap,
                                                 boolean hasAccountVal,
                                                 boolean hasPhoneVal,
                                                 String appId,
                                                 boolean hasUuidVal) throws PolicyException {

        DeviceEntity newDeviceEntity = null;
        if (hasUuidVal) {
            newDeviceEntity = deviceDao.getDeviceUuid4Two(Request.getMac(), appId);
        } else {
            newDeviceEntity = deviceDao.getDevice4Two(Request.getMac(), appId);
        }

        if (newDeviceEntity != null) {
            newDeviceEntity.setTimestamp((Date) entityMap.get("timestamp"));

            if (hasAccountVal) {
                entityMap = checkRequest(newDeviceEntity.getAccounts(), Request.getAccount(), entityMap);
                if(!(Boolean)entityMap.get("rightMark")) {
                    throw new PolicyException(String.format(EXP_DEVICE_ACCOUNT, newDeviceEntity.getAccounts(), newDeviceEntity.getKey()));
                }
                newDeviceEntity.setAccounts(entityMap.get("request").toString());
            }

            if (hasPhoneVal) {
                entityMap = checkRequest(newDeviceEntity.getPhones(), Request.getPhone(), entityMap);
                if(!(Boolean)entityMap.get("rightMark")) {
                    throw new PolicyException(String.format(EXP_DEVICE_PHONE, newDeviceEntity.getPhones(), newDeviceEntity.getKey()));
                }
                newDeviceEntity.setPhones(entityMap.get("request").toString());
            }
        } else {
            newDeviceEntity = new DeviceEntity(
                    Request.getMac(),
                    Request.getAccount(),
                    Request.getPhone(),
                    (Date)entityMap.get("timestamp"),
                    appId);
        }

        entityMap.put("newDeviceEntity", newDeviceEntity);

		return entityMap;
	}
	
	private Map<String,Object> checkPhoneEntity(RequestEntity request,
                                                Map<String,Object> entityMap,
                                                boolean hasAccountVal,
                                                boolean hasDeviceVal,
                                                String appId,
                                                boolean hasUuidVal) throws PolicyException {

        PhoneEntity newPhoneEntity = null;
        if (hasUuidVal) {
            newPhoneEntity = phoneDao.getPhoneUuid4Two(request.getPhone(), appId);
        } else {
            newPhoneEntity = phoneDao.getPhone4Two(request.getPhone(), appId);
        }

        if (newPhoneEntity != null) {
            newPhoneEntity.setTimestamp((Date) entityMap.get("timestamp"));

            if (hasAccountVal) {
                if (newPhoneEntity.getAccounts().indexOf(request.getAccount()) > -1) {
                    entityMap.put("isSameAccount", true);
                }

                entityMap = checkRequest(newPhoneEntity.getAccounts(), request.getAccount(), entityMap);
                if(!(Boolean)entityMap.get("rightMark")) {
                    throw new PolicyException(String.format(EXP_PHONE_ACCOUNT, newPhoneEntity.getAccounts(), newPhoneEntity.getKey()));
                }
                newPhoneEntity.setAccounts(entityMap.get("request").toString());
            }

            if (!(Boolean)entityMap.get("isSameAccount") && hasDeviceVal) {
                entityMap = checkRequest(newPhoneEntity.getDevices(), request.getMac(), entityMap);
                if(!(Boolean)entityMap.get("rightMark")) {
                    throw new PolicyException(String.format(EXP_PHONE_DEVICE, newPhoneEntity.getDevices(), newPhoneEntity.getKey()));
                }
                newPhoneEntity.setDevices(entityMap.get("request").toString());
            }
        } else {
            newPhoneEntity = new PhoneEntity(
                    request.getPhone(),
                    request.getAccount(),
                    request.getMac(),
                    (Date) entityMap.get("timestamp"),
                    appId);
        }

        entityMap.put("newPhoneEntity", newPhoneEntity);

		return entityMap;
	}

    @Override
    public String toString() {
        return POLICY_NAME;
    }
}
