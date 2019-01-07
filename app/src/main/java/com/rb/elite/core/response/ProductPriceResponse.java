package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.ProductPriceEntity;

import java.util.List;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class ProductPriceResponse extends APIResponse {

    private List<ProductPriceEntity> Data;

    public List<ProductPriceEntity> getData() {
        return Data;
    }

    public void setData(List<ProductPriceEntity> Data) {
        this.Data = Data;
    }


}
