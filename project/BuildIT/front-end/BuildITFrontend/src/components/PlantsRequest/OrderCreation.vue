<template>
    <div class="order">
      <b-tabs type="is-toggle" expanded v-model="activeTab">
        <b-tab-item label="Query catalog">
          <catalog-query @submitCatalogQuery="handleCatalogQuery"></catalog-query>
        </b-tab-item>
        <b-tab-item label="Select plant">
          <query-result :plants= "plants" @selectPlant="handlePlantSelection"></query-result>
        </b-tab-item>
        <b-tab-item label="Submit order">
          <submit-order :order="order" @submitOrder="handlePHRsubmission"></submit-order>
        </b-tab-item>
       <b-tab-item label="Review order">
         <order-review :orderStatus= "order"></order-review>
        </b-tab-item>
    </b-tabs>
    </div>
</template>

<script>
import CatalogQuery from "./CatalogQuery.vue";
import QueryResult from "./QueryResult.vue";
import SubmitOrder from "./SubmitOrder.vue";
import OrderReview from "./OrderReview.vue";

import axios from 'axios';
import moment from "moment";

export default {
  name: "OrderCreation",
  components: {
    CatalogQuery,
    QueryResult,
    SubmitOrder,
    OrderReview
  },
  data: function(){
    return {
      activeTab: 0,
      plants: [],
      order: {
        plant: {
          name: null,
          supplier: {name: null},
          href: null
        },
        rentalPeriod: {
          startDate: null,
            endDate: null
        },
        rentalCost: null,
        status: null,
        site: {id: null}
      }
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
    handlePHRsubmission: function() {
         const params = {
           "constructionSiteId": this.order.site.id,
           "supplierId": 1,
           "plantHref": this.order.plant.href,
           "rentalPeriod" : {
             "startDate" : this.order.rentalPeriod.startDate,
             "endDate" : this.order.rentalPeriod.endDate
           }
        };
        console.log('[Create PHR obj]', params);
        axios.post("http://localhost:8080/api/requests", params)
          .then(response => {
              this.$snackbar.open("Plant hire request. Waiting for confirmation from works engineer.");
              this.order = response.data;
              console.log('[Create PHR]', response);
          }).catch(error => {
              console.log('[Create PHR error]', error);
              this.$snackbar.open({
                  type: 'is-danger',
                  message: "Something went wrong with purchase order submission."
              });
          });
        this.activeTab = 3;
    }
  }
}
</script>

<style>  .order{
    width: 80%;
    margin-left: 7%;
    margin-bottom: 50%;
  }
</style>
