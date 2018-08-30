package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.NonRtoProductDisplayMainEntity;

import java.util.List;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class NonRtoProductDisplayResponse extends APIResponse {


    private List<NonRtoProductDisplayMainEntity> Data;

    public List<NonRtoProductDisplayMainEntity> getData() {
        return Data;
    }

    public void setData(List<NonRtoProductDisplayMainEntity> Data) {
        this.Data = Data;
    }


}
