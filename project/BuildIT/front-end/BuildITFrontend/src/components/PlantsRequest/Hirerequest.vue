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
        </tr>
    </thead>
    <tbody>
       <tr class="table-row-hire" v-show="orderStatus">
            <td id="plantNameHire">{{orderStatus.plant.name}}</td>
            <td id="plantStartDateHire">{{orderStatus.rentalPeriod.startDate}}</td>
            <td id="plantEndDateHire">{{orderStatus.rentalPeriod.endDate}}</td>
            <td id="plantTotalCostHire">{{orderStatus.rentalCost}}</td>
            <td id="plantStatusHire">{{orderStatus.status.replace(/_/g, ' ')}}</td>
        </tr>
    </tbody>
</table>
  </div>
</template>

<script>
export default {
  name: "HireRequest",
  props: ["orderStatus"]
}
</script>
