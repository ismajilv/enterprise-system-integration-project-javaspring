<template>
  <div class="InvoiceList">
    <h1>List of Invoices</h1>
    <table class="table is-table-bordered is-table-striped is-fullwidth">
      <thead>
        <tr>
          <th class="has-text-center">ID</th>
          <th class="has-text-center">Status</th>
          <th class="has-text-center">Due date</th>
        </tr>
      </thead>
      <tbody>
        <tr class="table-row-we2" v-for="invoice in invoices" :key="invoice._id" >

        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    name: 'InvoiceList',
    data: () => ({
      invoices: []
    }),
    mounted: function() {
      this.fetchAllInvoices();
    },
    methods: function () {
      fetchAllInvoices: function() {
        axios.get()
      }
    }
  }
</script>
