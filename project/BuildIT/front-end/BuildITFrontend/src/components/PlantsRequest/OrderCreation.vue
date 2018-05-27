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
         <hire-request :orderStatus= "orderStatus">
         </hire-request>
        </b-tab-item>
    </b-tabs>
    </div>
</template>

<script>
import CatalogQuery from "./CatalogQuery.vue";
import QueryResult from "./QueryResult.vue";
import OrderData from "./OrderData.vue";
import HireRequest from "./HireRequest.vue";

import axios from 'axios';
import moment from "moment";

export default {
  name: "OrderCreation",
  components: {
    CatalogQuery,
    QueryResult,
    OrderData,
    HireRequest
  },
  data: function(){
    return {
      activeTab: 0,
      plants: [],
      orderStatus: {
        plant: {name: null},
        rentalPeriod: {
          startDate: null,
            endDate: null
        },
        rentalCost: null,
          status: null
      },
      order: {
          id: 1,
          plant: {},
          rentalPeriod: {},
        moreInfo: {
            name: '',
            suppliersid: 0 ,
            siteid: 0,
            index: 0
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
        };
        axios.get("http://localhost:8080/api/plants", { params: params})
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
     handlePOCreation: function() {
         let obj = {
           "constructionSiteId": this.order.siteid,
           "supplierId": this.order.suppliersid,
           "plantHref":  this.order.plant._links.self.href,
           "rentalPeriod" : {
             "startDate" : this.order.rentalPeriod.startDate,
             "endDate" : this.order.rentalPeriod.endDate
            }
        };
        console.log("Plant submission before", obj);
              axios.get("http://localhost:8080/api/requests", obj)
                .then(response => {
                    this.$snackbar.open("Plant hire request. Waiting for confirmation from works engineer.");
                 this.orderStatus = response.data;
                   console.log("Plant submission after", this.orderStatus);
                }).catch(error => {
                    this.$snackbar.open({
                        type: 'is-danger',
                        message: "Something went wrong with purchase order submission."
                    });
                });
          this.activeTab = 3;
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
