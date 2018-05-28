<template>
  <table class="table is-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Supplier</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Action</th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="plant in plants" :key="plant.id" class="table-row">
            <td class="has-text-center">{{ plant.name }}</td>
            <td class="has-text-center">{{plant.supplier.name}}</td>
            <td class="has-text-center">{{ plant.pricePerDay }}</td>
            <td class="has-text-center">
                <button class="button is-link is-small is-outlined"
                @click="submit(plant)">Select plant</button>
            </td>
        </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  name: "QueryResult",
  props: ["plants"],
    methods: {
        submit: function(plant) {
            this.$emit("selectPlant", plant);
        }
    }
}
</script>

<style>

</style>
