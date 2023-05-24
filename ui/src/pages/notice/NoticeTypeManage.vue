<template>
    <el-container>
        <el-header>
            <el-button type="primary" :size="'large'" @click="handleOpenAddDialog"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-add /> </el-icon
                >添加类型</el-button
            >
            <el-button type="primary" :size="'large'"
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
        </el-header>
        <el-main>
            <notice-type-table @deleteTypeById="deleteTypeById" />
        </el-main>
    </el-container>
</template>
<script lang="ts">
import { SIZE_ICON_MD } from '@/constants/Common.constants'
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
            'addTypeData'
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
            noticeTypeStore.selectNoticeType()
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
                        if (response.data.code === 20024) {
                            ElMessage({
                                message: '删除成功.',
                                type: 'success'
                            })
                            noticeTypeStore.selectNoticeType()
                        } else if (response.data.code === 20034) {
                            ElMessage({
                                message: response.data.msg,
                                type: 'error'
                            })
                        }
                    })
                })
                .catch(() => {})
        }
    },
    mounted() {}
}
</script>

<style scoped></style>
