package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.RateDisplayEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 08/01/2019.
 */

public class DisplayRateResponse extends APIResponse {


    private List<RateDisplayEntity> Data;

    public List<RateDisplayEntity> getData() {
        return Data;
    }

    public void setData(List<RateDisplayEntity> Data) {
        this.Data = Data;
    }


}
