<template>
	<v-container fluid>
		<v-row>
			<v-col v-for="item in getRootItem" :key="item.id">
				<v-card class="pa-3">
					<v-row class="mb-1">
						<v-card-actions>
							<v-btn text color="teal accent-4" @click="selectDriver(item)">
								드라이브 {{ item.name.replaceAll('\\', '') }}
							</v-btn>
						</v-card-actions>
					</v-row>

					<v-progress-linear
						:value="item.useSizePercent"
						height="25"
						color="amber"
					>
						<strong>{{ Math.ceil(item.useSizePercent) }}%</strong>
					</v-progress-linear>

					<v-row justify="end">
						<v-col cols="auto" align-self="center">
							<v-card-subtitle>
								{{ item.useSize }} / {{ item.totalSize }}
							</v-card-subtitle>
						</v-col>
					</v-row>
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>
<script>
export default {
	data: () => ({
		skill: 20,
	}),

	computed: {
		getRootItem() {
			return this.$store.state.storage.rootItems
		},
	},

	methods: {
		selectDriver(event) {
			this.$store.dispatch('storage/selectNodeTable', event.absolutePath)
		},
	},
}
</script>
