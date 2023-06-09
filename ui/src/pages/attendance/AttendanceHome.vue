<template>
    <el-row :gutter="10">
        <el-col :span="12">
            <el-date-picker
                v-model="attTime"
                type="datetimerange"
                style="width: 100%"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="YYYY-MM-DD HH:mm:ss"
            />
        </el-col>
        <el-col :span="-1">
            <el-button type="primary" @click="getAttendancesByTime()">
                <el-icon :size="SIZE_ICON_SM()" style="vertical-align: center">
                    <icon-pinnacle-search />
                </el-icon>
                <span style="vertical-align: center">查询</span>
            </el-button>
            <el-button type="warning" @click="resetParam()">
                <el-icon :size="SIZE_ICON_SM()" style="vertical-align: center">
                    <icon-pinnacle-reset />
                </el-icon>
                <span style="vertical-align: center">重置</span>
            </el-button>
            <el-button type="success" @click="handleAdd()">
                <el-icon :size="SIZE_ICON_SM()" style="vertical-align: center">
                    <icon-pinnacle-plus />
                </el-icon>
                <span style="vertical-align: center">添加</span>
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
        </el-col>
    </el-row>

    <el-table
        v-loading="dataLoading"
        element-loading-text="加载中..."
        :data="tableData"
        border
        :header-cell-style="{ background: 'aliceblue' }"
        @selection-change="handleSelectionChange"
        style="margin-top: 10px"
    >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="user.username" label="用户名" align="center" />
        <el-table-column prop="attTime" label="考勤时间" width="250" align="center">
            <template #default="scope">
                {{ formatDate(scope.row.attTime) }}
            </template>
        </el-table-column>
        <el-table-column prop="status" label="考勤状态" width="150" align="center">
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
        <el-table-column prop="operations" label="操作" width="250" align="center">
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

    <el-dialog
        v-model="addDialogFormVisible"
        title="添加考勤信息"
        width="25% "
        :close-on-click-modal="false"
        :show-close="false"
        style="min-width: 320px"
    >
        <edit-attendance
            :users="users"
            :isShow="isShow"
            :isDisabled="isDisabled"
            @addAttendance="addAttendance"
            @setDialogVisible="setDialogVisible"
        ></edit-attendance>
    </el-dialog>
    <el-dialog
        v-model="editDialogFormVisible"
        title="更新考勤信息"
        width="25% "
        :close-on-click-modal="false"
        :show-close="false"
        style="min-width: 320px"
    >
        <edit-attendance
            :users="users"
            :form-data="form"
            :isShow="isShow"
            :isDisabled="isDisabled"
            @addAttendance="addAttendance"
            @setDialogVisible="setDialogVisible"
        ></edit-attendance>
    </el-dialog>
</template>
<script lang="ts">
import { DATABASE_SELECT_OK, SIZE_ICON_SM, SIZE_ICON_XL } from '@/constants/Common.constants.js'
import { ElMessage } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'

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
            isShow: true,
            dataLoading: true,
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
                .catch((reportError) => {})
        },
        // 获取所有考勤信息
        getAttendances() {
            this.dataLoading = true
            request
                .get('/attendance/findAllAttendance')
                .then((response) => {
                    if (response.data.code === DATABASE_SELECT_OK) {
                        this.dataLoading = false
                        this.tableData = response.data.data
                    }
                })
                .catch((reportError) => {
                    this.dataLoading = false
                })
        },
        getAttendancesByTime() {
            this.dataLoading = true
            if (this.attTime.length !== 0) {
                const start = this.handleDateFormatUTC(this.attTime[0])
                const end = this.handleDateFormatUTC(this.attTime[1])
                request
                    .get('/attendance/findAttendanceByTime', {
                        startTime: start,
                        endTime: end
                    })
                    .then((response) => {
                        if (response.data.code === DATABASE_SELECT_OK) {
                            this.dataLoading = false
                            this.tableData = response.data.data
                            ElMessage({
                                message: '查询成功',
                                type: 'success'
                            })
                        }
                    })
                    .catch((reportError) => {
                        this.dataLoading = false
                        ElMessage({
                            message: '查询失败',
                            type: 'error'
                        })
                    })
            } else {
                this.getAttendances()
            }
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
            this.isShow = true
        },
        // 处理保存
        doSave(formData) {
            request
                .post('/attendance/saveAttendance', formData)
                .then((response) => {
                    this.getAttendances()
                })
                .catch((reportError) => {})
        },
        // 获取更改数据
        viewUpdate(row) {
            this.getFormData()
            this.editDialogFormVisible = true
            this.isDisabled = true
            this.isShow = false
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
                .catch((reportError) => {})
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
                .catch((reportError) => {})
        }
    },
    created() {
        this.getAttendances()
    }
}
</script>

<style scoped></style>
