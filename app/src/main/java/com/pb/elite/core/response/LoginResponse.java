package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.LoginEntity;
import com.pb.elite.core.model.OrderStatusEntity;
import com.pb.elite.core.model.UserEntity;

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
