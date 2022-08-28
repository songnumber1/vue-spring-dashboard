import Vue from 'vue'
import Vuex from 'vuex'
import app from '@/store/modules/app'
import storage from '@/store/modules/storage'

Vue.use(Vuex)

const store = new Vuex.Store({
	modules: {
		app: app,
		storage: storage,
	},
})

export default store
