<template>
    <el-button @click="resetDateFilter">reset date filter</el-button>
    <el-button @click="clearFilter">reset all filters</el-button>
    <el-table
        ref="tableRef"
        row-key="date"
        :data="tableData"
        style="width: 100%"
        border
        highlight-current-row
    >
        <el-table-column
            prop="title"
            label="公告标题"
            width="180"
            :formatter="formatter"
            show-overflow-tooltip
        />
        <el-table-column prop="category" label="公告类别" width="180" />
        <el-table-column prop="status" label="状态" width="180" />
        <el-table-column
            prop="sendTime"
            label="创建时间"
            sortable
            width="180"
            column-key="date"
            :filters="[
                { text: '2016-05-01', value: '2016-05-01' },
                { text: '2016-05-02', value: '2016-05-02' },
                { text: '2016-05-03', value: '2016-05-03' },
                { text: '2016-05-04', value: '2016-05-04' }
            ]"
            :filter-method="filterHandler"
            :formatter="formatDate"
        />
        <el-table-column
            prop="endDate"
            label="失效时间"
            sortable
            width="180"
            value-format="yyyy-MM-dd HH:mm:ss"
            :formatter="formatDate"
        />
        <el-table-column
            prop="sender.userName"
            label="发布人"
            width="100"
            :filters="[
                { text: 'Home', value: 'Home' },
                { text: 'Office', value: 'Office' }
            ]"
            :filter-method="filterTag"
            filter-placement="bottom-end"
        >
            <template #default="scope">
                <el-tag
                    :type="scope.row.sender.userName === 'Home' ? '' : 'success'"
                    disable-transitions
                    >{{ scope.row.sender.userName }}
                </el-tag>
            </template>
        </el-table-column>
        <el-table-column label="Operations">
            <template #default="scope">
                <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
                    >编辑</el-button
                >
                <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)"
                    >删除</el-button
                >
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
import axios from 'axios'

export default {
    data() {
        return {
            tableData: []
        }
    },
    methods: {
        resetDateFilter() {
            this.$refs.tableRef.clearFilter(['date'])
        },
        clearFilter() {
            this.$refs.tableRef.clearFilter()
        },
        formatter(row, column) {
            return row.title
        },
        filterTag(value, row) {
            return row.sender.userName === value
        },
        filterHandler(value, row, column) {
            const property = column['property']
            return row[property] === value
        },
        formatDate(row, column) {
            // 获取单元格数据
            let data = row[column.property]
            if (data == null) return null

            let dt = data.replace('T', ' ')
            return dt
        },
        handleEdit(index, row) {
            console.log(index, row.id)
        },
        handleDelete(index, row) {
            axios.delete('http://localhost:8080/notice/' + row.id).then((response) => {
                console.log(response.data)
                this.selectAllNotice()
            })
        },
        selectAllNotice() {
            axios.get('http://localhost:8080/notice').then((response) => {
                this.tableData = response.data.data
            })
        }
    },
    mounted() {
        this.selectAllNotice()
    }
}
</script>

<style scoped></style>
