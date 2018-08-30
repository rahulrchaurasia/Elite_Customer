package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.AllCityData;

import java.util.List;

/**
 * Created by IN-RB on 05-07-2018.
 */

public class CityResponse extends APIResponse {




    private AllCityData Data;

    public AllCityData getData() {
        return Data;
    }

    public void setData(AllCityData Data) {
        this.Data = Data;
    }


}
