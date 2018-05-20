import Vue from 'vue'
import Router from 'vue-router'

import SiteEngineer from '../views/SiteEngineer.vue'
import WorkEngineer from '../views/WorkEngineer.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'SiteEngineer',
      component: Home
    },
    {
      path: '/WorkEngineer',
      name: 'WorkEngineer',
      component: About
    }
  ]
})
