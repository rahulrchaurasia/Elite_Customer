package com.pb.elite.core.controller.rto_service;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.requestmodel.AnalysisCurrentHealthRequestEntity;
import com.pb.elite.core.requestmodel.AssistanceObtainingRequestEntity;
import com.pb.elite.core.requestmodel.BeyondLifeFinancialRequestEntity;
import com.pb.elite.core.requestmodel.ClaimGuidanceHospRequestEntity;
import com.pb.elite.core.requestmodel.ComplimentaryCreditReportRequestEntity;
import com.pb.elite.core.requestmodel.ComplimentaryLoanAuditRequestEntity;
import com.pb.elite.core.requestmodel.LifeInsurancePolicyNomineeRequestEntity;
import com.pb.elite.core.requestmodel.MiscReminderPUCRequestEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.pb.elite.core.requestmodel.RCRequestEntity;
import com.pb.elite.core.requestmodel.SpecialBenefitsRequestEntity;
import com.pb.elite.core.requestmodel.TransferBenefitsNCBRequestEntity;

/**
 * Created by Rahul  on 14-12-2018.
 */

public interface IRTO {

    void saveRCService1(RCRequestEntity  entity, IResponseSubcriber iResponseSubcriber);

    void saveAssistanceObtaining(AssistanceObtainingRequestEntity entity, IResponseSubcriber iResponseSubcriber);


}
