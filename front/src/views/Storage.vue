<template>
	<div>
		<v-card>
			<Split style="height: 88vh">
				<SplitArea :size="20"> <treeviewVue></treeviewVue> </SplitArea>
				<SplitArea :size="80"> <tableVue></tableVue> </SplitArea>
			</Split>
		</v-card>
	</div>
</template>

<script>
import treeviewVue from './tree/treeview.vue'
import tableVue from './tree/table.vue'
import eventBus from '@/data/EventBus.js'
import eventBusVariable from '@/data/EventBusVariable.js'

export default {
	data() {
		return {
			eventBusTest: '',
		}
	},
	components: {
		treeviewVue,
		tableVue,
	},
	created() {
		eventBus.$on(eventBusVariable.eventBusStorageSelectNodeTable, (data) => {
			this.eventBusTest = data
			console.log(this.eventBusTest)
		})
	},
	beforeDestroy() {
		eventBus.$off(eventBusVariable.eventBusStorageSelectNodeTable)
	},
}
</script>
