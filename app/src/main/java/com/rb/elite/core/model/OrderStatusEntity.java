package com.rb.elite.core.model;


public  class OrderStatusEntity  {
            /**
             * id : 1
             * order_status : Pending
             */

            private int id;
            private String order_status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }
        }