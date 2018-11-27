package com.pb.elite.core.controller.register;

import android.content.Context;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.AsyncCarMaster;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.requestbuilder.RegisterRequestBuilder;
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
import com.pb.elite.database.DataBaseController;
import com.pb.elite.splash.PrefManager;

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
    DataBaseController dataBaseController;

    public RegisterController(Context context) {
        registerQuotesNetworkService = new RegisterRequestBuilder().getService();
        mContext = context;
        dataBaseController = new DataBaseController(mContext);
        loginEntity = dataBaseController.getUserData();
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
    public void getCarMaster(final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("ProductId", "1");

        registerQuotesNetworkService.getCarMaster(hashMap).enqueue(new Callback<CarMasterResponse>() {
            @Override
            public void onResponse(Call<CarMasterResponse> call, Response<CarMasterResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        if (response.body().getMasterData() != null || response.body().getMasterData().size() != 0)
                            new AsyncCarMaster(mContext, response.body().getMasterData()).execute();
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                    } else {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }
                } else {
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(new RuntimeException("Failed to fetch information."));
                }
            }

            @Override
            public void onFailure(Call<CarMasterResponse> call, Throwable t) {
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
    public void getLogin(String mobile, String password, String token,String devId,final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("mobile", mobile);
        body.put("password", password);
        body.put("device_token",token);
        body.put("device_id",devId);
        body.put("user_type_id","1");

        registerQuotesNetworkService.getLogin(body).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    if (response.body().getStatus_code() == 0) {
                        new AsyncLoginMaster(mContext, response.body().getData().get(0)).execute();
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
    public void getCarVehicleMaster() {

        String url = "http://202.131.96.100:7541/LeadGenration.svc/VehicleMaster?VehicleTypeID=2";

        registerQuotesNetworkService.getCarMaster(url).enqueue(new Callback<VehicleMasterResponse>() {
            @Override
            public void onResponse(Call<VehicleMasterResponse> call, Response<VehicleMasterResponse> response) {
                if (response.isSuccessful()) {
                    new PrefManager(mContext).storeVehicle(response.body().getVehicleMasterResult());
                }
            }

            @Override
            public void onFailure(Call<VehicleMasterResponse> call, Throwable t) {


            }
        });
    }

    @Override
    public void getUserConstatnt( final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("userid", String.valueOf(loginEntity.getUser_id()));

        registerQuotesNetworkService.getUserConstant(body).enqueue(new Callback<UserConsttantResponse>() {
            @Override
            public void onResponse(Call<UserConsttantResponse> call, Response<UserConsttantResponse> response) {
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        new PrefManager(mContext).storeUserConstatnt(response.body().getData().get(0));
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<UserConsttantResponse> call, Throwable t) {
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
