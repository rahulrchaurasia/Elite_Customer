package com.pb.elite.core.response;

import com.pb.elite.core.APIResponse;

import java.util.List;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class MotorInsuranceListResponse extends APIResponse {


    private List<InsuranceCompanyEntity> Data;

    public List<InsuranceCompanyEntity> getData() {
        return Data;
    }

    public void setData(List<InsuranceCompanyEntity> Data) {
        this.Data = Data;
    }

    public static class InsuranceCompanyEntity {
        /**
         * Motor_Insurance_Id : 1
         * Motor_Insurance_Name : THE ORIENTAL INSURANCE COMPANY LIMITED
         */

        private int Motor_Insurance_Id;
        private String Motor_Insurance_Name;

        public int getMotor_Insurance_Id() {
            return Motor_Insurance_Id;
        }

        public void setMotor_Insurance_Id(int Motor_Insurance_Id) {
            this.Motor_Insurance_Id = Motor_Insurance_Id;
        }

        public String getMotor_Insurance_Name() {
            return Motor_Insurance_Name;
        }

        public void setMotor_Insurance_Name(String Motor_Insurance_Name) {
            this.Motor_Insurance_Name = Motor_Insurance_Name;
        }
    }
}
