package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.PolicyEntity;

import java.util.List;

/**
 * Created by IN-RB on 22-10-2018.
 */

public class PolicyResponse extends APIResponse {


    private List<PolicyEntity> Data;
    public List<PolicyEntity> getData() {
        return Data;
    }

    public void setData(List<PolicyEntity> data) {
        Data = data;
    }

    /**
     * Data : {"PolicyNumber":"110521823110005216","ProductCode":"2311","RiskEndDate":"21 Jan 2019","RiskStartDate":"22 Jan 2018","InsuredName":"RUDRAGOUDA S PATIL","VehicleNumber":"KA04ML6747","Make":"SKODA","Model":"RAPID","PolicyStatus":"Active","ResponseStatus":"Policy is eligible for ELITE services"}
     */








}
