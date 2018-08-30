package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.RTOList;

import java.util.List;

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
