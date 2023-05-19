import { defineStore } from 'pinia'
import request from '@/services'
import { ElMessage } from 'element-plus'

export interface IAddFormData {
    title: string
    typeId: string
    sendTime: string
    endTime: string
    top: boolean
    priority: number
    content: string
    receivers: []
}
export interface INotice {
    content: string
    createTime: string
    endTime: string
    id: string
    modifyTime: string
    priority: number
    receivers: []
    sendTime: string
    title: string
    top: number
    isRead: number
    noticeType: {
        id: string
        name: string
        enable: number
    }
    sender: {
        id: string
        username: string
        enable: number
    }
    senderId: string
    typeId: string
}
export const useNoticeStore = defineStore('notice', {
    state: () => {
        return {
            total: 0,
            selectData: [
                {
                    content: '',
                    createTime: '',
                    endTime: '',
                    id: '',
                    modifyTime: '',
                    priority: 0,
                    receivers: [],
                    sendTime: '',
                    title: '',
                    top: 0,
                    isRead: 0,
                    noticeType: {
                        id: '',
                        name: '',
                        enable: 0
                    },
                    sender: {
                        id: '',
                        username: '',
                        enable: 0
                    },
                    senderId: '',
                    typeId: ''
                }
            ],
            loading: true,
            dialogShowVisible: false,
            dialogAddVisible: false,
            dialogEditVisible: false,
            editFlag: false,
            currentViewPage: 'All',
            hackReset: true,
            EnableNoticeTypeList: [],
            noticeTypeList: [
                {
                    id: '',
                    name: '',
                    enable: true
                }
            ],
            departmentList: [],
            noticeShowData: {
                content: '',
                createTime: '',
                endTime: '',
                id: '',
                modifyTime: '',
                priority: 0,
                receivers: [],
                sendTime: '',
                title: '',
                top: 0,
                isRead: 0,
                noticeType: {
                    id: '',
                    name: '',
                    enable: 0
                },
                sender: {
                    id: '',
                    username: '',
                    enable: 0
                },
                senderId: '',
                typeId: ''
            }
        }
    },
    getters: {},
    actions: {
        selectAllNotice(currentPage: number, pageSize: number) {
            void request
                .get('/notice/page', {
                    currentPage,
                    pageSize
                })
                .then((response) => {
                    this.selectData = response.data.data
                    this.total = parseInt(response.data.msg)
                    if (this.selectData.length !== 0) {
                        this.loading = false
                    }
                })
        },
        async selectAllNoticeByUserId(readStatus: number) {
            await request
                .get('/notice/ByUserId', {
                    readStatus
                })
                .then((response) => {
                    this.selectData = response.data.data
                    if (this.selectData.length !== 0) {
                        this.loading = false
                    }
                })
        },
        async selectEnableNoticeType() {
            await request.get('/noticeType/enable').then((response) => {
                this.EnableNoticeTypeList = response.data.data
            })
        },
        async selectNoticeType() {
            await request.get('/noticeType').then((response) => {
                this.noticeTypeList = response.data.data
                if (response.data.data.length >= 0) {
                    for (let i = 0; i < this.noticeTypeList.length; i++) {
                        this.noticeTypeList[i].enable = response.data.data[i].enable === 1
                    }
                    this.loading = false
                }
            })
        },
        async selectDepartment() {
            await request.get('/department').then((response) => {
                this.departmentList = response.data.data
            })
        },
        async handleAddNotice(addFormData: IAddFormData) {
            await request.post('/notice', addFormData).then((response) => {
                if (response.data.code === 20022) {
                    this.dialogAddVisible = false
                    ElMessage({
                        message: '发布成功.',
                        type: 'success'
                    })
                } else if (response.data.code === 20032) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            this.selectAllNotice(1, 5)
        },
        async handleUpdateNotice(updateNotice: IAddFormData) {
            await request.put('/notice', updateNotice).then((response) => {
                if (response.data.code === 20023) {
                    this.dialogEditVisible = false
                    ElMessage({
                        message: '修改成功.',
                        type: 'success'
                    })
                } else if (response.data.code === 20033) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            this.selectAllNotice(1, 5)
            this.hackReset = false
        },
        async updateNoticeTypeEnable(typeId: string, enable: boolean) {
            await request
                .get('/noticeType/update', {
                    typeId,
                    enable
                })
                .then((response) => {
                    if (response.data.code === 20023) {
                        ElMessage({
                            message: '修改成功.',
                            type: 'success'
                        })
                    } else if (response.data.code === 20033) {
                        ElMessage({
                            message: response.data.msg,
                            type: 'error'
                        })
                    }
                })
        },
        async modifyNoticeIsRead(notice: INotice) {
            await request.put('/notice/modifyNoticeIsRead', notice).then((response) => {
                if (response.data.code === 20033) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
        },
        async modifyTop(notice: INotice) {
            await request.put('/notice/updateNoticeTop', notice).then((response) => {
                if (response.data.code === 20023) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'success'
                    })
                } else if (response.data.code === 20033) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
        }
    }
})
