package com.pb.elite.productServiceRtoFragment;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pb.elite.R;
import com.pb.elite.core.model.CorrectiontEnity;
import com.pb.elite.core.model.NonRtoSpeciallistEntity;
import com.pb.elite.product.ProductActivity;

import java.util.List;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class CorrectionAdapter extends RecyclerView.Adapter<CorrectionAdapter.ProductItem> {

    Activity mContext;

    List<CorrectiontEnity> lstCorrection;

    public CorrectionAdapter(Activity mContext, List<CorrectiontEnity> lstCorrection) {
        this.mContext = mContext;
        this.lstCorrection = lstCorrection;
    }


    public class ProductItem extends RecyclerView.ViewHolder {
        LinearLayout lyParent;
        public TextView txtTitle, txtPrice;
        CheckBox chkPrd;


        public ProductItem(View itemView) {
            super(itemView);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            chkPrd = (CheckBox) itemView.findViewById(R.id.chkPrd);

        }
    }

    @Override
    public CorrectionAdapter.ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_assist_correction, parent, false);

        return new CorrectionAdapter.ProductItem(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductItem holder,  int position) {

        final CorrectiontEnity entity = lstCorrection.get(position);

        holder.txtTitle.setText("" + entity.getDocument_name());

        if (entity.getCheck()) {
            holder.chkPrd.setChecked(true);
            holder.lyParent.setBackgroundColor(mContext.getResources().getColor(R.color.seperator_white));

        } else {
            holder.chkPrd.setChecked(false);
            holder.lyParent.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        }



        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (entity.getCheck()) {
                    holder.chkPrd.setChecked(false);

                } else {
                    holder.chkPrd.setChecked(true);

                }


            }
        });


    }






    @Override
    public int getItemCount() {
        return lstCorrection.size();
    }
}
