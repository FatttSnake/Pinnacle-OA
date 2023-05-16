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
}

export default attendanceRouter
