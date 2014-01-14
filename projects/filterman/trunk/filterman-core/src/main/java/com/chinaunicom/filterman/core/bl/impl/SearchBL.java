package com.chinaunicom.filterman.core.bl.impl;

import com.chinaunicom.filterman.comm.vo.FilterVO;
import com.chinaunicom.filterman.comm.vo.KeyValueVO;
import com.chinaunicom.filterman.comm.vo.SearchVO;
import com.chinaunicom.filterman.core.bl.IAccountBL;
import com.chinaunicom.filterman.core.bl.IAppBL;
import com.chinaunicom.filterman.core.bl.IBlackListBL;
import com.chinaunicom.filterman.core.bl.IDeviceBL;
import com.chinaunicom.filterman.core.bl.IGroupBL;
import com.chinaunicom.filterman.core.bl.IPhoneBL;
import com.chinaunicom.filterman.core.bl.IPolicyBL;
import com.chinaunicom.filterman.core.bl.ISearchBL;
import com.chinaunicom.filterman.core.db.entity.AccountEntity;
import com.chinaunicom.filterman.core.db.entity.AppEntity;
import com.chinaunicom.filterman.core.db.entity.DeviceEntity;
import com.chinaunicom.filterman.core.db.entity.GroupEntity;
import com.chinaunicom.filterman.core.db.entity.PhoneEntity;
import com.chinaunicom.filterman.core.db.entity.PolicyEntity;
import com.chinaunicom.filterman.core.db.entity.WBListEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: larry
 */

public class SearchBL implements ISearchBL {

    @Autowired
    private IAccountBL accountBL;

    @Autowired
    private IPhoneBL phoneBL;

    @Autowired
    private IDeviceBL deviceBL;

    @Autowired
    private IBlackListBL blackListBL;

    @Autowired
    private IPolicyBL policyBL;

    @Autowired
    private IAppBL appBL;

    @Autowired
    private IGroupBL groupBL;

    public List<SearchVO> getData(FilterVO myFilter) {
        List<SearchVO> tableData = new ArrayList<SearchVO>();

        String account = myFilter.getAccount();
        String phone = myFilter.getPhone();
        String device = myFilter.getDevice();
        String appId = myFilter.getAppId();

        if(appId == null || myFilter.getAppId().equalsIgnoreCase("")){
            return tableData;
        }

        String policyIds = getCurrentPolicy(appId);
        if(account != null && !"".equals(account)){
            if(policyIds.indexOf("10") > -1){
                AccountEntity accountEntity = accountBL.getAccount4One(account, appId);
                if(accountEntity != null){
                    SearchVO accountData = convertAccountEntityToTableData(accountEntity);
                    tableData.add(accountData);
                }
            }

            if(policyIds.indexOf("20") > -1){
                AccountEntity accountEntity = accountBL.getAccount4Two(account, appId);
                if(accountEntity != null){
                    SearchVO accountData = convertAccountEntityToTableData(accountEntity);
                    tableData.add(accountData);
                }
            }
        }

        if(phone != null && !"".equals(phone)){
            if(policyIds.indexOf("10") > -1){
                PhoneEntity phoneEntity = phoneBL.getPhone4One(phone, appId);
                if(phoneEntity != null){
                    SearchVO phoneData = convertPhoneEntityToTableData(phoneEntity);
                    tableData.add(phoneData);
                }
            }

            if(policyIds.indexOf("20") > -1){
                PhoneEntity phoneEntity = phoneBL.getPhone4Two(phone, appId);
                if(phoneEntity != null){
                    SearchVO phoneData = convertPhoneEntityToTableData(phoneEntity);
                    tableData.add(phoneData);
                }
            }
        }

        if(device != null && !"".equals(device)){
            if(policyIds.indexOf("10") > -1){
                DeviceEntity deviceEntity = deviceBL.getDevice4One(device, appId);
                if(deviceEntity != null){
                    SearchVO deviceData = convertDeviceEntityToTableData(deviceEntity);
                    tableData.add(deviceData);
                }
            }

            if(policyIds.indexOf("20") > -1){
                DeviceEntity deviceEntity = deviceBL.getDevice4Two(device, appId);
                if(deviceEntity != null){
                    SearchVO deviceData = convertDeviceEntityToTableData(deviceEntity);
                    tableData.add(deviceData);
                }
            }
        }

        if(policyIds.indexOf("30") > -1){
            if(account != null && !"".equals(account)){
                WBListEntity blacklistEntity = blackListBL.getBlackList(account);
                if(blacklistEntity != null){
                    tableData.add(convertBlackListEntityToTableData(blacklistEntity));
                }
            }

            if(phone != null && !"".equals(phone)){
                WBListEntity blacklistEntity = blackListBL.getBlackList(phone);
                if(blacklistEntity != null){
                    tableData.add(convertBlackListEntityToTableData(blacklistEntity));
                }
            }

            if(device != null && !"".equals(device)){
                WBListEntity blacklistEntity = blackListBL.getBlackList(device);
                if(blacklistEntity != null){
                    tableData.add(convertBlackListEntityToTableData(blacklistEntity));
                }
            }
        }
        
        return tableData;
    }

    private SearchVO convertDeviceEntityToTableData(DeviceEntity deviceEntity) {
        SearchVO tableData = new SearchVO();
        tableData.setKey(deviceEntity.getKey());
        tableData.setDevices(deviceEntity.getKey());
        tableData.setAppId(deviceEntity.getAppid());
        if(deviceEntity.getAccounts() != null){
            tableData.setAccounts(deviceEntity.getAccounts());
        }
        if(deviceEntity.getPhones() != null){
            tableData.setPhones(deviceEntity.getPhones());
        }
        tableData.setUid("--");
        return tableData;
    }

    private SearchVO convertPhoneEntityToTableData(PhoneEntity phoneEntity) {
        SearchVO tableData = new SearchVO();
        tableData.setKey(phoneEntity.getKey());
        tableData.setPhones(phoneEntity.getKey());
        tableData.setAppId(phoneEntity.getAppid());
        if(phoneEntity.getDevices() != null){
            tableData.setDevices(phoneEntity.getDevices());
        }
        if(phoneEntity.getAccounts() != null){
            tableData.setAccounts(phoneEntity.getAccounts());
        }
        tableData.setUid("--");
        return tableData;
    }

    private SearchVO convertAccountEntityToTableData(AccountEntity accountEntity){
        SearchVO tableData = new SearchVO();
        tableData.setKey(accountEntity.getKey());
        tableData.setAccounts(accountEntity.getKey());
        tableData.setAppId(accountEntity.getAppid());
        if(accountEntity.getDevices() != null){
            tableData.setDevices(accountEntity.getDevices());
        }
        if(accountEntity.getPhones() != null){
            tableData.setPhones(accountEntity.getPhones());
        }
        tableData.setUid("--");
        return tableData;
    }

    private SearchVO convertBlackListEntityToTableData(WBListEntity blackListEntity){
        SearchVO tableData = new SearchVO();
        tableData.setKey(blackListEntity.getKeyId());
        tableData.setAppId(blackListEntity.getAppId());
        tableData.setAccounts("--");
        tableData.setPhones("--");
        tableData.setDevices("--");
        tableData.setUid(blackListEntity.getId());
        tableData.setHasBlackListData("0");
        return tableData;
    }

    public boolean updateData(String data) {
        String[] waitChange = data.split(";");
        for(int i=0;i<waitChange.length;i++){
            String[] keyValue = waitChange[i].split("&&");
            Map<String, String> records = convertKeyValue(keyValue);
            String accounts = records.get("accounts");
            String phones = records.get("phones");
            String devices = records.get("devices");
            String appId = records.get("appId");
            String key = records.get("key");
            String uid = records.get("uid");
            Date date = new Date();
            if(appId == null || appId.equals("")){
                return false;
            }

            String policyIds = getCurrentPolicy(appId);

            //change account data
            if(key != null && key.equalsIgnoreCase(accounts)){

                if(policyIds.indexOf("10") > -1){
                    AccountEntity oldAccountEntity = accountBL.getAccount4One(key, appId);
                    if(oldAccountEntity != null){
                        oldAccountEntity.setPhones(phones);
                        oldAccountEntity.setDevices(devices);
                        oldAccountEntity.setTimestamp(date);
                        accountBL.updateAccount4One(oldAccountEntity);
                    }
                }

                if(policyIds.indexOf("20") > -1){
                    AccountEntity oldAccountEntity = accountBL.getAccount4Two(key, appId);
                    if(oldAccountEntity != null){
                        oldAccountEntity.setPhones(phones);
                        oldAccountEntity.setDevices(devices);
                        oldAccountEntity.setTimestamp(date);
                        accountBL.updateAccount4Two(oldAccountEntity);
                    }
                }
            }

            //change phone data
            if(key != null && key.equalsIgnoreCase(phones)){
                if(policyIds.indexOf("10") > -1){
                    PhoneEntity oldPhoneEntity = phoneBL.getPhone4One(key, appId);
                    if(oldPhoneEntity != null){
                        oldPhoneEntity.setAccounts(accounts);
                        oldPhoneEntity.setDevices(devices);
                        oldPhoneEntity.setTimestamp(date);
                        phoneBL.updatePhone4Two(oldPhoneEntity);
                    }
                }

                if(policyIds.indexOf("20") > -1){
                    PhoneEntity oldPhoneEntity = phoneBL.getPhone4Two(key, appId);
                    if(oldPhoneEntity != null){
                        oldPhoneEntity.setAccounts(accounts);
                        oldPhoneEntity.setDevices(devices);
                        oldPhoneEntity.setTimestamp(date);
                        phoneBL.updatePhone4Two(oldPhoneEntity);
                    }
                }
            }

            //change device data
            if(key != null && key.equalsIgnoreCase(devices)){
                if(policyIds.indexOf("10") > -1){
                    DeviceEntity oldDeviceEntity = deviceBL.getDevice4One(key, appId);
                    if(oldDeviceEntity != null){
                        oldDeviceEntity.setAccounts(accounts);
                        oldDeviceEntity.setPhones(phones);
                        oldDeviceEntity.setTimestamp(date);
                        deviceBL.updateDevice4One(oldDeviceEntity);
                    }
                }

                if(policyIds.indexOf("20") > -1){
                    DeviceEntity oldDeviceEntity = deviceBL.getDevice4Two(key, appId);
                    if(oldDeviceEntity != null){
                        oldDeviceEntity.setAccounts(accounts);
                        oldDeviceEntity.setPhones(phones);
                        oldDeviceEntity.setTimestamp(date);
                        deviceBL.updateDevice4Two(oldDeviceEntity);
                    }
                }
            }
        }

        return true;
    }

    private Map<String, String> convertKeyValue(String[] keyValue){
        Map<String, String> records = new HashMap<String, String>();
        for(int i=0;i<keyValue.length;i++){
            String[] record = keyValue[i].split(":");
            if (record.length < 2)
                records.put(record[0], "");
            else
                records.put(record[0], record[1]);
        }
        return records;
    }

    private String getCurrentPolicy(String id){
    	//if we use id as appId, there are some change here
        //AppEntity appEntity = appBL.getAppById(appId);
    	//AppEntity appEntity = null;
    	//List<AppEntity> appEntities = appBL.getAppByAppId(appId);
    	/*if(appEntities != null && appEntities.size() > 0){
    		appEntity = appEntities.get(0);
    	}*/
    	AppEntity appEntity = appBL.getAppById(id);
        if (appEntity == null) {
        	return "";
        }
        PolicyEntity policyEntity = policyBL.getAvailablePolicy(appEntity.getGroupId());
        if (policyEntity == null) {
            //using PinTai policy when the groupId has no policy.
            List<GroupEntity> groupEntities = groupBL.getGroupsByType("0");
            if (groupEntities != null && !groupEntities.isEmpty()) {
                GroupEntity groupEntity = groupEntities.get(0);
                policyEntity = policyBL.getAvailablePolicy(groupEntity.getId());
            }
        }
        if(policyEntity == null){
        	return "";
        }
        return policyEntity.getPolicyIds();
    }

    public List<KeyValueVO> getAppKeyValue() {
        List<KeyValueVO> lst = new ArrayList<KeyValueVO>();
        List<AppEntity> entities = appBL.getAllApps(0, 0);
        if (entities != null) {
            for (int i=0;i<entities.size();i++) {
            	AppEntity entity = entities.get(i);
                KeyValueVO vo = new KeyValueVO();
                //vo.setValue(entity.getAppId());
                vo.setId(entity.getId());
                vo.setName(entity.getAppName());
                lst.add(vo);
            }
        }
        return lst;
    }
}
