const noticeRouter = {
    path: '/notice',
    name: 'noticeHome',
    meta: {
        title: '公告',
        icon: shallowRef(IconPinnacleNotice),
        requiresMenu: true,
        requiresScrollbar: false,
        requiresPadding: true,
        requiresAuth: true
    },
    children: [
        {
            path: 'view',
            component: async () => await import('@/pages/notice/NoticeView.vue'),
            name: 'noticeView',
            redirect: '/notice/view/all',
            meta: {
                title: '公告查看',
                requiresScrollbar: false,
                requiresMenu: true,
                requiresPadding: true
            },
            children: [
                {
                    path: 'all',
                    component: async () => await import('@/pages/notice/AllReceiveNoticeView.vue'),
                    name: 'all'
                },
                {
                    path: 'alRead',
                    component: async () => await import('@/pages/notice/AlReadView.vue'),
                    name: 'alRead'
                },
                {
                    path: 'toRead',
                    component: async () => await import('@/pages/notice/ToReadView.vue'),
                    name: 'toRead'
                }
            ]
        },
        {
            path: 'manage',
            component: async () => await import('@/pages/notice/NoticeManage.vue'),
            name: 'noticeManage',
            meta: {
                title: '公告管理',
                requiresMenu: true,
                requiresScrollbar: false,
                requiresPadding: true
            }
        },
        {
            path: 'typeManage',
            component: async () => await import('@/pages/notice/NoticeTypeManage.vue'),
            name: 'noticeTypeManage',
            meta: {
                title: '公告类型管理',
                requiresMenu: true,
                requiresScrollbar: false,
                requiresPadding: true
            }
        }
    ]
}

export default noticeRouter
