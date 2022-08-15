import Vue from 'vue'
import Vuex from 'vuex'
import app from '@/store/modules/app'

Vue.use(Vuex)

const store = new Vuex.Store({
	modules: {
		app: app
	}
})

export default store
