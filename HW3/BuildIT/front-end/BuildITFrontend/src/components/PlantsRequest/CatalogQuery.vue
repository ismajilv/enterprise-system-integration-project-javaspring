<template>
 <div>
    <b-field label="Plant name">
      <b-input v-model="query.name"></b-input>
    </b-field>
    <div>
      <form class="form_style">
        Enter Start date:
        <input type="date" name="startdate" v-model="query.startDate">
        Enter End date:
        <input type="date" name="startdate" v-model="query.endDate">
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
