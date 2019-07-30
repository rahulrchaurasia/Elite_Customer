package com.rb.elite.core.controller.register;

import android.content.Context;

import com.rb.elite.core.IResponseSubcriber;

import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.requestbuilder.RegisterRequestBuilder;
import com.rb.elite.core.requestmodel.AddUserRequestEntity;
import com.rb.elite.core.requestmodel.RateRequestEntity;
import com.rb.elite.core.requestmodel.RegisterRequest;
import com.rb.elite.core.requestmodel.UpdateUserRequestEntity;
import com.rb.elite.core.response.AddUserResponse;
import com.rb.elite.core.response.CarMasterResponse;
import com.rb.elite.core.response.CityMainResponse;
import com.rb.elite.core.response.CommonResponse;
import com.rb.elite.core.response.DBVersionRespone;
import com.rb.elite.core.response.DisplayFeedbackResponse;
import com.rb.elite.core.response.DisplayRateResponse;
import com.rb.elite.core.response.FastLaneDataResponse;
import com.rb.elite.core.response.FeedbackResponse;
import com.rb.elite.core.response.GetOtpResponse;
import com.rb.elite.core.response.LoginResponse;
import com.rb.elite.core.response.PincodeResponse;
import com.rb.elite.core.response.PolicyResponse;
import com.rb.elite.core.response.ProfileResponse;
import com.rb.elite.core.response.RateResponse;
import com.rb.elite.core.response.UpdateUserResponse;
import com.rb.elite.core.response.UserConsttantResponse;
import com.rb.elite.core.response.UserRegistrationResponse;
import com.rb.elite.core.response.VehicleMasterResponse;
import com.rb.elite.core.response.VerifyUserRegisterResponse;
import com.rb.elite.splash.PrefManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class RegisterController implements IRegister {

    RegisterRequestBuilder.RegisterQuotesNetworkService registerQuotesNetworkService;
    Context mContext;
    UserEntity loginEntity;
    PrefManager prefManager;


    public RegisterController(Context context) {
        registerQuotesNetworkService = new RegisterRequestBuilder().getService();
        mContext = context;
        prefManager = new PrefManager(mContext);
        loginEntity = prefManager.getUserData();
    }

    @Override
    public void getDbVersion(final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.getDBVersion().enqueue(new Callback<DBVersionRespone>() {
            @Override
            public void onResponse(Call<DBVersionRespone> call, Response<DBVersionRespone> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<DBVersionRespone> call, Throwable t) {
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
    public void addUser(AddUserRequestEntity addUserRequestEntity, final IResponseSubcriber iResponseSubcriber) {
        registerQuotesNetworkService.addUser(addUserRequestEntity).enqueue(new Callback<AddUserResponse>() {
            @Override
            public void onResponse(Call<AddUserResponse> call, Response<AddUserResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<AddUserResponse> call, Throwable t) {
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
    public void getCityState(String pincode, final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("pincode", pincode);
        registerQuotesNetworkService.getCity(body).enqueue(new Callback<PincodeResponse>() {
            @Override
            public void onResponse(Call<PincodeResponse> call, Response<PincodeResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<PincodeResponse> call, Throwable t) {
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
    public void updateUser(UpdateUserRequestEntity updateUserRequestEntity, final IResponseSubcriber iResponseSubcriber) {
        registerQuotesNetworkService.updateUser(updateUserRequestEntity).enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
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
    public void getLogin(String mobile, String password, String token, String devId, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("mobile", mobile);
        body.put("password", password);
        body.put("device_token", token);
        body.put("device_id", devId);
        body.put("user_type_id", "1");

        registerQuotesNetworkService.getLogin(body).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {

                        new PrefManager(mContext).storeUserData(response.body().getData().get(0).getUserdetails().get(0));
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
    public void changePassword(String mobile, String curr_password, String new_password, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("mobile", mobile);
        body.put("current_password", curr_password);
        body.put("new_password", new_password);
        body.put("confirm_password", new_password);
        body.put("type", "1");

        registerQuotesNetworkService.changePassword(body).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
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
    public void forgotPassword(String mobile, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("mobile", mobile);
        body.put("type", "1");

        registerQuotesNetworkService.forgotPassword(body).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
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
    public void getPolicyData(String PolicyNumber, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("PolicyNumber", PolicyNumber);


        registerQuotesNetworkService.getPolicyData(body).enqueue(new Callback<PolicyResponse>() {
            @Override
            public void onResponse(Call<PolicyResponse> call, Response<PolicyResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<PolicyResponse> call, Throwable t) {
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
    public void saveUserRegistration(RegisterRequest registerRequest, final IResponseSubcriber iResponseSubcriber) {


        registerQuotesNetworkService.userRegistration(registerRequest).enqueue(new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {
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
    public void saveUserProfile(RegisterRequest registerRequest, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.userProfile(registerRequest).enqueue(new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {
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
    public void getUserProfile(final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("user_id", String.valueOf(loginEntity.getUser_id()));

        registerQuotesNetworkService.getProfile(body).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });
    }

    @Override
    public void verifyOTPTegistration(String email, String mobile, String ip, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("mobNo", mobile);
        body.put("ip", "");
        registerQuotesNetworkService.verifyUserRegistration(body).enqueue(new Callback<VerifyUserRegisterResponse>() {
            @Override
            public void onResponse(Call<VerifyUserRegisterResponse> call, Response<VerifyUserRegisterResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<VerifyUserRegisterResponse> call, Throwable t) {
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
    public void getOtp(String email, String mobile, String ip, final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("mobNo", mobile);
        body.put("ip", "");
        registerQuotesNetworkService.getOtp(body).enqueue(new Callback<GetOtpResponse>() {
            @Override
            public void onResponse(Call<GetOtpResponse> call, Response<GetOtpResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {
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
    public void getUserConstatnt(final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("userid", String.valueOf(loginEntity.getUser_id()));

        registerQuotesNetworkService.getUserConstant(body).enqueue(new Callback<UserConsttantResponse>() {
            @Override
            public void onResponse(Call<UserConsttantResponse> call, Response<UserConsttantResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        new PrefManager(mContext).storeUserConstatnt(response.body().getData().get(0));
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<UserConsttantResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });

    }

    @Override
    public void getCityMainMaster(final IResponseSubcriber iResponseSubcriber) {


        registerQuotesNetworkService.getCityMainMaster().enqueue(new Callback<CityMainResponse>() {
            @Override
            public void onResponse(Call<CityMainResponse> call, Response<CityMainResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        new PrefManager(mContext).storeCityData(response.body().getData());
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null) {
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                }
            }

            @Override
            public void onFailure(Call<CityMainResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });
    }

    @Override
    public void getCarVehicleMaster(final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.getVehicleData().enqueue(new Callback<VehicleMasterResponse>() {
            @Override
            public void onResponse(Call<VehicleMasterResponse> call, Response<VehicleMasterResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        if (response.body().getData().getVehicleMasterResult() != null) {

                            new PrefManager(mContext).storeVehicle(response.body().getData().getVehicleMasterResult());

                            if (iResponseSubcriber != null)
                                iResponseSubcriber.OnSuccess(response.body(), "");
                        }
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null) {
                        iResponseSubcriber.OnFailure(new RuntimeException((response.body().getMessage())));
                    }

                }
            }

            @Override
            public void onFailure(Call<VehicleMasterResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });


    }

    @Override
    public void saveFeedBack(String reqId, String userID, String feedback, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("request_id", String.valueOf(reqId));
        body.put("userid", String.valueOf(userID));
        body.put("feedback_comment", String.valueOf(feedback));


        registerQuotesNetworkService.saveFeedBack(body).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });
    }

    @Override
    public void displayFeedBack(int user_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("user_id", String.valueOf(user_id));


        registerQuotesNetworkService.displayFeedBack(body).enqueue(new Callback<DisplayFeedbackResponse>() {
            @Override
            public void onResponse(Call<DisplayFeedbackResponse> call, Response<DisplayFeedbackResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<DisplayFeedbackResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });
    }

    @Override
    public void saveRate(RateRequestEntity rateRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.saveRate(rateRequestEntity).enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
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
    public void displayRate(int userid, String request_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("request_id", String.valueOf(request_id));
        body.put("userid", String.valueOf(userid));

        registerQuotesNetworkService.dispalyRate(body).enqueue(new Callback<DisplayRateResponse>() {
            @Override
            public void onResponse(Call<DisplayRateResponse> call, Response<DisplayRateResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<DisplayRateResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });
    }

    @Override
    public void getVechileDetails(String RegistrationNumber, final IResponseSubcriber iResponseSubcriber) {

        //  String url1 = "http://api.rupeeboss.com/BankAPIService.svc/GetCitywiseBankList?City_Id=" + cityid+"&Product_Id="+Productid;

        String url = "http://api.magicfinmart.com/api/vehicle-info";

        HashMap<String, String> body = new HashMap<>();

        body.put("RegistrationNumber", RegistrationNumber);

        registerQuotesNetworkService.getFastLaneData(url, body).enqueue(new Callback<FastLaneDataResponse>() {
            @Override
            public void onResponse(Call<FastLaneDataResponse> call, Response<FastLaneDataResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<FastLaneDataResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });
    }


}
