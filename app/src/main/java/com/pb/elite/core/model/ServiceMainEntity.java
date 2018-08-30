package com.pb.elite.core.model;

import java.util.List;

public  class ServiceMainEntity {
        private List<RTOServiceEntity> RTO;
        private List<NONRTOServiceEntity> NONRTO;

        public List<RTOServiceEntity> getRTO() {
            return RTO;
        }

        public void setRTO(List<RTOServiceEntity> RTO) {
            this.RTO = RTO;
        }

        public List<NONRTOServiceEntity> getNONRTO() {
            return NONRTO;
        }

        public void setNONRTO(List<NONRTOServiceEntity> NONRTO) {
            this.NONRTO = NONRTO;
        }


    }