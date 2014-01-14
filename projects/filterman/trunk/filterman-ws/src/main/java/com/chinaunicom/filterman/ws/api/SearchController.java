package com.chinaunicom.filterman.ws.api;

import com.chinaunicom.filterman.comm.vo.FilterVO;
import com.chinaunicom.filterman.comm.vo.KeyValueVO;
import com.chinaunicom.filterman.comm.vo.SearchVO;
import com.chinaunicom.filterman.core.bl.ISearchBL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: larry
 */

@Controller
@RequestMapping(value = "/search")
public class SearchController {
    @Autowired
    private ISearchBL searchBL;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public List<SearchVO> getAllTableData(@RequestBody FilterVO filter){
        return searchBL.getData(filter);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Boolean updateTableData(@RequestBody String data){
        return searchBL.updateData(data);
    }

    @RequestMapping(value = "/appKeyValue", method = RequestMethod.GET)
    @ResponseBody
    public List<KeyValueVO> getAppKeyValue(){
        return searchBL.getAppKeyValue();
    }
}
