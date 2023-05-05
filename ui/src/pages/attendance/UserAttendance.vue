<template>
    <div id="attendanceMain">
        <div id="attendanceMain1">
            <el-date-picker
                v-model="attTimeB"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="YYYY-MM-DD HH:mm:ss"
            >
            </el-date-picker>

            <el-button type="primary" style="margin-left: 15px" @click="getOneAttendancesByTime()">
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
            <el-button type="success" @click="handleAdd()" style="margin-left: 15px">
                <el-icon :size="SIZE_ICON_SM()" style="vertical-align: center">
                    <icon-pinnacle-click />
                </el-icon>
                <span style="vertical-align: center">打卡</span>
            </el-button>
        </div>

        <div id="attendanceMain2">
            <el-table
                :data="tableData"
                border
                style="width: 80%"
                :header-cell-style="{ background: 'aliceblue' }"
            >
                <el-table-column prop="id" label="考勤编号" width="400"></el-table-column>
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
                                    ? 'primary'
                                    : scope.row.status === 3
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
                                    : '异常'
                            }}
                        </el-tag>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="demo-pagination-block">
            <el-pagination
                small
                :current-page="pageNum"
                :page-size="pageSize"
                :page-sizes="[5, 10, 20, 30]"
                background
                layout="total,sizes,prev, pager, next,jumper"
                :total="total"
            />
        </div>
        <div>
            <el-dialog v-model="dialogFormVisible" title="考勤信息" width="25%">
                <el-form ref="ruleForm" :rules="rules" :model="form" :label-width="formLabelWidth">
                    <el-form-item label="用户编号" prop="userId">
                        <el-input
                            v-model="form.userId"
                            autocomplete="off"
                            style="width: 200px"
                            disabled
                        />
                    </el-form-item>

                    <el-form-item label="考勤时间" v-model="attTime" prop="attTime">
                        <div class="block">
                            <el-date-picker
                                v-model="nowTime"
                                type="datetime"
                                disabled
                                style="width: 200px"
                            />
                        </div>
                    </el-form-item>
                </el-form>

                <template #footer>
                    <span class="dialog-footer">
                        <el-button type="primary" @click="submitForm()">确认</el-button>
                        <el-button @click="cancel">取消</el-button>
                    </span>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script lang="ts">
import axios from 'axios'
import { SIZE_ICON_SM, SIZE_ICON_XL } from '@/constants/Common.constants.js'
import { ElMessage } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import _ from 'lodash'

export default {
    name: 'UserAttendance',
    data() {
        return {
            attendance: '',
            id: '',
            userId: 1,
            username: '',
            attTime: '',
            attTimeB: [],
            status: '',
            pageNum: 1,
            pageSize: 10,
            total: 0,
            nowTime: new Date(),
            formLabelWidth: '80px',
            value1: '',
            form: {
                userId: '',
                userName: '',
                status: '',
                attTime: new Date()
            },
            tableData: [],
            dialogFormVisible: false,
            rules: {
                userId: [{ required: true, message: '请输入用户编号', trigger: 'change' }],
                attTime: [{ required: true, message: '请选择考勤时间', trigger: 'change' }]
            }
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
            this.attTimeB = []
            this.status = ''
            this.getAttendancesByUserId()
            ElMessage({
                message: '重置成功',
                type: 'success'
            })
        },

        getOneAttendancesByTime() {
            console.log(typeof this.attTimeB[0])
            const start = this.handleDateFormatUTC(this.attTimeB[0])
            const end = this.handleDateFormatUTC(this.attTimeB[1])
            console.log(start + '\t' + end)
            axios
                .get('http://localhost:8621/attendance/findOneAttendanceByTime', {
                    params: {
                        startTime: start,
                        endTime: end,
                        userId: this.userId
                    }
                })
                .then((response) => {
                    console.log(response.data.data)
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
                    this.getAttendancesByUserId()
                })
        },
        getAttendancesByUserId() {
            axios
                .get('http://localhost:8621/attendance/selectAttendance/' + this.userId)
                .then((response) => {
                    console.log(response.data.data)
                    this.tableData = response.data.data
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        resetForm() {
            this.$refs.form.resetFields()
        },
        handleAdd() {
            this.dialogFormVisible = true
            // this.getNowDate()
            this.nowTime = new Date()
            this.form.attTime = this.nowTime
            this.$nextTick(() => {
                this.form.userId = this.userId + ''
            })
        },
        doSave() {
            axios
                .post('http://localhost:8621/attendance/saveOneAttendance', this.form)
                .then((response) => {
                    this.dialogFormVisible = false
                    this.getAttendancesByUserId()
                    console.log(response.data.data)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },

        submitForm() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    console.log(this.form.attTime)
                    this.doSave()
                    ElMessage({
                        message: '操作成功',
                        type: 'success'
                    })
                } else {
                    ElMessage.error('操作失败')
                    return false
                }
            })
        },
        // 点击取消
        cancel() {
            this.dialogFormVisible = false
            ElMessage({
                message: '取消操作',
                type: 'warning'
            })
            this.getAttendancesByUserId()
        }
    },

    created() {
        this.getAttendancesByUserId()
    }
}
</script>

<style scoped></style>
