package com.chinaunicom.filterman.ws;

import com.chinaunicom.filterman.comm.vo.HardInfoVO;
import com.chinaunicom.filterman.comm.vo.ResultVO;
import com.chinaunicom.filterman.comm.vo.UserVO;
import com.chinaunicom.filterman.comm.vo.UuidVO;
import com.chinaunicom.filterman.ws.des.CryptUtil;
import com.google.gson.Gson;
import com.sun.jersey.api.client.GenericType;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;

import javax.ws.rs.core.MediaType;
import java.util.List;


public class UuidApiCallSample extends BaseTest{
	
    @Test
	public void testUuidCheckApi() throws Exception {
        wr = client.resource(rootPath);
        HardInfoVO hardInfoVO = new HardInfoVO();
        hardInfoVO.setMac("mac002");
        hardInfoVO.setImei("imei002");
        hardInfoVO.setImsi("imsi001");
        Gson gson = new Gson();

        String enStr = CryptUtil.encryptBy3DesAndBase64(gson.toJson(hardInfoVO), "FILTERMAN");
        UuidVO uuidVO = new UuidVO();
        uuidVO.setMessage(enStr);

        clientResponse = wr.path("/uuid/check")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, uuidVO);

        ResultVO resultVO = clientResponse.getEntity(new GenericType<ResultVO>(){});
        String result = CryptUtil.decryptBy3DesAndBase64(gson.toJson(resultVO.getResult()), "FILTERMAN");

	}


}
