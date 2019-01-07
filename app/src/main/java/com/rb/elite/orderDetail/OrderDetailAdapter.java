package com.rb.elite.orderDetail;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rb.elite.R;
import com.rb.elite.core.model.OrderDetailEntity;

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
        TextView txtPrdName, txtAmnt, txtOrderID, txtDate, txtStatus;
        Button btnUpload;
        LinearLayout lyUpload,lyReceipt,lyFeedback;
        ImageView ivReceipt,ivFeedBack,ivReqLogo;
        RatingBar ratingBar;

        public OrderDetailItem(View itemView) {
            super(itemView);
            txtPrdName =  itemView.findViewById(R.id.txtPrdName);
            txtAmnt =  itemView.findViewById(R.id.txtAmnt);
            txtOrderID = itemView.findViewById(R.id.txtOrderID);
            txtDate =  itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnUpload =  itemView.findViewById(R.id.btnUpload);

            lyUpload =  itemView.findViewById(R.id.lyUpload);
            lyReceipt =    itemView.findViewById(R.id.lyReceipt);
            ivReceipt = itemView.findViewById(R.id.ivReceipt);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ivFeedBack  = itemView.findViewById(R.id.ivFeedBack);
            lyFeedback =  itemView.findViewById(R.id.lyFeedback);
            ivReqLogo =  itemView.findViewById(R.id.ivReqLogo);

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

        holder.txtDate.setText("" + orderDetailEntity.getPayment_date());
        holder.txtStatus.setText(""+ orderDetailEntity.getOrder_status());

        if(orderDetailEntity.getLogo() !=null && orderDetailEntity.getLogo() !=""){
            Glide.with(mContext).load(orderDetailEntity.getLogo()).into(holder.ivReqLogo);
        }else{
            Glide.with(mContext).load(R.drawable.elite_placeholder).into(holder.ivReqLogo);

        }


        if(orderDetailEntity.getRating().trim().length()>0) {
            holder.ratingBar.setRating(Float.valueOf(orderDetailEntity.getRating()));
            holder.ratingBar.setVisibility(View.VISIBLE);
        }else{
            holder.ratingBar.setVisibility(View.GONE);
            holder.ratingBar.setRating(0.0f);
        }




        if (orderDetailEntity.getStatus().equals("1")) {
            holder.txtStatus.setTextColor(Color.parseColor("#009EE3"));
        } else if (orderDetailEntity.getStatus().equals("2")) {
            holder.txtStatus.setTextColor(Color.parseColor("#212121"));
        } else if (orderDetailEntity.getStatus().equals("3")) {
            holder.txtStatus.setTextColor(Color.parseColor("#42ceb2"));
        } else if (orderDetailEntity.getStatus().equals("4")) {
            holder.txtStatus.setTextColor(Color.parseColor("#de6d75"));
        }



         if(orderDetailEntity.getDocumentPending() == 0 )

        {
            holder.btnUpload.setBackgroundColor(mContext.getResources().getColor(R.color.buttonGreenBackground));
            holder.btnUpload.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility, 0, 0, 0);

        } else {
            holder.btnUpload.setBackgroundColor(mContext.getResources().getColor(R.color.buttonRedBackground));
             holder.btnUpload.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_file_upload, 0, 0, 0);

        }

        holder.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((OrderDetailFragment) mContext).getOrderId(orderDetailEntity.getOrder_id());
            }
        });
        holder.lyReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((OrderDetailFragment) mContext).redirectToreceipt(orderDetailEntity);
            }
        });

        holder.lyFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderDetailFragment) mContext).redirectToFeedBack(orderDetailEntity);
            }
        });


    }


    @Override
    public int getItemCount() {
        return lstOrderDetail.size();
    }
}
