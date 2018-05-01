<template>
   <div>
    <ul v-for="plant in plants" :key="plant.id">
      <li> Plant name: {{plant.name}}</li>
      <li> Price per day: {{plant.pricePerDay.total}}</li>
    </ul>

    <button v-on:click="supplierlists"> Plant hire Request </button>
  </div>
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
      plants: []
    }
  },
  mounted: function () {
             this.plantlists();
        },
  methods: {
    plantlists: function(){
        axios.get("http://localhost:8080/api/plants")
        .then(response => {
          console.log("Plants List", response.data);
          this.plants = response.data;
        })
      },
      supplierlists: function() {
      axios.get("http://localhost:8080/api/sites")
      .then(response => {
       console.log("Sites", response.data);
          this.sites = response.data;
      })
    }
  }
}
</script>

<style>

</style>
