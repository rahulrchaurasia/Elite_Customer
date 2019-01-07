package com.rb.elite.core.controller.misc_non_rto;

import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.requestmodel.AnalysisCurrentHealthRequestEntity;
import com.rb.elite.core.requestmodel.BeyondLifeFinancialRequestEntity;
import com.rb.elite.core.requestmodel.ClaimGuidanceHospRequestEntity;
import com.rb.elite.core.requestmodel.ComplimentaryCreditReportRequestEntity;
import com.rb.elite.core.requestmodel.ComplimentaryLoanAuditRequestEntity;
import com.rb.elite.core.requestmodel.LifeInsurancePolicyNomineeRequestEntity;
import com.rb.elite.core.requestmodel.MiscReminderPUCRequestEntity;
import com.rb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.rb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.rb.elite.core.requestmodel.SpecialBenefitsRequestEntity;
import com.rb.elite.core.requestmodel.TransferBenefitsNCBRequestEntity;

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

    void saveBeyondLifeFinancial(BeyondLifeFinancialRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveComplimentaryCreditReport(ComplimentaryCreditReportRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveComplimentaryLoanAudit(ComplimentaryLoanAuditRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveTransferNCBBenefits(TransferBenefitsNCBRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void getProductTAT(ProductPriceRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void getMotorInsuranceList(IResponseSubcriber iResponseSubcriber);

    void getHealthInsuranceList(IResponseSubcriber iResponseSubcriber);
}
