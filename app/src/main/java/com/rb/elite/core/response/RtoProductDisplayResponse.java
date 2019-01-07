package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.RtoProductDisplayMainEntity;

import java.util.List;

/**
 * Created by IN-RB on 08-08-2018.
 */

public class RtoProductDisplayResponse extends APIResponse {
    private List<RtoProductDisplayMainEntity> Data;

    public List<RtoProductDisplayMainEntity> getData() {
        return Data;
    }

    public void setData(List<RtoProductDisplayMainEntity> Data) {
        this.Data = Data;
    }








}
