import { createApp } from 'vue'
import App from '@/App.vue'
import router from '@/router'

import { PRODUCTION_NAME } from './constants/Common.constants.js'

import '@/assets/css/base.css'

router.beforeEach((to, from, next) => {
    if (to.matched.length === 0) {
        from.path ? next({ path: from.path }) : next('/')
    } else {
        if (to.meta.title) {
            document.title = PRODUCTION_NAME + ' - ' + to.meta.title
        }
    }
})

const app = createApp(App)

app.use(router).mount('#app')
