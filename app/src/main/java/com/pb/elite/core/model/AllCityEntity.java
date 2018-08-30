package com.pb.elite.core.model;

import io.realm.RealmObject;

public  class AllCityEntity  extends RealmObject  {
            /**
             * city_id : 1
             * cityname : Ranga Reddy
             */

            private int city_id;
            private String cityname;

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }
        }