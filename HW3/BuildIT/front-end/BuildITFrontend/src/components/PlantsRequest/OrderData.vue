<template>
<div>
   <b-field label="Choose supplier">
            <b-select  v-model="order.supplierName"
            placeholder="Choose supplier" expanded>
                <option v-for="supplier in suppliers" :key="supplier.id">
                  {{supplier.name}}</option>
            </b-select>
   </b-field>

   <b-field label="Choose construction site">
            <b-select  v-model="order.constructionSite"
            placeholder="Choose supplier" expanded>
                <option v-for="site in sites" :key="site.id">
                  {{site.address}}</option>
            </b-select>
   </b-field>

<button class="button is-primary" v-on:click="submit">Create Purchase Order</button>
<table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Subtotal</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr v-if="!!order.plant">
            <td>{{order.plant.name}}</td>
            <td>{{order.rentalPeriod.startDate|formatDate}}</td>
            <td>{{order.rentalPeriod.endDate|formatDate}}</td>
            <td class="has-text-right">{{order.plant.price}}</td>
            <td class="has-text-right">{{order.total}}</td>
            <a class="button is-success is-outlined">Update</a>
            <a class="button is-danger is-outlined">Remove</a>
        </tr>
    </tbody>
</table>
</div>
</template>

<script>
import axios from 'axios';

export default {
    name: "OrderData",
    props: ["order"],
    data: function(){
        return {
        plants: [],
        sites: [],
        suppliers: [],
    }
  },
  mounted: function(){
    this.supplierlists();
    this.sitelists();
  },
    methods: {
      submit: function() {
            this.$emit("submitPurchaseOrder");
      },
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
    }
    }
}
</script>
