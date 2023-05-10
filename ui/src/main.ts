import { createApp } from 'vue'
import App from '@/App.vue'
import router from '@/router'

import '@/assets/css/base.css'
import '@/assets/css/common.css'

import 'element-plus/theme-chalk/el-message.css'
import { createPinia } from 'pinia'

const app = createApp(App)
const pinia = createPinia()

app.use(router).use(pinia).mount('#app')
