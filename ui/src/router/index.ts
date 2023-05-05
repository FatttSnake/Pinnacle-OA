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
                },
                {
                    path: '/work',
                    name: 'work',
                    children: [
                        {
                            path: 'manage',
                            component: async () => await import('@/pages/work/Manage.vue'),
                            name: 'manage',
                            meta: {
                                title: '工作管理',
                                requiresScrollbar: false,
                                requiresPadding: true
                            }
                        },
                        {
                            path: 'task',
                            component: async () => await import('@/pages/work/Task.vue'),
                            name: 'task',
                            redirect: '/work/task/todo',
                            children: [
                                {
                                    path: 'todo',
                                    component: async () => await import('@/pages/work/Todo.vue'),
                                    name: 'todo'
                                },
                                {
                                    path: 'complete',
                                    component: async () =>
                                        await import('@/pages/work/Complete.vue'),
                                    name: 'complete'
                                }
                            ],
                            meta: {
                                title: '工作事项',
                                requiresScrollbar: false,
                                requiresPadding: true
                            }
                        }
                    ],
                    meta: {
                        title: '工作事项',
                        icon: IconPinnacleWork,
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
