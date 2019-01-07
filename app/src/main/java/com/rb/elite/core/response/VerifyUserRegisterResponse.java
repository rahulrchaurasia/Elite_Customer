package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.VerifyOTPEntity;

/**
 * Created by IN-RB on 14-11-2018.
 */

public class VerifyUserRegisterResponse extends APIResponse {


    /**
     * Data : {"SavedStatus":1,"OTP":"344324"}
     */

    private VerifyOTPEntity Data;

    public VerifyOTPEntity getData() {
        return Data;
    }

    public void setData(VerifyOTPEntity Data) {
        this.Data = Data;
    }


}
