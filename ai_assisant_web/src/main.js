import './assets/main.css'
import * as Icons from '@ant-design/icons-vue'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axios from './axiosInstance.js';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';

import App from './App.vue'
import router from './router'

const app = createApp(App)
app.config.globalProperties.$axios = axios;
app.use(createPinia())
app.use(router)
app.use(Antd)

const icons = Icons;

for (const i in icons) {
  app.component(i, icons[i]);
}

app.mount('#app')
