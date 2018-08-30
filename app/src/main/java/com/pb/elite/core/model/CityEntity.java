package com.pb.elite.core.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityEntity  extends RealmObject {
            /**
             * srno : 1
             * cityname : Mumbai
             * city_id : 1276
             */
            @PrimaryKey
            private int srno;
            private String cityname;
            private int city_id;

            public int getSrno() {
                return srno;
            }

            public void setSrno(int srno) {
                this.srno = srno;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }
        }