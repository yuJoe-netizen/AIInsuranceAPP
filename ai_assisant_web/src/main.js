import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axios from './axiosInstance.js';

import App from './App.vue'
import router from './router'

const app = createApp(App)
app.config.globalProperties.$axios = axios;
app.use(createPinia())
app.use(router)

app.mount('#app')
