package com.rb.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rajeev Ranjan on 10/07/2019.
 */
public class ChatEntity implements  Parcelable  {


    private String chat_id;
    private String req_id;
    String display_req_id;
    private String message;
    private String created_date_time;
    private String type;


    protected ChatEntity(Parcel in) {
        chat_id = in.readString();
        req_id = in.readString();
        display_req_id = in.readString();
        message = in.readString();
        created_date_time = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chat_id);
        dest.writeString(req_id);
        dest.writeString(display_req_id);
        dest.writeString(message);
        dest.writeString(created_date_time);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatEntity> CREATOR = new Creator<ChatEntity>() {
        @Override
        public ChatEntity createFromParcel(Parcel in) {
            return new ChatEntity(in);
        }

        @Override
        public ChatEntity[] newArray(int size) {
            return new ChatEntity[size];
        }
    };

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getDisplay_req_id() {
        return display_req_id;
    }

    public void setDisplay_req_id(String display_req_id) {
        this.display_req_id = display_req_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_date_time() {
        return created_date_time;
    }

    public void setCreated_date_time(String created_date_time) {
        this.created_date_time = created_date_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public ChatEntity() {
    }

    public ChatEntity(String chat_id, String req_id, String message, String created_date_time, String type) {
        this.chat_id = chat_id;
        this.req_id = req_id;
        this.message = message;
        this.created_date_time = created_date_time;
        this.type = type;
    }


}
