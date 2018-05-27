import Vue from 'vue';
import Router from 'vue-router';
import SiteEngineer from '../views/SiteEngineer.vue';
import AllEngineers from '../views/AllEngineers.vue';
// import Login from '../components/Login.vue';
// import auth from '../auth';

Vue.use(Router);

// function requireAuth (to, from, next) {
//   if (!auth.loggedIn()) {
//     next({
//       path: '/login',
//       query: { redirect: to.fullPath }
//     })
//   } else {
//     next()
//   }
// }

// export default new Router({
//   routes: [
//     {
//       path: '/SiteEngineer',
//       name: 'SiteEngineer',
//       component: SiteEngineer,
//       beforeEnter: requireAuth
//     },
//     {
//       path: '/',
//       name: 'AllEngineers',
//       component: AllEngineers,
//       beforeEnter: requireAuth
//     },
//     { path: '/login', component: Login },
//     { path: '/logout',
//       beforeEnter (to, from, next) {
//         auth.logout();
//         next('/')
//       }
//     }
//   ]
// })

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
