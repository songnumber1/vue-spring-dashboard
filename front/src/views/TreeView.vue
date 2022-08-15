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

		<!-- 필터없이 적용 (이걸로 해야할 듯하다. 이유는 tree item에 존재하는 데이터만 filter하므로 동적으로 item이 변경되어 적용하기 힘들듯) -->
		<!-- <v-treeview
			activatable
			rounded
			hoverable
			v-model="active"
			:items="items"
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
		</v-treeview> -->
	</v-card>
</template>

<script>
import treeitemInfo from '@/data/treeitemInfo'
import { no } from 'vuetify/lib/locale'

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
			this.activeNode = node
			console.log(
				'Method : selectNode',
				'node.name :' + node.name,
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
		activeNode: null,
		items: treeitemInfo.trees,
		search: null,
		caseSensitive: false,
		oldOpenNodeCnt: 0,
		menuItems: ['create file', 'create directory'],
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
