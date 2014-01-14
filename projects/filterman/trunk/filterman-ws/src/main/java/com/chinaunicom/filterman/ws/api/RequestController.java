package com.chinaunicom.filterman.ws.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.comm.vo.ModifiedVO;
import com.chinaunicom.filterman.comm.vo.PagerVO;
import com.chinaunicom.filterman.comm.vo.RequestVO;
import com.chinaunicom.filterman.core.bl.IAccountBL;
import com.chinaunicom.filterman.core.bl.IAppBL;
import com.chinaunicom.filterman.core.bl.IDeviceBL;
import com.chinaunicom.filterman.core.bl.IPhoneBL;
import com.chinaunicom.filterman.core.bl.IRequestBL;
import com.chinaunicom.filterman.core.db.entity.AccountEntity;
import com.chinaunicom.filterman.core.db.entity.AppEntity;
import com.chinaunicom.filterman.core.db.entity.DeviceEntity;
import com.chinaunicom.filterman.core.db.entity.PhoneEntity;
import com.chinaunicom.filterman.core.db.entity.RequestEntity;
import com.chinaunicom.filterman.utilities.Logging;

@Controller
@RequestMapping(value = "/request")
public class RequestController {
	@Autowired
    private IRequestBL requestBL;
	
	@Autowired
    private IAccountBL accountBL;
	
	@Autowired
    private IDeviceBL deviceBL;
	
	@Autowired
    private IPhoneBL phoneBL;
	
	@Autowired
    private IAppBL appBL;
	
	@RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public List<RequestVO> getAllRequests(@RequestBody PagerVO pagerVO) {
		List<RequestVO> requestData = new ArrayList<RequestVO>();
		List<RequestEntity> entities = null;
		String orderIdOrPhone = (String) pagerVO.getOtherMessage();
		int pageOffset = pagerVO.getPageOffset();
		int rowsPerPage = pagerVO.getRowsPerPage();
		Long count = null;
		try{
			if(!orderIdOrPhone.equals("-1")){
				entities = requestBL.getRequestsByOrderId(orderIdOrPhone, pageOffset, rowsPerPage);
				if(entities == null || entities.size() == 0){
					entities = requestBL.getRequestsByPhoneNumber(orderIdOrPhone, pageOffset, rowsPerPage);
					count = requestBL.getRequestsCountByPhoneNumber(orderIdOrPhone, pageOffset, rowsPerPage);
				}else{
					count = requestBL.getRequestsCountByOrderId(orderIdOrPhone, pageOffset, rowsPerPage);
				}
			}else{
				entities = requestBL.getAllRequests(pageOffset, rowsPerPage);
				count = requestBL.getAllRequestsCount(pageOffset, rowsPerPage);
			}
			
			for(int i=0;i<entities.size();i++){
				RequestEntity entity = entities.get(i);
				RequestVO request = new RequestVO();
				if(entity.getAccount() != null){
					request.setAccount(entity.getAccount());
				}
				if(entity.getTimestamp() != null){
					request.setDate(entity.getTimestamp());
				}
				if(entity.getOrderid() != null){
					request.setOrderId(entity.getOrderid());
				}
				if(entity.getAppName() != null){
					request.setAppName(entity.getAppName());
				}
				if(entity.getMac() != null){
					request.setDevice(entity.getMac());
				}
				if(entity.getPhone() != null){
					request.setPhone(entity.getPhone());
				}
				if(entity.getCheckFlg() != null){
					request.setIsAccess(entity.getCheckFlg());
				}
				if(entity.getExplanation() != null){
					request.setBlockReason(entity.getExplanation());
				}
				if(entity.getEnabledPolicy() != null){
					request.setEnabledPolicy(entity.getEnabledPolicy());
				}
				if(entity.getAppid() != null){
					request.setAppId(entity.getAppid());
				}
				if(i == 0){
					request.setCount(count);
				}
				requestData.add(request);
			}
		}catch(Exception e){
			Logging.logError("Doing RequestController.getAllRequests error", e);
		}
		return requestData;
	}
	
	@RequestMapping(value = "/apdDetailByKey", method = RequestMethod.POST)
	@ResponseBody
    public ModifiedVO apdDetailByKey(@RequestBody ModifiedVO request) {
		
		ModifiedVO mv = new ModifiedVO();
		
		try{
			String changedTable = request.getChangedTable();
			AppEntity appEntity = appBL.getAppByIdAndName(request.getAppId(), request.getAppName());
			String reallyAppId = appEntity.getId();
			if(changedTable.equals("account111")){
				AccountEntity an = accountBL.getAccount4One(request.getAccount(), reallyAppId);
				if(an.getKey() != null){
					mv.setAccount(an.getKey());
				}
				if(an.getDevices() != null){
					mv.setDevice(an.getDevices());
				}
				if(an.getPhones() != null){
					mv.setPhone(an.getPhones());
				}
			}else if(changedTable.equals("account222")){
				AccountEntity an = accountBL.getAccount4Two(request.getAccount(), reallyAppId);
				if(an.getKey() != null){
					mv.setAccount(an.getKey());
				}
				if(an.getDevices() != null){
					mv.setDevice(an.getDevices());
				}
				if(an.getPhones() != null){
					mv.setPhone(an.getPhones());
				}
			}else if(changedTable.equals("device111")){
				DeviceEntity dn = deviceBL.getDevice4One(request.getDevice(), reallyAppId);
				if(dn.getAccounts() != null){
					mv.setAccount(dn.getAccounts());
				}
				if(dn.getKey() != null){
					mv.setDevice(dn.getKey());
				}
				if(dn.getPhones() != null){
					mv.setPhone(dn.getPhones());
				}
			}else if(changedTable.equals("device222")){
				DeviceEntity dn = deviceBL.getDevice4Two(request.getDevice(), reallyAppId);
				if(dn.getAccounts() != null){
					mv.setAccount(dn.getAccounts());
				}
				if(dn.getKey() != null){
					mv.setDevice(dn.getKey());
				}
				if(dn.getPhones() != null){
					mv.setPhone(dn.getPhones());
				}
			}else if(changedTable.equals("phone111")){
				PhoneEntity pn = phoneBL.getPhone4One(request.getPhone(), reallyAppId);
				if(pn.getAccounts() != null){
					mv.setAccount(pn.getAccounts());
				}
				if(pn.getDevices() != null){
					mv.setDevice(pn.getDevices());
				}
				if(pn.getKey() != null){
					mv.setPhone(pn.getKey());
				}
			}else if(changedTable.equals("phone222")){
				PhoneEntity pn = phoneBL.getPhone4Two(request.getPhone(), reallyAppId);
				if(pn.getAccounts() != null){
					mv.setAccount(pn.getAccounts());
				}
				if(pn.getDevices() != null){
					mv.setDevice(pn.getDevices());
				}
				if(pn.getKey() != null){
					mv.setPhone(pn.getKey());
				}
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyController.apdDetailByKey error", e);
		}
		return mv;
	}
	
	@RequestMapping(value = "/updateAccountPhoneDevice", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateAccountPhoneDevice(@RequestBody ModifiedVO request) {
		try{
			
			String changedTable = request.getChangedTable();
			
			AppEntity appEntity = appBL.getAppByIdAndName(request.getAppId(), request.getAppName());
			String reallyAppId = appEntity.getId();
			
			if(changedTable.equals("account111")){
				AccountEntity an = accountBL.getAccount4One(request.getAccount(), reallyAppId);
				if(an != null){
					an.setDevices(request.getDevice());
					an.setPhones(request.getPhone());
					accountBL.updateAccount4One(an);
				}
			}else if(changedTable.equals("account222")){
				AccountEntity an = accountBL.getAccount4Two(request.getAccount(), reallyAppId);
				if(an != null){
					an.setDevices(request.getDevice());
					an.setPhones(request.getPhone());
					accountBL.updateAccount4Two(an);
				}
			}else if(changedTable.equals("device111")){
				DeviceEntity dn = deviceBL.getDevice4One(request.getDevice(), reallyAppId);
				if(dn != null){
					dn.setAccounts(request.getAccount());
					dn.setPhones(request.getPhone());
					deviceBL.updateDevice4One(dn);
				}
			}else if(changedTable.equals("device222")){
				DeviceEntity dn = deviceBL.getDevice4Two(request.getDevice(), reallyAppId);
				if(dn != null){
					dn.setAccounts(request.getAccount());
					dn.setPhones(request.getPhone());
					deviceBL.updateDevice4Two(dn);
				}
			}else if(changedTable.equals("phone111")){
				PhoneEntity pn = phoneBL.getPhone4One(request.getPhone(), reallyAppId);
				if(pn != null){
					pn.setAccounts(request.getAccount());
					pn.setDevices(request.getDevice());
					phoneBL.updatePhone4One(pn);
				}
			}else if(changedTable.equals("phone222")){
				PhoneEntity pn = phoneBL.getPhone4Two(request.getPhone(), reallyAppId);
				if(pn != null){
					pn.setAccounts(request.getAccount());
					pn.setDevices(request.getDevice());
					phoneBL.updatePhone4Two(pn);
				}
			}
		}catch(Exception e){
			Logging.logError("Doing PolicyController.updateAccountPhoneDevice error", e);
			return false;
		}
		return true;
	}
}
