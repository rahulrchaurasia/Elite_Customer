package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.PolicyEntity;

import java.util.List;

/**
 * Created by IN-RB on 22-10-2018.
 */

public class PolicyResponse extends APIResponse {


    private List<PolicyEntity> Data;

    public List<PolicyEntity> getData() {
        return Data;
    }

    public void setData(List<PolicyEntity> Data) {
        this.Data = Data;
    }


}
