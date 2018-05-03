<template>
  <div>
    <h2> Lists of plants</h2>
  <table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Requested Plant</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Total Price</th>
            <th class="has-text-center">Current Order Status</th>
            <th class="has-text-center">Updated Order Status</th>
        </tr>
    </thead>
    <tbody>
       <tr v-show="orderStatus">
            <td>{{orderStatus.plant.name}}</td>
            <td>{{orderStatus.rentalPeriod.startDate}}</td>
            <td>{{orderStatus.rentalPeriod.endDate}}</td>
            <td>{{orderStatus.rentalCost.total}}</td>
            <td>{{orderStatus.status}}</td>
           <th class="has-text-center">Should display status order</th>
        </tr>
    </tbody>
</table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "Hirerequest",
  props: ["orderStatus"],
  data: function(){
      return{
      }
  },
  methods: {
     statusofOrder: function(){
        let changeStatus = {
        "href": this.orderStatus.plant.href,
        "value": "APPROVED"
      }
       axios.get("http://localhost:8080/callbacks/orderStateChanged", changeStatus)
      .then(response => {
          console.log("Response", response)
          return response.data;
      })
     }
  }
}
</script>

<style>

</style>

