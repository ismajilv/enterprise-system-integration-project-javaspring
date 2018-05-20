d<template>
  <div class="SiteEngineer">
   <order-creation></order-creation>
  </div>
</template>

<script>
import OrderCreation from "../components/PlantsRequest/OrderCreation.vue"

export default {
  name: 'SiteEngineer',
  components: {
    OrderCreation
  },
  data () {
    return {
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
