<template>
    <el-button
        size="large"
        @click="clearFilter"
        style="background-color: rgba(71, 138, 173, 0.85); color: white"
        >清除筛选条件
    </el-button>
    <el-table
        v-loading="loading"
        element-loading-text="加载中..."
        ref="tableRef"
        :data="selectData"
        border
        highlight-current-row
        @selection-change="handleSelectionChange"
        :header-cell-style="{
            background: 'darksalmon',
            'text-align': 'center',
            color: '#fff',
            'font-size': '20px'
        }"
        ><el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" width="70" />
        <el-table-column
            prop="title"
            label="公告标题"
            width="180"
            :formatter="formatter"
            show-overflow-tooltip
        />
        <el-table-column prop="noticeType.name" label="公告类别" width="150">
            <template #default="scope">
                <el-tag
                    size="default"
                    :type="scope.row.noticeType.name === '通知公告' ? 'warning' : 'success'"
                    disable-transitions
                >
                    {{ scope.row.noticeType.name }}
                </el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="priority" label="优先级" width="100" />
        <el-table-column
            prop="createTime"
            label="创建时间"
            sortable
            width="180"
            :formatter="formatDate"
        />
        <el-table-column
            prop="sendTime"
            label="生效时间"
            sortable
            width="180"
            :formatter="formatDate"
        />
        <el-table-column
            prop="endTime"
            label="失效时间"
            sortable
            width="180"
            :formatter="formatDate"
        />
        <el-table-column
            prop="sender.username"
            label="发布人"
            width="100"
            column-key="sender.username"
            :filters="filterSenderName"
            :filter-method="filterTag"
            filter-placement="bottom-end"
        >
            <template #default="scope">
                <el-tag
                    :type="scope.row.sender.username === 'cyb' ? '' : 'success'"
                    disable-transitions
                    >{{ scope.row.sender.username }}
                </el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
            <template #default="scope">
                <el-button size="small" color="#626aef" @click="handleShow(scope.$index, scope.row)"
                    >查看
                </el-button>
                <el-button size="small" type="primary" @click="handleEdit(scope.$index, scope.row)"
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
        :before-close="handleDialogClose"
    >
        <template #header>
            <h2 style="color: red">编辑公告</h2>
        </template>
        <commitForm />
    </el-dialog>
    <!--        查看会话框-->
    <el-dialog v-model="dialogShowVisible" center>
        <template #header>
            <h2 style="color: red">查看公告</h2>
        </template>
        <notice-show-dialog />
    </el-dialog>
</template>

<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeStore } from '@/store/notice'
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
            'hackReset'
        ])
    },
    data() {
        return {
            filterSenderName: [],
            multipleSelection: [],
            currentPage: 1,
            pageSize: 5
        }
    },
    props: [],
    methods: {
        handleSelectionChange(val) {
            // val的值为所勾选行的数组对象
            this.multipleSelection = val
        },
        clearFilter() {
            this.$refs.tableRef.clearFilter(['senderName'])
            this.$emit('clearFilter')
        },
        formatter(row) {
            return row.title
        },
        filterTag(value, row) {
            return row.sender.username === value
        },
        formatDate(row, column) {
            // 获取单元格数据
            const data = row[column.property]
            if (data == null) return null
            return new Date(data).toLocaleString()
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
        handleShow(index, row) {
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
            noticeStore.selectAllNotice(this.currentPage, parseInt(pageSize))
        },
        handleCurrentChange(currentPage) {
            // currentPage：当前第几页
            noticeStore.selectAllNotice(parseInt(currentPage), this.pageSize)
        }
    },
    mounted() {
        noticeStore.selectAllNotice(this.currentPage, this.pageSize)
    },
    updated() {
        this.$refs.tableRef.clearFilter(['sender.username'])
        this.filterSenderName = []
        const nameArray = []
        for (let i = 0; i < this.selectData.length; i++) {
            nameArray.push(this.selectData[i].sender.username)
        }
        const newArr = nameArray.filter((item, i, arr) => {
            return arr.indexOf(item) === i
        })
        for (let j = 0; j < newArr.length; j++) {
            const senderName = { text: '', value: '' }
            senderName.text = newArr[j]
            senderName.value = newArr[j]
            this.filterSenderName.push(senderName)
        }
    }
}
</script>

<style scoped>
.pagination {
    margin: 30px 400px;
}
</style>
