package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class GetOtpResponse extends APIResponse {
    /**
     * Data : 123456
     */

    private int Data;

    public int getData() {
        return Data;
    }

    public void setData(int Data) {
        this.Data = Data;
    }
}
