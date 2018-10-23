package com.pb.elite.core.model;

public  class PolicyEntity {
        /**
         * PolicyStatusNo : 1
         * PolicyNumber :
         * ProductCode :
         * RiskEndDate :
         * RiskStartDate :
         * InsuredName :
         * VehicleNumber :
         * Make :
         * Model :
         * PolicyStatus : InActive
         * ResponseStatus : Policy is not eligible for ELITE services
         */

        private int PolicyStatusNo;
        private String PolicyNumber;
        private String ProductCode;
        private String RiskEndDate;
        private String RiskStartDate;
        private String InsuredName;
        private String VehicleNumber;
        private String Make;
        private String Model;
        private String PolicyStatus;
        private String ResponseStatus;

        public int getPolicyStatusNo() {
            return PolicyStatusNo;
        }

        public void setPolicyStatusNo(int PolicyStatusNo) {
            this.PolicyStatusNo = PolicyStatusNo;
        }

        public String getPolicyNumber() {
            return PolicyNumber;
        }

        public void setPolicyNumber(String PolicyNumber) {
            this.PolicyNumber = PolicyNumber;
        }

        public String getProductCode() {
            return ProductCode;
        }

        public void setProductCode(String ProductCode) {
            this.ProductCode = ProductCode;
        }

        public String getRiskEndDate() {
            return RiskEndDate;
        }

        public void setRiskEndDate(String RiskEndDate) {
            this.RiskEndDate = RiskEndDate;
        }

        public String getRiskStartDate() {
            return RiskStartDate;
        }

        public void setRiskStartDate(String RiskStartDate) {
            this.RiskStartDate = RiskStartDate;
        }

        public String getInsuredName() {
            return InsuredName;
        }

        public void setInsuredName(String InsuredName) {
            this.InsuredName = InsuredName;
        }

        public String getVehicleNumber() {
            return VehicleNumber;
        }

        public void setVehicleNumber(String VehicleNumber) {
            this.VehicleNumber = VehicleNumber;
        }

        public String getMake() {
            return Make;
        }

        public void setMake(String Make) {
            this.Make = Make;
        }

        public String getModel() {
            return Model;
        }

        public void setModel(String Model) {
            this.Model = Model;
        }

        public String getPolicyStatus() {
            return PolicyStatus;
        }

        public void setPolicyStatus(String PolicyStatus) {
            this.PolicyStatus = PolicyStatus;
        }

        public String getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(String ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
        }
    }