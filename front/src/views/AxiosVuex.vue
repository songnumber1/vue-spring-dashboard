<template>
	<v-container>
		<v-row class="mb-10"> Axios Test Page </v-row>

		<v-row>
			<v-btn @click="AxiosText"> Axios Test1234 </v-btn>
		</v-row>
	</v-container>
</template>

<script>
import axios from '@/http/axios'

export default {
	name: 'DefaultLayout',
	components: {},
	data() {
		return {
			drawer: true,
		}
	},

	computed: {
		getTest: function () {
			return this.$store.state.app.appTitle
		},
	},

	methods: {
		AxiosText() {
			axios
				.get('https://reqres.in/api/user?page=2')
				.then((res) => {
					console.log(res)
					//this.axiosResult = res.data.data.map((item) => item.name)
				})
				.catch((err) => {
					console.log(err)
				})
				.then(() => {})
		},
	},
}
</script>

<style></style>
