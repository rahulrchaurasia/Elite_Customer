package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.UserConstatntEntity;

import java.util.List;

/**
 * Created by IN-RB on 16-11-2018.
 */

public class UserConsttantResponse extends APIResponse {


    private List<UserConstatntEntity> Data;

    public List<UserConstatntEntity> getData() {
        return Data;
    }

    public void setData(List<UserConstatntEntity> Data) {
        this.Data = Data;
    }


}
