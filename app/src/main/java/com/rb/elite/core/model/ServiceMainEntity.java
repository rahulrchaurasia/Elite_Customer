package com.rb.elite.core.model;

import java.util.List;

public class ServiceMainEntity {

    private List<RTOServiceEntity> RTO;

    private List<RTOServiceEntity> NONRTO;

    public List<RTOServiceEntity> getRTO() {
        return RTO;
    }

    public void setRTO(List<RTOServiceEntity> RTO) {
        this.RTO = RTO;
    }

    public List<RTOServiceEntity> getNONRTO() {
        return NONRTO;
    }

    public void setNONRTO(List<RTOServiceEntity> NONRTO) {
        this.NONRTO = NONRTO;
    }


}