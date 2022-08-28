import axios from '@/http/axios'
import url from '@/http/url'

const storage = {
	namespaced: true,
	state: {
		treeItems: [],
	},
	getters: {
		getTreeItems: (state) => state.treeItems,
	},
	mutations: {
		setreeItems(state, items) {
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
	},
	actions: {
		openRoot(commit) {
			return axios
				.get(url.StorageRootNode)
				.then((res) => {
					commit.commit('setreeItems', res.data.data)
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
	},
}

export default storage
