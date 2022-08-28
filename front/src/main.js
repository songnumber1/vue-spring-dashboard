import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import VueSplit from 'vue-split-panel'

Vue.use(VueSplit)

Vue.config.productionTip = false

new Vue({
	router,
	store,
	vuetify,
	render: (h) => h(App),
}).$mount('#app')
