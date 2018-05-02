<template>
    <div class="order">
      <b-tabs type="is-toggle" expanded v-model="activeTab">
        <b-tab-item label="Query catalog">
          <catalog-query @submitCatalogQuery="handleCatalogQuery"></catalog-query>
        </b-tab-item>
        <b-tab-item label="Select plant">
          <query-result :plants= "plants" @selectPlant="handlePlantSelection"></query-result>
        </b-tab-item>
        <b-tab-item label="Plant hire">
          <order-data :order="order" @submitPurchaseOrder="handlePOCreation">
          </order-data>
        </b-tab-item>
       <b-tab-item label="Review order">
         <hirerequest> </hirerequest>
        </b-tab-item>
    </b-tabs>
    </div>
</template>

<script>
import CatalogQuery from "./CatalogQuery.vue";
import QueryResult from "./QueryResult.vue";
import OrderData from "./OrderData.vue";
import Hirerequest from "./Hirerequest.vue";

import axios from 'axios';
import moment from "moment";

export default {
  name: "OrderCreation",
  components: {
    CatalogQuery,
    QueryResult,
    OrderData,
    Hirerequest
  },
  data: function(){
    return {
      activeTab: 0,
      plants: [],
      order: {
          id: 0,
          plant: {},
          rentalPeriod: {},
        moreInfo: {
            supplierName: '' ,
            constructionSite: "",
        }
      },
    }
  },
  methods:{
    handleCatalogQuery: function(query){
      if(query.name && query.startDate && query.endDate) {
        let params = {
          name: query.name,
          startDate: moment(String(query.startDate)).format("YYYY-MM-DD"),
          endDate: moment(String(query.endDate)).format("YYYY-MM-DD")
        }
        axios.get("http://localhost:8090/api/sales/plants", { params: params})
        .then(response => {
          console.log(response);
           this.order.rentalPeriod.startDate = params.startDate;
           this.order.rentalPeriod.endDate = params.endDate;
           this.plants = response.data;
          this.activeTab = 1;
        });
      }
    },
     handlePlantSelection: function(plant) {
            this.order.plant = plant;
            this.activeTab = 2;
        },
     handleStatusOrder: function(plant) {
            this.order.plant = plant;
            this.activeTab = 2;
        },
     handlePOCreation: function() {

         let obj = {
          //  orders: this.order,
          //  orderid: this.order.id++
          "constructionSiteId": 2,
          "supplierId": 4,
          "plantHref": "http://localhost:8090/api/sales/plants/3",
          "rentalPeriod" : {
          "startDate" : "2018-05-25",
           "endDate" : "2018-05-30"
    }
        };
        //console.log("Plant submission before", obj);
              axios.post("http://localhost:8080/api/requests", obj)
                .then(response => {
                    this.$snackbar.open("Purchase order submitted. Waiting for confirmation.");
                }).catch(error => {
                    this.$snackbar.open({
                        type: 'is-danger',
                        message: "Something went wrong with purchase order submition."
                    });
                });
           // console.log("Plant submission after", obj);
     },
}
}
</script>

<style>
.order{
  width: 80%;
  margin-left: 7%;
  margin-bottom: 50%;
}
</style>
