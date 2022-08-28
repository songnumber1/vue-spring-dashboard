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
export default {
	components: {
		treeviewVue,
		tableVue,
	},
}
</script>
