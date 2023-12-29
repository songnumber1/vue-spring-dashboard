import axios from '@/http/axios'
import url from '@/http/url'
import eventBus from '@/data/EventBus.js'
import eventBusVariable from '@/data/EventBusVariable.js'

const auth = {
	namespaced: true,
	state: {},
	getters: {},
	mutations: {},
	actions: {
		loginProc(commit, { username, password }) {
			console.log(username, password)

			const data = {
				username: username,
				password: password,
			}

			console.log(data)

			// const config = {
			// 	headers: {
			// 		'Content-Type': 'multipart/form-data',
			// 	},
			// }

			let form = new FormData()
			form.append('username', username)
			form.append('password', password)

			return axios
				.post(
					url.AuthLogin,
					form
					// {
					// 	headers: {
					// 		'Content-type': 'application/x-www-form-urlencoded',
					// 	},
					// 	username: username,
					// 	password: password,
					// }
				)
				.then((res) => {
					console.log('1234', res.headers['Set-Cookie'])
				})
				.catch((err) => {
					console.log(err)
				})
		},
	},
}

export default auth
