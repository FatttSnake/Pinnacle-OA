const infoRouter = {
    path: '/info',
    name: 'infoManagement',
    redirect: '/info/staff',
    children: [
        {
            path: 'staff',
            name: 'staffManagement',
            component: async () => await import('@/pages/info/StaffManagement.vue'),
            meta: {
                title: '员工信息管理',
                requiresMenu: true,
                requiresScrollbar: false,
                requiresPadding: true
            }
        }
    ],
    meta: {
        title: '信息管理',
        icon: shallowRef(IconPinnacleInfo),
        requiresMenu: true,
        requiresScrollbar: false,
        requiresPadding: true,
        requiresAuth: true
    }
}

export default infoRouter
