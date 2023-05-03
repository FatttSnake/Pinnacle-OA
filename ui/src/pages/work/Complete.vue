<template>
    <div class="main">
        <div class="main-table">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column fixed prop="publisherName" label="发布者" width="120" />
                <el-table-column prop="content" label="内容" width="800" />
                <el-table-column prop="deadline" label="结束时间" width="200">
                    <template #default="scope">
                        {{ formatDate(scope.row.deadline) }}
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="150">
                    <template #default="scope">
                        <el-button link type="primary" size="large">查看</el-button>
                        <el-popconfirm
                            width="220"
                            confirm-button-text="是"
                            cancel-button-text="否"
                            :icon="InfoFilled"
                            icon-color="#00d4ff"
                            title="是否修改为未完成？"
                            @confirm="todoConfirmEvent(scope.row)"
                            @cancel="todoCancelEvent"
                        >
                            <template #reference>
                                <el-button link type="primary" size="default">未完成</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'CompletePage',
    data() {
        return {
            tableData: []
        }
    },
    methods: {
        formatDate(deadline) {
            console.log(new Date(deadline).toLocaleString())
            return new Date(deadline).toLocaleString()
        },
        todoConfirmEvent(row) {
            const userWork = {
                workId: '',
                userId: '1652714496280469506',
                status: 1
            }
            userWork.workId = row.id
            userWork.status = 0
            this.setTaskStatus(userWork)
        },
        todoCancelEvent() {
            console.log('complete cancel!')
        },
        getTableData() {
            axios
                .get('http://localhost:8621/work/complete/1652714496280469506')
                .then((response) => {
                    console.log(response.data.data)
                    this.tableData = response.data.data
                    console.log(this.tableData)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        setTaskStatus(userWork) {
            console.log(userWork)
            axios
                .put('http://localhost:8621/work/setStatus', userWork)
                .then((response) => {
                    console.log(response.data.data)
                    this.getTableData()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        }
    },
    created() {
        this.getTableData()
    }
}
</script>

<style scoped>
@import '@/assets/css/work/work.css';
</style>
