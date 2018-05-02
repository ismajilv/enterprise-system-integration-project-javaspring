<template>
  <div>
    <h2> Lists of plants</h2>
    <table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Actions</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr class="table-row-we">
            <td id = "plantNameWE" class="has-text-center"> Plant Name</td>
            <td id = "plantStartDateWE" class="has-text-center">Plant Start Date</td>
            <td id = "plantEndDateWE" class="has-text-center">Plant End Date</td>
            <td id = "plantPriceWE" class="has-text-center">Plant Price</td>
            <td><a v-on:click="update" class="button is-success is-outlined">Accept</a> </td>
            <td> <a class="button is-danger is-outlined">Reject</a> </td>
        </tr>
    </tbody>
</table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "Planthirerequest",
  props: ["orderStatus"],
  data: function(){
      return{
        allrequest: {}
      }
  },
  mounted:function(){
     axios.get("http://localhost:8080/api/requests")
        .then(response => {
          console.log(response.data);
          this.allrequest = response.data;
        });
  }
}
</script>

<style>

</style>

