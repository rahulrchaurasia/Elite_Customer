package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.CompleteOrderEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 07/01/2019.
 */

public class CompleteOrderResponse extends APIResponse {


    private List<CompleteOrderEntity> Data;

    public List<CompleteOrderEntity> getData() {
        return Data;
    }

    public void setData(List<CompleteOrderEntity> Data) {
        this.Data = Data;
    }


}
