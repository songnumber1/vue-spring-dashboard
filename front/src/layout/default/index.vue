<template lang="">
	<v-app>
		<v-app-bar app color="primary" dark>
			<v-app-bar-nav-icon @click="drawer = !drawer" />
			<v-toolbar-title>{{ $route.name }}</v-toolbar-title>
			<v-spacer />
		</v-app-bar>
		<v-navigation-drawer
			v-model="drawer"
			dark
			:src="require('@/assets/sidebar.jpg')"
			app
		>
			<template #img="props">
				<v-img :gradient="gradient" v-bind="props" />
			</template>
			<v-list-item>
				<v-list-item-avatar>
					<v-img :src="require('@/assets/logo-bg.png')" />
				</v-list-item-avatar>
				<v-list-item-content>
					<v-list-item-title class="text-h6">
						{{ this.$store.state['app'].appTitle }}
						<!-- {{ this.$store.state.app.appTitle }} -->
					</v-list-item-title>
					<v-list-item-subtitle>
						{{ this.$store.state.app.appVersion }}
					</v-list-item-subtitle>
				</v-list-item-content>
			</v-list-item>
			<v-divider />
			<v-list dense nav>
				<v-list-item
					v-for="(item, index) in getMenu"
					:key="`item-${index}`"
					link
					:to="item.to"
					class="py-1"
					active-class="primary white--text"
				>
					<v-list-item-icon>
						<v-icon>{{ item.icon }}</v-icon>
					</v-list-item-icon>
					<v-list-item-content>
						<v-list-item-title>{{ item.title }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>
		<v-main>
			<v-container fluid>
				<router-view />
			</v-container>
		</v-main>
	</v-app>
</template>
<script>
export default {
	name: 'defaultLayout',
	data() {
		return {
			drawer: true,
			gradient: 'rgba(0, 0, 0, .7), rgba(0, 0, 0, .7)',
			right: null,
		}
	},

	computed: {
		getMenu() {
			return this.$store.state.app.menus
		},
	},
}
</script>
<style lang=""></style>
