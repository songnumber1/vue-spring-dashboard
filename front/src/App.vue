<template>
	<v-fade-transition mode="out-in">
		<v-app>
			<v-layout
				justify-center
				v-show="this.$store.state['app'].isLoading"
				fluid
			>
				<v-card-actions>
					<v-progress-circular
						:size="50"
						color="primary"
						indeterminate
					></v-progress-circular>
				</v-card-actions>
			</v-layout>

			<v-container v-show="!this.$store.state['app'].isLoading" fluid>
				<router-view></router-view>
			</v-container>
		</v-app>
	</v-fade-transition>
</template>

<script>
export default {
	name: 'App',
}
</script>
