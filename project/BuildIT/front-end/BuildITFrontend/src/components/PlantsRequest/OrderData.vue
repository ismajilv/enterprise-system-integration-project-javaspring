<template>
<div>
   <b-field label="Choose supplier">
          <b-select autofocus id="v-for-object" v-model="order.moreInfo.suppliersid"
            placeholder="Choose supplier" expanded>
                <option v-for="supplier in suppliers" :key="supplier._id">
                {{supplier._id}}
                </option>
          </b-select>
   </b-field>
{{order.suppliersid}}


   <b-field label="Choose construction site">
            <b-select autofocus v-model="order.moreInfo.siteid" placeholder="Choose supplier" expanded>
                <option v-for="site in sites" :value="site" :key="site._id">
                  {{site._id}} </option>
  </b-select>
   </b-field>
<button class="button is-primary" v-on:click="submit">Request Plant</button>
<table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr v-if="!!order.plant">
            <td>{{order.plant.name}}</td>
            <td>{{order.rentalPeriod.startDate}}</td>
            <td>{{order.rentalPeriod.endDate}}</td>
            <td class="has-text-right">{{order.plant.price}}</td>
            <td><button v-on:click="update" @click="isupdateModalActive = true" class="button is-success is-outlined">Update</button>
            <button class="button is-danger is-outlined">Remove</button> </td>
        </tr>
    </tbody>
</table>
   <b-modal :active.sync="isupdateModalActive" class="showUpdate">
            <p>
                  <b-field label="Choose supplier">
            <b-select  v-model="updatedOrder.suppliersid"
            placeholder="Choose supplier" expanded>
                <option v-for="supplier in suppliers" :key="supplier._id">
                {{supplier._id}}
                </option>
            </b-select>
   </b-field>

   <b-field label="Choose construction site">
            <b-select  v-model="updatedOrder.siteid"
            placeholder="Choose supplier" expanded>
                <option v-for="site in sites" :key="site._id">
                  {{site._id}} </option>
  </b-select>
   </b-field>
            </p>
            <div>
    <b-field label="Plant name">
      <b-input id="name" v-model="updatedOrder.query.name"></b-input>
    </b-field>
    <div>
      <form class="form_style">
        Enter Start date:
        <input type="date"
        id="start-date"
        name="startdate"
        v-model="updatedOrder.query.startDate"
        icon="calendar-today">

        Enter End date:
        <input type="date"
        id="end-date"
        name="startdate"
        v-model="updatedOrder.query.endDate"
        icon="calendar-today">
      </form>

      <button @click="isupdateModalActive = false" class="button is-danger is-outlined"> Back </button>
      <button class="button is-primary is-outlined"
    v-on:click="updatenewOrder" id="submit-query"> Update Query </button>
    </div>

 </div>
    </b-modal>
</div>
</template>

<script>
import axios from 'axios';

export default {
    name: "OrderData",
    props: ["order"],
    data: function(){
        return {
          plants: [],
          sites: [],
          suppliers: [],
          isupdateModalActive: false,
          updatedOrder:{
             id: 1,
            plant: {},
            rentalPeriod: {},
          moreInfo: {
              name: '',
              suppliersid: 0 ,
              siteid: 0,
              index: 0
          },
           query: {
             name: "",
             startDate: undefined,
             endDate: undefined
            }
          }
      }
  },
  mounted: function(){
    this.supplierlists();
    this.sitelists();
  },
    methods: {
      submit: function() {
            this.$emit("submitPurchaseOrder");
      },
      update: function(){
          this.$emit("updatePurchaseOrder");
      },
      supplierlists: function() {
      axios.get("http://localhost:8080/api/suppliers")
      .then(response => {
          this.suppliers = response.data;
          return suppliers;
      })
    },
    sitelists: function() {
      axios.get("http://localhost:8080/api/sites")
      .then(response => {
          this.sites = response.data;
          return sites;
      })
    },
    updatenewOrder: function(){
        let object = {
           "constructionSiteId": this.updatedOrder.siteid,
           "supplierId": this.updatedOrder.suppliersid,
           "plantHref":  this.order.plant._links.self.href,
           "rentalPeriod" : {
           "startDate" : this.updatedOrder.rentalPeriod.startDate,
           "endDate" : this.updatedOrder.rentalPeriod.endDate
          }
        };
        console.log("Update data", object)
          var a= document.getElementById("name").innerHTML;
           var parameter = a + "/"+object;
        console.log("Plant submission before", object);
              axios.put("http://localhost:8080/api/requests/", parameter)
                .then(response => {
                    this.$snackbar.open("Plant hire request. Waiting for confirmation from works engineer.");
                 this.orderStatus = response.data;
                   console.log("Plant submission after", this.orderStatus);
                }).catch(error => {
                    this.$snackbar.open({
                        type: 'is-danger',
                        message: "Something went wrong with purchase order submition."
                    });
         });
    }
    }
}
</script>

<style>
.modal-background {
  background-color: beige;
}
</style>
