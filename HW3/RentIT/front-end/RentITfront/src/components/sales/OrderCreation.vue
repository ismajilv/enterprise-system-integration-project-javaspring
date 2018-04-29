<template>
    <b-tabs type="is-toggle" expanded v-model="activeTab">
        <b-tab-item label="Query catalog">
          <catalog-query @submitCatalogQuery="handleCatalogQuery"></catalog-query>
        </b-tab-item>
        <b-tab-item label="Select plant">
          <query-result :plants= "plants"></query-result>
        </b-tab-item>
        <b-tab-item label="Review order">
        </b-tab-item>
    </b-tabs>
</template>

<script>
import CatalogQuery from "./CatalogQuery.vue"
import QueryResult from "./QueryResult.vue"

import axios from 'axios'

export default {
  name: "OrderCreation",
  components: {
    CatalogQuery,
    QueryResult
  },
  data: function(){
    return {
      activeTab: 0,
      plants: [{id: 1, name: "Bike", description: "Nice and shinny", price: 100}]
    }
  },
  methods:{
    handleCatalogQuery: function(query){
      if(query.name && query.startDate && query.endDate) {
        let params = {
          name: query.name,
          startDate: query.startDate,
          endDate: query.endDate
        }
        axios.get("http://localhost:8090/api/sales/plants", { params: params})
        .then(respone => {
          console.log(query);
          this.activeTab = 1;
          this.plants = respone.data;
        })
      }
    }
}
}
</script>

<style>

</style>
