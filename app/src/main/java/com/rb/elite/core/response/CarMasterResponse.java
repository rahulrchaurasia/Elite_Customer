package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.CarMasterEntity;

import java.util.List;



/**
 * Created by Rajeev Ranjan on 23/01/2018.
 */

public class CarMasterResponse extends APIResponse {

    private List<CarMasterEntity> MasterData;

    public List<CarMasterEntity> getMasterData() {
        return MasterData;
    }

    public void setMasterData(List<CarMasterEntity> MasterData) {
        this.MasterData = MasterData;
    }
}
