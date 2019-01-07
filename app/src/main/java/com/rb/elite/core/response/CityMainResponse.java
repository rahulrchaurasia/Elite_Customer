package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.CityMainEntity;

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
