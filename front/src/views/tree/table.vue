<template>
	<div>
		<div v-if="getPath !== ''">
			<v-card-title>
				PATH : {{ getPath === '' ? 'HOME' : getPath }}
				<v-spacer></v-spacer>
				<v-container>
					<v-text-field
						v-model="search"
						append-icon="mdi-magnify"
						label="Search"
						single-line
						hide-details
					></v-text-field>
				</v-container>
			</v-card-title>
			<v-data-table
				class="pa-3"
				:headers="getHeader"
				:items="getItem"
				:search="search"
				:items-per-page="-1"
				:disable-items-per-page="true"
				:footer-props="footerProps"
				:hide-default-footer="true"
				@dblclick:row="showRowInfo"
				style="cursor: pointer"
			></v-data-table>
		</div>
		<div v-if="getPath === ''"><root></root></div>
	</div>
</template>
<script>
import root from '@/views/tree/root.vue'
export default {
	components: { root },
	data() {
		return {
			footerProps: { 'items-per-page-options': [-1] },
			search: '',
		}
	},

	computed: {
		getHeader() {
			return this.$store.state.storage.tableHeaders
		},

		getItem() {
			return this.$store.state.storage.tableItems
		},

		getPath() {
			return this.$store.state.storage.tableSelectPath
		},
	},

	methods: {
		showRowInfo(event, { item }) {
			this.$store.dispatch('storage/selectNodeTable', item.path)
		},
	},
}
</script>
