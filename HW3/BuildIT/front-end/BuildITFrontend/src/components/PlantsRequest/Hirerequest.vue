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
       <tr class="table-row-hire" v-show="orderStatus">
            <td id="plantNameHire">{{orderStatus.plant.name}}</td>
            <td id="plantStartDateHire">{{orderStatus.rentalPeriod.startDate}}</td>
            <td id="plantEndDateHire">{{orderStatus.rentalPeriod.endDate}}</td>
            <td id="plantTotalCostHire">{{orderStatus.rentalCost.total}}</td>
            <td id="plantStatusHire">{{orderStatus.status}}</td>
           <td id="plantStatusHireUp" class="has-text-center">Should display status order</td>
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

