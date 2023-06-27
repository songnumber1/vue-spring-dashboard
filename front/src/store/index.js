import Vue from 'vue'
import Vuex from 'vuex'
import app from '@/store/modules/app'
import storage from '@/store/modules/storage'
import auth from '@/store/modules/auth'

Vue.use(Vuex)

const store = new Vuex.Store({
	modules: {
		app: app,
		storage: storage,
		auth: auth,
	},
})

export default store
