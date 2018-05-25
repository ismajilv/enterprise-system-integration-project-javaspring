<template>
  <div id="app">
    <h2> RentIT </h2>
    <h2> Lists of purchase Order</h2>
    <table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">id</th>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Site Engineer</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center"></th>
            <th class="has-text-center"></th>
            <th class="has-text-center"></th>
        </tr>
    </thead>
    <tbody>
          <tr class="table-row-rentit" v-for="pending in allrequest" :key="pending._id" >
            <td class="has-text-center" id="name"> {{pending._id}}</td>
            <td id = "plantNameWE2" class="has-text-center">{{pending.plant.name}}</td>
            <td id = "plantStartDateWE2" class="has-text-center">{{pending.rentalPeriod.startDate}}</td>
            <td id = "plantEndDateWE2" class="has-text-center">{{pending.rentalPeriod.endDate}}</td>
            <td class="has-text-center"></td>
            <td id = "plantPriceWE2" class="has-text-center">{{pending.plant.price}}</td>
            <td><a v-on:click="markDispatched" class="button is-success is-outlined">Dispatch</a> </td>
            <td><a v-on:click="markDelivered" class="button is-success is-outlined">Deliver</a> </td>
            <td><a v-on:click="markReturned" class="button is-success is-outlined">Return</a> </td>
        </tr>
    </tbody>
</table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'PlantManagement',

  data: function(){
      return{
        allrequest: [],
        rejectedRequest:{},
      }
  },
   mounted:function(){
    this.pendingpurchaseOrder();
  },
  methods: {
      pendingpurchaseOrder: function(){
         axios.get("http://localhost:8090/api/sales/orders")
        .then(response => {
          this.allrequest = response.data._embedded.purchaseOrderDTOList;
          console.log("Response", this.allrequest);
        });
      },
      markDispatched: function(inputOrder){
           let i= document.getElementById("name").innerHTML
           let params = i + "/dispatch"
           console.log("Dispatch", i);
        axios.get("http://localhost:8080/api/requests/", params)
        .then(response => {
           this.$snackbar.open("You have dispatched this purchase order.");
         console.log("Dispatch", response.data._embedded);
        });
      },
      markDelivered: function(inputOrder){
           let i= document.getElementById("name").innerHTML
           let params = i + "/deliver"
           console.log("Deliver", i);
        axios.get("http://localhost:8080/api/requests/", params)
        .then(response => {
          this.$snackbar.open("You have delivered this purchase order.");
          console.log("Deliver", response);
        });
      },
      markReturned: function(inputOrder){
           let i= document.getElementById("name").innerHTML
           let params = i + "/return"
           console.log("Return", i);
        axios.get("http://localhost:8080/api/requests/", params)
        .then(response => {
          this.$snackbar.open("You have returned this purchase order.");
          console.log("Deliver", response);
        });
      },
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>