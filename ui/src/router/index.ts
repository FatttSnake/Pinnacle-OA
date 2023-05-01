import { createRouter, createWebHistory } from 'vue-router'

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
        }
    ]
})

export default router
