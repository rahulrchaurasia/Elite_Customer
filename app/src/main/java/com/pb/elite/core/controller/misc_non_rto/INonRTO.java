package com.pb.elite.core.controller.misc_non_rto;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.requestmodel.AnalysisCurrentHealthRequestEntity;
import com.pb.elite.core.requestmodel.ClaimGuidanceHospRequestEntity;
import com.pb.elite.core.requestmodel.LifeInsurancePolicyNomineeRequestEntity;
import com.pb.elite.core.requestmodel.MiscReminderPUCRequestEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.pb.elite.core.requestmodel.SpecialBenefitsRequestEntity;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public interface INonRTO {

    void saveProvideClaimAssistance(ProvideClaimAssRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveClaimGuidanceHosp(ClaimGuidanceHospRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveMiscRemiderPUC(MiscReminderPUCRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveSpecialBenifits(SpecialBenefitsRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAnalysisCurrentHealth(AnalysisCurrentHealthRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveLifeInsurancePolicyNominee(LifeInsurancePolicyNomineeRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void getProductTAT(ProductPriceRequestEntity entity, IResponseSubcriber iResponseSubcriber);
}
