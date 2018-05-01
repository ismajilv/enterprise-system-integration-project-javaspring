<template>
 <div>
    <b-field label="Plant name">
      <b-input id="name" v-model="query.name"></b-input>
    </b-field>
    <div>
      <form class="form_style">
        Enter Start date:
        <input type="date"
        id="start-date"
        name="startdate"
        v-model="query.startDate"
        icon="calendar-today">

        Enter End date:
        <input type="date"
        id="end-date"
        name="startdate"
        v-model="query.endDate"
        icon="calendar-today">
      </form>

      <button class="button is-primary is-outlined"
    v-on:click="submit"> Submit Query </button>
    </div>

 </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker';

export default {
  name: "CatalogQuery",
  data: function(){
    return {
      query: {
         name: "",
         startDate: undefined,
         endDate: undefined
      }
    }
  },
  methods: {
    submit: function(){
      console.log(this.query);
      this.$emit("submitCatalogQuery", this.query)
    }
  }
}
</script>

<style>
.form_style{
  margin: 4%;
  font-size: 16px;
}
</style>
