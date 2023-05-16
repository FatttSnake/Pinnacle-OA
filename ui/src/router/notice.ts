const noticeRouter = {
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
            redirect: '/notice/noticeView/all',
            meta: {
                title: '公告查看',
                requiresScrollbar: false,
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
            path: 'noticeTypeManage',
            component: async () => await import('@/pages/notice/NoticeTypeManage.vue'),
            name: 'noticeTypeManage',
            meta: {
                title: '公告类型管理',
                requiresScrollbar: false,
                requiresPadding: true
            }
        }
    ]
}

export default noticeRouter
