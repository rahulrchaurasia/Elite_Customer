package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 05/02/2018.
 */

public class ClientCommonResponse extends APIResponse {
    private List<Object> Data;

    public List<Object> getData() {
        return Data;
    }

    public void setData(List<Object> Data) {
        this.Data = Data;
    }
}
