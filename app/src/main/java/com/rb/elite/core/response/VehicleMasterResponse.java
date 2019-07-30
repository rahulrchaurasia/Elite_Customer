package com.rb.elite.core.response;

import com.rb.elite.core.APIResponse;
import com.rb.elite.core.model.VehicleMasterEntity;

/**
 * Created by Nilesh Birhade on 15-11-2018.
 */

public class VehicleMasterResponse extends APIResponse{

    private MasterData Data;

    public MasterData getData() {
        return Data;
    }

    public void setData(MasterData Data) {
        this.Data = Data;
    }

    public static class MasterData {


        private VehicleMasterEntity VehicleMasterResult;

        public VehicleMasterEntity getVehicleMasterResult() {
            return VehicleMasterResult;
        }

        public void setVehicleMasterResult(VehicleMasterEntity VehicleMasterResult) {
            this.VehicleMasterResult = VehicleMasterResult;
        }


    }






}
