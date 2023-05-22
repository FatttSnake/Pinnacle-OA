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
                destroy-on-close
            >
                <template #header>
                    <h2 style="color: red">添加公告类型</h2>
                </template>
                <notice-type-commit-form />
                <template #footer>
                    <span class="dialog-footer">
                        <el-button>取消</el-button>
                        <el-button type="primary" @click="submitForm"> 确定 </el-button>
                    </span>
                </template>
            </el-dialog>
        </el-header>
        <el-main>
            <notice-type-table />
        </el-main>
    </el-container>
</template>
<script lang="ts">
import { SIZE_ICON_MD } from '@/constants/Common.constants'
import { useNoticeTypeStore } from '@/store/notice'
import { mapState } from 'pinia'

const noticeTypeStore = useNoticeTypeStore()

export default {
    name: 'NoticeTypeManage',
    computed: {
        ...mapState(useNoticeTypeStore, ['dialogAddTypeVisible', 'editFlag'])
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
            noticeTypeStore.dialogAddTypeVisible = true
        },
        submitForm() {
            this.$refs.addTypeData.validate((valid) => {
                if (valid) {
                    if (this.editFlag) {
                        // 编辑操作
                        // noticeTypeStore.handleUpdateNoticeType(this.addTypeData)
                    } else {
                        // 添加操作
                        // noticeTypeStore.handleAddNoticeType(this.addTypeData)
                    }
                } else {
                    return false
                }
            })
        }
    },
    mounted() {}
}
</script>

<style scoped></style>
