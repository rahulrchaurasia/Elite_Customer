package com.pb.elite.orderDetail;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pb.elite.R;
import com.pb.elite.core.model.OrderDetailEntity;

import java.util.List;

/**
 * Created by IN-RB on 05-02-2018.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailItem> {


    Fragment mContext;
    List<OrderDetailEntity> lstOrderDetail;

    public OrderDetailAdapter(Fragment mContext, List<OrderDetailEntity> lstOrderDetail) {
        this.mContext = mContext;
        this.lstOrderDetail = lstOrderDetail;
    }

    public class OrderDetailItem extends RecyclerView.ViewHolder {
        TextView txtPrdName, txtAmnt, txtOrderID, txtcustName, txtDate, txtStatus,  txtUpload;
        LinearLayout lyUpload;
        ImageView ivReceipt;

        public OrderDetailItem(View itemView) {
            super(itemView);
            txtPrdName = (TextView) itemView.findViewById(R.id.txtPrdName);
            txtAmnt = (TextView) itemView.findViewById(R.id.txtAmnt);

            txtOrderID = (TextView) itemView.findViewById(R.id.txtOrderID);
         //   txtcustName = (TextView) itemView.findViewById(R.id.txtcustName);

            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);

            txtUpload = (TextView) itemView.findViewById(R.id.txtUpload);

            lyUpload = (LinearLayout) itemView.findViewById(R.id.lyUpload);

            ivReceipt = itemView.findViewById(R.id.ivReceipt);
        }
    }

    @Override
    public OrderDetailItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_layout_item, parent, false);

        return new OrderDetailAdapter.OrderDetailItem(itemView);
    }

    @Override
    public void onBindViewHolder(OrderDetailItem holder, int position) {


        final OrderDetailEntity orderDetailEntity = lstOrderDetail.get(position);

        holder.txtPrdName.setText("" + orderDetailEntity.getProduct_name());
        holder.txtAmnt.setText("" + "\u20B9" + " " + orderDetailEntity.getAmount());
        holder.txtOrderID.setText("" + orderDetailEntity.getOrder_id());
      //  holder.txtcustName.setText("" + orderDetailEntity.getCustomer_name());
        holder.txtDate.setText("" + orderDetailEntity.getPayment_date());
        holder.txtStatus.setText("Request " + orderDetailEntity.getOrder_status());

//        if(orderDetailEntity.getPayment_status().equals("1")) {
//            holder.imgPay.setBackgroundResource(R.drawable.tick);
//        }else{
//            holder.imgPay.setBackgroundResource(R.drawable.cross);
//        }

        if (orderDetailEntity.getStatus().equals("1")) {
            holder.txtStatus.setTextColor(Color.parseColor("#009EE3"));
        } else if (orderDetailEntity.getStatus().equals("2")) {
            holder.txtStatus.setTextColor(Color.parseColor("#212121"));
        } else if (orderDetailEntity.getStatus().equals("3")) {
            holder.txtStatus.setTextColor(Color.parseColor("#42ceb2"));
        } else if (orderDetailEntity.getStatus().equals("4")) {
            holder.txtStatus.setTextColor(Color.parseColor("#de6d75"));
        }



        //txtUpload

         if(orderDetailEntity.getDocumentPending() == 0 )


        {

            holder.txtUpload.setText("Document Complete");

            holder.txtUpload.setBackgroundColor(mContext.getResources().getColor(R.color.buttonGreenBackground));
            holder.txtUpload.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility, 0, 0, 0);

        } else {
            holder.txtUpload.setText("Document Pending  ");
            holder.txtUpload.setBackgroundColor(mContext.getResources().getColor(R.color.buttonRedBackground));
             holder.txtUpload.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_file_upload, 0, 0, 0);

        }

        holder.lyUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((OrderDetailFragment) mContext).getOrderId(orderDetailEntity.getOrder_id());
            }
        });
        holder.ivReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((OrderDetailFragment) mContext).redirectToreceipt(orderDetailEntity);
            }
        });


    }


    @Override
    public int getItemCount() {
        return lstOrderDetail.size();
    }
}
