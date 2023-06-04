<template>
    <el-table
        v-loading="loading"
        element-loading-text="加载中..."
        ref="tableRef"
        :data="selectData"
        border
        highlight-current-row
        @selection-change="handleSelectionChange"
        style="font-size: 18px"
        :header-cell-style="{
            background: 'darksalmon',
            'text-align': 'center',
            color: '#fff',
            'font-size': '20px'
        }"
        @filter-change="handleFilterChange"
    >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column type="index" label="序号" width="75" align="center">
            <template #default="scope">
                <el-icon :size="SIZE_ICON_SM()" :color="COLOR_TOP()" v-if="scope.row.top === 1">
                    <icon-pinnacle-top />
                </el-icon>
                {{ (this.currentPage - 1) * this.pageSize + scope.$index + 1 }}
            </template>
        </el-table-column>
        <el-table-column
            prop="title"
            label="公告标题"
            width="200"
            show-overflow-tooltip
            align="center"
        >
            <template #default="scope"> {{ formatterTitle(scope.row.title) }} </template>
        </el-table-column>
        <el-table-column prop="noticeType.name" label="公告类别" width="160" align="center">
            <template #default="scope">
                <el-tag
                    size="default"
                    :type="
                        scope.row.noticeType.name === '通知公告'
                            ? 'warning'
                            : scope.row.noticeType.name === '紧急公告'
                            ? 'danger'
                            : 'success'
                    "
                    disable-transitions
                >
                    {{ scope.row.noticeType.name }}
                </el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="priority" label="优先级" width="100" align="center" />
        <el-table-column
            prop="createTime"
            label="创建时间"
            sortable
            width="220"
            :formatter="formatDate"
            align="center"
        />
        <el-table-column
            prop="sendTime"
            label="生效时间"
            sortable
            width="220"
            :formatter="formatDate"
            align="center"
        />
        <el-table-column
            prop="endTime"
            label="失效时间"
            sortable
            width="220"
            :formatter="formatDate"
            align="center"
        />
        <el-table-column
            prop="sender.username"
            label="发布者"
            width="100"
            column-key="senderId"
            :filters="senderList"
            filter-placement="bottom-end"
            align="center"
        >
            <template #default="scope">
                <el-tag
                    :type="scope.row.sender.username === 'cyb' ? '' : 'success'"
                    disable-transitions
                    >{{ scope.row.sender.username }}
                </el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" min-width="200px" align="center">
            <template #default="scope">
                <el-button size="small" color="#626aef" @click="handleShow(scope.row)"
                    >查看
                </el-button>
                <el-button size="small" type="primary" @click="handleEdit(scope.row)"
                    >编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteById(scope.row.id)"
                    >删除
                </el-button>
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
    <el-dialog
        v-model="dialogEditVisible"
        center
        v-if="hackReset"
        :close-on-click-modal="false"
        :before-close="handleDialogClose"
    >
        <template #header>
            <h2 style="color: red">编辑公告</h2>
        </template>
        <notice-commit-form />
    </el-dialog>
    <!--        查看会话框-->
    <el-dialog
        v-model="dialogShowVisible"
        center
        :close-on-click-modal="false"
        :before-close="handleDialogClose"
    >
        <template #header>
            <h2 style="color: red">查看公告</h2>
        </template>
        <notice-show-dialog />
    </el-dialog>
</template>

<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeStore } from '@/store/notice'
import { COLOR_TOP, SIZE_ICON_MD, SIZE_ICON_SM } from '@/constants/Common.constants'

const noticeStore = useNoticeStore()

export default {
    computed: {
        ...mapState(useNoticeStore, [
            'total',
            'selectData',
            'loading',
            'dialogShowVisible',
            'noticeShowData',
            'dialogEditVisible',
            'hackReset',
            'currentPage',
            'pageSize',
            'multiDeleteSelection',
            'search',
            'senderList'
        ])
    },
    emits: ['handleDeleteById', 'getNoticeSender', 'filterSender'],
    props: [],
    methods: {
        SIZE_ICON_SM() {
            return SIZE_ICON_SM
        },
        COLOR_TOP() {
            return COLOR_TOP
        },
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        handleSelectionChange(val) {
            // val的值为所勾选行的数组对象
            noticeStore.$patch((state) => {
                state.multiDeleteSelection = val
            })
        },
        formatterTitle(title) {
            if (title.length > 10) {
                return title.substring(0, 10) + ' ...'
            } else {
                return title
            }
        },
        formatDate(row, column) {
            // 获取单元格数据
            const data = row[column.property]
            if (data == null) return '暂无数据'
            return new Date(data).toLocaleString()
        },
        handleEdit(row) {
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
                state.dialogAddVisible = false
                state.dialogShowVisible = false
                state.editFlag = false
                state.hackReset = false
            })
        },
        handleShow(row) {
            noticeStore.$patch((state) => {
                state.dialogShowVisible = true
                state.noticeShowData = row
            })
        },
        handleDeleteById(deleteId) {
            this.$emit('handleDeleteById', deleteId)
        },
        handleSizeChange(pageSize) {
            // pageSize：每页多少条数据
            noticeStore.$patch((state) => {
                state.pageSize = pageSize
            })
            noticeStore.selectAllNotice(
                this.currentPage,
                parseInt(pageSize),
                this.search.title,
                this.search.type,
                this.search.startTime,
                this.search.endTime,
                this.search.userIdList
            )
        },
        handleCurrentChange(currentPage) {
            // currentPage：当前第几页
            noticeStore.$patch((state) => {
                state.currentPage = currentPage
            })
            noticeStore.selectAllNotice(
                parseInt(currentPage),
                this.pageSize,
                this.search.title,
                this.search.type,
                this.search.startTime,
                this.search.endTime,
                this.search.userIdList
            )
        },
        handleFilterChange(filters) {
            noticeStore.$patch((state) => {
                state.search.userIdList = filters.senderId
            })
            this.$emit('filterSender')
        }
    },
    mounted() {
        noticeStore.selectAllNotice(this.currentPage, this.pageSize, '', '', '', '', [])
    },
    updated() {
        this.$emit('getNoticeSender')
    }
}
</script>

<style scoped>
.el-table {
    margin-top: 10px;
}

.pagination {
    margin: 30px 400px;
}
</style>
