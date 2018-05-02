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
            <th class="has-text-center">Status</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>{{orderStatus.plant.name}}</td>
            <td>{{orderStatus.rentalPeriod.startDate}}</td>
            <td>{{orderStatus.rentalPeriod.endDate}}</td>
            <td>{{orderStatus.rentalCost.total}}</td>
            <td>{{orderStatus.status}}</td>
        </tr>
    </tbody>
</table>
  </div>
</template>

<script>

export default {
  name: "Hirerequest",
  props: ["orderStatus"],
  data: function(){
      return{
      }
  }
}
</script>

<style>

</style>

