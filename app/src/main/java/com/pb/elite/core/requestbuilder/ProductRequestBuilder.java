package com.pb.elite.core.requestbuilder;

import com.pb.elite.core.RetroRequestBuilder;
import com.pb.elite.core.requestmodel.UpdateOrderRequestEntity;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.core.response.CityResponse;
import com.pb.elite.core.response.DocumentResponse;
import com.pb.elite.core.response.DocumentViewResponse;
import com.pb.elite.core.response.NonRtoProductDisplayResponse;
import com.pb.elite.core.response.OrderDetailResponse;
import com.pb.elite.core.response.OrderResponse;
import com.pb.elite.core.response.ProductDocumentResponse;
import com.pb.elite.core.response.ProductResponse;
import com.pb.elite.core.response.RtoLocationReponse;
import com.pb.elite.core.response.ClientCommonResponse;
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

public class ProductRequestBuilder extends RetroRequestBuilder {

    public ProductRequestBuilder.ProductNetworkService getService() {

        return super.build().create(ProductRequestBuilder.ProductNetworkService.class);
    }

    public interface ProductNetworkService {

        @GET("/api/loadproduct")
        Call<ProductResponse> getProductMaster();    // Not in Used

        @GET("/api/load-all-cities")
        Call<CityResponse> getCityMaster();

        @POST("/api/insertorder")
        Call<OrderResponse> insertOrder(@Body InsertOrderRequestEntity requestEntity);

        @POST("/api/get-orders")
        Call<OrderDetailResponse> getOrder(@Body HashMap<String, String> body);


        @POST("/api/update-order")
        Call<ClientCommonResponse> updateOrder(@Body UpdateOrderRequestEntity body);

        @POST("/api/rto-byproducts")
        Call<RtoLocationReponse> getRTOLocation(@Body HashMap<String, String> body);   // Not in Used

        @POST("/api/get-rto-data")
        Call<ServiceListResponse> getRtoAndNonRto();     //used

        @POST("/api/get-rto-list")
        Call<RtoProductDisplayResponse> getRTOProductList(@Body HashMap<String, String> body);   //used

        @POST("/api/get-non-rto-list")
        Call<NonRtoProductDisplayResponse> getNonRTOProductList(@Body HashMap<String, String> body);   //used

        @POST("/api/get-product-wise-document")
        Call<ProductDocumentResponse> getProdDocument(@Body HashMap<String, String> body);   //used

        @POST("/api/get-order-document ")
        Call<DocumentViewResponse> getDocumentView(@Body HashMap<String, String> body);   //used

        @Multipart
        @POST("/api/doc-upload")
        Call<DocumentResponse> uploadDocument(@Part() MultipartBody.Part doc, @PartMap() Map<String, Integer> partMap);  //used


    }
}