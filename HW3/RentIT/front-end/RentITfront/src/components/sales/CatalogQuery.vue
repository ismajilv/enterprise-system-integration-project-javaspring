<template>
  <section>
    <b-field label="Plant name">
        <b-input v-model="query.name"></b-input>
    </b-field>
    <b-field label="Start date">
        <b-datepicker
            placeholder="Click to select..."
            icon="calendar-today"
            v-model="query.startDate">
        </b-datepicker>
    </b-field>
    <b-field label="End date">
        <b-datepicker
            placeholder="Click to select..."
            icon="calendar-today"
            v-model="query.endDate">
        </b-datepicker>
    </b-field>
    <button 
        class="button is-primary is-outlined"
        v-on:click="submit">
        Submit query
    </button>
    <br v-for="v in 12" :key="v"/>
  </section>
</template>

<script>
export default {
    name: "CatalogQuery",
    data: function() {
        return {
            query: {
                name: "",
                startDate: undefined,
                endDate: undefined
            }
        }
    },
    methods: {
        submit: function() {
            this.$emit("submitCatalogQuery", this.query);
        }
    }
}
</script>
