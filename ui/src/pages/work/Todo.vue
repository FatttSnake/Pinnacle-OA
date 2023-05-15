<template>
    <div class="main">
        <div class="main-table">
            <el-table
                :data="tableData"
                style="width: 100%"
                v-loading="loading"
                element-loading-text="加载中..."
            >
                <el-table-column fixed prop="publisherName" label="发布者" width="150" />
                <el-table-column prop="content" label="内容" width="800" />
                <el-table-column prop="deadline" label="结束时间" width="200" sortable>
                    <template #default="scope">
                        <span :style="{ color: scope.row.color1 }">{{ formatDate(scope) }}</span>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="150">
                    <template #default="scope">
                        <el-button link type="primary" size="large" @click="viewClick(scope.row.id)"
                            >查看</el-button
                        >
                        <el-popconfirm
                            width="220"
                            confirm-button-text="是"
                            cancel-button-text="否"
                            :icon="InfoFilled"
                            icon-color="#00d4ff"
                            title="是否确认完成？"
                            @confirm="completeConfirmEvent(scope.row)"
                            @cancel="completeCancelEvent"
                        >
                            <template #reference>
                                <el-button link type="primary" size="default">完成</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog v-model="visible" width="60%">
            <detail-components :taskData="taskData"></detail-components>
        </el-dialog>
    </div>
</template>

<script>
import request from '@/services'
export default {
    name: 'TodoPage',
    data() {
        return {
            tableData: [],
            visible: false,
            taskData: [],
            loading: true,
            color1: '#000000'
        }
    },
    methods: {
        formatDate(scope) {
            const deadDate = new Date(scope.row.deadline)
            const beforeTime = new Date(deadDate)
            const beforeDate = new Date(beforeTime.setDate(deadDate.getDate() - 3))
            const nowTime = new Date()
            console.log(scope)
            if (nowTime <= beforeDate) {
                scope.row.color1 = '#909399'
            } else if (nowTime > beforeDate && nowTime < deadDate) {
                scope.row.color1 = '#E6A23C'
            } else if (nowTime >= deadDate) {
                scope.row.color1 = '#F56C6C'
            }
            return new Date(scope.row.deadline).toLocaleString()
        },
        completeConfirmEvent(row) {
            const userWork = {
                workId: '',
                status: 0
            }
            userWork.workId = row.id
            userWork.status = 1
            this.setTaskStatus(userWork)
            console.log('complete confirm!')
        },
        completeCancelEvent() {
            console.log('complete cancel!')
        },
        getTableData() {
            request
                .get('/work/todo')
                .then((response) => {
                    this.tableData = response.data.data
                    if (this.taskData) {
                        this.loading = false
                    }
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        getTaskData(workId) {
            console.log(workId)
            request
                .get('/work/' + workId)
                .then((response) => {
                    console.log(response.data.data)
                    this.taskData = response.data.data
                    console.log(this.tableData)
                    return true
                })
                .catch((reportError) => {
                    console.log(reportError)
                    return false
                })
        },
        setTaskStatus(userWork) {
            userWork.completeTime = new Date()
            console.log(userWork)
            request
                .put('/work/setStatus', userWork)
                .then((response) => {
                    console.log(response.data.data)
                    this.getTableData()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        viewClick(workId) {
            this.getTaskData(workId)
            this.visible = true
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
