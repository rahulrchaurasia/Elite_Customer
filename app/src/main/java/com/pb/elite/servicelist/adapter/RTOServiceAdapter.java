package com.pb.elite.servicelist.adapter;



import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.pb.elite.R;
import com.pb.elite.core.model.RTOServiceEntity;
import com.pb.elite.servicelist.RTOListFragment;

import java.util.List;

/**
 * Created by IN-RB on 22-06-2018.
 */

public class RTOServiceAdapter extends RecyclerView.Adapter<RTOServiceAdapter.ProductItem>  {


    Fragment mContext;
    List<RTOServiceEntity> RTOList;



    public RTOServiceAdapter(Fragment mContext, List<RTOServiceEntity> rTOList ) {
        this.mContext = mContext;
        this.RTOList = rTOList;

    }

    public class ProductItem extends RecyclerView.ViewHolder
    {
        public TextView txtTitle;
        public CardView cvParent;
        public ImageView ivProduct;


        public ProductItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            cvParent = (CardView) itemView.findViewById(R.id.cvParent);
            ivProduct = (ImageView)itemView.findViewById(R.id.ivProduct);


        }
    }


    @Override
    public RTOServiceAdapter.ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_prod_disp_data, parent, false);

        return new RTOServiceAdapter.ProductItem (itemView);
    }

    @Override
    public void onBindViewHolder(final ProductItem holder,  int position) {


        final RTOServiceEntity entity = RTOList.get(position);

        holder.txtTitle.setText( "" +entity.getName());

        Glide.with(mContext).load(entity.getProduct_logo())
                .fitCenter()
                .placeholder(R.drawable.elite_placeholder)
                .crossFade(1000)
                .into(holder.ivProduct);

//        if(entity.getProduct_logo() != null) {
//            Glide.with(mContext).load(entity.getProduct_logo())
//                    .into(holder.ivProduct);
//
//        }


        holder.cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((RTOListFragment)mContext).getProduct(entity);

            }
        });




    }





    @Override
    public int getItemCount() {
        return RTOList.size();
    }
}
