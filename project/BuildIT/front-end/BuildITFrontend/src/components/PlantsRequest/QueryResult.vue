<template>
  <table class="table is-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center">Name</th>
            <th class="has-text-center">Description</th>
            <th class="has-text-center">Price</th>
            <th class="has-text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="plant in plants" :key="plant.id" class="table-row">
            <td>{{ plant.name }}</td>
            <td>{{ plant.description }}</td>
            <td class="has-text-right">{{ plant.price }}</td>
            <td>
                <a class="button is-link is-small is-outlined"
                @click="submit(plant)">Select plant</a>
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
