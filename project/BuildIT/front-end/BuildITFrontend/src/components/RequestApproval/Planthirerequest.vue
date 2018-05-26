<template>
  <div>
    <h2> Lists of plants</h2>
    <label class="checkbox">
      <input type="checkbox" v-model="checkbox" v-on:click="fetchHireRequests()">
      Show only pending
    </label>
    <table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Id</th>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Site Engineer</th>
            <th class="has-text-center">Total Price</th>
            <th class="has-text-center">Status</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
          <tr class="table-row-we2" v-for="request in allRequests" :key="request._id" >
            <td id = "name" class="has-text-center"> {{request._id}}</td>
            <td id = "plantNameWE2" class="has-text-center"> {{request.plant.name}}</td>
            <td id = "plantStartDateWE2" class="has-text-center"> {{request.rentalPeriod.startDate}}</td>
            <td id = "plantEndDateWE2" class="has-text-center">{{request.rentalPeriod.endDate}}</td>
            <td class="has-text-center"> {{request.requestingSiteEngineer.firstName}} {{request.requestingSiteEngineer.lastName}} </td>
            <td id = "plantPriceWE2" class="has-text-center">
              {{countPrice(request.rentalPeriod.startDate, request.rentalPeriod.endDate, request.rentalCost)}}
            </td>
            <td>{{request.status.replace(/_/g, ' ')}}</td>
            <td>
              <button v-on:click="accept(request._id)" class="button is-success is-outlined">Accept</button>
              <button v-on:click="reject(request._id)" @click="isActiveReject = !isActiveReject" class="button is-danger is-outlined">Reject</button>
              <button v-on:click="focus(request._id)" @click="isActiveExtend = !isActiveExtend" class="button is-warning is-outlined">Extend</button>
              <button v-on:click="cancel(request._id)" class="button is-danger is-outlined">Cancel</button>
            </td>
        </tr>
    </tbody>
</table>
    <b-modal title="You have rejected this order, Why?" :active.sync="isActiveReject">
       <textarea name="comment" form="usrform" v-model="request.comments" placeholder="Enter comment here..."></textarea>
       <button v-on:click="comment" class="button is-success"> Comment </button>
    </b-modal>

    <b-modal title="Extension Request" :active.sync="isActiveExtend">
      <div>
        Enter End date:
        <input type="date"
               id="end-date"
               name="startdate"
               v-model="request.newEndDate"
               icon="calendar-today">
      </div>
      <br>
      <textarea name="comment" form="usrform" v-model="request.comments" placeholder="Enter comment here..."></textarea>
      <br>
      <button v-on:click="extend" class="button is-success"> Submit </button>
    </b-modal>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';

export default {
  name: "Planthirerequest",
  props: ["orderStatus"],
  data: function(){
      return{
        allRequests: [],
        checkbox: false,
        isActiveReject: false,
        isActiveExtend: false,
        rejectedRequest:{},
        request: {
          id: null,
          newEndDate: null,
          comments: null
        }
      }
  },
   mounted:function(){
    this.fetchHireRequests();
  },
  methods: {
    fetchHireRequests: function(){
         axios.get("http://localhost:8080/api/requests")
        .then(response => {
          let requests = response.data._embedded.plantHireRequestDTOList;
          if (this.checkbox){
            requests = requests.filter(req => req.status === 'PENDING_WORKS_ENGINEER_APPROVAL');
          }
          this.allRequests = requests;
          console.log("[Fetching response]", this.allRequests);
        });
      },
      countPrice: (start, end, price) => {
        const sd = moment(start, 'YYYY-MM-DD');
        const ed = moment(end, 'YYYY-MM-DD');
        return ed.diff(sd, 'days')*price;
      },
      accept: function(id){
        console.log('[Accept Id]', id);
        axios.post(`http://localhost:8080/api/requests/${id}/accept`)
          .then(response => {
             this.updatePHR(response.data);
             this.$snackbar.open("Plant hire request accepted for site engineer.");
          })
          .catch(error => {
            console.log('[Accept error]', error);
          });
      },
      reject: function(id){
        console.log("[Reject id]", id);
        this.request.id = id;
        axios.post(`http://localhost:8080/api/requests/${id}/reject`)
        .then(response => {
          this.updatePHR(response.data);
        })
        .catch(error => {
          console.log('[Rejection error]', error);
        });
      },
      comment: function(){
        const params = {value: this.request.comments};
        axios.post(`http://localhost:8080/api/requests/${this.request.id}/addComment`, params)
        .then(response => {
          console.log("[Comment]", response);
            this.isActiveReject = false;
           this.$snackbar.open("Comment submitted successfully");
        });
      },
      extend: function() {
          console.log('[Extending]', this.request);
          const params = {
            newEndDate: moment(String(this.request.newEndDate)).format("YYYY-MM-DD"),
            comment: this.request.comments
          };
          const id = this.request.id;
          if (params.newEndDate && id) {
            axios.post(`http://localhost:8080/api/requests/${id}/requestExtension`, params)
              .then(response => {
                this.isActiveExtend = false;
                this.updatePHR(response.data);
                console.log('[Extension response]', response);
              })
              .catch(error => {
                console.log('[Extension error]', error);
              });
          }
      },
      cancel: (id) => {
        axios.post(`http://localhost:8080/api/requests/${id}/cancel`)
          .then(response => {
            this.updatePHR(response.data);
            console.log('[Cancelled response]', response)
          })
          .catch(error => {
            console.log('[Cancel error]', error)
          })
      },
      focus: function(id) {
        console.log('[Focused on]', id);
        this.request.id = id;
      },
      updatePHR: function(phr) {
        this.allRequests = this.allRequests.map(req => {
          if(phr._id === req._id) {
            return phr;
          }
          return req;
        });
      }
  }
}
</script>

<style>
  label {
    text-align: left;
    display: block;
    float: left;
    margin-left: 5%;
  }
  textarea {
    width: 100%;
    height: 60px;
  }
</style>

