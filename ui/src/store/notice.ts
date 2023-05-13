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
            selectData: [],
            loading: true,
            dialogShowVisible: false,
            dialogAddVisible: false,
            noticeTypeList: [],
            departmentList: [],
            noticeShowData: {
                content: String,
                createTime: String,
                endTime: String,
                id: String,
                priority: Number,
                receivers: [],
                sendTime: String,
                title: String,
                top: Number,
                noticeType: {
                    id: String,
                    name: String,
                    enable: Number
                },
                sender: {
                    id: String,
                    username: String
                }
            }
        }
    },
    getters: {},
    actions: {
        selectAllNotice() {
            console.log('selectAll')
            void request.get('/notice').then((response) => {
                this.selectData = response.data.data
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
            this.selectAllNotice()
        }
    }
})
