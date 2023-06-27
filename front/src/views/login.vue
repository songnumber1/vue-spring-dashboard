<template>
	<v-app>
		<v-container fill-height fluid style="max-width: 450px">
			<v-row row wrap align-center>
				<v-col xs12>
					<v-alert class="mb-3" type="error" v-if="false">
						아이디와 비밀번호를 확인해주세요.
					</v-alert>

					<v-alert class="mb-3" type="success" v-if="false">
						로그인이 완료되었습니다.
					</v-alert>

					<!-- <v-alert class="mb-3" :value="isLoginError" type="error">
                  아이디와 비밀번호를 확인해주세요.
              </v-alert>

              <v-alert class="mb-3" :value="isLogin" type="success">
                  로그인이 완료되었습니다.
              </v-alert> -->

					<v-card>
						<v-toolbar flat>
							<v-toolbar-title>로그인</v-toolbar-title>
						</v-toolbar>

						<div class="pa-3">
							<v-text-field
								label="이메일을 입력하세요."
								v-model="email"
							></v-text-field>
							<v-text-field
								label="비밀본호를 입력하세요."
								v-model="password"
							></v-text-field>

							<v-btn color="primary" depressed block large @click="login"
								>로그인</v-btn
							>

							<!-- <v-text-field v-model="email" label="이메일을 입력하세요."></v-text-field>
                      <v-text-field v-model="password" label="비밀본호를 입력하세요."></v-text-field>

                      <v-btn color="primary" depressed block large @click="login({email, password})">로그인</v-btn> -->
							<br /><v-btn @click="test">테스트</v-btn>
							<br />
							<v-textarea
								class="mt-3"
								outlined
								name="input-7-1"
								label="Axios result (Users)"
								v-model="axiosResult"
							/>
						</div>
					</v-card>
				</v-col>
			</v-row>
		</v-container>
	</v-app>
</template>

<script>
//import { mapState, mapActions } from "vuex"
import axios from 'axios'

export default {
	data() {
		return {
			email: null,
			password: null,
			axiosResult: null,
			alignments: ['start', 'center', 'end'],
		}
	},
	computed: {
		//...mapState(["isLogin", "isLoginError"])
	},
	methods: {
		//...mapActions("[login"),
		login() {
			this.$store.dispatch('auth/loginProc', {
				username: this.email,
				password: this.password,
			})
		},
		test() {
			axios
				.get('https://reqres.in/api/user?page=2')
				.then((res) => {
					console.log(res)
					this.axiosResult = res.data.data.map((item) => item.name)
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
