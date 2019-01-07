package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.RTOList;

/**
 * Created by IN-RB on 07-02-2018.
 */

public class RtoLocationReponse extends APIResponse {


    private RTOList Data;

    public RTOList getData() {
        return Data;
    }

    public void setData(RTOList Data) {
        this.Data = Data;
    }





}
