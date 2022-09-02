<template>
	<div>
		<v-treeview
			activatable
			hoverable
			:items="getItems"
			return-object
			@update:open="openNode"
		>
			<template slot="label" slot-scope="{ item, open }">
				<a @click="selectNode(item)">
					<v-icon>
						{{ open ? 'mdi-folder-open' : 'mdi-folder' }}
					</v-icon>
					{{ item.name }}
				</a>
			</template>
		</v-treeview>
	</div>
</template>

<script>
import treeitemInfo from '@/data/treeitemInfo'

export default {
	methods: {
		addChild() {
			if (this.activeNode === undefined || this.activeNode === null) {
				console.log('Please select item')
				return
			}

			if (this.activeNode.children !== undefined) {
				this.activeNode.children.push({ id: '100', name: 'test' })
			} else {
				this.$set(this.activeNode, 'children', [{ id: '1001', name: 'test1' }])

				console.log(this.activeNode.children)
			}
		},

		// 로드 선택할 때
		selectNode(node) {
			// this.activeNode = node
			// console.log(
			// 	'Method : selectNode',
			// 	'node.name :' + node.name,
			// 	'${JSON.stringify(node)} : ' + `${JSON.stringify(node)}`
			// )

			this.$store.dispatch('storage/selectNodeTable', node.absolutePath)
		},

		openNode(node) {
			if (node !== undefined && node !== null && node.length != 0) {
				if (this.oldOpenNodeCnt > node.length) {
					this.oldOpenNodeCnt = node.length
					return
				} else {
					this.oldOpenNodeCnt = node.length
				}

				var index = 0

				if (node.length > 1) {
					index = node.length - 1
				}

				const path = node[index].id.replaceAll('\\', '//')

				const para = {
					nodeList: node,
					node: node[index],
					path: path,
				}

				this.$store.dispatch('storage/openNode', para)

				// console.log(
				// 	'Method : openNode',
				// 	'index : ' + index,
				// 	'this.oldOpenNodeCnt : ' + this.oldOpenNodeCnt,
				// 	'node.length : ' + node.length,
				// 	'node[index] : ' + node[index],
				// 	'${JSON.stringify(node[index])} :' + `${JSON.stringify(node[index])}`
				// )
			}
		},
	},
	mounted() {
		this.$store.dispatch('storage/openRoot')
	},
	data: () => ({
		activeNode: null,
		items: treeitemInfo.trees,
		caseSensitive: false,
		oldOpenNodeCnt: 0,
		menuItems: ['create file', 'create directory'],
	}),
	computed: {
		getItems() {
			return this.$store.getters['storage/getTreeItems']
		},
	},
}
</script>
