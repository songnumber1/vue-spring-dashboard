const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
	transpileDependencies: ['vuetify'],
	lintOnSave: false, // 이부분을 추가하면 eslint가 꺼지고 파일 저장 시 자동 정렬이 된다
	// devServer: {
	// 	proxy: {
	// 		'/': {
	// 			target: 'http://localhost:8081/',
	// 			pathRewrite: { '^/': '' },
	// 			changeOrigin: true,
	// 			secure: false,
	// 			ws: false,
	// 		},
	// 	},
	// 	historyApiFallback: true,
	// },
})
