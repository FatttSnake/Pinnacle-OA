const powerRouter = {
    path: '/power',
    name: 'powerManagement',
    redirect: '/power/role',
    children: [
        {
            path: 'role',
            name: 'roleManagement',
            component: async () => await import('@/pages/power/RoleManagement.vue'),
            meta: {
                title: '角色管理',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            }
        },
        {
            path: 'group',
            name: 'groupManagement',
            component: async () => await import('@/pages/power/GroupManagement.vue'),
            meta: {
                title: '用户组管理',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            }
        },
        {
            path: 'user',
            name: 'userManagement',
            component: async () => await import('@/pages/power/UserManagement.vue'),
            meta: {
                title: '用户管理',
                requiresMenu: true,
                requiresScrollbar: true,
                requiresPadding: true
            }
        }
    ],
    meta: {
        title: '权限管理',
        icon: shallowRef(IconPinnaclePower),
        requiresMenu: true,
        requiresAuth: true
    }
}

export default powerRouter
