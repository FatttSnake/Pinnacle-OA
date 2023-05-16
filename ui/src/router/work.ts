const workRouter = {
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
                    component: async () => await import('@/pages/work/Complete.vue'),
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
}

export default workRouter
