package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.DocumentViewEntity;

import java.util.List;

/**
 * Created by IN-RB on 14-08-2018.
 */

public class DocumentViewResponse extends APIResponse {


    private List<DocumentViewEntity> Data;

    public List<DocumentViewEntity> getData() {
        return Data;
    }

    public void setData(List<DocumentViewEntity> Data) {
        this.Data = Data;
    }


}
