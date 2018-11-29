package com.pb.elite.rtoAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pb.elite.R;
import com.pb.elite.core.model.RtoProductDisplayMainEntity;
import com.pb.elite.product.ProductMainActivity;
import com.pb.elite.productServiceRtoFragment.AssistanObtainFragment;
import com.pb.elite.productServiceRtoFragment.RenewRcFragment;

import java.util.List;

/**
 * Created by IN-RB on 26-11-2018.
 */

public class CityMainAdapter extends RecyclerView.Adapter<CityMainAdapter.CityItem> {


    Fragment mContext;

    List<RtoProductDisplayMainEntity> rtoCityList;

    public CityMainAdapter(Fragment mContext, List<RtoProductDisplayMainEntity> rtoCityList) {
        this.mContext = mContext;
        this.rtoCityList = rtoCityList;

    }

    public class CityItem extends RecyclerView.ViewHolder
    {
        public TextView txtTitle;
        public LinearLayout lyParent;


        public CityItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lyParent  = (LinearLayout) itemView.findViewById(R.id.lyParent);
        }
    }


    @Override
    public CityMainAdapter.CityItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rto_list_item, parent, false);

        return new CityMainAdapter.CityItem (itemView);
    }

    @Override
    public void onBindViewHolder(CityMainAdapter.CityItem holder, int position) {
        final RtoProductDisplayMainEntity cityEntity = rtoCityList.get(position);

        holder.txtTitle.setText( "" +cityEntity.getCityname());

        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof RenewRcFragment ) {
                    ((RenewRcFragment) mContext).getCityBottomSheet(cityEntity);
                }
                else if(mContext instanceof AssistanObtainFragment ) {
                    ((AssistanObtainFragment) mContext).getCityBottomSheet(cityEntity);
                }
            }
        });


    }

    public void updateList(List<RtoProductDisplayMainEntity> temprtoCityList)
    {
        rtoCityList = temprtoCityList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return rtoCityList.size();
    }
}
