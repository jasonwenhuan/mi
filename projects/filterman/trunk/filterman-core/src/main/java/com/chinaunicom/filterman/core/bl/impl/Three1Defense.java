package com.chinaunicom.filterman.core.bl.impl;

import java.util.Date;

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

import com.chinaunicom.filterman.core.db.entity.PhoneEntity;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import org.springframework.util.StringUtils;

public class Three1Defense implements IDefenseBL {

    private final static String POLICY_NAME = "1-1-1";

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

    private final static String EXP_ACCOUNT_PHONE = "Trigger " + POLICY_NAME + ". The phone[%s] has been bound on account[%s].";
    private final static String EXP_ACCOUNT_DEVICE = "Trigger " + POLICY_NAME + ". The device[%s] has been bound on account[%s].";
    private final static String EXP_PHONE_ACCOUNT = "Trigger " + POLICY_NAME + ". The account[%s] has been bound on phone[%s].";
    private final static String EXP_PHONE_DEVICE = "Trigger " + POLICY_NAME + ". The device[%s] has been bound on phone[%s].";
    private final static String EXP_DEVICE_ACCOUNT = "Trigger " + POLICY_NAME + ". The account[%s] has been bound on device[%s].";
    private final static String EXP_DEVICE_PHONE = "Trigger " + POLICY_NAME + ". The phone[%s] has been bound on device[%s].";

	@Override
	public boolean execute(final RequestEntity requestEntity) throws RequestException, PolicyException {

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

        Date timestamp = new Date();
		boolean rightMark = true;
		
		String account = request.getAccount();
		String phone = request.getPhone();
		String device = request.getMac();
        if (uuid != null) {
            device = uuid;
            hasUuidVal = true;
            request.setMac(device);
        }

        boolean hasAccountVal = true;
        if (account == null || "".equals(account))
        	hasAccountVal = false;
        
        boolean hasDeviceVal = true;
        if (device == null || "".equals(device))
        	hasDeviceVal = false;
        
        boolean hasPhoneVal = true;
        if (phone == null || "".equals(phone))
        	hasPhoneVal = false;

        String appId = "";
        AppEntity appEntity = appDao.getAppByIdAndName(request.getAppid(), request.getAppName());
        if (appEntity == null) {
            return false;
        } else {
            appId = appEntity.getId();
        }

        AccountEntity accountEntity = null;

        DeviceEntity deviceEntity = null;

        PhoneEntity phoneEntity = null;

        if(hasAccountVal){
            if (hasUuidVal) {
                accountEntity = accountDao.getAccountUuid4One(account, appId);
            } else {
                accountEntity = accountDao.getAccount4One(account, appId);
            }
        }

        if(hasDeviceVal){
            if (hasUuidVal) {
                deviceEntity = deviceDao.getDeviceUuid4One(device, appId);
            } else {
                deviceEntity = deviceDao.getDevice4One(device, appId);
            }
        }

        if(hasPhoneVal){
            if (hasUuidVal) {
                phoneEntity = phoneDao.getPhoneUuid4One(phone, appId);
            } else {
                phoneEntity = phoneDao.getPhone4One(phone, appId);
            }
        }

        boolean isSameAccount = false;
        boolean isSamePhone = false;

        if (accountEntity != null) {
            if (hasPhoneVal) {
                if (accountEntity.getPhones().indexOf(phone) > -1) {
                    isSamePhone = true;
                }

                rightMark = checkNewMessage(accountEntity.getPhones(), phone, rightMark);
                if(!rightMark) {
                    throw new PolicyException(String.format(EXP_ACCOUNT_PHONE, accountEntity.getPhones(), accountEntity.getKey()));
                }
            }

            if (!isSamePhone && hasDeviceVal) {
                rightMark = checkNewMessage(accountEntity.getDevices(), device, rightMark);
                if(!rightMark) {
                    throw new PolicyException(String.format(EXP_ACCOUNT_DEVICE, accountEntity.getDevices(), accountEntity.getKey()));
                }
            }
        }

        if (phoneEntity != null) {
            if (hasAccountVal) {
                if (phoneEntity.getAccounts().indexOf(account) > -1) {
                    isSameAccount = true;
                }

                rightMark = checkNewMessage(phoneEntity.getAccounts(), account, rightMark);
                if(!rightMark) {
                    throw new PolicyException(String.format(EXP_PHONE_ACCOUNT, phoneEntity.getAccounts(), phoneEntity.getKey()));
                }
            }

            if (!isSameAccount && hasDeviceVal) {
                rightMark = checkNewMessage(phoneEntity.getDevices(), device, rightMark);
                if(!rightMark) {
                    throw new PolicyException(String.format(EXP_PHONE_DEVICE, phoneEntity.getDevices(), phoneEntity.getKey()));
                }
            }
        }

        if (!(isSameAccount && isSamePhone) && deviceEntity != null) {
            if (hasAccountVal) {
                rightMark = checkNewMessage(deviceEntity.getAccounts(), account, rightMark);
                if(!rightMark) {
                    throw new PolicyException(String.format(EXP_DEVICE_ACCOUNT, deviceEntity.getAccounts(), deviceEntity.getKey()));
                }
            }

            if (hasPhoneVal) {
                rightMark = checkNewMessage(deviceEntity.getPhones(), phone, rightMark);
                if(!rightMark) {
                    throw new PolicyException(String.format(EXP_DEVICE_PHONE, deviceEntity.getPhones(), deviceEntity.getKey()));
                }
            }
        }

        if (rightMark) {
            if(hasAccountVal){
                accountEntity = new AccountEntity(account, phone, device, timestamp, appId);
                if (hasUuidVal) {
                    accountDao.updateAccountUuid4One(accountEntity);
                } else {
                    accountDao.updateAccount4One(accountEntity);
                }
            }

            if(hasPhoneVal){
                phoneEntity = new PhoneEntity(phone, account, device, timestamp, appId);
                if (hasUuidVal) {
                    phoneDao.updatePhoneUuid4One(phoneEntity);
                } else {
                    phoneDao.updatePhone4One(phoneEntity);
                }
            }

            if(!(isSameAccount && isSamePhone) && hasDeviceVal){
                deviceEntity = new DeviceEntity(device, account, phone, timestamp, appId);
                if (hasUuidVal) {
                    deviceDao.updateDeviceUuid4One(deviceEntity);
                } else {
                    deviceDao.updateDevice4One(deviceEntity);
                }
            }

        }

		return rightMark;
	}

	private boolean checkNewMessage(String originalMessage, String receivedMessage, boolean rightMark) {
		if(rightMark){
			if (originalMessage == null
                    || "".equalsIgnoreCase(originalMessage)
                    || (originalMessage.split(",").length == 1
                        && originalMessage.trim().equalsIgnoreCase(receivedMessage.trim()))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

    @Override
    public String toString() {
        return POLICY_NAME;
    }
}
