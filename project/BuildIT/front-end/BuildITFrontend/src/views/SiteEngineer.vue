d<template>
  <div class="SiteEngineer">
    <b-tabs type="is-toggle" expanded v-model="activeTab">
      <b-tab-item label="Order Creation">
        <order-creation></order-creation>
      </b-tab-item>
      <b-tab-item label="Invoices">
        <invoice-list></invoice-list>
      </b-tab-item>
    </b-tabs>
  </div>
</template>

<script>
import OrderCreation from "../components/PlantsRequest/OrderCreation.vue"
import InvoiceList from "../components/InvoiceRequest/InvoiceList.vue"

export default {
  name: 'SiteEngineer',
  components: {
    OrderCreation,
    InvoiceList
  },
  data () {
    return {
      activeTab: 0
    }
  }
}
</script>

<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
