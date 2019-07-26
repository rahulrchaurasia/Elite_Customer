package com.rb.elite.chat;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rb.elite.R;
import com.rb.elite.core.model.ChatEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by IN-RB on 22-02-2018.
 */


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatItem> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    Activity mContext;
    List<ChatEntity> chatEntityList;

    public ChatAdapter(Activity mContext, List<ChatEntity> chatList) {
        this.mContext = mContext;
        this.chatEntityList = chatList;
    }

    public class ChatItem extends RecyclerView.ViewHolder {
        public TextView txtMessage, txtChatTime;
        public LinearLayout lvParent;

        public ChatItem(View itemView) {
            super(itemView);

            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtChatTime = itemView.findViewById(R.id.txtChatTime);
            lvParent = itemView.findViewById(R.id.lvParent);

        }
    }


    @Override
    public ChatAdapter.ChatItem onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);

            return new ChatAdapter.ChatItem(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);

            return new ChatAdapter.ChatItem(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ChatItem holder, int position) {

        final ChatEntity chat = chatEntityList.get(position);
        holder.txtMessage.setText(chat.getMessage());
        //  holder.txtChatTime.setText(chat.getChatTime());

        holder.txtChatTime.setText((chat.getCreated_date_time()));

    }


    @Override
    public int getItemCount() {
        return chatEntityList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (chatEntityList.get(position).getType().trim().toUpperCase().equals("C")) {
            return MSG_TYPE_RIGHT;
        } else {

            return MSG_TYPE_LEFT;
        }
    }

    public void setChatData(ChatEntity chatEntity) {
        chatEntityList.add(chatEntity);
        notifyItemInserted(chatEntityList.size() - 1);

    }

    public void addChatList(List<ChatEntity> tempEntityList) {
        chatEntityList.addAll(tempEntityList);
        notifyDataSetChanged();
    }

    private String getCurrentDate(String tempdate)
    {
        try {
            SimpleDateFormat format = new SimpleDateFormat("DD-MMM-YYYY hh:mm:ss a", Locale.US);
            Date date = format.parse(tempdate);
            // format = new SimpleDateFormat("hh:mm a");   // for 24 hours Use : HH   and For 12 Use hh
            String dateString = format.format(date);
            return dateString;
        } catch (Exception ex) {
            return "";
        }
    }
}
