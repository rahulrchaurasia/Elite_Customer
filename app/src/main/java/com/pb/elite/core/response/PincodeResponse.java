package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.PincodeEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 03/02/2018.
 */

public class PincodeResponse extends APIResponse {

    private List<PincodeEntity> Data;

    public List<PincodeEntity> getData() {
        return Data;
    }

    public void setData(List<PincodeEntity> Data) {
        this.Data = Data;
    }
}
