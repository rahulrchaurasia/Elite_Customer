package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.CityMainEntity;
import com.pb.elite.core.model.RtoCityMain;

import java.util.List;

/**
 * Created by IN-RB on 04-12-2018.
 */

public class CityMainResponse extends APIResponse {
    private List<CityMainEntity> Data;

    public List<CityMainEntity> getData() {
        return Data;
    }

    public void setData(List<CityMainEntity> Data) {
        this.Data = Data;
    }




}
