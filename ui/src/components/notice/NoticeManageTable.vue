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
        style="width: 100%"
        border
        highlight-current-row
        :header-cell-style="{
            background: 'darksalmon',
            'text-align': 'center',
            color: '#fff',
            'font-size': '20px'
        }"
    >
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
            column-key="senderName"
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
                <el-button size="small" type="danger" @click="handleDelete(scope.row.id)"
                    >删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
    <!--        编辑会话框-->
    <el-dialog v-model="dialogEditVisible" center>
        <template #header>
            <h2 style="color: red">编辑公告</h2>
        </template>
        <commitForm
            :noticeEdit="noticeEdit"
            :noticeTypeList="noticeTypeList"
            :departmentList="departmentList"
            @handleUpdateNotice="handleUpdateNotice"
        ></commitForm>
    </el-dialog>
    <!--        查看会话框-->
    <el-dialog v-model="dialogShowVisible" center>
        <template #header>
            <h2 style="color: red">查看公告</h2>
        </template>
        <notice-show-dialog @showDialogVisible="showDialogVisible" :noticeShow="noticeShow" />
    </el-dialog>
</template>

<script lang="ts">
export default {
    data() {
        return {
            filterSenderName: [],
            dialogEditVisible: false,
            dialogShowVisible: false,
            noticeEdit: {},
            noticeShow: {},
            getLoading: true
        }
    },
    props: ['noticeTypeList', 'selectData', 'departmentList', 'dialogUpdateVisible', 'loading'],
    methods: {
        clearFilter() {
            this.$refs.tableRef.clearFilter(['senderName'])
            this.$emit('clearFilter')
        },
        formatter(row, column) {
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
            this.dialogEditVisible = true
            this.noticeEdit = row
        },
        handleUpdateNotice(updateData) {
            this.$emit('handleUpdateNotice', updateData)
            this.dialogEditVisible = this.dialogUpdateVisible
        },
        handleShow(index, row) {
            this.dialogShowVisible = true
            this.noticeShow = row
        },
        handleDelete(deleteId) {
            this.$emit('handleDelete', deleteId)
        },
        showDialogVisible(visible) {
            this.dialogShowVisible = visible
        }
    },
    mounted() {},
    updated() {
        this.$refs.tableRef.clearFilter(['senderName'])
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
        // console.log(this.filterSenderName)
    }
}
</script>

<style scoped></style>