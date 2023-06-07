<template>
    <el-container>
        <el-header style="width: 100%">
            <notice-type-head @selectTypeByCond="getLoadData"></notice-type-head>
        </el-header>
        <el-main>
            <el-button type="primary" :size="'large'" @click="handleOpenAddDialog"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-add /> </el-icon
                >添加类型</el-button
            >
            <el-button type="primary" :size="'large'" @click="deleteBatchByTypeIds"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-delete /> </el-icon
                >批量删除</el-button
            >
            <el-button type="primary" :size="'large'" @click="getLoadData"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-reset /> </el-icon
                >刷新数据</el-button
            >
            <!-- 添加公告类型对话框-->
            <el-dialog
                v-model="dialogAddTypeVisible"
                center
                :close-on-click-modal="false"
                v-if="hackReset"
                :before-close="closeForm"
                style="min-width: 320px; max-width: 700px"
            >
                <template #header>
                    <h2 style="color: red">添加公告类型</h2>
                </template>
                <notice-type-commit-form ref="addForm" />
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="resetForm" style="margin-right: 20px">重置</el-button>
                        <el-button type="primary" @click="submitForm" style="margin-right: 20px">
                            确定
                        </el-button>
                        <el-button @click="closeForm">取消</el-button>
                    </span>
                </template>
            </el-dialog>
            <notice-type-table @deleteTypeById="deleteTypeById" />
        </el-main>
    </el-container>
</template>
<script lang="ts">
import {
    DATABASE_DELETE_ERROR,
    DATABASE_DELETE_OK,
    SIZE_ICON_MD
} from '@/constants/Common.constants'
import { useNoticeTypeStore } from '@/store/notice'
import { mapState } from 'pinia'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/services'

const noticeTypeStore = useNoticeTypeStore()

export default {
    name: 'NoticeTypeManage',
    computed: {
        ...mapState(useNoticeTypeStore, [
            'dialogAddTypeVisible',
            'dialogEditTypeVisible',
            'editFlag',
            'hackReset',
            'addTypeData',
            'currentPage',
            'pageSize',
            'multiDeleteSelection',
            'searchType'
        ])
    },
    data() {
        return {}
    },
    methods: {
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        getLoadData() {
            noticeTypeStore.dataLoading = true
            noticeTypeStore.selectNoticeType(
                this.currentPage,
                this.pageSize,
                this.searchType.name,
                this.searchType.enable
            )
        },
        handleOpenAddDialog() {
            noticeTypeStore.$patch((state) => {
                state.dialogAddTypeVisible = true
                state.hackReset = true
            })
        },
        submitForm() {
            this.$refs.addForm.$refs.addTypeData.validate((valid) => {
                if (valid) {
                    noticeTypeStore.handleAddNoticeType(this.addTypeData)
                } else {
                    return false
                }
            })
        },
        closeForm() {
            noticeTypeStore.$patch((state) => {
                state.dialogAddTypeVisible = false
                state.dialogEditTypeVisible = false
                state.hackReset = false
                state.editFlag = false
                state.addTypeData = { name: '', enable: 1 }
            })
        },
        resetForm() {
            this.$refs.addForm.$refs.addTypeData.resetFields()
        },
        deleteTypeById(typeId) {
            ElMessageBox.confirm('确定是否要删除？该操作将无法回退', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '我再想想',
                type: 'warning'
            })
                .then(() => {
                    request.delete('/notice_type/' + typeId).then((response) => {
                        if (response.data.code === DATABASE_DELETE_OK) {
                            ElMessage({
                                message: '删除成功.',
                                type: 'success'
                            })
                            noticeTypeStore.selectNoticeType(
                                this.currentPage,
                                this.pageSize,
                                '',
                                -1
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
        deleteBatchByTypeIds() {
            const multiDeleteTypeIds = []
            if (this.multiDeleteSelection.length > 0) {
                for (let i = 0; i < this.multiDeleteSelection.length; i++) {
                    multiDeleteTypeIds.push(this.multiDeleteSelection[i].id)
                }
                ElMessageBox.confirm('确定是否要批量删除？该操作将无法回退', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '我再想想',
                    type: 'warning'
                })
                    .then(() => {
                        request.post('/notice_type/batch', multiDeleteTypeIds).then((response) => {
                            if (response.data.code === DATABASE_DELETE_OK) {
                                ElMessage({
                                    message: '删除成功.',
                                    type: 'success'
                                })
                                noticeTypeStore.selectNoticeType(
                                    this.currentPage,
                                    this.pageSize,
                                    '',
                                    -1
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
        }
    },
    mounted() {}
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
