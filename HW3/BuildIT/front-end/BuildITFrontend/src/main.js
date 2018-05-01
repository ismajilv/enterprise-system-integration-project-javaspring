// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Buefy from 'buefy'
import 'buefy/lib/buefy.css'
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import moment from 'moment'

Vue.use(VueMaterial)
Vue.use(Buefy)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

Vue.filter('formatDate', function(value) {
  if (value) {
    return moment(String(value)).format('MM/DD/YYYY')
  }
})

