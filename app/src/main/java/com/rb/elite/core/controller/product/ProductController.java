package com.rb.elite.core.controller.product;

import android.content.Context;

import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.requestbuilder.ProductRequestBuilder;
import com.rb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.rb.elite.core.requestmodel.UpdateOrderRequestEntity;
import com.rb.elite.core.response.ChatResponse;
import com.rb.elite.core.response.ClientCommonResponse;
import com.rb.elite.core.response.CompleteOrderResponse;
import com.rb.elite.core.response.DocumentResponse;
import com.rb.elite.core.response.DocumentViewResponse;
import com.rb.elite.core.response.NonRtoProductDisplayResponse;
import com.rb.elite.core.response.NotificationResponse;
import com.rb.elite.core.response.OrderDetailResponse;
import com.rb.elite.core.response.OrderResponse;
import com.rb.elite.core.response.ProductDocumentResponse;
import com.rb.elite.core.response.ProductResponse;
import com.rb.elite.core.response.RtoLocationReponse;
import com.rb.elite.core.response.RtoProductDisplayResponse;
import com.rb.elite.core.response.SaveChatResponse;
import com.rb.elite.core.response.ServiceListResponse;
import com.rb.elite.core.response.UpdateChatResponse;
import com.rb.elite.splash.PrefManager;

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

public class ProductController implements IProduct {

    ProductRequestBuilder.ProductNetworkService productNetworkService;
    Context mContext;
    PrefManager prefManager;
    IResponseSubcriber iResponseSubcriber;

    public ProductController(Context context) {
        productNetworkService = new ProductRequestBuilder().getService();
        mContext = context;
        prefManager = new PrefManager(mContext);
    }


    @Override
    public void inserOrderData(InsertOrderRequestEntity requestEntity, final IResponseSubcriber iResponseSubcriber) {

        productNetworkService.insertOrder(requestEntity).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
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
            public void onFailure(Call<OrderResponse> call, Throwable t) {
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
    public void updateOrderId(UpdateOrderRequestEntity updateOrderRequestEntity, final IResponseSubcriber iResponseSubcriber) {
        productNetworkService.updateOrder(updateOrderRequestEntity).enqueue(new Callback<ClientCommonResponse>() {
            @Override
            public void onResponse(Call<ClientCommonResponse> call, Response<ClientCommonResponse> response) {
                if (response.body() != null) {
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(Call<ClientCommonResponse> call, Throwable t) {
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
    public void getOrderData(int userid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("user_id", String.valueOf(userid));

        productNetworkService.getOrder(body).enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
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
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
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
    public void getCompleteOrderData(int userid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("user_id", String.valueOf(userid));

        productNetworkService.getCompletetOrder(body).enqueue(new Callback<CompleteOrderResponse>() {
            @Override
            public void onResponse(Call<CompleteOrderResponse> call, Response<CompleteOrderResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
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
            public void onFailure(Call<CompleteOrderResponse> call, Throwable t) {
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
    public void getRTOLocationMaster(int prodid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("productid", String.valueOf(prodid));

        productNetworkService.getRTOLocation(body).enqueue(new Callback<RtoLocationReponse>() {
            @Override
            public void onResponse(Call<RtoLocationReponse> call, Response<RtoLocationReponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        //callback of data

                        //  new AsyncRTOMaster(mContext, response.body().getData().getRtolist(),response.body().getData().getCities()).execute();

                        //  new SyncRTOMaster(mContext, response.body().getData().getRtolist(),response.body().getData().getCities()).getRTOData();
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
            public void onFailure(Call<RtoLocationReponse> call, Throwable t) {
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
    public void getProductMaster(final IResponseSubcriber iResponseSubcriber) {

        productNetworkService.getProductMaster().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
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
            public void onFailure(Call<ProductResponse> call, Throwable t) {
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
    public void getRtoAndNonRtoList(final IResponseSubcriber iResponseSubcriber) {

        productNetworkService.getRtoAndNonRto().enqueue(new Callback<ServiceListResponse>() {
            @Override
            public void onResponse(Call<ServiceListResponse> call, Response<ServiceListResponse> response) {
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
            public void onFailure(Call<ServiceListResponse> call, Throwable t) {
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
    public void getRTOProductList(int prdid, String prdCode, int userID, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("product_id", String.valueOf(prdid));
        body.put("productcode", prdCode);
        body.put("userid", String.valueOf(userID));

        productNetworkService.getRTOProductList(body).enqueue(new Callback<RtoProductDisplayResponse>() {
            @Override
            public void onResponse(Call<RtoProductDisplayResponse> call, Response<RtoProductDisplayResponse> response) {
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
            public void onFailure(Call<RtoProductDisplayResponse> call, Throwable t) {
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
    public void RTOProductListOnChangeVehicle(int prdid, String prdCode, int UserId, String make, String model, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("product_id", String.valueOf(prdid));
        body.put("productcode", prdCode);
        body.put("userid", String.valueOf(UserId));
        body.put("make", make);
        body.put("model", model);

        productNetworkService.getRTOProductListOnChangeVehicle(body).enqueue(new Callback<RtoProductDisplayResponse>() {
            @Override
            public void onResponse(Call<RtoProductDisplayResponse> call, Response<RtoProductDisplayResponse> response) {
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
            public void onFailure(Call<RtoProductDisplayResponse> call, Throwable t) {
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
    public void getNonRTOProductList(int prdid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("product_id", String.valueOf(prdid));

        productNetworkService.getNonRTOProductList(body).enqueue(new Callback<NonRtoProductDisplayResponse>() {
            @Override
            public void onResponse(Call<NonRtoProductDisplayResponse> call, Response<NonRtoProductDisplayResponse> response) {
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
            public void onFailure(Call<NonRtoProductDisplayResponse> call, Throwable t) {
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
    public void getProducDoc(int prdid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("product_id", String.valueOf(prdid));

        productNetworkService.getProdDocument(body).enqueue(new Callback<ProductDocumentResponse>() {
            @Override
            public void onResponse(Call<ProductDocumentResponse> call, Response<ProductDocumentResponse> response) {
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
            public void onFailure(Call<ProductDocumentResponse> call, Throwable t) {
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
    public void getDocumentView(String order_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("order_id", order_id);

        productNetworkService.getDocumentView(body).enqueue(new Callback<DocumentViewResponse>() {
            @Override
            public void onResponse(Call<DocumentViewResponse> call, Response<DocumentViewResponse> response) {
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
            public void onFailure(Call<DocumentViewResponse> call, Throwable t) {
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
    public void getNotifcation(int userid, String count, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("isagentapp", "0");
        body.put("count", count);
        body.put("userid", String.valueOf(userid));

        productNetworkService.getNotification(body).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
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
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
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
    public void uploadDocuments(MultipartBody.Part document, HashMap<String, Integer> body, final IResponseSubcriber iResponseSubcriber) {

        productNetworkService.uploadDocument(document, body).enqueue(new Callback<DocumentResponse>() {
            @Override
            public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {
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
            public void onFailure(Call<DocumentResponse> call, Throwable t) {
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
    public void getChatDetail(String req_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("req_id", req_id);

        productNetworkService.getChatDetail(body).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
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
            public void onFailure(Call<ChatResponse> call, Throwable t) {
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
    public void updateReadChat(String req_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("req_id", req_id);

        productNetworkService.updateReadChat(body).enqueue(new Callback<UpdateChatResponse>() {
            @Override
            public void onResponse(Call<UpdateChatResponse> call, Response<UpdateChatResponse> response) {
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
            public void onFailure(Call<UpdateChatResponse> call, Throwable t) {
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
    public void saveChat(String req_id, String cust_id, String message, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("req_id", req_id);
        body.put("cust_id", cust_id);
        body.put("message", message);

        productNetworkService.saveChat(body).enqueue(new Callback<SaveChatResponse>() {
            @Override
            public void onResponse(Call<SaveChatResponse> call, Response<SaveChatResponse> response) {
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
            public void onFailure(Call<SaveChatResponse> call, Throwable t) {
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


    //insertOrder
}
