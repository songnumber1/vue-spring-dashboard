const appStore = {
	namespaced: true,
	state: {
		appTitle: 'Dashboard',
		appVersion: 'Ver 1.0.0',
		isLoading: false,
		menus: [
			{ title: 'Dashboard', icon: 'mdi-view-dashboard', to: '/' },
			{ title: 'AxoisVuex', icon: 'mdi-image', to: '/axios-vuex' },
			{ title: 'BreakPoint', icon: 'mdi-help-box', to: '/break-point' },
			{ title: 'GridSystem', icon: 'mdi-help-box', to: '/grid-system' },
			{ title: 'TreeView', icon: 'mdi-help-box', to: '/tree-view' },
		],
	},
	getters: {
		getAppTitle: (state) => state.appTitle,
		getAppVersion: (state) => state.appVersion,
		getIsLoading: (state) => state.isLoading,
	},
	mutations: {
		setIsLoading(state, { isLoading }) {
			state.isLoading = isLoading
		},
	},
}

export default appStore
