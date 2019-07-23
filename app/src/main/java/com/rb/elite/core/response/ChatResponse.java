package com.rb.elite.core.response;

import com.google.gson.annotations.SerializedName;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.ChatEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 15/07/2019.
 */
public class ChatResponse extends APIResponse {


    private List<ChatEntity> Data;

    public List<ChatEntity> getData() {
        return Data;
    }

    public void setData(List<ChatEntity> Data) {
        this.Data = Data;
    }


}
