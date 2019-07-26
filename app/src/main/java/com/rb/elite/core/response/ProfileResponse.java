package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.ProfileEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 25/07/2019.
 */
public class ProfileResponse extends APIResponse {


    private List<ProfileEntity> Data;

    public List<ProfileEntity> getData() {
        return Data;
    }

    public void setData(List<ProfileEntity> Data) {
        this.Data = Data;
    }


}
