<template>
	<div>
		<v-card-title>
			{{ selectPath }}
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
</template>
<script>
export default {
	data() {
		return {
			footerProps: { 'items-per-page-options': [-1] },
			search: '',
			selectPath: '',
		}
	},

	computed: {
		getHeader() {
			return this.$store.state.storage.tableHeaders
		},

		getItem() {
			return this.$store.state.storage.tableItems
		},
	},

	methods: {
		showRowInfo(event, { item }) {
			console.log(item.path)
		},
	},
}
</script>
