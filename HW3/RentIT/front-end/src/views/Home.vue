<template>
  <div class="home">
   <b-tabs type="is-toggle" expanded v-model="activeTab">
        <b-tab-item label="Query catalog">
            <catalog-query @submitCatalogQuery="handleCatalogQuery"></catalog-query>
        </b-tab-item>
        <b-tab-item label="Select plant">
            <query-result :plants="plants" @selectPlant="handlePlantSelection"></query-result>
        </b-tab-item>
        <b-tab-item label="Review order">
            <order-data :order="order" @submitPurchaseOrder="handlePOCreation"></order-data>
        </b-tab-item>
    </b-tabs>
  </div>
</template>

<script>
// @ is an alias to /src
import CatalogQuery from "../components/sales/CatalogQuery.vue";
import QueryResult from "../components/sales/QueryResult.vue";
import OrderData from "../components/sales/OrderData.vue";

import axios from "axios";
import moment from "moment";

export default {
  name: 'home',
   components: {
        CatalogQuery,
        QueryResult,
        OrderData
    },
  data: function() {
    return {
      activeTab: 0,
            plants: [],
            order: {
                plant: {},
                rentalPeriod: {}
          }
    }
  },
  methods: {
    submit: function() {
            this.$emit("submitCatalogQuery", this.query);
        },
        handleCatalogQuery: function(query) {
            if (query.name && query.startDate && query.endDate) {
                let params = {
                    name: query.name,
                    startDate: moment(String(query.startDate)).format("YYYY-MM-DD"),
                    endDate: moment(String(query.endDate)).format("YYYY-MM-DD")
                };
                axios.get("http://localhost:8090/api/sales/plants?name=Exc&startDate=2016-03-14&endDate=2016-03-25", {params: params})
                    .then(response => {
                        console.log(response);
                        this.order.rentalPeriod.startDate = params.startDate;
                        this.order.rentalPeriod.endDate = params.endDate;
                        this.plants = response.data._embedded.plants;
                        this.activeTab = 1;
                    });
            }
        },
        handlePlantSelection: function(plant) {
            this.order.plant = plant;
            this.activeTab = 2;
        },
        handlePOCreation: function() {
            console.log("ORDER", this.order);
            axios.post("http://localhost:8090/api/sales/orders", this.order)
                .then(response => {
                    this.$snackbar.open("Purchase order submitted. Waiting for confirmation.");
                }).catch(error => {
                    this.$snackbar.open({
                        type: 'is-danger',
                        message: "Something went wrong with purchase order submition."
                    });
                });
        }
    }
}
</script>
