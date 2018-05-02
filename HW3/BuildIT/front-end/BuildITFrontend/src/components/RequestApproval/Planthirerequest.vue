<template>
<div>
  <table class="table is-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Plant</th>
            <th class="has-text-center">Rental period</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="(order, order_index) in orders" :key="order_index">
            <td id="plantName">
                {{order.plant.name}} <br/>
                <a class="button is-link is-small is-outlined">
                   See details
                </a>
            </td>
            <td id="plantRentalPeriod">{{order.rentalPeriod.startDate}} / {{order.rentalPeriod.endDate}}</td>
            <td class="has-text-right"></td>
            <td>
              <a v-for="(link, rel) in order._links" :key="rel" v-if="rel !== 'self'"
              v-bind:class="{ 'is-danger': link.method === 'DELETE', 'is-link': link.method !== 'DELETE' }"
              class="button is-small is-outlined"
              @click="followLink(link, rel, order_index)">
              {{rel}}
            </a>
            </td>
        </tr>
    </tbody>
  </table>
</div>
</template>

<script>

import axios from "axios";
export default {
    name: "Planthirerequest",
    data: function() {
        return {
            orders: []
        }
    },
    mounted: function() {
        axios.get("http://localhost:8090/api/sales/orders")
        .then(response => {
            this.orders = !response.data._embedded ? [] : response.data._embedded.orders;
        });
    }
}
</script>
