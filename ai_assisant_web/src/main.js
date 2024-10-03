import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axios from './axiosInstance.js';
import VuePlyr from 'vue-plyr'
import 'vue-plyr/dist/vue-plyr.css'

import App from './App.vue'
import router from './router'

const app = createApp(App)
app.config.globalProperties.$axios = axios;
app.use(createPinia())
app.use(router)
app.use(VuePlyr, {
    plyr: {}
  })

app.mount('#app')
