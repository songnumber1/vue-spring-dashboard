import Vue from "vue";
import VueRouter from "vue-router";
import Dashboard from "@/views/dashboard";
import GridSystem from "@/views/gridSystem";
import AxiosVuex from "@/views/axiosVuex";
import BreakPoint from "@/views/breakPoint";
import Storage from "@/views/storage/storage";
import ImageViewer from "@/views/imageViewer";
import DefaultLayout from "@/layout/default/index";
import Login from "@/views/login";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: DefaultLayout,
    children: [
      {
        path: "/",
        name: "Dashboard",
        component: Dashboard,
      },
      {
        path: "/axios-vuex",
        name: "AxiosVuex",
        component: AxiosVuex,
      },
      {
        path: "/grid-system",
        name: "GridSystem",
        component: GridSystem,
      },
      {
        path: "/break-point",
        name: "BreakPoint",
        component: BreakPoint,
      },
      {
        path: "/storage",
        name: "Storage",
        component: Storage,
      },
      {
        path: "/image-viewer",
        name: "ImageViewer",
        component: ImageViewer,
      },
      {
        path: "/auth/login",
        name: "Login",
        component: Login,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
