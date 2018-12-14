package com.pb.elite.core.requestbuilder;

import com.pb.elite.core.RetroRequestBuilder;
import com.pb.elite.core.requestmodel.ClaimGuidanceHospRequestEntity;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.core.requestmodel.MiscReminderPUCRequestEntity;
import com.pb.elite.core.requestmodel.ProductPriceRequestEntity;
import com.pb.elite.core.requestmodel.ProvideClaimAssRequestEntity;
import com.pb.elite.core.requestmodel.UpdateOrderRequestEntity;
import com.pb.elite.core.response.CityResponse;
import com.pb.elite.core.response.ClientCommonResponse;
import com.pb.elite.core.response.DocumentResponse;
import com.pb.elite.core.response.DocumentViewResponse;
import com.pb.elite.core.response.NonRtoProductDisplayResponse;
import com.pb.elite.core.response.NotificationResponse;
import com.pb.elite.core.response.OrderDetailResponse;
import com.pb.elite.core.response.OrderResponse;
import com.pb.elite.core.response.ProductDocumentResponse;
import com.pb.elite.core.response.ProductPriceResponse;
import com.pb.elite.core.response.ProductResponse;
import com.pb.elite.core.response.ProvideClaimAssResponse;
import com.pb.elite.core.response.RtoLocationReponse;
import com.pb.elite.core.response.RtoProductDisplayResponse;
import com.pb.elite.core.response.ServiceListResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public class MiscNonRtoRequestBuilder extends RetroRequestBuilder {

    public MiscNonRtoNetworkService getService() {

        return super.build().create(MiscNonRtoNetworkService.class);
    }

    public interface MiscNonRtoNetworkService {

        @POST("/api/order-save-misc-claim-assi-service1")
        Call<ProvideClaimAssResponse> saveProvideClaimAssistance(@Body ProvideClaimAssRequestEntity requestEntity);

        @POST("/api/get-product-price")
        Call<ProductPriceResponse> getProductTAT(@Body ProductPriceRequestEntity entity);

        @POST("/api/order-save-claim-guidance-hospi-service2")
        Call<ProvideClaimAssResponse> saveClaimGuidanceHosp(@Body ClaimGuidanceHospRequestEntity entity);

        @POST("api/save-misc-remider-puc-service-3")
        Call<ProvideClaimAssResponse> saveMiscRemiderPUC(@Body MiscReminderPUCRequestEntity entity);
    }
}