package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.ProductEntity;

import java.util.List;


/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class ProductResponse extends APIResponse {


    private List<ProductEntity> Data;

    public List<ProductEntity> getData() {
        return Data;
    }

    public void setData(List<ProductEntity> Data) {
        this.Data = Data;
    }


}
