const affairRouter = {
    path: '/affair',
    name: 'affair',
    children: [
        {
            path: 'personal',
            component: async () => await import('@/pages/affair/PersonalAffairsView.vue'),
            name: 'PersonalAffairs',
            redirect: '/affair/personal/person',
            meta: {
                title: '我的事务',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            },
            children: [
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
                    path: 'person',
                    component: async () => await import('@/pages/affair/PersonAffair.vue'),
                    name: 'person',
                    meta: {
                        title: '事务',
                        requiresScrollbar: true,
                        requiresPadding: true
                    }
                }
            ]
        },
        {
            path: 'manage',
            component: async () => await import('@/pages/affair/AffairManage.vue'),
            name: 'affairManage',
            redirect: '/affair/manage/toApprove',
            meta: {
                title: '事务管理',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            },
            children: [
                {
                    path: 'toApprove',
                    name: 'toApprove',
                    component: async () => await import('@/pages/affair/AffairNotApproved.vue'),
                    meta: {
                        title: '事务审批',
                        requiresScrollbar: true,
                        requiresPadding: true
                    }
                },
                {
                    path: 'approved',
                    component: async () => await import('@/pages/affair/AffairApproved.vue'),
                    name: 'approved',
                    meta: {
                        title: '审批记录',
                        requiresScrollbar: true,
                        requiresPadding: true
                    }
                }
            ]
        }
    ],
    meta: {
        title: '事务',
        icon: shallowRef(IconPinnacleAffairs),
        requiresMenu: true,
        requiresScrollbar: false,
        requiresPadding: true,
        requiresAuth: true
    }
}

export default affairRouter
