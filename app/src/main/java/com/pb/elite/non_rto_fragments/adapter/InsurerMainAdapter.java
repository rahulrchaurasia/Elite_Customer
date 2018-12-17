package com.pb.elite.non_rto_fragments.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pb.elite.R;
import com.pb.elite.core.model.InsuranceCompanyEntity;
import com.pb.elite.core.model.RtoCityMain;
import com.pb.elite.core.response.MotorInsuranceListResponse;
import com.pb.elite.rto_fragment.adapter.IRTOCity;

import java.util.List;

/**
 * Created by IN-RB on 26-11-2018.
 */

public class InsurerMainAdapter extends RecyclerView.Adapter<InsurerMainAdapter.InsurerItem> {

    Fragment mContext;

    List<InsuranceCompanyEntity> insuranceCompanyEntityList;

    IInsurer iInsurer;

    public InsurerMainAdapter(Fragment mContext, List<InsuranceCompanyEntity> insuranceCompanyEntityList,  IInsurer iInsurer) {
        this.mContext = mContext;
        this.insuranceCompanyEntityList = insuranceCompanyEntityList;
        this.iInsurer = iInsurer;
    }

    public class InsurerItem extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public LinearLayout lyParent;


        public InsurerItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
        }
    }


    @Override
    public InsurerMainAdapter.InsurerItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rto_list_item, parent, false);

        return new InsurerMainAdapter.InsurerItem(itemView);

    }

    @Override
    public void onBindViewHolder(InsurerMainAdapter.InsurerItem holder, int position) {

        final InsuranceCompanyEntity insuranceCompanyEntity = insuranceCompanyEntityList.get(position);

        holder.txtTitle.setText("" + insuranceCompanyEntity.getInsurance_Name());

        holder.lyParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iInsurer.getInsurer(insuranceCompanyEntity);

            }
        });


    }

    @Override
    public int getItemCount() {
        return insuranceCompanyEntityList.size();
    }
}
