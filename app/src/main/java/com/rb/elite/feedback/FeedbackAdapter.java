package com.rb.elite.feedback;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rb.elite.R;
import com.rb.elite.core.model.OrderDetailEntity;

import java.util.List;

/**
 * Created by IN-RB on 05-02-2018.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.OrderDetailItem> {


    Activity mContext;
    List<OrderDetailEntity> lstOrderDetail;

    public FeedbackAdapter(Activity mContext, List<OrderDetailEntity> lstOrderDetail) {
        this.mContext = mContext;
        this.lstOrderDetail = lstOrderDetail;
    }

    public class OrderDetailItem extends RecyclerView.ViewHolder {
        TextView  txtReqID , txtBody;
        LinearLayout lyParent;


        public OrderDetailItem(View itemView) {
            super(itemView);
            txtReqID = (TextView) itemView.findViewById(R.id.txtReqID);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);

            lyParent =   (LinearLayout) itemView.findViewById(R.id.lyParent);

        }
    }

    @Override
    public OrderDetailItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_feedback_item, parent, false);

        return new FeedbackAdapter.OrderDetailItem(itemView);
    }

    @Override
    public void onBindViewHolder(OrderDetailItem holder, int position) {


        final OrderDetailEntity orderDetailEntity = lstOrderDetail.get(position);

        holder.txtReqID.setText("" + orderDetailEntity.getOrder_id());
        holder.txtBody.setText("" + orderDetailEntity.getProduct_name());

        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((FeedbackActivity) mContext).getOrderData(orderDetailEntity);
            }
        });


    }


    @Override
    public int getItemCount() {
        return lstOrderDetail.size();
    }
}
