import Vue from 'vue';
import Router from 'vue-router';
import SiteEngineer from '../views/SiteEngineer.vue';
import AllEngineers from '../views/AllEngineers.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/SiteEngineer',
      name: 'SiteEngineer',
      component: SiteEngineer
    },
    {
      path: '/',
      name: 'AllEngineers',
      component: AllEngineers
    }
  ]
})
