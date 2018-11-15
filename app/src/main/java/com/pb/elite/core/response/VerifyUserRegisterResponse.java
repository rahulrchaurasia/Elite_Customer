package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.VerifyOTPEntity;

import java.util.List;

/**
 * Created by IN-RB on 14-11-2018.
 */

public class VerifyUserRegisterResponse extends APIResponse {


    private List<VerifyOTPEntity> Data;

    public List<VerifyOTPEntity> getData() {
        return Data;
    }

    public void setData(List<VerifyOTPEntity> Data) {
        this.Data = Data;
    }


}
