import Vue from 'vue'
import Router from 'vue-router'

import Home from '../views/Home.vue'
import About from '../views/about.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      component: About
    }
  ]
})
