package com.pb.elite.core.requestbuilder;

import com.pb.elite.core.RetroRequestBuilder;
import com.pb.elite.core.requestmodel.AddUserRequestEntity;
import com.pb.elite.core.requestmodel.RegisterRequest;
import com.pb.elite.core.requestmodel.UpdateUserRequestEntity;
import com.pb.elite.core.response.AddUserResponse;
import com.pb.elite.core.response.CarMasterResponse;
import com.pb.elite.core.response.CommonResponse;
import com.pb.elite.core.response.DBVersionRespone;
import com.pb.elite.core.response.GetOtpResponse;
import com.pb.elite.core.response.LoginResponse;
import com.pb.elite.core.response.PincodeResponse;
import com.pb.elite.core.response.PolicyResponse;
import com.pb.elite.core.response.UpdateUserResponse;
import com.pb.elite.core.response.UserConsttantResponse;
import com.pb.elite.core.response.UserRegistrationResponse;
import com.pb.elite.core.response.VehicleMasterResponse;
import com.pb.elite.core.response.VerifyUserRegisterResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class RegisterRequestBuilder extends RetroRequestBuilder {

    public RegisterRequestBuilder.RegisterQuotesNetworkService getService() {

        return super.build().create(RegisterRequestBuilder.RegisterQuotesNetworkService.class);
    }

    public interface RegisterQuotesNetworkService {

        @GET("/api/get-version")
        Call<DBVersionRespone> getDBVersion();

        @POST("/api/otp-insert")
        Call<GetOtpResponse> getOtp(@Body HashMap<String, String> body);

        @POST("/api/add-user")
        Call<AddUserResponse> addUser(@Body AddUserRequestEntity body);

        @POST("/api/login")
        Call<LoginResponse> getLogin(@Body HashMap<String, String> body);

        @POST("/api/add-by-pincode")
        Call<PincodeResponse> getCity(@Body HashMap<String, String> body);

        @POST("/api/update-user")
        Call<UpdateUserResponse> updateUser(@Body UpdateUserRequestEntity updateUserRequestEntity);


        @POST("/api/change-password")
        Call<CommonResponse> changePassword(@Body HashMap<String, String> body);

        @POST("/api/forgot-password")
        Call<CommonResponse> forgotPassword(@Body HashMap<String, String> body);

        @POST("/api/reliance-login")

        Call<PolicyResponse> getPolicyData(@Body HashMap<String, String> body);

        @POST("/api/check-user-registration")
        Call<VerifyUserRegisterResponse> verifyUserRegistration(@Body HashMap<String, String> body);

        @POST("/api/user-otp-verify")
        Call<UserRegistrationResponse> userRegistration(@Body RegisterRequest registerRequest);

        @POST("/api/vehicle-details")
        Call<CarMasterResponse> getCarMaster(@Body HashMap<String, String> body);

        @POST("/api/get-user-constant")
        Call<UserConsttantResponse> getUserConstant(@Body HashMap<String, String> body);   //used


        //vehicle request
        @GET
        Call<VehicleMasterResponse> getCarMaster(@Url String url);

    }
}