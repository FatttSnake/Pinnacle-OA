const attendanceRouter = {
    path: '/attendance',
    name: 'attendance',
    children: [
        {
            path: 'manage',
            component: async () => await import('@/pages/attendance/AttendanceHome.vue'),
            name: 'attendanceManage',
            meta: {
                title: '考勤管理',
                requiresMenu: true,
                requiresScrollbar: false,
                requiresPadding: true
            }
        },
        {
            path: 'user',
            component: async () => await import('@/pages/attendance/UserAttendance.vue'),
            name: 'user',
            meta: {
                title: '员工考勤',
                requiresMenu: true,
                requiresScrollbar: false,
                requiresPadding: true
            }
        }
    ],
    meta: {
        title: '考勤',
        icon: shallowRef(IconPinnacleAttendance),
        requiresMenu: true,
        requiresScrollbar: false,
        requiresPadding: true,
        requiresAuth: true
    }
}

export default attendanceRouter
