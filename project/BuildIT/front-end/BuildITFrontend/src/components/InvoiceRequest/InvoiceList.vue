<template>
  <div class="InvoiceList">
    <h1>List of Invoices</h1>
    <table class="table is-table-bordered is-table-striped is-fullwidth">
      <thead>
        <tr>
          <th class="has-text-center">ID</th>
          <th class="has-text-center">Status</th>
          <th class="has-text-center">Payable amount</th>
          <th class="has-text-center">Due date</th>
          <th class="has-text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr class="table-row-we2" v-for="invoice in invoices" :key="invoice._id" >
          <td class="has-text-center"> {{invoice._id}}</td>
          <td class="has-text-center">{{invoice.status}}</td>
          <td class="has-text-center">{{invoice.payableAmount}}</td>
          <td class="has-text-center">{{invoice.dueDate}}</td>
          <td>
            <button v-on:click="accept(invoice._id)" class="button is-success is-outlined">Approve</button>
          </td>
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
            this.invoices = response.data ? response.data._embedded.invoiceDTOList : [];
            console.log('[Fetching invoices]', response);
          })
          .catch(error => {
            console.log('[Fetching error]', error);
          });
      },
      accept: function (id) {
        axios.post(`http://localhost:8080/api/invoices/${id}/accept`)
          .catch(error => {
            console.log('[Approve error]', error);
          });
        this.fetchAllInvoices();
        console.log('[Approve request]', id)
      }
    }
  }
</script>
