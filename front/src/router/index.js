import Vue from 'vue'
import VueRouter from 'vue-router'
import Dashboard from '@/views/Dashboard'
import GridSystem from '@/views/GridSystem'
import AxiosVuex from '@/views/AxiosVuex'
import BreakPoint from '@/views/BreakPoint'
import Storage from '@/views/Storage'
import DefaultLayout from '@/layout/default/index'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		component: DefaultLayout,
		children: [
			{
				path: '/',
				name: 'Dashboard',
				component: Dashboard,
			},
			{
				path: '/axios-vuex',
				name: 'AxiosVuex',
				component: AxiosVuex,
			},
			{
				path: '/grid-system',
				name: 'GridSystem',
				component: GridSystem,
			},
			{
				path: '/break-point',
				name: 'BreakPoint',
				component: BreakPoint,
			},
			{
				path: '/storage',
				name: 'Storage',
				component: Storage,
			},
		],
	},
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes,
})

export default router
