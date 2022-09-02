import axios from '@/http/axios'
import url from '@/http/url'
import eventBus from '@/data/EventBus.js'
import eventBusVariable from '@/data/EventBusVariable.js'

const storage = {
	namespaced: true,
	state: {
		treeItems: [],
		tableItems: [],
		tableHeaders: [],
		tableSelectPath: '',
		rootItems: [],
	},
	getters: {
		getTreeItems: (state) => state.treeItems,

		getTableItems(state) {
			return state.tableItems
		},

		getTableHeader: (state) => state.tableHeaders,
	},
	mutations: {
		setTreeItems(state, items) {
			state.treeItems = items
		},

		setOpenNode(state, result) {
			const openNodes = result.nodeList.filter((x) => {
				return x.id.includes(result.node.id) && x.id !== result.node.id
			})

			openNodes.forEach((element) => {
				const openNode = result.data.find((x) => x.id === element.id)

				if (
					openNode.children !== undefined ||
					openNode.children !== null ||
					openNode.children !== []
				) {
					openNode.children = element.children
				}
			})

			result.node.children = result.data
		},

		setTreeNode(state, { header, item }) {
			state.tableHeaders = header
			state.tableItems = item
		},
	},
	actions: {
		openRoot(commit) {
			return axios
				.get(url.StorageRootNode)
				.then((res) => {
					commit.state.rootItems = []

					res.data.data.forEach((x) => {
						commit.state.rootItems.push({
							id: x.id,
							name: x.name,
							absolutePath: x.absolutePath,
							totalSize: x.totalSize,
							useSize: x.useSize,
							useSizePercent: x.useSizePercent,
						})
					})

					commit.commit('setTreeItems', res.data.data)
				})
				.catch((err) => {
					console.log(err)
				})
		},
		openNode(commit, { nodeList, node, path }) {
			if (path === null || path === undefined) return

			return axios
				.get(url.StorageOpenNode + path)
				.then((res) => {
					var data = res.data.data
					commit.commit('setOpenNode', {
						nodeList: nodeList,
						node: node,
						data: data,
					})
				})
				.catch((err) => {
					console.log(err)
				})
		},

		selectNodeTable(commit, path) {
			if (path === null || path === undefined) return

			return axios
				.get(url.StorageSelectNodeTable + path.replaceAll('\\', '//'))
				.then((res) => {
					commit.state.tableItems = res.data.data.storageSelectContentItemModels
					commit.state.tableHeaders = res.data.data.storageSelectHeaderModels
					commit.state.tableSelectPath = path

					eventBus.$emit(
						eventBusVariable.eventBusStorageSelectNodeTable,
						'eventBusTest'
					)
				})
				.catch((err) => {
					console.log(err)
				})
		},
	},
}

export default storage
