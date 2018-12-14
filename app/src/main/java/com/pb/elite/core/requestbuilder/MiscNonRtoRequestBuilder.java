package com.pb.elite.core.requestbuilder;

import com.pb.elite.core.RetroRequestBuilder;
import com.pb.elite.core.requestmodel.AnalysisCurrentHealthRequestEntity;
import com.pb.elite.core.requestmodel.ClaimGuidanceHospRequestEntity;
import com.pb.elite.core.requestmodel.LifeInsurancePolicyNomineeRequestEntity;
import com.pb.elite.core.requestmodel.MiscReminderPUCRequestEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.pb.elite.core.requestmodel.SpecialBenefitsRequestEntity;
import com.pb.elite.core.response.MotorInsuranceListResponse;
import com.pb.elite.core.response.ProductPriceResponse;
import com.pb.elite.core.response.ProvideClaimAssResponse;

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


        @POST("/api/order-save-misc-claim-assi-service1")
        Call<ProvideClaimAssResponse> saveProvideClaimAssistance(@Body ProvideClaimAssRequestEntity requestEntity);

        @POST("/api/order-save-claim-guidance-hospi-service2")
        Call<ProvideClaimAssResponse> saveClaimGuidanceHosp(@Body ClaimGuidanceHospRequestEntity entity);

        @POST("/api/save-misc-remider-puc-service-3")
        Call<ProvideClaimAssResponse> saveMiscRemiderPUC(@Body MiscReminderPUCRequestEntity entity);

        @POST("/api/save-special-beanifiets-service-4")
        Call<ProvideClaimAssResponse> saveSpecialBenifits(@Body SpecialBenefitsRequestEntity entity);

        @POST("/api/save-analysis-current-health-service-6")
        Call<ProvideClaimAssResponse> saveAnalysisCurrentHealth(@Body AnalysisCurrentHealthRequestEntity entity);

        @POST("/api/save-life-insurance-policy-nominee-service-7")
        Call<ProvideClaimAssResponse> saveLifeInsurancePolicyNominee(@Body LifeInsurancePolicyNomineeRequestEntity entity);

    }
}