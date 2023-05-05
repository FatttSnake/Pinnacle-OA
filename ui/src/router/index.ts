import { createRouter, createWebHistory } from 'vue-router'
import { PRODUCTION_NAME } from '@/constants/Common.constants'
import { getLoginStatus } from '@/utils/auth'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: async () => await import('@/pages/Main.vue'),
            children: [
                {
                    path: '',
                    redirect: 'home'
                },
                {
                    path: '/home',
                    component: async () => await import('@/pages/home/Home.vue'),
                    name: 'home',
                    meta: {
                        title: '首页',
                        icon: IconPinnacleHome,
                        requiresScrollbar: false,
                        requiresPadding: true
                    }
                }
            ]
        },
        {
            path: '/login',
            component: async () => await import('@/pages/Login.vue'),
            name: 'Login',
            meta: {
                title: '登录'
            }
        }
    ]
})

router.beforeEach((to, from, next) => {
    if (to.matched.length === 0) {
        from.path !== '' ? next({ path: from.path }) : next('/')
    } else {
        if (to.meta.title !== '') {
            // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
            document.title = `${PRODUCTION_NAME} - ${to.meta.title}`
        }

        if (getLoginStatus()) {
            if (to.name === 'Login') {
                next('/')
            } else {
                next()
            }
        } else {
            if (to.name === 'Login') {
                next()
            } else {
                next('/login')
            }
        }
    }
})

export default router
