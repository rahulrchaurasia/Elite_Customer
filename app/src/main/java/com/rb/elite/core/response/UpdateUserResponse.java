package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;

/**
 * Created by Rajeev Ranjan on 03/02/2018.
 */

public class UpdateUserResponse extends APIResponse {
    /**
     * Data : Record updated successfully
     */

    private String Data;

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
