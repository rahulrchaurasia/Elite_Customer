package com.pb.elite.core.controller.misc_non_rto;

import android.content.Context;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.AsyncCityMaster;
import com.pb.elite.core.controller.product.IProduct;
import com.pb.elite.core.controller.product.SyncRTOMaster;
import com.pb.elite.core.requestbuilder.MiscNonRtoRequestBuilder;
import com.pb.elite.core.requestbuilder.ProductRequestBuilder;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
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
import com.pb.elite.database.DataBaseController;
import com.pb.elite.splash.PrefManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class MiscNonRTOController implements INonRTO {

    MiscNonRtoRequestBuilder.MiscNonRtoNetworkService miscNonRtoNetworkService;
    Context mContext;
    PrefManager prefManager;
    IResponseSubcriber iResponseSubcriber;

    public MiscNonRTOController(Context context) {
        miscNonRtoNetworkService = new MiscNonRtoRequestBuilder().getService();
        mContext = context;
        prefManager = new PrefManager(mContext);
    }

    @Override
    public void getProductTAT(ProductPriceRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.getProductTAT(entity).enqueue(new Callback<ProductPriceResponse>() {
            @Override
            public void onResponse(Call<ProductPriceResponse> call, Response<ProductPriceResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProductPriceResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }

    @Override
    public void saveProvideClaimAssistance(ProvideClaimAssRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        miscNonRtoNetworkService.saveProvideClaimAssistance(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }
}
