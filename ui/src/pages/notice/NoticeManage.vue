<template>
    <el-container>
        <el-header style="width: 100%">
            <notice-manage-head @selectByCond="getLoading"></notice-manage-head>
        </el-header>
        <el-main>
            <el-button
                size="large"
                type="primary"
                @click="openAddNoticeDialog"
                style="margin-right: 15px"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-add /> </el-icon
                >发布公告</el-button
            >
            <el-button type="primary" :size="'large'" @click="deleteBatchByIds"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-delete /> </el-icon
                >批量删除</el-button
            >
            <el-button type="primary" :size="'large'" @click="getLoading"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-reset /> </el-icon
                >刷新数据</el-button
            >
            <!-- 添加公告对话框-->
            <el-dialog
                v-model="dialogAddVisible"
                center
                :close-on-click-modal="false"
                :before-close="handleDialogClose"
                style="min-width: 450px; max-width: 900px"
            >
                <template #header>
                    <h2 style="color: red">发布公告</h2>
                </template>
                <notice-commit-form ref="addForm" />
            </el-dialog>
            <notice-manage-table
                @handleDeleteById="handleDeleteById"
                @getNoticeSender="getNoticeSender"
                @filterSender="getLoading"
                ref="manageTable"
            ></notice-manage-table>
        </el-main>
    </el-container>
</template>

<script lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import 'element-plus/theme-chalk/el-message-box.css'
import request from '@/services'
import { useNoticeStore, useNoticeTypeStore } from '@/store/notice'
import { mapState } from 'pinia'
import {
    DATABASE_DELETE_ERROR,
    DATABASE_DELETE_OK,
    SIZE_ICON_MD
} from '@/constants/Common.constants'
const noticeStore = useNoticeStore()
const noticeTypeStore = useNoticeTypeStore()

export default {
    name: 'NoticeHome',
    data() {
        return {}
    },
    methods: {
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        handleDialogClose() {
            noticeStore.$patch((state) => {
                state.dialogEditVisible = false
                state.dialogAddVisible = false
                state.dialogShowVisible = false
                state.editFlag = false
                state.hackReset = false
            })
            this.$refs.addForm.$refs.addData.resetFields()
        },
        handleDeleteById(deleteID) {
            ElMessageBox.confirm('确定是否要删除？该操作将无法回退', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '我再想想',
                type: 'warning'
            })
                .then(() => {
                    request.delete('/notice/' + deleteID).then((response) => {
                        if (response.data.code === DATABASE_DELETE_OK) {
                            ElMessage({
                                message: '删除成功.',
                                type: 'success'
                            })
                            noticeStore.selectAllNotice(
                                this.currentPage,
                                this.pageSize,
                                '',
                                '',
                                '',
                                '',
                                []
                            )
                        } else if (response.data.code === DATABASE_DELETE_ERROR) {
                            ElMessage({
                                message: response.data.msg,
                                type: 'error'
                            })
                        }
                    })
                })
                .catch(() => {})
        },
        openAddNoticeDialog() {
            noticeStore.$patch((state) => {
                state.dialogAddVisible = true
                state.editFlag = false
            })
        },
        getLoading() {
            noticeStore.loading = true
            noticeStore.selectAllNotice(
                this.currentPage,
                this.pageSize,
                this.search.title,
                this.search.type,
                this.search.startTime,
                this.search.endTime,
                this.search.userIdList
            )
        },
        deleteBatchByIds() {
            const multiDeleteIds = []
            if (this.multiDeleteSelection.length > 0) {
                for (let i = 0; i < this.multiDeleteSelection.length; i++) {
                    multiDeleteIds.push(this.multiDeleteSelection[i].id)
                }
                ElMessageBox.confirm('确定是否要批量删除？该操作将无法回退', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '我再想想',
                    type: 'warning'
                })
                    .then(() => {
                        request.post('/notice/batch', multiDeleteIds).then((response) => {
                            if (response.data.code === DATABASE_DELETE_OK) {
                                ElMessage({
                                    message: '删除成功.',
                                    type: 'success'
                                })
                                noticeStore.selectAllNotice(
                                    this.currentPage,
                                    this.pageSize,
                                    '',
                                    '',
                                    '',
                                    '',
                                    []
                                )
                            } else if (response.data.code === DATABASE_DELETE_ERROR) {
                                ElMessage({
                                    message: response.data.msg,
                                    type: 'error'
                                })
                            }
                        })
                    })
                    .catch(() => {})
            } else {
                ElMessage({
                    message: '请至少选择一项进行删除',
                    type: 'warning'
                })
            }
        },
        getNoticeSender() {
            const senders = []
            request.get('/user/notice').then((response) => {
                for (let i = 0; i < response.data.data.length; i++) {
                    const senderName = { text: '', value: '' }
                    senderName.text = response.data.data[i].username
                    senderName.value = response.data.data[i].id
                    senders.push(senderName)
                }
                noticeStore.$patch((state) => {
                    state.senderList = senders
                })
            })
        }
    },
    mounted() {
        noticeTypeStore.selectEnableNoticeType()
    },
    computed: {
        ...mapState(useNoticeStore, [
            'dialogAddVisible',
            'currentPage',
            'pageSize',
            'multiDeleteSelection',
            'search',
            'senderList'
        ])
    }
}
</script>

<style scoped>
.el-header {
    background-color: #fff;
}
.el-main {
    padding: 0;
    margin-top: 20px;
}
</style>
