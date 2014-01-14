package com.chinaunicom.filterman.ws.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chinaunicom.filterman.comm.vo.PagerVO;
import com.chinaunicom.filterman.comm.vo.WBListVO;
import com.chinaunicom.filterman.core.bl.IBlackListBL;
import com.chinaunicom.filterman.core.bl.IWhiteListBL;
import com.chinaunicom.filterman.core.db.entity.WBListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.filterman.core.db.entity.WBListEntity;
import com.chinaunicom.filterman.utilities.Logging;

@Controller
@RequestMapping(value = "/")
public class WBListController {
	@Autowired
    private IBlackListBL blackListBL;
	
	@Autowired
    private IWhiteListBL whiteListBL;
	
	@RequestMapping(value = "/blacklist/info", method = RequestMethod.POST)
    @ResponseBody
    public List<WBListVO> getBlacklistById(@RequestBody PagerVO pagerVO) {
		String keyId = pagerVO.getWblistId();
		int pageOffset = pagerVO.getPageOffset();
		int pageSize = pagerVO.getRowsPerPage();
		
        List<WBListVO> vos = new ArrayList<WBListVO>();
        
        Long count = null;
        List<WBListEntity> entities = blackListBL.getBlackListByKeyId(keyId, pageOffset, pageSize);
        if(entities.size() > 0){
        	count = blackListBL.getBlackListCountByKeyId(keyId);
        }
        for(int i=0;i<entities.size();i++){
        	WBListEntity entity = entities.get(i);
        	WBListVO vo = new WBListVO();
        	
        	if (entity != null) {
                vo.setKey(entity.getId());
                if(entity.getAppId() != null){
                    vo.setAppId(entity.getAppId());
                }
                if(entity.getComment() != null){
                    vo.setComment(entity.getComment());
                }
                if(entity.getKeyId() != null){
                    vo.setKeyId(entity.getKeyId());
                }
                if(entity.getType() != null){
                    vo.setType(entity.getType());
                }
                if(entity.getUpdateDt() != null){
                    vo.setUpdateDt(entity.getUpdateDt());
                }
                if(entity.getUpdateUser() != null){
                    vo.setUpdateUser(entity.getUpdateUser());
                }
                if(i == 0){
                	vo.setCount(count);
                }
                vos.add(vo);
            }
        }
		return vos;
	}

    @RequestMapping(value = "/whitelist/info", method = RequestMethod.POST)
    @ResponseBody
    public List<WBListVO> getWhiteListById(@RequestBody PagerVO pagerVO) {
    	String keyId = pagerVO.getWblistId();
		int pageOffset = pagerVO.getPageOffset();
		int pageSize = pagerVO.getRowsPerPage();
		
        List<WBListVO> vos = new ArrayList<WBListVO>();
        Long count = null;
        
        List<WBListEntity> entities = whiteListBL.getWhiteListByKeyId(keyId, pageOffset, pageSize);
        if(entities.size() > 0){
        	count = whiteListBL.getWhiteListCountByKeyId(keyId);
        }
        
        for(int i=0;i<entities.size();i++){
        	WBListEntity entity = entities.get(i);
        	WBListVO vo = new WBListVO();
        	if (entity != null) {
                vo.setKey(entity.getId());
                if(entity.getAppId() != null){
                    vo.setAppId(entity.getAppId());
                }
                if(entity.getComment() != null){
                    vo.setComment(entity.getComment());
                }
                if(entity.getKeyId() != null){
                    vo.setKeyId(entity.getKeyId());
                }
                if(entity.getType() != null){
                    vo.setType(entity.getType());
                }
                if(entity.getUpdateDt() != null){
                    vo.setUpdateDt(entity.getUpdateDt());
                }
                if(entity.getUpdateUser() != null){
                    vo.setUpdateUser(entity.getUpdateUser());
                }
                if(i == 0){
                	vo.setCount(count);
                }
                vos.add(vo);
            }
        }
        return vos;
    }

	@RequestMapping(value = "/blacklist/save", method = RequestMethod.POST)
    @ResponseBody
    public Boolean saveBlackList(@RequestBody WBListVO blacklist) {
		try{
			String keyId = blacklist.getKeyId();
			WBListEntity be = blackListBL.getBlackList(keyId);
			if(be != null){
				return false;
			}
			WBListEntity blacklistEntity = new WBListEntity();
			if(blacklist.getAppId() != null){
				blacklistEntity.setAppId(blacklist.getAppId());
			}
			if(blacklist.getComment() != null){
				blacklistEntity.setComment(blacklist.getComment());
			}
			if(blacklist.getKeyId() != null){
				blacklistEntity.setKeyId(blacklist.getKeyId());
			}
			if(blacklist.getType() != null){
				blacklistEntity.setType(blacklist.getType());
			}
			if(blacklist.getUpdateUser() != null){
				blacklistEntity.setUpdateUser(blacklist.getUpdateUser());
			}
			blacklistEntity.setUpdateDt(new Date());
			
			blackListBL.saveBlackList(blacklistEntity);
		}catch(Exception e){
			Logging.logError("Doing WBListController.saveBlackList error", e);
		}
		return true;
	}
	
	@RequestMapping(value = "/whitelist/save", method = RequestMethod.POST)
    @ResponseBody
    public Boolean saveWhiteList(@RequestBody WBListVO whitelist) {
		try{
			String keyId = whitelist.getKeyId();
			WBListEntity be = whiteListBL.getWhiteList(keyId);
			if(be != null){
				return false;
			}
			
			WBListEntity whitelistEntity = new WBListEntity();
			if(whitelist.getAppId() != null){
				whitelistEntity.setAppId(whitelist.getAppId());
			}
			if(whitelist.getComment() != null){
				whitelistEntity.setComment(whitelist.getComment());
			}
			if(whitelist.getKeyId() != null){
				whitelistEntity.setKeyId(whitelist.getKeyId());
			}
			if(whitelist.getType() != null){
				whitelistEntity.setType(whitelist.getType());
			}
			if(whitelist.getUpdateUser() != null){
				whitelistEntity.setUpdateUser(whitelist.getUpdateUser());
			}
			whitelistEntity.setUpdateDt(new Date());
			
			whiteListBL.saveWhiteList(whitelistEntity);
		}catch(Exception e){
			Logging.logError("Doing WBListController.saveWhiteList error", e);
		}
		return true;
	}
	
	@RequestMapping(value = "/blacklist/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeBlacklist(@RequestBody List<String> blacklistIds) {
		try{
			for(int i=0;i<blacklistIds.size();i++){
				blackListBL.removeBlackList(blacklistIds.get(i));
			}
			
		}catch(Exception e){
			Logging.logError("Doing WBListController.removeBlacklist error", e);
		}
		return null;
	}
	
	@RequestMapping(value = "/whitelist/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeWhitelist(@RequestBody List<String> whitelistIds) {
		try{
			for(int i=0;i<whitelistIds.size();i++){
				whiteListBL.removeWhiteList(whitelistIds.get(i));
			}
		}catch(Exception e){
			Logging.logError("Doing WBListController.removeWhitelist error", e);
		}
		return null;
	}
	
	@RequestMapping(value = "/blacklist/check", method = RequestMethod.POST)
    @ResponseBody
    public Boolean validateIsInBlacklist(@RequestBody String keyId) {
		Boolean isInBlacklist = false;
		try{
			WBListEntity be = blackListBL.getBlackList(keyId);
			if(be != null){
				isInBlacklist = true;
			}
		}catch(Exception e){
			Logging.logError("Doing WBListController.validateIsInBlacklist error", e);
		}
		return isInBlacklist;
	}
}
