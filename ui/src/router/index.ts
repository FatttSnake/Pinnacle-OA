import { createRouter, createWebHistory } from 'vue-router'
import { PRODUCTION_NAME } from '@/constants/Common.constants'
import { getLoginStatus, getUser } from '@/utils/auth'
import workRouter from '@/router/work'
import attendanceRouter from '@/router/attendance'
import affairRouter from '@/router/affair'
import noticeRouter from '@/router/notice'
import powerRouter from '@/router/power'
import _ from 'lodash'
import infoRouter from '@/router/info'

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
                        icon: shallowRef(IconPinnacleHome),
                        requiresMenu: true,
                        requiresScrollbar: false,
                        requiresPadding: true,
                        requiresAuth: false
                    }
                },
                {
                    path: '/profile',
                    component: async () => await import('@/pages/profile/PersonalProfile.vue'),
                    name: 'profile',
                    meta: {
                        title: '个人档案'
                    }
                },
                workRouter,
                noticeRouter,
                attendanceRouter,
                affairRouter,
                infoRouter,
                powerRouter
            ]
        },
        {
            path: '/login',
            component: async () => await import('@/pages/Login.vue'),
            name: 'Login',
            meta: {
                title: '登录',
                requiresAuth: false
            }
        }
    ]
})

router.beforeEach(async (to, from, next) => {
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
                if (to.meta.requiresAuth === true) {
                    const user = await getUser()
                    const menus = user.menus
                    for (const menu of menus) {
                        if (menu.url === '/') continue
                        if (_.startsWith(to.path, menu.url)) {
                            next()
                            return
                        }
                    }
                    next('/')
                } else {
                    next()
                }
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
