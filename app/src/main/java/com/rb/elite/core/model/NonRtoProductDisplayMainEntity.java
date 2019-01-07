package com.rb.elite.core.model;

import java.util.List;

public  class NonRtoProductDisplayMainEntity {
        /**
         * prod_id : 137
         * product : Beyond Life Financial Services - 25% Discount on Fees 1 yr subscription, 40% on Life Time Services.
         * price : 0
         * TAT : 
         * product_logo : /images/icons/Non-RTO/Beyond-Life-Financial-Services-25% Discount-on-fees-for-1-year-subscription-&-40%-on-Life-Time-subscription.png
         * isSpecial : 1
         * speciallist : [{"productid":137,"spName":"1 Year Subscription","spPrice":"1099"},{"productid":137,"spName":"3 Years Subscription","spPrice":"1199"},{"productid":137,"spName":"5 Years Subscription","spPrice":"1799"},{"productid":137,"spName":"Life Time Subscription","spPrice":"13999"}]
         */

        private String prod_id;
        private String product;
        private String price;
        private String TAT;
        private String product_logo;
        private String isSpecial;
        private List<NonRtoSpeciallistEntity> speciallist;

        public String getProd_id() {
            return prod_id;
        }

        public void setProd_id(String prod_id) {
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

        public String getIsSpecial() {
            return isSpecial;
        }

        public void setIsSpecial(String isSpecial) {
            this.isSpecial = isSpecial;
        }

        public List<NonRtoSpeciallistEntity> getSpeciallist() {
            return speciallist;
        }

        public void setSpeciallist(List<NonRtoSpeciallistEntity> speciallist) {
            this.speciallist = speciallist;
        }


    }