<template lang="">
	<div>
		<v-row align="center">
			<v-col cols="12">
				<v-select
					v-model="objectfitModel"
					:items="items"
					filled
					label="object-fit"
					@change="objectfitSelected"
				></v-select>
			</v-col>
		</v-row>

		<v-row>
			<v-col cols="6" v-for="person in people" :key="person.id">
				<v-card class="pa-3" outlined>
					<a :href="person.profileUrl" target="_blank"
						><img :src="person.profileUrl" :style="objectfitType" /></a
				></v-card>
			</v-col>
		</v-row>
	</div>
</template>
<script>
export default {
	data() {
		return {
			people: [
				{
					// ... 생략
					profileUrl: require(`@/assets/ImageViewer_Test1.png`),
				},
				{
					// ... 생략
					profileUrl: require(`@/assets/ImageViewer_Test2.png`),
				},
			],

			// fill	기본값. 주어진 너비와 높이에 딱 맞도록 사이즈를 조절합니다. 이미지의 가로세로 비율은 유지되지 않아요.
			// contain	가로세로 비율을 유지한 채로 사이즈가 조절되지만, 이미지와 컨테이너 간의 비율이 맞지 않는 경우엔 자리가 남게 됩니다.
			// cover	가로세로 비율을 유지한 채로 사이즈가 조절되며, 비율이 맞지 않더라도 이미지를 확대해 컨테이너를 완전히 채웁니다.
			// 	none	아무것도 하지 않고 본래의 이미지 사이즈를 유지합니다. 그래도 이미지 가운데가 보여지도록 하는 센스가 있어요.
			// 	scale - down	none 또는 contain 중에 더 적절한 방향으로 이미지 사이즈를 조절합니다.
			// https://nykim.work/86

			items: ['fill', 'contain', 'cover', 'none', 'scale-down'],

			objectfitType: {},
			objectfitModel: 'fill',
		}
	},
	methods: {
		objectfitSelected(selectObj) {
			let result = { 'object-fit': '' }
			result['object-fit'] = selectObj
			this.objectfitType = result
			return result
		},
	},
}
</script>
<style>
img {
	width: 600px;
	height: 600px;
	object-fit: fill;
	margin: auto;
	display: block;
}
</style>
