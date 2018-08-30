package com.pb.elite.product;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pb.elite.R;
import com.pb.elite.core.model.DocProductEnity;
import com.pb.elite.servicelist.adapter.RTOServiceAdapter;

import java.util.List;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class ProductDocAdapter extends RecyclerView.Adapter<ProductDocAdapter.ProductItem> {

    Activity mContext;
    List<DocProductEnity> lstDoc;

    public ProductDocAdapter(Activity mContext, List<DocProductEnity> lstDoc) {
        this.mContext = mContext;
        this.lstDoc = lstDoc;
    }


    public class ProductItem extends RecyclerView.ViewHolder
    {
        public TextView txtTitle;


        public ProductItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

        }
    }

    @Override
    public ProductDocAdapter.ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_prd_doc_disp_item, parent, false);

        return new ProductDocAdapter.ProductItem (itemView);
    }

    @Override
    public void onBindViewHolder(ProductItem holder, int position) {

        final DocProductEnity docProductEnity = lstDoc.get(position);

        holder.txtTitle.setText( "" +docProductEnity.getDocument_name());

    }



    @Override
    public int getItemCount() {
        return lstDoc.size();
    }
}
