package com.rb.elite.orderDetail;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rb.elite.BaseFragment;
import com.rb.elite.R;
import com.rb.elite.chat.ChatActivity;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.product.ProductController;
import com.rb.elite.core.model.OrderDetailEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.response.OrderDetailResponse;

import com.rb.elite.database.DataBaseController;
import com.rb.elite.document.DocUploadActivity;
import com.rb.elite.feedback.FeedbackActivity;

import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends BaseFragment implements IResponseSubcriber {

    RecyclerView rvOrderDtl;
    List<OrderDetailEntity> lstOrderDetail;
    OrderDetailAdapter mAdapter;

    DataBaseController dataBaseController;
    UserEntity loginEntity;
    PrefManager prefManager;

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        initilize(view);
        dataBaseController = new DataBaseController(getActivity());
        prefManager = new PrefManager(getActivity());

        loginEntity = prefManager.getUserData();

        showDialog();
        new ProductController(getContext()).getOrderData(loginEntity.getUser_id(), this);
        return view;
    }

    private void initilize(View view) {
        rvOrderDtl = (RecyclerView) view.findViewById(R.id.rvOrderDtl);
        rvOrderDtl.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvOrderDtl.setLayoutManager(mLayoutManager);

        lstOrderDetail = new ArrayList<OrderDetailEntity>();
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof OrderDetailResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                lstOrderDetail = ((OrderDetailResponse) response).getData();
                mAdapter = new OrderDetailAdapter(OrderDetailFragment.this, lstOrderDetail);
                rvOrderDtl.setAdapter(mAdapter);

            } else {
                rvOrderDtl.setAdapter(null);
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    public void getOrderId(int orderId) {


        startActivityForResult(new Intent(getActivity(), DocUploadActivity.class)
                .putExtra("ORDER_ID",orderId), Constants.ORDER_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ORDER_CODE) {

            showDialog();
            new ProductController(getContext()).getOrderData(loginEntity.getUser_id(), this);
        }
    }

    public void redirectToFeedBack(OrderDetailEntity orderDetailEntity) {

        startActivity(new Intent(getActivity(), FeedbackActivity.class)
                .putExtra(Constants.FEEDBACK_DATA, orderDetailEntity));
    }

    public void redirectToChat(OrderDetailEntity orderDetailEntity) {

        startActivity(new Intent(getActivity(), ChatActivity.class)
                .putExtra(Constants.CHAT_REQUEST_DATA, orderDetailEntity));
    }

    public void redirectToreceipt(OrderDetailEntity orderDetailEntity) {
        //todo: need to change the id
        //region Comment : Code For Webview Which have Html inbuild file in asset
//        String id = "172";
//       // String url = "http://www.rupeeboss.com/receipt";
//      //  String url ="https://www.rupeeboss.com/receipt/index1.html";
//        String url = "file:///android_asset/index1.html";
//        startActivity(new Intent(getActivity(), CommonWebViewActivity.class)
//                .putExtra("URL", url)
//                .putExtra("NAME", "RECEIPT_" + id)
//                .putExtra("TITLE", "RECEIPT"));
        //endregion

       // new DownloadFromUrl(orderDetailEntity.getReceipt()+".pdf", "EliteReceipt").execute();

        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(orderDetailEntity.getReceipt()));
        startActivity(intent);
    }
}
