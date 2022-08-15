import axios from 'axios'
import store from '@/store/index'

axios.defaults.baseURL = 'https://reqres.in/'

// Add a request interceptor
axios.interceptors.request.use(
	function (config) {
		// Do something before request is sent
		console.log('request interceptor!!!!')

		store.state['app'].isLoading = true

		return config
	},
	function (error) {
		// Do something with request error
		return Promise.reject(error)
	}
)

// Add a response interceptor
axios.interceptors.response.use(
	function (response) {
		// Any status code that lie within the range of 2xx cause this function to trigger
		// Do something with response data
		console.log('response interceptor!!!!')
		var timeout = setTimeout(function () {
			store.state['app'].isLoading = false
			clearTimeout(timeout)
			//실행할 코드
		}, 500)

		return response
	},
	function (error) {
		store.state['app'].isLoading = false
		// Any status codes that falls outside the range of 2xx cause this function to trigger
		// Do something with response error
		return Promise.reject(error)
	}
)

export default axios
