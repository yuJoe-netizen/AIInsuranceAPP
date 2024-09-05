import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
// import VueNativeSock from "vue-native-websocket-vue3";

import App from './App.vue'
import router from './router'

const app = createApp(App)
// app.use(VueNativeSock, 'ws://127.0.0.1:8891/call');

app.use(createPinia())
app.use(router)

app.mount('#app')
