package com.pb.elite.core.controller.product;

import android.content.Context;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.requestbuilder.ProductRequestBuilder;
import com.pb.elite.core.requestmodel.InsertOrderRequestEntity;
import com.pb.elite.core.response.CityResponse;
import com.pb.elite.core.response.DocumentResponse;
import com.pb.elite.core.response.DocumentViewResponse;
import com.pb.elite.core.response.NonRtoProductDisplayResponse;
import com.pb.elite.core.response.OrderDetailResponse;
import com.pb.elite.core.requestmodel.UpdateOrderRequestEntity;
import com.pb.elite.core.response.OrderResponse;
import com.pb.elite.core.response.ProductDocumentResponse;
import com.pb.elite.core.response.ProductResponse;
import com.pb.elite.core.response.RtoLocationReponse;
import com.pb.elite.core.response.ClientCommonResponse;
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
                    if(response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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

                    if(response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.message());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
    public void getRTOLocationMaster(int prodid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("productid", String.valueOf(prodid));

        productNetworkService.getRTOLocation(body).enqueue(new Callback<RtoLocationReponse>() {
            @Override
            public void onResponse(Call<RtoLocationReponse> call, Response<RtoLocationReponse> response) {
                if (response.body() != null) {
                    if(response.body().getStatus_code() == 0) {
                        //callback of data

                      //  new AsyncRTOMaster(mContext, response.body().getData().getRtolist(),response.body().getData().getCities()).execute();
                        new DataBaseController(mContext).removeCity_RTO();
                        new SyncRTOMaster(mContext, response.body().getData().getRtolist(),response.body().getData().getCities()).getRTOData();
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
                    if(response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
    public void getCityMaster(final IResponseSubcriber iResponseSubcriber) {
        productNetworkService.getCityMaster().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.body() != null) {
                    if(response.body().getStatus_code() == 0) {
                        //callback of data

                        new AsyncCityMaster(mContext,response.body().getData().getAllCities()).execute();
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
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
                    if(response.body().getStatus_code() == 0) {
                        //callback of data


                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
    public void getRTOProductList(int prdid, String prdCode,int userID, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("product_id", String.valueOf(prdid));
        body.put("productcode", prdCode);
        body.put("userid", String.valueOf(userID));

        productNetworkService.getRTOProductList(body).enqueue(new Callback<RtoProductDisplayResponse>() {
            @Override
            public void onResponse(Call<RtoProductDisplayResponse> call, Response<RtoProductDisplayResponse> response) {
                if (response.body() != null) {
                    if(response.body().getStatus_code() == 0) {
                        //callback of data

                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
                    if(response.body().getStatus_code() == 0) {
                        //callback of data

                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
                    if(response.body().getStatus_code() == 0) {
                        //callback of data

                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
    public void getDocumentView(String order_id , final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("order_id", order_id);

        productNetworkService.getDocumentView(body).enqueue(new Callback<DocumentViewResponse>() {
            @Override
            public void onResponse(Call<DocumentViewResponse> call, Response<DocumentViewResponse> response) {
                if (response.body() != null) {
                    if(response.body().getStatus_code() == 0) {
                        //callback of data

                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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
    public void uploadDocuments(MultipartBody.Part document, HashMap<String, Integer> body, final IResponseSubcriber iResponseSubcriber) {

        productNetworkService.uploadDocument(document, body).enqueue(new Callback<DocumentResponse>() {
            @Override
            public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {
                if (response.body() != null) {
                    if(response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    }
                    else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
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




    //insertOrder
}
