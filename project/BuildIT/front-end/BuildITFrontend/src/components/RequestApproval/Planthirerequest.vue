<template>
  <div>
    <h2> Lists of plants</h2>
    <table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">id</th>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Site Engineer</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
          <tr class="table-row-we2" v-for="pending in allrequest" :key="pending._id" >
            <td class="has-text-center" id="name"> {{pending._id}}</td>
            <td id = "plantNameWE2" class="has-text-center"> {{pending.plant.name}}</td>
            <td id = "plantStartDateWE2" class="has-text-center"> {{pending.rentalPeriod.startDate}}</td>
            <td id = "plantEndDateWE2" class="has-text-center"> {{pending.rentalPeriod.endDate}}</td>
            <td class="has-text-center"> {{pending.requestingSiteEngineer.firstName}} {{pending.requestingSiteEngineer.lastName}} </td>
            <td id = "plantPriceWE2" class="has-text-center"> {{pending.rentalCost.total}}</td>
            <td>
              <a v-on:click="accept" class="button is-success is-outlined">Accept</a>
              <a v-on:click="reject" @click="isActiveReject = !isActiveReject" class="button is-danger is-outlined">Reject</a>
              <a v-on:click="reject" @click="isActiveExtend = !isActiveExtend" class="button is-outlined">Extend</a>
            </td>
        </tr>
    </tbody>
</table>
    <b-message title="You have rejected this order, Why?" :active.sync="isActiveReject">
       <textarea rows="4" cols="120" name="comment" form="usrform" v-model="request.comments">
        Enter text here...</textarea>
       <a v-on:click="comment" class="button is-success"> Comment </a>
     </b-message>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "Planthirerequest",
  props: ["orderStatus"],
  data: function(){
      return{
        allrequest: [],
        isActiveReject: false,
        isActiveExtend: false,
        rejectedRequest:{},
        request: {
          id: 0,
          comments: ''
        }
      }
  },
   mounted:function(){
    this.pendingHire();
  },
  methods: {
      pendingHire: function(){
         axios.get("http://localhost:8080/api/requests?status=PENDING")
        .then(response => {
          this.allrequest = response.data._embedded.plantHireRequestDTOList;
          console.log("Response", this.allrequest);
        });
      },
      accept: function(inputOrder){
           let i= document.getElementById("name").innerHTML;
           let params = i + "/accept";
        axios.get("http://localhost:8080/api/requests/", params)
        .then(response => {
           this.$snackbar.open("Plant hire request accepted for site engineer.");
         console.log("Accept", response.data._embedded);
        });
      },
      reject: function(inputOrder){
           let i= document.getElementById("name").innerHTML;
           let params = i + "/reject";
           console.log("[Reject id]", i);
        axios.get("http://localhost:8080/api/requests/", params)
        .then(response => {
          console.log("[After Reject]", response.data._embedded.plantHireRequestDTOList);
          this.rejectedRequest = response.data._embedded.plantHireRequestDTOList;
        });
      },
      comment: function(){
           let i= document.getElementById("name").innerHTML;
           let obj = {"value": this.request.comments};
           let params = i + "/addComment";
        axios.get("http://localhost:8080/api/requests/", params, obj)
        .then(response => {
          console.log("Comment", response);
           this.$snackbar.open("Comment submitted successfully");
        });
      }
  }
}
</script>

