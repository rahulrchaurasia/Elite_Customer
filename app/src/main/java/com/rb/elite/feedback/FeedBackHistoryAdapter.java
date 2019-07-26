package com.rb.elite.feedback;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rb.elite.R;
import com.rb.elite.core.model.FeedBackDisplayEntity;

import java.util.List;

/**
 * Created by IN-RB on 26-11-2018.
 */

public class FeedBackHistoryAdapter extends RecyclerView.Adapter<FeedBackHistoryAdapter.InsurerItem> {

    Activity mContext;


    List<FeedBackDisplayEntity>  feedBackDisplayEntityList;



    public FeedBackHistoryAdapter(Activity mContext, List<FeedBackDisplayEntity>  feedBackDisplayEntityList) {
        this.mContext = mContext;
        this.feedBackDisplayEntityList = feedBackDisplayEntityList;

    }

    public class InsurerItem extends RecyclerView.ViewHolder {
        public TextView txtTitle ,txtBody;
        public LinearLayout lyParent;


        public InsurerItem(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);
            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
        }
    }


    @Override
    public FeedBackHistoryAdapter.InsurerItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_feedback_list_item, parent, false);

        return new FeedBackHistoryAdapter.InsurerItem(itemView);

    }

    @Override
    public void onBindViewHolder(FeedBackHistoryAdapter.InsurerItem holder, int position) {

        final FeedBackDisplayEntity feedBackDisplayEntity = feedBackDisplayEntityList.get(position);

        holder.txtTitle.setText("" + feedBackDisplayEntity.getDisplay_request_id());
        holder.txtBody.setText("" + feedBackDisplayEntity.getFeedback_comment());

    }

    @Override
    public int getItemCount() {
        return feedBackDisplayEntityList.size();
    }
}
