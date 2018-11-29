package com.pb.elite.servicelist.adapter;


import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pb.elite.R;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.servicelist.NonRTOListFragment;

import java.util.List;

/**
 * Created by IN-RB on 22-06-2018.
 */

public class NonRTOServiceAdapter extends RecyclerView.Adapter<NonRTOServiceAdapter.ProductItem> {


    Fragment mContext;
    List<RTOServiceEntity> NonRTOList;


    public NonRTOServiceAdapter(Fragment mContext, List<RTOServiceEntity> nonRTOList) {
        this.mContext = mContext;
        this.NonRTOList = nonRTOList;

    }

    public class ProductItem extends RecyclerView.ViewHolder {
        public TextView txtTitle;

        public CardView cvParent;

        public ImageView ivProduct;

        public ProductItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

            cvParent = (CardView) itemView.findViewById(R.id.cvParent);

            ivProduct = (ImageView) itemView.findViewById(R.id.ivProduct);


        }
    }


    @Override
    public NonRTOServiceAdapter.ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_prod_disp_data, parent, false);

        return new NonRTOServiceAdapter.ProductItem(itemView);
    }

    @Override
    public void onBindViewHolder(ProductItem holder, int position) {


        final RTOServiceEntity entity = NonRTOList.get(position);

        holder.txtTitle.setText("" + entity.getName());

        Glide.with(mContext).load(entity.getProduct_logo())
                .fitCenter()
                .placeholder(R.drawable.elite_placeholder)
                .crossFade(1000)
                .into(holder.ivProduct);

        holder.cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NonRTOListFragment) mContext).getProduct(entity);

            }
        });


    }

    @Override
    public int getItemCount() {
        return NonRTOList.size();
    }
}
