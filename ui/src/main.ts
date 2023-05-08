import { createApp } from 'vue'
import App from '@/App.vue'
import router from '@/router'

import '@/assets/css/base.css'
import '@/assets/css/common.css'

import 'element-plus/theme-chalk/el-message.css'

const app = createApp(App)

app.use(router).mount('#app')
