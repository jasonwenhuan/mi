package com.chinaunicom.filterman.ws.api;

import com.chinaunicom.filterman.comm.vo.HardInfoVO;
import com.chinaunicom.filterman.comm.vo.ResultVO;
import com.chinaunicom.filterman.comm.vo.UuidVO;
import com.chinaunicom.filterman.core.bl.IHardInfoBL;
import com.chinaunicom.filterman.ws.des.CryptUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: larry
 */

@Controller
@RequestMapping(value = "/uuid")
public class UuidController {
    @Autowired
    private IHardInfoBL hardInfoBL;

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Object getUser(@RequestBody UuidVO uuidVO){
        ResultVO resultVO = new ResultVO();
        String hardInfo = CryptUtil.decryptBy3DesAndBase64(uuidVO.getMessage(), "FILTERMAN");
        if (hardInfo != null) {
            Gson gson = new Gson();
            HardInfoVO hardInfoVO = gson.fromJson(hardInfo, HardInfoVO.class);

            String mac = hardInfoVO.getMac().replaceAll(" ", "").replaceAll(":", "").toUpperCase();
            hardInfoVO.setMac(mac);

            String uuid = hardInfoBL.checkUuid(hardInfoVO);
            resultVO.setResult(CryptUtil.encryptBy3DesAndBase64(uuid, "FILTERMAN"));
        }

        return resultVO;
    }
}
