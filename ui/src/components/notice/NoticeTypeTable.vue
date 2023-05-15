<template>
    <el-table
        v-loading="loading"
        element-loading-text="加载中..."
        ref="tableRef"
        :data="noticeTypeList.filter((data) => !search || data.name.includes(search))"
        style="font-size: 20px"
        stripe
        border
        highlight-current-row
        @selection-change="handleSelectionChange"
        :header-cell-style="{
            background: 'darksalmon',
            'text-align': 'center',
            color: '#fff',
            'font-size': '20px'
        }"
        ><el-table-column type="selection" width="65" align="center" />
        <el-table-column type="index" label="序号" width="80" align="center" />
        <el-table-column label="类型名" prop="name" width="500" align="center" />
        <el-table-column label="是否启用" prop="enable" width="350" align="center">
            <template #default="scope">
                <el-switch
                    v-model="scope.row.enable"
                    style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                    active-text="启用"
                    inactive-text="禁用"
                    @change="switchChang(scope.row.id, scope.row.enable)"
                />
            </template>
        </el-table-column>
        <el-table-column align="center">
            <template #header>
                <el-input v-model="search" size="default" placeholder="请输入关键字搜索" />
            </template>
            <template #default="scope">
                <el-button
                    size="default"
                    type="primary"
                    @click="handleEdit(scope.$index, scope.row)"
                    >编辑</el-button
                >
                <el-button size="default" type="danger" @click="handleDeleteById(scope.row)"
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
    <!--        编辑会话框-->
    <!--    <el-dialog-->
    <!--        v-model="dialogEditVisible"-->
    <!--        center-->
    <!--        v-if="hackReset"-->
    <!--        :before-close="handleDialogClose"-->
    <!--    >-->
    <!--        <template #header>-->
    <!--            <h2 style="color: red">编辑公告</h2>-->
    <!--        </template>-->
    <!--        <commitForm />-->
    <!--    </el-dialog>-->
</template>

<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeStore } from '@/store/notice'
const noticeStore = useNoticeStore()

export default {
    computed: {
        ...mapState(useNoticeStore, ['total', 'noticeTypeList', 'loading', 'dialogEditVisible'])
    },
    data() {
        return {
            filterSenderName: [],
            multipleSelection: [],
            currentPage: 1,
            pageSize: 5,
            search: ''
        }
    },
    props: [],
    methods: {
        handleSelectionChange(val) {
            // val的值为所勾选行的数组对象
            this.multipleSelection = val
        },
        switchChang(id, value) {
            if (!noticeStore.updateNoticeTypeEnable(id, value)) {
                noticeStore.selectNoticeType()
            }
        },
        handleEdit(index, row) {
            noticeStore.$patch((state) => {
                state.hackReset = true
                state.noticeShowData = row
                state.editFlag = true
                state.dialogEditVisible = true
            })
        },
        handleDialogClose() {
            noticeStore.$patch((state) => {
                state.dialogEditVisible = false
                state.editFlag = false
                state.hackReset = false
            })
        },
        handleDeleteById(deleteId) {
            this.$emit('handleDeleteById', deleteId)
        },
        handleSizeChange(pageSize) {
            // pageSize：每页多少条数据
            noticeStore.selectAllNotice(this.currentPage, parseInt(pageSize))
        },
        handleCurrentChange(currentPage) {
            // currentPage：当前第几页
            noticeStore.selectAllNotice(parseInt(currentPage), this.pageSize)
        }
    },
    mounted() {
        noticeStore.selectNoticeType()
    },
    updated() {}
}
</script>

<style scoped>
.pagination {
    margin: 30px 400px;
}
</style>
