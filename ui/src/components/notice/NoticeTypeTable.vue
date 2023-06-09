<template>
    <el-table
        v-loading="dataLoading"
        element-loading-text="加载中..."
        ref="tableRef"
        :data="noticeTypeList"
        stripe
        border
        highlight-current-row
        @selection-change="handleSelectionChange"
        :header-cell-style="{
            background: 'aliceblue',
            'text-align': 'center',
            'font-size': '16px'
        }"
        ><el-table-column type="selection" align="center" />
        <el-table-column type="index" label="序号" width="80" align="center" :index="indexFormat" />
        <el-table-column
            label="类型名称"
            prop="name"
            min-width="160"
            show-overflow-tooltip
            align="center"
        />
        <el-table-column label="是否启用" prop="enable" width="350" align="center">
            <template #default="scope">
                <el-switch
                    :loading="switchLoading"
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
        <el-table-column label="操作" align="center" width="200px">
            <template #default="scope">
                <el-button size="small" type="primary" @click="handleOpenEditDialog(scope.row)"
                    >编辑</el-button
                >
                <el-button size="small" type="danger" @click="deleteTypeById(scope.row.id)"
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
            :page-sizes="[10, 20, 50, 100]"
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
        style="min-width: 320px; max-width: 700px"
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
            'multiDeleteSelection',
            'searchType',
            'switchLoading'
        ])
    },
    data() {
        return {
            filterSenderName: []
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
        async switchChang(id, value) {
            this.switchLoading = true
            this.dataLoading = true
            await noticeTypeStore.updateNoticeTypeEnable(id, value)
            await noticeTypeStore.selectNoticeType(
                this.currentPage,
                this.pageSize,
                this.searchType.name,
                this.searchType.enable
            )
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
            noticeTypeStore.selectNoticeType(
                this.currentPage,
                parseInt(pageSize),
                this.searchType.name,
                this.searchType.enable
            )
        },
        handleCurrentChange(currentPage) {
            // currentPage：当前第几页
            noticeTypeStore.$patch((state) => {
                state.currentPage = currentPage
            })
            noticeTypeStore.selectNoticeType(
                parseInt(currentPage),
                this.pageSize,
                this.searchType.name,
                this.searchType.enable
            )
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
        noticeTypeStore.selectNoticeType(1, 10, '', -1)
    },
    updated() {}
}
</script>

<style scoped>
.el-table {
    margin-top: 10px;
}
.pagination {
    display: flex;
    margin: 20px 0;
    justify-content: center;
}
</style>
