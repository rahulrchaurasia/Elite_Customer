package com.pb.elite.core.requestbuilder;

import com.pb.elite.core.RetroRequestBuilder;


import com.pb.elite.core.requestmodel.AdditionHypothecationRequestEntity;
import com.pb.elite.core.requestmodel.AddressEndorsementRCRequestEntity;
import com.pb.elite.core.requestmodel.AssistanceObtainingRequestEntity;
import com.pb.elite.core.requestmodel.DriverDLVerificationRequestEntity;
import com.pb.elite.core.requestmodel.PaperToSmartCardRequestEntity;
import com.pb.elite.core.requestmodel.RCRequestEntity;
import com.pb.elite.core.requestmodel.TransferOwnershipRequestEntity;
import com.pb.elite.core.requestmodel.VehicleRegCertificateRequestEntity;
import com.pb.elite.core.response.ProvideClaimAssResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RtoRequestBuilder extends RetroRequestBuilder {

    public RtoNetworkService getService() {

        return super.build().create(RtoNetworkService.class);
    }

    public interface RtoNetworkService {


        @POST("/api/order-save-rcservice1")
        Call<ProvideClaimAssResponse> saveRCservice1(@Body RCRequestEntity requestEntity);

        @POST("/api/order-save-dl-service2")
        Call<ProvideClaimAssResponse> saveAssistObtaining(@Body AssistanceObtainingRequestEntity requestEntity);

        @POST("/api/save-addition-hypothecation-service-3")
        Call<ProvideClaimAssResponse> saveAdditionHypothecation(@Body AdditionHypothecationRequestEntity entity);

        @POST("/api/save-transfer-ownership-service-4")
        Call<ProvideClaimAssResponse> saveTransferOwnership(@Body TransferOwnershipRequestEntity entity);

        @POST("/api/save-drivers-dl-verification-service-5")
        Call<ProvideClaimAssResponse> saveDriverDLVerification(@Body DriverDLVerificationRequestEntity entity);

        @POST("/api/save-address-endorsement-on-rc-service-6")
        Call<ProvideClaimAssResponse> saveAddressEndorsementRC(@Body AddressEndorsementRCRequestEntity entity);

        @POST("/api/save-paper-to-smart-card-licence-service-7")
        Call<ProvideClaimAssResponse> savePaperSmartCard(@Body PaperToSmartCardRequestEntity entity);

        @POST("/api/save-vehicle-registration-certificate-service-8")
        Call<ProvideClaimAssResponse> saveVehicleRegCertificate(@Body VehicleRegCertificateRequestEntity entity);

    }
}