package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;
import com.pb.elite.core.model.NotificationEntity;

import java.util.List;

/**
 * Created by IN-RB on 20-11-2018.
 */

public class NotificationResponse extends APIResponse {


    private List<NotificationEntity> Data;

    public List<NotificationEntity> getData() {
        return Data;
    }

    public void setData(List<NotificationEntity> Data) {
        this.Data = Data;
    }


}
