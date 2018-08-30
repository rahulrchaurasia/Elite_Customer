package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.AddUserEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class AddUserResponse extends APIResponse {

    private List<AddUserEntity> Data;

    public List<AddUserEntity> getData() {
        return Data;
    }

    public void setData(List<AddUserEntity> Data) {
        this.Data = Data;
    }


}
