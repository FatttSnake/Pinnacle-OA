import { defineStore } from 'pinia'
import request from '@/services'
import { ElMessage } from 'element-plus'

export interface IAddNoticeData {
    title: string
    typeId: string
    sendTime: string
    endTime: string
    top: number
    priority: number
    content: string
    receivers: []
}
export interface IAddNoticeTypeData {
    id: string
    name: string
    enable: number
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
                        enable: 1
                    },
                    sender: {
                        id: '',
                        username: '',
                        enable: 1
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
                    enable: 1
                },
                sender: {
                    id: '',
                    username: '',
                    enable: 1
                },
                senderId: '',
                typeId: ''
            }
        }
    },
    getters: {},
    actions: {
        async selectAllNotice(currentPage: number, pageSize: number) {
            void request
                .get('/notice/page', {
                    currentPage,
                    pageSize
                })
                .then((response) => {
                    if (response.data.code === 20021) {
                        this.selectData = response.data.data
                        this.total = parseInt(response.data.msg)
                        if (this.selectData.length !== 0) {
                            this.loading = false
                        }
                    } else {
                        this.loading = false
                        ElMessage({
                            message: response.data.msg,
                            type: 'error'
                        })
                    }
                })
        },
        async selectAllNoticeSelf(readStatus: number) {
            await request
                .get('/notice/self', {
                    readStatus
                })
                .then((response) => {
                    if (response.data.code === 20021) {
                        this.selectData = response.data.data
                        if (this.selectData.length !== 0) {
                            this.loading = false
                        }
                    } else {
                        this.loading = false
                        ElMessage({
                            message: response.data.msg,
                            type: 'error'
                        })
                    }
                })
        },
        async selectDepartment() {
            await request.get('/department').then((response) => {
                this.departmentList = response.data.data
            })
        },
        async handleAddNotice(addFormData: IAddNoticeData) {
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
            await this.selectAllNotice(1, 5)
        },
        async handleUpdateNotice(updateNotice: IAddNoticeData) {
            await request.put('/notice', updateNotice).then((response) => {
                if (response.data.code === 20023) {
                    this.dialogEditVisible = false
                    this.editFlag = false
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
            await this.selectAllNotice(1, 5)
            this.hackReset = false
        },
        async modifyNoticeIsRead(notice: INotice) {
            await request.put('/notice/modify_notice_read', notice).then((response) => {
                if (response.data.code === 20033) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
        },
        async modifyTop(notice: INotice) {
            await request.put('/notice/update_notice_top', notice).then((response) => {
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

export const useNoticeTypeStore = defineStore('notice_type', {
    state: () => {
        return {
            total: 0,
            dataLoading: true,
            dialogAddTypeVisible: false,
            dialogEditTypeVisible: false,
            hackReset: true,
            editFlag: false,
            enableNoticeTypeList: [],
            noticeTypeList: [
                {
                    id: '',
                    name: '',
                    enable: 1
                }
            ],
            addTypeData: {
                id: '',
                name: '',
                enable: 1
            },
            showTypeData: {
                id: '',
                name: '',
                enable: 1
            }
        }
    },
    actions: {
        async selectEnableNoticeType() {
            await request.get('/notice_type/enable').then((response) => {
                this.enableNoticeTypeList = response.data.data
            })
        },
        async selectNoticeType() {
            await request.get('/notice_type').then((response) => {
                if (response.data.code === 20021) {
                    this.noticeTypeList = response.data.data
                    this.dataLoading = false
                } else {
                    this.dataLoading = false
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
        },
        async updateNoticeTypeEnable(typeId: string, enable: number) {
            await request
                .get('/notice_type/update', {
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
        async handleAddNoticeType(addFormData: IAddNoticeTypeData) {
            await request.post('/notice_type', addFormData).then((response) => {
                if (response.data.code === 20022) {
                    this.dialogAddTypeVisible = false
                    ElMessage({
                        message: '添加成功.',
                        type: 'success'
                    })
                } else if (response.data.code === 20032) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            await this.selectNoticeType()
        },
        async handleUpdateNoticeType(updateNotice: IAddNoticeTypeData) {
            await request.put('/notice_type', updateNotice).then((response) => {
                if (response.data.code === 20023) {
                    this.dialogEditTypeVisible = false
                    this.editFlag = false
                    this.hackReset = false
                    this.addTypeData = {
                        id: '',
                        name: '',
                        enable: 1
                    }
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
            await this.selectNoticeType()
        }
    }
})
