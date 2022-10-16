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

			<v-card-subtitle>
				<div class="text-right">
					<v-btn class="mx-2" fab dark small color="primary" @click="rootClick">
						<v-icon dark> mdi-home </v-icon>
					</v-btn>

					<v-btn class="mx-2" fab dark small color="purple">
						<v-icon dark> mdi-arrow-left </v-icon>
					</v-btn>

					<v-btn class="mx-2" fab dark small color="purple">
						<v-icon dark> mdi-arrow-right </v-icon>
					</v-btn>

					<v-btn class="mx-2" fab dark small color="pink">
						<v-icon dark> mdi-folder </v-icon>
					</v-btn>

					<v-btn class="mx-2" fab dark small color="indigo">
						<v-icon dark> mdi-file </v-icon>
					</v-btn>

					<!-- <v-btn class="mx-2" fab dark small color="cyan">
						<v-icon dark> mdi-pencil </v-icon>
					</v-btn> -->

					<!-- <v-btn class="mx-2" fab dark small color="teal">
						<v-icon dark> mdi-delete </v-icon>
					</v-btn> -->
				</div>
			</v-card-subtitle>

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
import root from './root.vue'

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
			this.$store.dispatch('storage/selectNodeTable', {
				path: item.path,
				historyAdd: true,
			})
		},

		rootClick() {
			this.$store.state.storage.tableSelectPath = ''
		},
	},
}
</script>
