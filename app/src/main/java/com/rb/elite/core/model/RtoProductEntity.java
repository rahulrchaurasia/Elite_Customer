package com.rb.elite.core.model;

public  class RtoProductEntity {
            /**
             * rto_id : 1
             * rto_location : Mumbai (Central)- Location- Tardeo
             * series_no : MH01
             * city_id : 1276
             */

            private int rto_id;
            private String rto_location;
            private String series_no;
            private int city_id;

            public int getRto_id() {
                return rto_id;
            }

            public void setRto_id(int rto_id) {
                this.rto_id = rto_id;
            }

            public String getRto_location() {
                return rto_location;
            }

            public void setRto_location(String rto_location) {
                this.rto_location = rto_location;
            }

            public String getSeries_no() {
                return series_no;
            }

            public void setSeries_no(String series_no) {
                this.series_no = series_no;
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }
        }