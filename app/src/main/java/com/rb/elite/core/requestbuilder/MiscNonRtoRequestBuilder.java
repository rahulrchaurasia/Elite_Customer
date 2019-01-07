package com.rb.elite.core.requestbuilder;

import com.rb.elite.core.RetroRequestBuilder;
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
import com.rb.elite.core.response.MotorInsuranceListResponse;
import com.rb.elite.core.response.ProductPriceResponse;
import com.rb.elite.core.response.ProvideClaimAssResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class MiscNonRtoRequestBuilder extends RetroRequestBuilder {

    public MiscNonRtoNetworkService getService() {

        return super.build().create(MiscNonRtoNetworkService.class);
    }

    public interface MiscNonRtoNetworkService {

        @POST("/api/get-product-price")
        Call<ProductPriceResponse> getProductTAT(@Body ProductPriceRequestEntity entity);

        @POST("/api/get-motor-insurance")
        Call<MotorInsuranceListResponse> getMotorInsuranceList();

        @POST("/api/get-health-insurance")
        Call<MotorInsuranceListResponse> getHealthInsuranceList();


        @POST("/api/order-save-misc-claim-assi-service1")
        Call<ProvideClaimAssResponse> saveProvideClaimAssistance(@Body ProvideClaimAssRequestEntity requestEntity);

        @POST("/api/order-save-claim-guidance-hospi-service2")
        Call<ProvideClaimAssResponse> saveClaimGuidanceHosp(@Body ClaimGuidanceHospRequestEntity entity);

        @POST("/api/save-misc-remider-puc-service-3")
        Call<ProvideClaimAssResponse> saveMiscRemiderPUC(@Body MiscReminderPUCRequestEntity entity);

        @POST("/api/save-special-beanifiets-service-4")
        Call<ProvideClaimAssResponse> saveSpecialBenifits(@Body SpecialBenefitsRequestEntity entity);

        @POST("/api/save-transfer-ncb-benefits-service-5")
        Call<ProvideClaimAssResponse> saveTransferNCBBenefits(@Body TransferBenefitsNCBRequestEntity entity);

        @POST("/api/save-analysis-current-health-service-6")
        Call<ProvideClaimAssResponse> saveAnalysisCurrentHealth(@Body AnalysisCurrentHealthRequestEntity entity);

        @POST("/api/save-life-insurance-policy-nominee-service-7")
        Call<ProvideClaimAssResponse> saveLifeInsurancePolicyNominee(@Body LifeInsurancePolicyNomineeRequestEntity entity);

        @POST("/api/save-beyond-life-financial-service-8")
        Call<ProvideClaimAssResponse> saveBeyondLifeFinancial(@Body BeyondLifeFinancialRequestEntity entity);

        @POST("/api/save-complimentary-credit-report-service-9")
        Call<ProvideClaimAssResponse> saveComplimentaryCreditReport(@Body ComplimentaryCreditReportRequestEntity entity);

        @POST("/api/save-complimentary-loan-audit-service-10")
        Call<ProvideClaimAssResponse> saveComplimentaryLoanAudit(@Body ComplimentaryLoanAuditRequestEntity entity);


    }
}