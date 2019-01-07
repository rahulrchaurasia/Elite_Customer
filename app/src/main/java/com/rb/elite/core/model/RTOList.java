package com.rb.elite.core.model;

import java.util.List;

public  class RTOList {

        private List<CityEntity> cities;
        private List<RTOEntity> rtolist;

        public List<CityEntity> getCities() {
            return cities;
        }

        public void setCities(List<CityEntity> cities) {
            this.cities = cities;
        }

        public List<RTOEntity> getRtolist() {
            return rtolist;
        }

        public void setRtolist(List<RTOEntity> rtolist) {
            this.rtolist = rtolist;
        }

    }