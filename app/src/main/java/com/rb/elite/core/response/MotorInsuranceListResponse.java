package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.InsuranceCompanyEntity;

import java.util.List;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class MotorInsuranceListResponse extends APIResponse {


    private List<InsuranceCompanyEntity> Data;

    public List<InsuranceCompanyEntity> getData() {
        return Data;
    }

    public void setData(List<InsuranceCompanyEntity> Data) {
        this.Data = Data;
    }


}
