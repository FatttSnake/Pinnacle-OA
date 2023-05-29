<template>
    <el-table
        v-loading="dataLoading"
        element-loading-text="加载中..."
        ref="tableRef"
        :data="noticeTypeList.filter((data) => !search || data.name.includes(search))"
        style="font-size: 18px"
        stripe
        border
        highlight-current-row
        @selection-change="handleSelectionChange"
        :header-cell-style="{
            background: 'darksalmon',
            'text-align': 'center',
            color: '#fff'
        }"
        ><el-table-column type="selection" width="65" align="center" />
        <el-table-column type="index" label="序号" width="80" align="center" :index="indexFormat" />
        <el-table-column label="类型名称" prop="name" width="500" align="center" />
        <el-table-column label="是否启用" prop="enable" width="350" align="center">
            <template #default="scope">
                <el-switch
                    v-model="scope.row.enable"
                    style="--el-switch-on-color: #13ce66; --el-switch-off-color: #afb2b8"
                    active-text="启用"
                    inactive-text="禁用"
                    :active-value="1"
                    :inactive-value="0"
                    @change="switchChang(scope.row.id, scope.row.enable)"
                />
            </template>
        </el-table-column>
        <el-table-column align="center">
            <template #header>
                <el-input v-model="search" size="default" placeholder="请输入关键字搜索" />
            </template>
            <template #default="scope">
                <el-button size="default" type="primary" @click="handleOpenEditDialog(scope.row)"
                    >编辑</el-button
                >
                <el-button size="default" type="danger" @click="deleteTypeById(scope.row.id)"
                    >删除</el-button
                >
            </template>
        </el-table-column>
    </el-table>
    <!--    分页条-->
    <div class="pagination">
        <el-pagination
            style="text-align: center"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            layout="total, sizes, prev, pager, next, jumper"
            background
            :page-sizes="[5, 10, 20, 40]"
            :total="total"
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
        >
        </el-pagination>
    </div>
    <!--    编辑公告类型会话框-->
    <el-dialog
        v-model="dialogEditTypeVisible"
        center
        v-if="hackReset"
        :close-on-click-modal="false"
        :before-close="closeEditForm"
    >
        <template #header>
            <h2 style="color: red">编辑公告类型</h2>
        </template>
        <notice-type-commit-form ref="editForm" />
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="submitEditForm" style="margin-right: 20px">
                    确定
                </el-button>
                <el-button @click="closeEditForm">取消</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeTypeStore } from '@/store/notice'
const noticeTypeStore = useNoticeTypeStore()

export default {
    emits: ['deleteTypeById'],
    computed: {
        ...mapState(useNoticeTypeStore, [
            'total',
            'currentPage',
            'pageSize',
            'dialogEditTypeVisible',
            'noticeTypeList',
            'dataLoading',
            'hackReset',
            'showTypeData',
            'addTypeData',
            'multiDeleteSelection'
        ])
    },
    data() {
        return {
            filterSenderName: [],
            search: ''
        }
    },
    props: [],
    methods: {
        handleSelectionChange(val) {
            // val的值为所勾选行的数组对象
            noticeTypeStore.$patch((state) => {
                state.multiDeleteSelection = val
            })
        },
        indexFormat(index) {
            return (this.currentPage - 1) * this.pageSize + index + 1
        },
        switchChang(id, value) {
            noticeTypeStore.updateNoticeTypeEnable(id, value)
            setTimeout(() => {
                noticeTypeStore.selectNoticeType(this.currentPage, this.pageSize)
            }, 800)
        },
        handleOpenEditDialog(row) {
            noticeTypeStore.$patch((state) => {
                state.hackReset = true
                state.showTypeData.id = row.id
                state.showTypeData.name = row.name
                state.showTypeData.enable = row.enable
                state.editFlag = true
                state.dialogEditTypeVisible = true
            })
        },
        deleteTypeById(deleteId) {
            this.$emit('deleteTypeById', deleteId)
        },
        handleSizeChange(pageSize) {
            // pageSize：每页多少条数据
            noticeTypeStore.$patch((state) => {
                state.pageSize = pageSize
            })
            noticeTypeStore.selectNoticeType(this.currentPage, parseInt(pageSize))
        },
        handleCurrentChange(currentPage) {
            // currentPage：当前第几页
            noticeTypeStore.$patch((state) => {
                state.currentPage = currentPage
            })
            noticeTypeStore.selectNoticeType(parseInt(currentPage), this.pageSize)
        },
        submitEditForm() {
            this.$refs.editForm.$refs.addTypeData.validate((valid) => {
                if (valid) {
                    noticeTypeStore.handleUpdateNoticeType(this.addTypeData)
                } else {
                    return false
                }
            })
        },
        closeEditForm() {
            noticeTypeStore.$patch((state) => {
                state.dialogEditTypeVisible = false
                state.editFlag = false
                state.hackReset = false
                state.addTypeData = { name: '', enable: 1 }
            })
        }
    },
    mounted() {
        noticeTypeStore.dataLoading = true
        noticeTypeStore.selectNoticeType(1, 5)
    },
    updated() {}
}
</script>

<style scoped>
.pagination {
    margin: 30px 400px;
}
</style>
