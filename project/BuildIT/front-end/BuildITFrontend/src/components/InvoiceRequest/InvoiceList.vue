<template>
  <div class="InvoiceList">
    <h1>List of Invoices</h1>
    <table class="table is-table-bordered is-table-striped is-fullwidth">
      <thead>
        <tr>
          <th class="has-text-center">ID</th>
          <th class="has-text-center">Status</th>
          <th class="has-text-center">Due date</th>
          <th class="has-text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr class="table-row-we2" v-for="invoice in invoices" :key="invoice._id" >
          <td class="has-text-center"> {{invoice._id}}</td>
          <th class="has-text-center">{{invoice.status}}</th>
          <th class="has-text-center">{{invoice.dueDate}}</th>
          <th>
            <button v-on:click="accept(invoice._id)" class="button is-success is-outlined">Approve</button>
          </th>
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
    methods: {
      fetchAllInvoices: function() {
        axios.get("http://localhost:8080/api/invoices/")
          .then(response => {
            this.invoices = response.data;
            console.log('[Fetching invoices]', response);
          })
          .catch(error => {
            console.log('[Fetching error]', error);
          })
      },
      accept: function (id) {
        console.log('[Approve request]', id)
      }
    }
  }
</script>
