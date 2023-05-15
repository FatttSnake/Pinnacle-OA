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
                        icon: shallowRef(IconPinnacleHome),
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
                            name: 'workManage',
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
                        icon: shallowRef(IconPinnacleWork),
                        requiresScrollbar: false,
                        requiresPadding: true
                    }
                },
                {
                    path: '/attendance',
                    name: 'attendance',
                    children: [
                        {
                            path: 'manage',
                            component: async () =>
                                await import('@/pages/attendance/AttendanceHome.vue'),
                            name: 'attendanceManage',
                            meta: {
                                title: '考勤管理',
                                requiresScrollbar: false,
                                requiresPadding: true
                            }
                        },
                        {
                            path: 'user',
                            component: async () =>
                                await import('@/pages/attendance/UserAttendance.vue'),
                            name: 'user',
                            meta: {
                                title: '员工考勤',
                                requiresScrollbar: false,
                                requiresPadding: true
                            }
                        }
                    ],
                    meta: {
                        title: '考勤',
                        icon: shallowRef(IconPinnacleAttendance),
                        requiresScrollbar: false,
                        requiresPadding: true
                    }
                },
                {
                    path: '/affair',
                    name: 'affair',
                    redirect: 'manage',
                    children: [
                        {
                            path: 'manage',
                            name: 'affairManage',
                            component: async () => await import('@/pages/affair/Affair.vue'),
                            meta: {
                                title: '事务管理',
                                requiresScrollbar: true,
                                requiresPadding: true
                            }
                        },
                        {
                            path: 'add',
                            component: async () => await import('@/pages/affair/AffairAdd.vue'),
                            name: 'affairAdd',
                            meta: {
                                title: '事务添加',
                                requiresScrollbar: true,
                                requiresPadding: true
                            }
                        },
                        {
                            path: 'approved',
                            component: async () =>
                                await import('@/pages/affair/AffairApproved.vue'),
                            name: 'affairApproved',
                            meta: {
                                title: '已审批事务',
                                requiresScrollbar: true,
                                requiresPadding: true
                            }
                        }
                    ],
                    meta: {
                        title: '事务',
                        icon: shallowRef(IconPinnacleHome),
                        requiresScrollbar: false,
                        requiresPadding: true
                    }
                },
                {
                    path: '/notice',
                    name: 'noticeHome',
                    meta: {
                        title: '公告管理',
                        icon: shallowRef(IconPinnacleNotice),
                        requiresScrollbar: false,
                        requiresPadding: true
                    },
                    children: [
                        {
                            path: 'noticeManage',
                            component: async () => await import('@/pages/notice/NoticeManage.vue'),
                            name: 'noticeManage',
                            meta: {
                                title: '公告管理',
                                requiresScrollbar: false,
                                requiresPadding: true
                            }
                        },
                        {
                            path: 'noticeView',
                            component: async () => await import('@/pages/notice/NoticeView.vue'),
                            name: 'noticeView',
                            meta: {
                                title: '公告查看',
                                requiresScrollbar: false,
                                requiresPadding: true
                            }
                        }
                    ]
                },
                {
                    path: '/power',
                    name: 'systemManagement',
                    children: [
                        {
                            path: 'role',
                            name: 'systemRole',
                            component: async () => await import('@/pages/power/RoleManagement.vue'),
                            meta: {
                                title: '角色管理',
                                requiresScrollbar: false,
                                requiresPadding: true
                            }
                        }
                    ],
                    meta: {
                        title: '权限管理',
                        icon: shallowRef(IconPinnaclePower),
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
