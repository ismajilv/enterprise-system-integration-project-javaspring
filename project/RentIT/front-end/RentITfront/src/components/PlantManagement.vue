<template>
  <div id="app">
    <h2> RentIT </h2>
    <h2> Lists of purchase Order</h2>
    <table class="table table-dark">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Status</th>
            <th class="has-text-center"></th>
            <th class="has-text-center"></th>
            <th class="has-text-center"></th>
            <th class="has-text-center"></th>
        </tr>
    </thead>
    <tbody>
          <tr class="table-row-rentit" v-for="(pending, index)  in allrequest" :key="pending._id" >
            <td v-show="display" class="has-text-center" id="name"> {{pending._id}}</td>
            <td id = "plantNameWE2" class="has-text-center">{{pending.plant.name}}</td>
            <td id = "plantStartDateWE2" class="has-text-center">{{pending.rentalPeriod.startDate}}</td>
            <td id = "plantEndDateWE2" class="has-text-center">{{pending.rentalPeriod.endDate}}</td>
            <td>{{pending.status}}</td>
            <td><button v-on:click="markDispatched(index)" :disabled="pending.status != 'PENDING'" class="button is-success is-outlined">Dispatch</button> </td>
            <td><a v-on:click="markDelivered(index)" :disabled="pending.status != 'DISPATCHED'" class="button is-success is-outlined">Deliver</a> </td>
            <td><button v-on:click="markRejectedByCustomer(index)" :disabled="pending.status != 'DISPATCHED'" class="button is-danger is-outlined">Rejected by customer</button> </td>
            <td><a v-on:click="markReturned(index)" :disabled="pending.status != 'DELIVERED'" class="button is-success is-outlined">Return</a> </td>
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
        display: false
      }
  },
   mounted:function(){
    this.findAllPOs();
  },
  methods: {
      findAllPOs: function(){
         axios.get("http://localhost:8090/api/orders?status=ALL")
        .then(response => {
          console.log("Response",response);
          if(response.data != null && response.data._embedded != null){
            this.allrequest = response.data._embedded.purchaseOrderDTOList;
          }
        });
      },
      markDispatched: function(inputOrder){
         const poId = this.allrequest[inputOrder]._id;
         console.log("Dispatch", poId);
         axios.post("http://localhost:8090/api/orders/"+poId+"/dispatch")
         .then(response => {
          this.$snackbar.open("You have dispatched this purchase order.");
          console.log("Response " +response);
          this.$set(this.allrequest, inputOrder, response.data);
        });
      },
      markRejectedByCustomer: function(inputOrder){
        const poId = this.allrequest[inputOrder]._id;
        console.log("Rejected by customer ", poId);
        axios.post("http://localhost:8090/api/orders/"+poId+"/customer_reject")
          .then(response => {
            this.$snackbar.open("This PO has been succesfully rejected by customer.");
            console.log("Response " +response);
            this.$set(this.allrequest, inputOrder, response.data);
          });
      },
      markDelivered: function(inputOrder){
        const poId = this.allrequest[inputOrder]._id;
        console.log("Delivered to customer ", poId);
        axios.post("http://localhost:8090/api/orders/"+poId+"/deliver")
          .then(response => {
            this.$snackbar.open("This PO has been succesfully delivered.");
            console.log("Response " +response);
            this.$set(this.allrequest, inputOrder, response.data);
          });
      },
      markReturned: function(inputOrder){
        const poId = this.allrequest[inputOrder]._id;
        console.log("Returned by customer ", poId);
        axios.post("http://localhost:8090/api/orders/"+poId+"/return")
          .then(response => {
            this.$snackbar.open("This PO has been succesfully returned.");
            console.log("Response " +response);
            this.$set(this.allrequest, inputOrder, response.data);
          });
      }
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
