<template>
	<v-container fluid>
		<v-card class="pa-3">
			<v-row no-gutters>
				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>
			</v-row>

			<v-row>
				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>
			</v-row>
		</v-card>

		<v-card class="pa-3">
			<v-row :style="{ height: '120px' }" class="red" align="end">
				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>
			</v-row>

			<v-row :style="{ height: '120px' }" class="blue" align="center">
				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col>
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>
			</v-row>

			<v-row :style="{ height: '120px' }" class="green">
				<v-col align-self="start">
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col align-self="center">
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>

				<v-col align-self="end">
					<v-card class="pa-3" outlined> Col</v-card>
				</v-col>
			</v-row>
		</v-card>

		<v-card class="mt-5">
			<v-layout>
				<v-flex>
					<v-card class="pa-3" outlined> Col </v-card>
				</v-flex>

				<v-flex>
					<v-card class="pa-3" outlined> Col </v-card>
				</v-flex>

				<v-flex>
					<v-card class="pa-3" outlined> Col </v-card>
				</v-flex>
			</v-layout>

			<v-layout>
				<v-flex>
					<v-card class="pa-3" outlined> Col </v-card>
				</v-flex>

				<v-spacer></v-spacer>
				<v-flex>
					<v-card class="pa-3" outlined> Col </v-card>
				</v-flex>

				<v-flex>
					<v-card class="pa-3" outlined> Col </v-card>
				</v-flex>
			</v-layout>
		</v-card>
	</v-container>
</template>
<script></script>
<style></style>
