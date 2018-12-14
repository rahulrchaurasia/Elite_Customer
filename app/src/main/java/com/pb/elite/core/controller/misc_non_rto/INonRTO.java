package com.pb.elite.core.controller.misc_non_rto;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public interface INonRTO {

    void saveProvideClaimAssistance(ProvideClaimAssRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void getProductTAT(ProductPriceRequestEntity entity, IResponseSubcriber iResponseSubcriber);
}
