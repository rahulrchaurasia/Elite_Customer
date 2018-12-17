package com.pb.elite.core.requestbuilder;

import com.pb.elite.core.RetroRequestBuilder;


import com.pb.elite.core.requestmodel.AssistanceObtainingRequestEntity;
import com.pb.elite.core.requestmodel.RCRequestEntity;
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


    }
}