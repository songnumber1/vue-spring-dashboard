<template>
	<v-card class="pa-3">
		<v-btn class="primary" outlined v-on:click="addChild">Add Child</v-btn>

		<v-sheet class="pa-4 primary lighten-2">
			<v-text-field
				v-model="search"
				label="Search Company Directory"
				dark
				flat
				solo-inverted
				hide-details
				clearable
				clear-icon="mdi-close-circle-outline"
			></v-text-field>
			<v-checkbox
				v-model="caseSensitive"
				dark
				hide-details
				label="Case sensitive search"
			></v-checkbox>
		</v-sheet>

		<v-card-text>
			<!-- <v-treeview
				:active.sync="active"
				rounded
				activatable
				hoverable
				return-object
				active-class="primary--text"
				:items="items"
				:search="search"
				:filter="filter"
				open-on-click
				@update:active="selectNode"
				@update:open="openNode"
				item-key="id"
			> -->
			<v-treeview
				activatable
				rounded
				hoverable
				v-model="active"
				:items="items"
				:search="search"
				:filter="filter"
				@update:open="openNode"
				item-key="id"
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
		</v-card-text>
	</v-card>
</template>

<script>
import treeitemInfo from '@/data/treeitemInfo'

export default {
	methods: {
		addChild() {
			if (this.active.length === 0) {
				console.log('Please select item')
				return
			}

			//console.log(this.active[0], this.active[0].id, this.active[0].name)

			if (this.active[0].children !== undefined)
				this.active[0].children.push({ id: '100', name: 'test' })
			else {
				// const newItem = [{ id: '1001', name: 'test1' }]
				this.active[0].children = []
				//this.$set(this.active[0], 'children', [{ id: '1001', name: 'test1' }])
				this.active[0].children.push([{ id: '1001', name: 'test1' }])
			}

			console.log(this.active[0].children)

			// if (this.active[0].children !== undefined)
			// 	this.active[0].children.push({ id: '100', name: 'test' })
			// else {
			// 	this.active[0].add({ children: [{ id: '1001', name: 'test1' }] })
			// }

			// this.items[0].children.forEach(x => {
			// 	console.log(x.id, x.name)
			// })

			// const childItem = this.items.find(x => {
			// 	return x.id === this.active[0]
			// })

			// console.log(childItem.id, childItem.name)
		},

		// 로드 선택할 때
		selectNode(node) {
			console.log(
				'Method : selectNode',
				'node.name :' + node.name,
				'this.active[0] : ' + this.active[0],
				'${JSON.stringify(node)} : ' + `${JSON.stringify(node)}`
			)
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

				console.log(
					'Method : openNode',
					'index : ' + index,
					'this.oldOpenNodeCnt : ' + this.oldOpenNodeCnt,
					'node.length : ' + node.length,
					'node[index] : ' + node[index],
					'${JSON.stringify(node[index])} :' + `${JSON.stringify(node[index])}`
				)
			}
		},
	},

	data: () => ({
		active: [],
		items: treeitemInfo.trees,
		search: null,
		caseSensitive: false,
		oldOpenNodeCnt: 0,
	}),
	computed: {
		filter() {
			return this.caseSensitive
				? (item, search, textKey) => item[textKey].indexOf(search) > -1
				: undefined
		},
	},
}
</script>
