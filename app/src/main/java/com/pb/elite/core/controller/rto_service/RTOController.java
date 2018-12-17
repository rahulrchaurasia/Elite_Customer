package com.pb.elite.core.controller.rto_service;

import android.content.Context;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.misc_non_rto.INonRTO;
import com.pb.elite.core.requestbuilder.MiscNonRtoRequestBuilder;
import com.pb.elite.core.requestbuilder.RtoRequestBuilder;
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
import com.pb.elite.core.response.MotorInsuranceListResponse;
import com.pb.elite.core.response.ProductPriceResponse;
import com.pb.elite.core.response.ProvideClaimAssResponse;
import com.pb.elite.splash.PrefManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class RTOController implements IRTO {


    RtoRequestBuilder.RtoNetworkService rtoNetworkService;
    Context mContext;
    PrefManager prefManager;
    IResponseSubcriber iResponseSubcriber;

    public RTOController(Context context) {
        rtoNetworkService = new RtoRequestBuilder().getService();
        mContext = context;
        prefManager = new PrefManager(mContext);
    }




    @Override
    public void saveRCService1(RCRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveRCservice1(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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

    @Override
    public void saveAssistanceObtaining(AssistanceObtainingRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveAssistObtaining(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
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
