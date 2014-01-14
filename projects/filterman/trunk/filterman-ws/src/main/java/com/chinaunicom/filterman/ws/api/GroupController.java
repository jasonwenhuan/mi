package com.chinaunicom.filterman.ws.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.chinaunicom.filterman.comm.vo.AppVO;
import com.chinaunicom.filterman.comm.vo.GroupVO;
import com.chinaunicom.filterman.comm.vo.PagerVO;
import com.chinaunicom.filterman.core.bl.IAppBL;
import com.chinaunicom.filterman.core.bl.IGroupBL;
import com.chinaunicom.filterman.core.bl.IPolicyBL;
import com.chinaunicom.filterman.core.db.entity.AppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.core.db.entity.GroupEntity;
import com.chinaunicom.filterman.utilities.Logging;

@Controller
@RequestMapping(value = "/group")
public class GroupController {
	@Autowired
    private IGroupBL groupBL;
	
	@Autowired
    private IPolicyBL policyBL;
	
	@Autowired
    private IAppBL appBL;
	
	@RequestMapping(value = "/allGroups", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupVO> getAllGroups() {
		List<GroupVO> lstGroupVO = new ArrayList<GroupVO>();

        List<GroupEntity> entities = groupBL.getGroups();
        if (entities != null) {
            for(GroupEntity entity : entities){
                GroupVO groupVO = new GroupVO();
                if(entity.getId() != null){
                    groupVO.setGroupId(entity.getId());
                }

                if(entity.getCreateUser() != null){
                    groupVO.setCreateUser(entity.getCreateUser());
                }

                if(entity.getCreateDate() != null){
                    groupVO.setCreateDate(entity.getCreateDate());
                }

                if(entity.getGroupName() != null){
                    groupVO.setGroupName(entity.getGroupName());
                }

                if(entity.getType() != null){
                    groupVO.setType(entity.getType());
                }

                lstGroupVO.add(groupVO);
            }
        }

        return lstGroupVO;
    }
	
	@RequestMapping(value = "/saveGroup", method = RequestMethod.POST)
    @ResponseBody
    public Boolean saveGroup(@RequestBody GroupVO vo){
		try{
			if(vo == null){
				return false;
			}
			
			GroupEntity groupEntity = new GroupEntity();
			if(vo.getCreateUser() != null){
				groupEntity.setCreateUser(vo.getCreateUser());
			}
			if(vo.getGroupName() != null){
				groupEntity.setGroupName(vo.getGroupName());
			}
			if(vo.getType() != null){
				groupEntity.setType(vo.getType());
			}
			vo.setCreateDate(new Date());
			groupBL.saveGroup(groupEntity);
		}catch(Exception e){
			Logging.logError("Doing GroupController.saveGroup error", e);
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value = "/removeGroups", method = RequestMethod.POST)
    @ResponseBody
    public Boolean removeGroups(@RequestBody List<String> groupIds){
		try{
			if(groupIds == null){
				return false;
			}
			for(String id : groupIds){
				groupBL.removeGroup(id);
				policyBL.removePolicyByGroupId(id);
				appBL.resetAppGroup(id);
			}
		}catch(Exception e){
			Logging.logError("Doing GroupController.removeGroups error", e);
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/allApps", method = RequestMethod.POST)
    @ResponseBody
    public List<AppVO> getAllApps(@RequestBody PagerVO pagerVO){
		int pageOffset = pagerVO.getPageOffset();
		int rowsPerPage = pagerVO.getRowsPerPage();
		
		List<AppEntity> entities = appBL.getAllApps(pageOffset, rowsPerPage);

        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		List<AppVO> apps= new ArrayList<AppVO>();
		 
        if (entities == null) return null;
        
        Long count = appBL.getAppsCount();
        
		for(int i=0;i<entities.size();i++) {
			AppVO appVO = new AppVO();
			AppEntity entity = entities.get(i);
			appVO.setId(entity.getId());
		        
			if(entity.getAppId() != null){
				appVO.setAppId(entity.getAppId());
			}
			if(entity.getAppName() != null){
				appVO.setAppName(entity.getAppName());
			}
			if(entity.getGroupId() != null){
			    appVO.setGroupId(entity.getGroupId());
			}
			if(entity.getGroupName() != null){
				appVO.setGroupName(entity.getGroupName());
			}
			if(entity.getUpdateDate() != null){
				String dateStr = format1.format(entity.getUpdateDate());
				appVO.setUpdateDate(dateStr);
			}
			if(entity.getUpdateUser() != null){
				appVO.setUpdateUser(entity.getUpdateUser());
			}
			if(i == 0){
				appVO.setCount(count);
			}
			apps.add(appVO);
		}
		return apps;
	}
	
	@RequestMapping(value = "/appsRefGroup", method = RequestMethod.POST)
    @ResponseBody
    public List<AppVO> getAppsByGroupId(@RequestBody PagerVO pagerVO){
		List<AppEntity> entities = null;
		
		String groupId = pagerVO.getGroupId();
		String appName = pagerVO.getAppName();
		
		int pageOffset = pagerVO.getPageOffset();
		int rowsPerPage = pagerVO.getRowsPerPage();
		Long count = null;
		if(!"-1".equals(appName)){
			entities = appBL.getAppsByAppName(appName, pageOffset, rowsPerPage);
			count = appBL.getAppsCountByAppName(appName);
		}else{
			entities = appBL.getAppsByGroupId(groupId, pageOffset, rowsPerPage);
			count = appBL.getAppsCountByGroupId(groupId);
		}

        java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		List<AppVO> apps= new ArrayList<AppVO>();
		 
        if (entities == null) return null;

		for(int i=0;i<entities.size();i++) {
			AppVO appVO = new AppVO();
            AppEntity entity = entities.get(i);
			appVO.setId(entity.getId());
		        
			if(entity.getAppId() != null){
				appVO.setAppId(entity.getAppId());
			}
			if(entity.getAppName() != null){
				appVO.setAppName(entity.getAppName());
			}
			if(entity.getGroupId() != null){
			    appVO.setGroupId(entity.getGroupId());
			}
			if(entity.getGroupName() != null){
				appVO.setGroupName(entity.getGroupName());
			}
			if(entity.getUpdateDate() != null){
				String dateStr = format1.format(entity.getUpdateDate());
				appVO.setUpdateDate(dateStr);
			}
			if(entity.getUpdateUser() != null){
				appVO.setUpdateUser(entity.getUpdateUser());
			}
			if(i == 0){
				appVO.setCount(count);
			}
			apps.add(appVO);
		}
		return apps;
	}
	
	@RequestMapping(value = "/updateApps", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateApps(@RequestBody List<AppVO> appData){
		if(appData == null){
			return false;
		}
		
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			for(int i=0;i<appData.size();i++){
				Object map = appData.get(i);
				AppEntity appEntity = new AppEntity();
				
				HashMap currentMap = new HashMap();;
				if(map instanceof HashMap){
					currentMap = (HashMap) map;
					appEntity.setId((String) currentMap.get("id"));
					appEntity.setAppId((String) currentMap.get("appId"));
	                appEntity.setAppName((String) currentMap.get("appName"));
	                appEntity.setGroupId((String) currentMap.get("groupId"));
	                appEntity.setGroupName((String) currentMap.get("groupName"));
	                appEntity.setUpdateDate(format1.parse((String) currentMap.get("updateDate")));
	                appEntity.setUpdateUser((String) currentMap.get("updateUser"));
					appBL.updateApp(appEntity);
				}
			}
		} catch (ParseException e) {
			Logging.logError("GroupController.updateApps parse date error occurred", e);
			return false;
		} catch (Exception e){
			Logging.logError("GroupController.updateApps error occurred", e);
			return false;
		}
		return true;
	}
}
