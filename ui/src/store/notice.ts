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
export const useNoticeStore = defineStore('notice', {
    state: () => {
        return {
            total: 0,
            selectData: [],
            loading: true,
            dialogShowVisible: false,
            dialogAddVisible: false,
            dialogEditVisible: false,
            editFlag: false,
            hackReset: true,
            noticeTypeList: [],
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
        // selectAllNotice() {
        //     void request.get('/notice/page').then((response) => {
        //         this.selectData = response.data.data
        //         if (this.selectData.length !== 0) {
        //             this.loading = false
        //         }
        //     })
        // },
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
        async selectAllNoticeByUserId() {
            await request.get('/notice/ByUserId').then((response) => {
                this.selectData = response.data.data
                if (this.selectData.length !== 0) {
                    this.loading = false
                }
            })
        },
        async selectNoticeType() {
            await request.get('/noticeType').then((response) => {
                this.noticeTypeList = response.data.data
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
        }
    }
})
