package com.rb.elite.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rb.elite.BaseActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.product.ProductController;
import com.rb.elite.core.model.ChatEntity;
import com.rb.elite.core.model.NotifyEntity;
import com.rb.elite.core.model.OrderDetailEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.response.ChatResponse;
import com.rb.elite.core.response.SaveChatResponse;
import com.rb.elite.core.response.UpdateChatResponse;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;
import com.rb.elite.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener {

    UserEntity loginEntity;
    PrefManager prefManager;
    ImageButton imgbtnSend;
    EditText etComment;

    RecyclerView rvChat;
    List<ChatEntity> chatEntityList;
    ChatAdapter mAdapter;
    ChatEntity chatEntity;
    OrderDetailEntity orderDetailEntity;
    String REQUEST_ID = "";
    TextWatcher commentTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.toString().trim().length() == 0) {
                imgbtnSend.setImageDrawable(null);

            } else {
                imgbtnSend.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this, R.drawable.ic_send));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //region broadcast receiver
    public BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null) {
                if (intent.getAction().equalsIgnoreCase(Utility.CHAT_BROADCAST_ACTION)) {
                    chatEntity = intent.getParcelableExtra(Utility.CHAT_DATA);

                    if(chatEntity.getReq_id().equals(REQUEST_ID))  // if Same Chat Page will not open we will ignore the Notify Message
                    {
                        if (mAdapter != null) {
                            mAdapter.setChatData(chatEntity);
                            scrollToBottom();
                        } else {
                            bindChatList();
                            mAdapter.setChatData(chatEntity);
                        }

                        new ProductController(ChatActivity.this).updateReadChat(REQUEST_ID, ChatActivity.this);
                    }

                }
            }

        }
    };

    //endregion



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        prefManager.setNotificationCounter(0);
        LocalBroadcastManager.getInstance(ChatActivity.this).registerReceiver(mHandleMessageReceiver, new IntentFilter(Utility.CHAT_BROADCAST_ACTION));
        initialize();
        setListener();
        bindChatList();


        if (getIntent().hasExtra(Constants.CHAT_REQUEST_DATA)) {
            if (getIntent().getExtras().getParcelable(Constants.CHAT_REQUEST_DATA) != null) {

                orderDetailEntity = getIntent().getExtras().getParcelable(Constants.CHAT_REQUEST_DATA);
                REQUEST_ID = String.valueOf(orderDetailEntity.getOrder_id());
                getSupportActionBar().setSubtitle("Request ID: "+orderDetailEntity.getDisplay_order_id());

                showDialog();
                new ProductController(this).getChatDetail(REQUEST_ID, ChatActivity.this);
            }
        }else if(getIntent().hasExtra(Utility.PUSH_NOTIFY))
        {
            if (getIntent().getExtras().getParcelable(Utility.PUSH_NOTIFY) != null) {

                NotifyEntity notifyEntity  = getIntent().getExtras().getParcelable(Utility.PUSH_NOTIFY);
                REQUEST_ID = String.valueOf(notifyEntity.getChatEntity().getReq_id());
                getSupportActionBar().setSubtitle("Request ID: "+notifyEntity.getChatEntity().getDisplay_req_id());
              //  addMessage(notifyEntity.getChatEntity());

                showDialog();
                new ProductController(this).getChatDetail(REQUEST_ID, ChatActivity.this);

            }
        }

    }


    private void initialize() {

        imgbtnSend = findViewById(R.id.imgbtnSend);
        etComment = findViewById(R.id.etComment);
        chatEntityList = new ArrayList<ChatEntity>();

        rvChat = (RecyclerView) findViewById(R.id.rvChat);
        rvChat.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        rvChat.setLayoutManager(layoutManager);


    }

    private void setListener() {
        imgbtnSend.setOnClickListener(this);
        etComment.addTextChangedListener(commentTextWatcher);
    }

    private void bindChatList() {
        mAdapter = new ChatAdapter(ChatActivity.this, chatEntityList);
        rvChat.setAdapter(mAdapter);
        scrollToBottom();

    }

    private void scrollToBottom() {
        rvChat.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void addMessage(ChatEntity chatEntity) {
        mAdapter.setChatData(chatEntity);
        scrollToBottom();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imgbtnSend) {
            if (etComment.getText().toString().trim().length() > 0) {

                ChatEntity chat = new ChatEntity("0", (REQUEST_ID), etComment.getText().toString(), getCurrentDate(), "C");
                addMessage(chat);
                new ProductController(this).saveChat(String.valueOf(REQUEST_ID), String.valueOf(loginEntity.getUser_id()), etComment.getText().toString(), this);
                etComment.setText("");
            }
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof ChatResponse) {
            if (response.getStatus_code() == 0) {

                chatEntityList = ((ChatResponse) response).getData();
                mAdapter.addChatList(chatEntityList);
                scrollToBottom();

            }
        } else if (response instanceof SaveChatResponse) {
            if (response.getStatus_code() == 0) {

                new ProductController(this).updateReadChat(REQUEST_ID, ChatActivity.this);

            }
        } else if (response instanceof UpdateChatResponse) {

        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandleMessageReceiver);
    }

    private String getCurrentDate() {
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
            String dateString = df.format(c.getTime());
            return dateString;
        } catch (Exception ex) {
            return "";
        }
    }



}
