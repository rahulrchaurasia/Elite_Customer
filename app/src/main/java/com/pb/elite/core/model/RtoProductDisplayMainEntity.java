package com.pb.elite.core.model;

import java.util.List;

public  class RtoProductDisplayMainEntity {
        /**
         * cityname : Mumbai
         * city_id : 1276
         * prod_id : 73
         * product : RC Extract
         * price : 350
         * TAT : 4 days
         * product_logo :  834BAJAJ.jpg
         * rtolist : [{"rto_id":1,"rto_location":"Mumbai (Central)- Location- Tardeo","series_no":"MH01","city_id":1276},{"rto_id":2,"rto_location":"Mumbai, west(West), Andheri (Mumbai Western Suburbs \u2013 Location Andheri West)","series_no":"MH02","city_id":1276},{"rto_id":3,"rto_location":"Mumbai, (East), Wadala, Vikroli, Ghatkopar, Mulund (Mumbai Eastern Suburbs \u2013 Location Anik)","series_no":"MH03","city_id":1276},{"rto_id":4,"rto_location":"Thane city & Bhiwandi & rural","series_no":"MH04","city_id":1276},{"rto_id":5,"rto_location":"Kalyan Titwala, (District \u2013 Thane) / Dombivli / Ulhasnagar / Ambernath / Badlapur","series_no":"MH05","city_id":1276},{"rto_id":6,"rto_location":"Pen, (District \u2013 Raigad)","series_no":"MH06","city_id":1276},{"rto_id":7,"rto_location":"Navi Mumbai, (District \u2013 Thane)","series_no":"MH43","city_id":1276},{"rto_id":8,"rto_location":"Panvel, (District \u2013 Raigad)","series_no":"MH46","city_id":1276},{"rto_id":9,"rto_location":"Malwani, (District \u2013 Mumbai Western Suburbs)","series_no":"MH47","city_id":1276},{"rto_id":10,"rto_location":"Vasai, (District \u2013 Thane)","series_no":"MH48","city_id":1276}]
         */

        private String cityname;
        private int city_id;
        private int prod_id;
        private String product;
        private String price;
        private String TAT;
        private String product_logo;
        private List<RtoProductEntity> rtolist;

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

        public int getProd_id() {
            return prod_id;
        }

        public void setProd_id(int prod_id) {
            this.prod_id = prod_id;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTAT() {
            return TAT;
        }

        public void setTAT(String TAT) {
            this.TAT = TAT;
        }

        public String getProduct_logo() {
            return product_logo;
        }

        public void setProduct_logo(String product_logo) {
            this.product_logo = product_logo;
        }

        public List<RtoProductEntity> getRtolist() {
            return rtolist;
        }

        public void setRtolist(List<RtoProductEntity> rtolist) {
            this.rtolist = rtolist;
        }


    }