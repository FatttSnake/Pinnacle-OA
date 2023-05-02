<template>
    <el-button @click="clearFilter">清除筛选条件</el-button>
    <el-table
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
        <el-table-column prop="noticeType.name" label="公告类别" width="180" />
        <el-table-column prop="priority" label="优先级" width="180" />
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
            value-format="yyyy-MM-dd HH:mm:ss"
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
        <el-table-column label="操作">
            <template #default="scope">
                <el-button size="small" type="primary" @click="handleEdit(scope.$index, scope.row)"
                    >编辑
                </el-button>
                <el-button
                    size="small"
                    type="danger"
                    @click="this.$emit('handleDelete', scope.row.id)"
                    >删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
    <!--        编辑会话框-->
    <el-dialog v-model="dialogFormVisible">
        <template #title>
            {{ dialogFormTitle }}
        </template>
        <notice-edit />
    </el-dialog>
</template>

<script lang="ts">
export default {
    data() {
        return {
            filterSenderName: [],
            dialogFormVisible: false,
            dialogFormTitle: ''
        }
    },
    props: ['msg', 'selectData'],
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

            const dt = data.replace('T', ' ')
            return dt
        },
        handleEdit(index, row) {
            this.dialogFormVisible = true
            this.dialogFormTitle = row.title
            console.log(index + '   ' + row)
        }
    },
    mounted() {},
    updated() {
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
        console.log(this.filterSenderName)
    }
}
</script>

<style scoped></style>
