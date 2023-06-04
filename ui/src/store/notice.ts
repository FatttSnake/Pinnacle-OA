import { defineStore } from 'pinia'
import request from '@/services'
import { ElMessage } from 'element-plus'
import {
    DATABASE_SAVE_ERROR,
    DATABASE_SAVE_OK,
    DATABASE_SELECT_OK,
    DATABASE_UPDATE_ERROR,
    DATABASE_UPDATE_OK
} from '@/constants/Common.constants'

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
            pageSize: 5,
            currentPage: 1,
            search: {
                title: '',
                type: '',
                startTime: '',
                endTime: '',
                userIdList: []
            },
            searchBySelf: {
                title: '',
                type: '',
                startTime: '',
                endTime: ''
            },
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
            showLoading: true,
            dialogShowVisible: false,
            dialogAddVisible: false,
            dialogEditVisible: false,
            editFlag: false,
            currentViewPage: 'All',
            hackReset: true,
            departmentList: [],
            senderList: [],
            multiDeleteSelection: [],
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
        selectAllNotice(
            currentPage: number,
            pageSize: number,
            title: string,
            type: string,
            startTime: string,
            endTime: string,
            userIdList: []
        ) {
            void request
                .get('/notice/page', {
                    currentPage,
                    pageSize,
                    title,
                    type,
                    startTime,
                    endTime,
                    userIdList: userIdList.toString() + ''
                })
                .then((response) => {
                    if (response.data.code === DATABASE_SELECT_OK) {
                        this.selectData = response.data.data
                        this.total = parseInt(response.data.msg)
                        this.loading = false
                    } else {
                        this.loading = false
                        ElMessage({
                            message: response.data.msg,
                            type: 'error'
                        })
                    }
                })
        },
        async selectAllNoticeSelf(
            readStatus: number,
            title: string,
            type: string,
            startTime: string,
            endTime: string
        ) {
            await request
                .get('/notice/self', {
                    readStatus,
                    title,
                    type,
                    startTime,
                    endTime
                })
                .then((response) => {
                    if (response.data.code === DATABASE_SELECT_OK) {
                        this.selectData = response.data.data
                        this.showLoading = false
                    } else {
                        this.showLoading = false
                        ElMessage({
                            message: response.data.msg,
                            type: 'error'
                        })
                    }
                })
        },
        async selectDepartment() {
            await request.get('/department/user').then((response) => {
                this.departmentList = response.data.data
            })
        },
        async handleAddNotice(addFormData: IAddNoticeData) {
            await request.post('/notice', addFormData).then((response) => {
                if (response.data.code === DATABASE_SAVE_OK) {
                    this.dialogAddVisible = false
                    ElMessage({
                        message: '发布成功.',
                        type: 'success'
                    })
                } else if (response.data.code === DATABASE_SAVE_ERROR) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            this.selectAllNotice(1, 5, '', '', '', '', [])
        },
        async handleUpdateNotice(updateNotice: IAddNoticeData) {
            await request.put('/notice', updateNotice).then((response) => {
                if (response.data.code === DATABASE_UPDATE_OK) {
                    this.dialogEditVisible = false
                    this.editFlag = false
                    ElMessage({
                        message: '修改成功.',
                        type: 'success'
                    })
                } else if (response.data.code === DATABASE_UPDATE_ERROR) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            this.selectAllNotice(1, 5, '', '', '', '', [])
            this.hackReset = false
        },
        async modifyNoticeIsRead(notice: INotice) {
            await request.put('/notice/modify_notice_read', notice).then((response) => {
                if (response.data.code === DATABASE_UPDATE_ERROR) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
        },
        async modifyTop(notice: INotice) {
            await request.put('/notice/update_notice_top', notice).then((response) => {
                if (response.data.code === DATABASE_UPDATE_OK) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'success'
                    })
                } else if (response.data.code === DATABASE_UPDATE_ERROR) {
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
            pageSize: 5,
            currentPage: 1,
            dataLoading: true,
            dialogAddTypeVisible: false,
            dialogEditTypeVisible: false,
            hackReset: true,
            editFlag: false,
            enableNoticeTypeList: [],
            multiDeleteSelection: [],
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
        async selectNoticeType(currentPage: number, pageSize: number) {
            await request.get('/notice_type/page', { currentPage, pageSize }).then((response) => {
                if (response.data.code === DATABASE_SELECT_OK) {
                    this.noticeTypeList = response.data.data
                    this.total = parseInt(response.data.msg)
                    if (this.noticeTypeList.length !== 0) {
                        this.dataLoading = false
                    }
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
                    if (response.data.code === DATABASE_UPDATE_OK) {
                        ElMessage({
                            message: '修改成功.',
                            type: 'success'
                        })
                    } else if (response.data.code === DATABASE_UPDATE_ERROR) {
                        ElMessage({
                            message: response.data.msg,
                            type: 'error'
                        })
                    }
                })
        },
        async handleAddNoticeType(addFormData: IAddNoticeTypeData) {
            await request.post('/notice_type', addFormData).then((response) => {
                if (response.data.code === DATABASE_SAVE_OK) {
                    this.dialogAddTypeVisible = false
                    ElMessage({
                        message: '添加成功.',
                        type: 'success'
                    })
                } else if (response.data.code === DATABASE_SAVE_ERROR) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            await this.selectNoticeType(1, 5)
        },
        async handleUpdateNoticeType(updateNotice: IAddNoticeTypeData) {
            await request.put('/notice_type', updateNotice).then((response) => {
                if (response.data.code === DATABASE_UPDATE_OK) {
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
                } else if (response.data.code === DATABASE_UPDATE_ERROR) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            await this.selectNoticeType(this.currentPage, this.pageSize)
        }
    }
})
