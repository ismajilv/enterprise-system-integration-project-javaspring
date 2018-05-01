<template>
   <div>
      <div class="order">
      <b-tabs type="is-toggle" expanded v-model="activeTab">
        <b-tab-item label="Query catalog">
          <catalog-query @submitCatalogQuery="handleCatalogQuery"></catalog-query>
        </b-tab-item>
        <b-tab-item label="Select plant">
          <query-result :plants= "plants" @selectPlant="handlePlantSelection"></query-result>
        </b-tab-item>
       <b-tab-item label="Review order">
            <order-data :order="order" @submitPurchaseOrder="handlePOCreation"></order-data>
        </b-tab-item>
    </b-tabs>
    </div>
    <ul v-for="plant in plants" :key="plant.id">
      <li> Plant name: {{plant.name}}</li>
      <li> Price per day: {{plant.pricePerDay.total}}</li>
    </ul>

     <ul v-for="site in sites" :key="site.id">
      <li> Site address: {{site.address}}</li>
    </ul>

     <ul v-for="supplier in suppliers" :key="supplier.id">
      <li> Supplier's name: {{supplier.name}}</li>
      <li> Supplier's id: {{supplier._id}}</li>
      <button v-on:click="chooseSupplier"> Choose Supplier </button>
    </ul>

    <select v-model="select.supplierName" v-bind:id="order.id">
      <option disabled value="">Choose a supplier</option>
      <option v-for="supplier in suppliers" :key="supplier.id">{{supplier.name}} {{supplier._id}}</option>
    </select>
    <select v-model="order.site">
      <option disabled value="">Choose a site</option>
      <option v-for="site in sites" :key="site.id" >{{site.address}}</option>
    </select>

  <button v-on:click="chooseSupplier"> Plant hire Request </button>
  </div>
</template>

<script>
// import CatalogQuery from "./CatalogQuery.vue"
// import QueryResult from "./QueryResult.vue"

import axios from 'axios'

export default {
  name: "OrderCreation",
  components: {
    // CatalogQuery,
    // QueryResult
  },
  data: function(){
    return {
      plants: [],
      sites: [],
      suppliers: [],
      select: {
                supplierName: '' ,
                site: '',
      }
    }
  },
  mounted: function () {
        this.supplierlists();
        this.sitelists()
        },
  methods: {
    supplierlists: function() {
     axios.get("http://localhost:8080/api/suppliers")
      .then(response => {
          this.suppliers = response.data;
      })
    },
    plantslists: function(){
         axios.get("http://localhost:8080/api/plants")
        .then(response => {
          this.plants = response.data;
        })
    },
    sitelists: function() {
      axios.get("http://localhost:8080/api/sites")
      .then(response => {
          this.sites = response.data;
      })
    },
    selectPlant:function() {
      let sitesinfo = this.sites;
      let suppliersinfo = this.suppliers;
      let plantsinfo = this.plants;
      console.log("Sites", sitesinfo);
      console.log("Plants", plantsinfo);
      console.log("Suppliers", suppliersinfo);
      let params = {
       "constructionSiteId": 2,
       "supplierId": 4,
       "plantHref": "http://ramirent.ee:9550/api/plants/2",
       "rentalPeriod" : {
        "startDate" : "2018-04-29",
        "endDate" : "2018-05-03"
      }
    }
      console.log("Params", params)
      axios.post("http://localhost:8080/api/requests"+ {params: params})
      .then(response => {
        console.log("My post request", response);
      })
    },
    chooseSupplier: function(){
      console.log("Choose supplier", this.select.supplierName);
    }
  }
}
</script>

<style>

</style>
