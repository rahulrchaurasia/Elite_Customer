package com.pb.elite.product;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pb.elite.R;
import com.pb.elite.core.model.DocProductEnity;
import com.pb.elite.core.model.NonRtoSpeciallistEntity;

import java.util.List;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class SpecialProductAdapter extends RecyclerView.Adapter<SpecialProductAdapter.ProductItem> {

    Activity mContext;

    List<NonRtoSpeciallistEntity> lstSpecial;

    public SpecialProductAdapter(Activity mContext, List<NonRtoSpeciallistEntity> lstSpecial) {
        this.mContext = mContext;
        this.lstSpecial = lstSpecial;
    }


    public class ProductItem extends RecyclerView.ViewHolder {
        LinearLayout lyParent;
        public TextView txtTitle, txtPrice;
        CheckBox chkPrd;


        public ProductItem(View itemView) {
            super(itemView);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            chkPrd = (CheckBox) itemView.findViewById(R.id.chkPrd);

        }
    }

    @Override
    public SpecialProductAdapter.ProductItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_prd_spec_disp_item, parent, false);

        return new SpecialProductAdapter.ProductItem(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductItem holder,  int position) {

        final NonRtoSpeciallistEntity entity = lstSpecial.get(position);

        holder.txtTitle.setText("" + entity.getSpName());
        holder.txtPrice.setText("" + "\u20B9" + entity.getSpPrice());

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
                    ((ProductActivity)mContext).setSpecialAmount(entity,false);
                } else {
                    holder.chkPrd.setChecked(true);
                    ((ProductActivity)mContext).setSpecialAmount(entity,true);

                }

                updateList(entity);
            }
        });


    }

    public NonRtoSpeciallistEntity  getUserSelectedItem()
    {
       for(NonRtoSpeciallistEntity entity : lstSpecial )
       {
           if(entity.getCheck())
           {
               return entity;
           }
       }

       return null;
    }

    public void refreshAdapter(List<NonRtoSpeciallistEntity> lstSpecial) {
        this.lstSpecial = lstSpecial;
        notifyDataSetChanged();
    }

    private void updateList(NonRtoSpeciallistEntity curEntity) {

        for (int i = 0; i < lstSpecial.size(); i++) {
            if (lstSpecial.get(i).getSpName().equals(curEntity.getSpName())) {
                if (curEntity.getCheck()) {
                    lstSpecial.get(i).setCheck(false);
                } else {
                    lstSpecial.get(i).setCheck(true);


                }

            } else {
                lstSpecial.get(i).setCheck(false);
            }
        }

        notifyDataSetChanged();

        //  refreshAdapter(lstSpecial);
    }

    @Override
    public int getItemCount() {
        return lstSpecial.size();
    }
}
