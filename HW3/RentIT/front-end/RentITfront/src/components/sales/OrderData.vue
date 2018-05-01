<template>
<div>
  <section>
    <b-field label="Customer reference">
        <b-input v-model="customerinfo.customer"></b-input>
    </b-field>
    <b-field label="Site address">
        <b-input v-model="customerinfo.siteAddress"></b-input>
    </b-field>
    <button class="button is-primary" v-on:click="submit">Create Purchase Order</button>
  </section>
<table class="table is-table-bordered is-table-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Start Date</th>
            <th class="has-text-center">End Date</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Subtotal</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr v-if="!!order.plant">
            <td>{{order.plant.name}}</td>
            <td>{{order.rentalPeriod.startDate|formatDate}}</td>
            <td>{{order.rentalPeriod.endDate|formatDate}}</td>
            <td class="has-text-right">{{order.plant.price}}</td>
            <td class="has-text-right">{{order.total}}</td>
            <a class="button is-danger is-outlined">Remove</a>
        </tr>
    </tbody>
</table>

    {{customerinfo }}
</div>
</template>

<script>
export default {
    name: "OrderData",
    props: ["order"],
    data: function() {
        return {
            customerinfo: {
              customer: "",
              siteAddress: ""
            }
        }
    },
    methods: {
        submit: function() {
            this.$emit("submitPurchaseOrder");
        }
    }
}
</script>
