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

<script lang="ts">
import request from '@/services'
import { DATABASE_SELECT_OK, DATABASE_UPDATE_OK } from '@/constants/Common.constants'
import { ElMessage } from 'element-plus'
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
        },
        completeCancelEvent() {},
        getTableData() {
            request.get('/work/todo').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.tableData = response.data
                    if (this.taskData) {
                        this.loading = false
                    }
                } else {
                    ElMessage({
                        message: '数据查询出错',
                        type: 'error'
                    })
                }
            })
        },
        getTaskData(workId) {
            request.get('/work/' + workId).then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.taskData = response.data
                    return true
                } else {
                    return false
                }
            })
        },
        setTaskStatus(userWork) {
            userWork.completeTime = new Date()
            request.put('/work/set_status', userWork).then((res) => {
                const response = res.data
                if (response.code === DATABASE_UPDATE_OK) {
                    this.getTableData()
                } else {
                    ElMessage({
                        message: '状态修改失败',
                        type: 'error'
                    })
                }
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
