package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.PolicyEntity;

/**
 * Created by IN-RB on 22-10-2018.
 */

public class PolicyResponse extends APIResponse {
    /**
     * Data : {"PolicyNumber":"110521823110005216","ProductCode":"2311","RiskEndDate":"21 Jan 2019","RiskStartDate":"22 Jan 2018","InsuredName":"RUDRAGOUDA S PATIL","VehicleNumber":"KA04ML6747","Make":"SKODA","Model":"RAPID","PolicyStatus":"Active","ResponseStatus":"Policy is eligible for ELITE services"}
     */

    private PolicyEntity Data;

    public PolicyEntity getData() {
        return Data;
    }

    public void setData(PolicyEntity Data) {
        this.Data = Data;
    }



}
