<template>
  <div>
     <b-field label="Choose construction site">
        <b-select autofocus v-model="order.site.id" placeholder="Choose supplier" expanded>
            <option v-for="site in sites" :value="site._id" :key="site._id">{{site.address}}</option>
        </b-select>
     </b-field>
      <table class="table is-table-bordered is-table-striped is-fullwidth">
          <thead>
              <tr>
                  <th class="has-text-center">Name</th>
                  <th class="has-text-center">Supplier</th>
                  <th class="has-text-center">Start Date</th>
                  <th class="has-text-center">End Date</th>
                  <th class="has-text-center">Total Price</th>
              </tr>
          </thead>
          <tbody>
              <tr v-if="!!order.plant">
                  <td class="has-text-center">{{order.plant.name}}</td>
                  <td class="has-text-center">{{order.plant.supplier.name}}</td>
                  <td class="has-text-center">{{order.rentalPeriod.startDate}}</td>
                  <td class="has-text-center">{{order.rentalPeriod.endDate}}</td>
                  <td class="has-text-center">{{countPrice(order.rentalPeriod.startDate, order.rentalPeriod.endDate, order.plant.pricePerDay)}}</td>
              </tr>
          </tbody>
      </table>
      <button class="button is-primary" v-on:click="submit">Request Plant</button>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';

export default {
    name: "SubmitOrder",
    props: ["order"],
    data: function(){
        return {
          plants: [],
          sites: []
      }
  },
  mounted: function(){
    this.listSites();
  },
  methods: {
    submit: function () {
      this.$emit("submitOrder");
    },
    listSites: function () {
      axios.get("http://localhost:8080/api/sites")
        .then(response => {
          console.log('[Sites]', response);
          this.sites = response.data;
        })
    },
    countPrice: (start, end, price) => {
      const sd = moment(start, 'YYYY-MM-DD');
      const ed = moment(end, 'YYYY-MM-DD');
      return ed.diff(sd, 'days') * price;
    }
  }
}
</script>
