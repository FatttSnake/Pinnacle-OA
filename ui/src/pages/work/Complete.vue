<template>
    <div class="main">
        <div class="main-table">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column fixed prop="id" label="工作事项ID" width="150" />
                <el-table-column prop="publisherId" label="发布者ID" width="120" />
                <el-table-column prop="content" label="内容" width="800" />
                <el-table-column prop="worker" label="工作人员" width="200">
                    <template #default="{ row }">
                        <span v-for="item in row.worker" :key="item.userID">
                            {{ item.userName }},&nbsp;
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="deadline" label="结束时间" width="200">
                    <template #default="scope">
                        {{ formatDate(scope.row.deadline) }}
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="240">
                    <template #default="scope">
                        <el-button link type="primary" size="large">查看</el-button>
                        <el-popconfirm
                            width="220"
                            confirm-button-text="是"
                            cancel-button-text="否"
                            :icon="InfoFilled"
                            icon-color="#00d4ff"
                            title="是否确认完成？"
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
            console.log(row)
            this.setTaskTodo(row)
            console.log('complete confirm!')
            location.reload()
        },
        todoCancelEvent() {
            console.log('complete cancel!')
        },
        getTableData() {
            axios
                .get('http://localhost:8080/work/complete')
                .then((response) => {
                    console.log(response.data)
                    this.tableData = response.data
                    console.log(this.tableData)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        setTaskTodo(row) {
            var workDo = new Object()
            workDo.id = row.id
            workDo.status = false
            axios
                .put('http://localhost:8080/work', workDo)
                .then((response) => {
                    console.log(response.data)
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
