<template>
  <div id="app">
    <h2> RentIT </h2>
    <h2> Lists of purchase Order</h2>
    <table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Status</th>
            <th class="has-text-center">Actions</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
          <tr class="table-row-rentit" v-for="(pending, index) in allrequest" :key="pending._id" >
            <td v-show="seen" class="has-text-center" id="app"> {{pending._id}}</td>
            <td id = "plantNameWE2" class="has-text-center">{{pending.plant.name}}</td>
            <td id = "plantStartDateWE2" class="has-text-center">{{pending.rentalPeriod.startDate}}</td>
            <td id = "plantEndDateWE2" class="has-text-center">{{pending.rentalPeriod.endDate}}</td>
            <td id = "plantPriceWE2" class="has-text-center">{{pending.plant.price}}</td>
            <td id = "plantPriceWE2" class="has-text-center">{{pending.status}}</td>
            <td><a v-on:click="accept(index)" class="button is-success is-outlined">Accept</a> </td>
            <td><a v-on:click="reject(index)" class="button is-danger is-outlined">Reject</a> </td>
        </tr>
    </tbody>
</table>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'PurchaseOrderApproval',

  data: function(){
      return{
        allrequest: [],
        rejectedRequest:{},
        orderInfo: {},
        acceptStatus: {},
        seen: false
      }
  },
   mounted:function(){
    this.pendingpurchaseOrder();
  },
  methods: {
      pendingpurchaseOrder: function(){
         axios.get("http://localhost:8090/api/orders")
        .then(response => {
          this.allrequest = response.data._embedded.purchaseOrderDTOList;
          console.log("Response", this.allrequest);
        });
      },
      // orderDetails: function(inputOrder){
      //     var orderInfo;
      //     this.orderInfo = this.acceptStatus;
      //      console.log("View Order Information", orderInfo);
      //      return orderInfo;
      // },const poId = this.allrequest[inputOrder]._id;
      accept: function(inputOrder){
           let poId= this.allrequest[inputOrder]._id;
              console.log("Input id", poId);
           let piiId = this.allrequest[inputOrder].plant._id;
           console.log("piiId plant ID", piiId);
        axios.post("http://localhost:8090/api/orders/"+ poId +"/accept?"+ "piiId="+ piiId )
        .then(response => {
           this.$snackbar.open("You have accepted this plant request.");
           var acceptStatus;
           acceptStatus = response.data._embedded;
           console.log("Accept Status", acceptStatus)
           return acceptStatus;
        });
      },
      reject: function(inputOrder){
           let i= this.allrequest[inputOrder]._id;
           let params = i + "/reject"
           console.log("Reject", i);
        axios.post("http://localhost:8090/api/orders/" + params)
        .then(response => {
          this.$snackbar.open("You have rejected this plant request.");
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
