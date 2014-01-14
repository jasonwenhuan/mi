package com.chinaunicom.filterman.core.bl;

import com.chinaunicom.filterman.comm.vo.FilterVO;
import com.chinaunicom.filterman.comm.vo.KeyValueVO;
import com.chinaunicom.filterman.comm.vo.SearchVO;

import java.util.List;

/**
 * User: larry
 */

public interface ISearchBL {
    public List<SearchVO> getData(FilterVO myFilter);
    public boolean updateData(String data);
    public List<KeyValueVO> getAppKeyValue();
}
