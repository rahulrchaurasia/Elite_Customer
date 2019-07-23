package com.rb.elite.core.controller.product;

import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.requestmodel.UpdateOrderRequestEntity;
import com.rb.elite.core.requestmodel.InsertOrderRequestEntity;

import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * Created by Rahul on 02/02/2018.
 */

public interface IProduct {


    void inserOrderData(InsertOrderRequestEntity requestEntity, IResponseSubcriber iResponseSubcriber);

    void updateOrderId(UpdateOrderRequestEntity updateOrderRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getOrderData( int userid, IResponseSubcriber iResponseSubcriber);

    void getCompleteOrderData( int userid, IResponseSubcriber iResponseSubcriber);

    void getRTOLocationMaster(int prdid, IResponseSubcriber iResponseSubcriber);

    void getProductMaster(IResponseSubcriber iResponseSubcriber);

    void getRtoAndNonRtoList(IResponseSubcriber iResponseSubcriber);

    void getRTOProductList(int prdid,String prdCode, int UserId, IResponseSubcriber iResponseSubcriber);

    void RTOProductListOnChangeVehicle(int prdid,String prdCode, int UserId,String make,String model, IResponseSubcriber iResponseSubcriber);

    void getNonRTOProductList(int prdid, IResponseSubcriber iResponseSubcriber);

    void getProducDoc(int prdid, IResponseSubcriber iResponseSubcriber);

    void getDocumentView(String order_id, IResponseSubcriber iResponseSubcriber);

    void getNotifcation(int userid,String count, IResponseSubcriber iResponseSubcriber);


    void uploadDocuments(MultipartBody.Part document, HashMap<String, Integer> body, final IResponseSubcriber iResponseSubcriber);


    void getChatDetail(String req_id, IResponseSubcriber iResponseSubcriber);

    void updateReadChat(String req_id, IResponseSubcriber iResponseSubcriber);

    void saveChat(String req_id,String cust_id,String message, IResponseSubcriber iResponseSubcriber);
}
