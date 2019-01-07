package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.LoginEntity;

import java.util.List;

/**
 * Created by IN-RB on 03-02-2018.
 */

public class LoginResponse  extends APIResponse {

    private List<LoginEntity> Data;

    public List<LoginEntity> getData() {
        return Data;
    }

    public void setData(List<LoginEntity> Data) {
        this.Data = Data;
    }






}
