package com.pb.elite.non_rto_fragments.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pb.elite.R;
import com.pb.elite.core.model.RtoCityMain;
import com.pb.elite.rto_fragment.adapter.IRTOCity;

import java.util.List;

/**
 * Created by IN-RB on 26-11-2018.
 */

public class InsurerMainAdapter extends RecyclerView.Adapter<InsurerMainAdapter.RtoItem> {

    Fragment mContext;

    List<RtoCityMain> rtoProductDisplayList;

    IRTOCity iRTOCity;

    public InsurerMainAdapter(Fragment mContext, List<RtoCityMain> rtoProductDisplayList, IRTOCity irtoCity) {
        this.mContext = mContext;
        this.rtoProductDisplayList = rtoProductDisplayList;
        iRTOCity = irtoCity;
    }

    public class RtoItem extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public LinearLayout lyParent;


        public RtoItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
        }
    }


    @Override
    public InsurerMainAdapter.RtoItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rto_list_item, parent, false);

        return new InsurerMainAdapter.RtoItem(itemView);

    }

    @Override
    public void onBindViewHolder(InsurerMainAdapter.RtoItem holder, int position) {

        final RtoCityMain rtoEntity = rtoProductDisplayList.get(position);

        holder.txtTitle.setText("" + rtoEntity.getSeries_no() + "-" + rtoEntity.getRto_location());

        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iRTOCity.getRTOCity(rtoEntity);

            }
        });


    }

    @Override
    public int getItemCount() {
        return rtoProductDisplayList.size();
    }
}
