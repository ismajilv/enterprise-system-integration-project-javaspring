<template>
  <div class="AllEngineers">
    <h2> For All engineers </h2>
    <plant-hire-request> </plant-hire-request>
  </div>
</template>

<script>
import PlantHireRequest from "../components/RequestApproval/PlantHireRequest.vue";

export default {
  name: "WorkEngineer",
   components: {
     PlantHireRequest
  }
}
</script>
