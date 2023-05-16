const affairRouter = {
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
            component: async () => await import('@/pages/affair/AffairApproved.vue'),
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
        icon: shallowRef(IconPinnacleAffairs),
        requiresScrollbar: false,
        requiresPadding: true
    }
}

export default affairRouter
