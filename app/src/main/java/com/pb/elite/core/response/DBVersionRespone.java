package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.DBVersionEntity;

import java.util.List;

/**
 * Created by IN-RB on 15-06-2018.
 */

public class DBVersionRespone extends APIResponse {


    private List<DBVersionEntity> Data;

    public List<DBVersionEntity> getData() {
        return Data;
    }

    public void setData(List<DBVersionEntity> Data) {
        this.Data = Data;
    }


}
