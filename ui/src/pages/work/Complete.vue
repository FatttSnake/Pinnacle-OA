<template>
    <div class="main">
        <div class="main-table">
            <el-table
                :data="tableData"
                style="width: 100%"
                v-loading="loading"
                element-loading-text="加载中..."
            >
                <el-table-column fixed prop="publisherName" label="发布者" width="120" />
                <el-table-column prop="content" label="内容" width="800" />
                <el-table-column prop="status" label="完成状态" width="100">
                    <template #default="scope">
                        <el-tag :type="scope.row.tagType">
                            {{ statusTag(scope.row) }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="completeTime" label="完成时间" width="200" sortable>
                    <template #default="scope">
                        {{ formatDate(scope.row.userWorkList[0].completeTime) }}
                    </template>
                </el-table-column>
                <el-table-column prop="deadline" label="结束时间" width="200">
                    <template #default="scope">
                        {{ formatDate(scope.row.deadline) }}
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
        <el-dialog v-model="visible" width="60%">
            <detail-components :taskData="taskData"></detail-components>
        </el-dialog>
    </div>
</template>

<script lang='ts'>
import request from '@/services'

export default {
    name: 'CompletePage',
    data() {
        return {
            tableData: [],
            visible: false,
            taskData: [],
            loading: true,
            tagType: 'info'
        }
    },
    methods: {
        formatDate(time) {
            console.log(new Date(time).toLocaleString())
            return new Date(time).toLocaleString()
        },
        statusTag(row) {
            if (row.userWorkList[0].completeTime <= row.deadline) {
                row.tagType = 'success'
                return '已完成'
            } else {
                row.tagType = 'danger'
                return '超时完成'
            }
        },
        todoConfirmEvent(row) {
            const userWork = {
                workId: '',
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
            request
                .get('/work/complete')
                .then((response) => {
                    this.tableData = response.data.data
                    if (this.tableData) {
                        console.log(this.tableData)
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
            console.log(userWork)
            request
                .put('/work/set_status', userWork)
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
