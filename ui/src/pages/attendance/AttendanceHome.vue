<template>
    <div id="attendanceMain">
        <div id="attendanceMain1">
            <el-date-picker
                v-model="attTime"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="YYYY-MM-DD HH:mm:ss"
            >
            </el-date-picker>

            <el-button type="primary" style="margin-left: 15px" @click="getAttendancesByTime()">
                <el-icon :size="SIZE_ICON_SM()" style="vertical-align: center">
                    <icon-pinnacle-search />
                </el-icon>
                <span style="vertical-align: center">查询</span>
            </el-button>

            <el-button type="warning" style="margin-left: 15px" @click="resetParam()">
                <el-icon :size="SIZE_ICON_SM()" style="vertical-align: center">
                    <icon-pinnacle-reset />
                </el-icon>
                <span style="vertical-align: center">重置</span>
            </el-button>

            <el-button type="success" style="margin-left: 15px" @click="handleAdd()">
                <el-icon :size="SIZE_ICON_SM()" style="vertical-align: center">
                    <icon-pinnacle-click />
                </el-icon>
                <span style="vertical-align: center">增加</span>
            </el-button>

            <el-popconfirm
                title="你确定要批量删除这些数据吗?"
                confirm-button-text="确定"
                cancel-button-text="再想想"
                icon-color="red"
                @cancel="cancel"
                @confirm="delBatch"
            >
                <template #reference>
                    <el-button type="danger" style="margin-left: 15px">
                        <el-icon :size="SIZE_ICON_SM()" style="vertical-align: center">
                            <icon-pinnacle-deleteAll />
                        </el-icon>
                        <span style="vertical-align: center">批量删除</span>
                    </el-button>
                </template>
            </el-popconfirm>
        </div>

        <div id="attendanceMain2">
            <el-table
                :data="tableData"
                border
                style="width: 80%"
                :header-cell-style="{ background: 'aliceblue' }"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" />
                <el-table-column prop="id" label="考勤编号" width="200"></el-table-column>
                <el-table-column prop="user.username" label="用户名" width="180"></el-table-column>
                <el-table-column prop="attTime" label="考勤时间">
                    <template #default="scope">
                        {{ formatDate(scope.row.attTime) }}
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="考勤状态">
                    <template v-slot="scope">
                        <el-tag
                            :type="
                                scope.row.status === 1
                                    ? 'success'
                                    : scope.row.status === 2
                                    ? ''
                                    : scope.row.status === 3
                                    ? 'warning'
                                    : scope.row.status === 1 + ''
                                    ? 'success'
                                    : scope.row.status === 2 + ''
                                    ? ''
                                    : scope.row.status === 3 + ''
                                    ? 'warning'
                                    : 'danger'
                            "
                            disable-transitions
                            >{{
                                scope.row.status === 1
                                    ? '签到'
                                    : scope.row.status === 2
                                    ? '签退'
                                    : scope.row.status === 3
                                    ? '迟到'
                                    : scope.row.status === 1 + ''
                                    ? '签到'
                                    : scope.row.status === 2 + ''
                                    ? '签退'
                                    : scope.row.status === 3 + ''
                                    ? '迟到'
                                    : '异常'
                            }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="operations" label="操作">
                    <template #default="scope">
                        <el-button type="success" size="small" @click="viewUpdate(scope.row)"
                            >更改
                        </el-button>
                        <el-popconfirm
                            title="您确定要删除吗?"
                            confirm-button-text="确定"
                            cancel-button-text="再想想"
                            icon-color="red"
                            @cancel="cancel"
                            @confirm="handleDelete(scope.row.id)"
                        >
                            <template #reference>
                                <el-button type="danger" size="small">删除</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div>
            <el-dialog
                v-model="addDialogFormVisible"
                title="考勤信息"
                width="25% "
                :close-on-click-modal="false"
                :show-close="false"
            >
                <edit-attendance
                    :users="users"
                    :isDisabled="isDisabled"
                    @addAttendance="addAttendance"
                    @setDialogVisible="setDialogVisible"
                ></edit-attendance>
            </el-dialog>
            <el-dialog
                v-model="editDialogFormVisible"
                title="考勤信息"
                width="25% "
                :close-on-click-modal="false"
                :show-close="false"
            >
                <edit-attendance
                    :users="users"
                    :form-data="form"
                    :isDisabled="isDisabled"
                    @addAttendance="addAttendance"
                    @setDialogVisible="setDialogVisible"
                ></edit-attendance>
            </el-dialog>
        </div>
    </div>
</template>
<script lang="ts">
import { SIZE_ICON_SM, SIZE_ICON_XL } from '@/constants/Common.constants.js'
import { ElMessage } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import '@/assets/css/attendance.css'

import _ from 'lodash'
import request from '@/services'

export default {
    name: 'AttendanceHome',
    data() {
        return {
            attendance: '',
            id: '',
            username: '',
            attTime: [],
            status: '',
            pageNum: 1,
            pageSize: 10,
            total: 0,
            multipleSelection: [],
            value1: '',
            users: [],
            tableData: [],
            addDialogFormVisible: false,
            editDialogFormVisible: false,
            form: {
                userId: '',
                userName: '',
                status: '',
                attTime: ''
            },
            isDisabled: false,

            options: [
                {
                    value: 1,
                    label: '签到'
                },
                {
                    value: 2,
                    label: '签退'
                },
                {
                    value: 3,
                    label: '迟到'
                }
            ],
            nameOptions: [
                {
                    userId: '',
                    username: ''
                }
            ]
        }
    },

    methods: {
        SIZE_ICON_SM() {
            return SIZE_ICON_SM
        },
        SIZE_ICON_XL() {
            return SIZE_ICON_XL
        },
        formatDate(time) {
            return new Date(time).toLocaleString()
        },
        // 获取所有user信息
        getFormData() {
            request
                .get('/user/department')
                .then((response) => {
                    this.users = response.data.data
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        // 获取所有考勤信息
        getAttendances() {
            request
                .get('/attendance/findAllAttendance')
                .then((response) => {
                    this.tableData = response.data.data
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        getAttendancesByTime() {
            const start = this.handleDateFormatUTC(this.attTime[0])
            const end = this.handleDateFormatUTC(this.attTime[1])
            request
                .get('/attendance/findAttendanceByTime', {
                    startTime: start,
                    endTime: end
                })
                .then((response) => {
                    this.tableData = response.data.data
                    ElMessage({
                        message: '查询成功',
                        type: 'success'
                    })
                })
                .catch((reportError) => {
                    console.log(reportError)
                    ElMessage({
                        message: '查询失败',
                        type: 'error'
                    })
                })
        },
        handleDateFormatUTC(date) {
            let newFormat = ''
            const dateParse = new Date(Date.parse(date))
            const yy = dateParse.getUTCFullYear()
            const mm = _.padStart((dateParse.getUTCMonth() + 1).toString(), 2, '0')
            const dd = _.padStart(dateParse.getUTCDate().toString(), 2, '0')
            const hh = _.padStart(dateParse.getUTCHours().toString(), 2, '0')
            const mf = _.padStart(dateParse.getUTCMinutes().toString(), 2, '0')
            const ss = _.padStart(dateParse.getUTCSeconds().toString(), 2, '0')
            newFormat = yy + '-' + mm + '-' + dd + ' ' + hh + ':' + mf + ':' + ss
            return newFormat
        },

        // 重置参数
        resetParam() {
            this.attTime = []
            this.status = ''
            this.getAttendances()
            ElMessage({
                message: '重置成功',
                type: 'success'
            })
        },
        setDialogVisible(dialogVisible) {
            this.addDialogFormVisible = dialogVisible
            this.editDialogFormVisible = dialogVisible
            this.getAttendances()
        },
        // 打开添加弹窗
        handleAdd() {
            this.getFormData()
            this.addDialogFormVisible = true
            this.isDisabled = false
        },
        // 处理保存
        doSave(formData) {
            request
                .post('/attendance/saveAttendance', formData)
                .then((response) => {
                    console.log(response)
                    this.getAttendances()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        // 获取更改数据
        viewUpdate(row) {
            this.getFormData()
            this.editDialogFormVisible = true
            this.isDisabled = true
            this.form = row
            this.form.status = row.status + ''
        },
        addAttendance(formData) {
            this.doSave(formData)
            this.addDialogFormVisible = false
        },
        // 点击取消
        cancel() {
            this.resetForm()
            ElMessage({
                message: '取消操作',
                type: 'warning'
            })
            this.getAttendances()
        },
        // 表单重置
        resetForm() {
            this.$nextTick(() => {
                this.$refs.ruleForm.resetFields()
            })
        },

        // 操作删除
        handleDelete(id) {
            request
                .delete('/attendance/delAttendance/' + id)
                .then((response) => {
                    if (response) {
                        console.log(response)
                        ElMessage({
                            message: '删除成功',
                            type: 'success'
                        })
                        this.getAttendances()
                    } else {
                        ElMessage({
                            message: '删除失败',
                            type: 'error'
                        })
                    }
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        // 批量删除
        handleSelectionChange(val) {
            this.multipleSelection = val
        },
        // 批量删除
        delBatch() {
            const map = this.multipleSelection.map((v) => v.id)
            request
                .post('/attendance/delBatchAttendance', map)
                .then((response) => {
                    if (response) {
                        ElMessage({
                            message: '批量删除成功',
                            type: 'success'
                        })
                        this.getAttendances()
                    } else {
                        ElMessage({
                            message: '批量删除失败',
                            type: 'error'
                        })
                    }
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        }
    },
    created() {
        this.getAttendances()
    }
}
</script>

<style scoped></style>
