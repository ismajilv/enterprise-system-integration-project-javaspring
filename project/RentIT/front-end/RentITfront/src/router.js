import Vue from 'vue'
import Router from 'vue-router'
import RentItEmployee from './views/RentItEmployee.vue'
import PlantDepotEmployee from './views/PlantDepotEmployee.vue'

Vue.use(Router)

export default new Router({
    routes: [
      {
        path: '/',
        name: 'RentItEmployee',
        component: RentItEmployee
      },
      {
        path: '/PlantDepotEmployee',
        name: 'PlantDepotEmployee',
        component: PlantDepotEmployee
      }
    ]
  })
  