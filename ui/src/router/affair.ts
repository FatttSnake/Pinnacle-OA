const affairRouter = {
    path: '/affair',
    name: 'affair',
    redirect: 'manage',
    children: [
        {
            path: 'add',
            component: async () => await import('@/pages/affair/AffairAdd.vue'),
            name: 'affairAdd',
            meta: {
                title: '事务添加',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            }
        },
        {
            path: 'personalAffairs',
            component: async () => await import('@/pages/affair/PersonalAffairsView.vue'),
            name: 'PersonalAffairs',
            meta: {
                title: '我的事务',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            }
        },
        {
            path: 'manage',
            name: 'affairManage',
            component: async () => await import('@/pages/affair/Affair.vue'),
            meta: {
                title: '事务审批',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            }
        },
        {
            path: 'approved',
            component: async () => await import('@/pages/affair/AffairApproved.vue'),
            name: 'affairApproved',
            meta: {
                title: '审批记录',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            }
        }
    ],
    meta: {
        title: '事务',
        icon: shallowRef(IconPinnacleAffairs),
        requiresMenu: true,
        requiresScrollbar: false,
        requiresPadding: true
    }
}

export default affairRouter
