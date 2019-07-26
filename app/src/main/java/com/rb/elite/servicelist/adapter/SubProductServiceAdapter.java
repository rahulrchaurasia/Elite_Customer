package com.rb.elite.servicelist.adapter;


import android.app.Activity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rb.elite.R;
import com.rb.elite.core.model.RTOServiceEntity;
import com.rb.elite.servicelist.SubProductActivity;

import java.util.List;

/**
 * Created by IN-RB on 22-06-2018.
 */

public class SubProductServiceAdapter extends RecyclerView.Adapter<SubProductServiceAdapter.ProductItem> {


    Activity mContext;
    List<RTOServiceEntity> subPrdList;


    public SubProductServiceAdapter(Activity mContext, List<RTOServiceEntity> subPrdList) {
        this.mContext = mContext;
        this.subPrdList = subPrdList;

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
    public SubProductServiceAdapter.ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_sub_prod_disp_data, parent, false);

        return new SubProductServiceAdapter.ProductItem(itemView);
    }

    @Override
    public void onBindViewHolder(ProductItem holder, int position) {


        final RTOServiceEntity entity = subPrdList.get(position);

        holder.txtTitle.setText("" + entity.getName());

//        if(entity.getProduct_logo() != null) {
//            Glide.with(mContext).load(entity.getProduct_logo())
//                    .into(holder.ivProduct);
//        }

        Glide.with(mContext).load(entity.getProduct_logo())
                .fitCenter()
                .placeholder(R.drawable.elite_placeholder)
                .crossFade(1000)
                .into(holder.ivProduct);

        holder.cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getProduct()
                  ((SubProductActivity)mContext).getProduct(entity);

            }
        });


    }


    @Override
    public int getItemCount() {
        return subPrdList.size();
    }
}
