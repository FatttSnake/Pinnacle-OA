<template>
    <el-table :data="tableData" style="width: 100%">
        <!--        <el-table-column label="事务编号" prop="id" />-->

        <el-table-column label="事务名称" prop="title" />

        <el-table-column label="事务类型" prop="typeId">
            <template #default="scope">
                {{
                    scope.row.typeId === 1
                        ? '事假'
                        : scope.row.typeId === 2
                        ? '病假'
                        : scope.row.typeId === 3
                        ? '财务报销'
                        : '调休'
                }}
            </template>
        </el-table-column>

        <el-table-column label="申请者" prop="applicantId">
            <template #default="scope">
                {{
                    scope.row.applicantId === 1652714496280469506
                        ? 'cyb'
                        : scope.row.applicantId === 1654151146072145921
                        ? 'syf'
                        : scope.row.applicantId === 1654151877520973826
                        ? 'gzw'
                        : scope.row.applicantId === 1654151930402746370
                        ? 'yrm'
                        : scope.row.applicantId === 1
                        ? 'admin'
                        : 'ggb'
                }}
            </template>
        </el-table-column>
        <el-table-column label="提交日期" prop="createTime">
            <template #default="scope">
                {{ format(scope.row.createTime) }}
            </template>
        </el-table-column>

        <el-table-column label="审批日期" prop="inspectTime">
            <template #default="scope">
                {{ format(scope.row.inspectTime) }}
            </template>
        </el-table-column>

        <el-table-column label="操作" key="slot" width="180" prop="content">
            <template #default="scope">
                <el-button size="small" type="text" @click="dialogTure(scope.row)"
                    >具体内容
                </el-button>
                <el-button size="small" type="danger" @click="handleDelete(scope.row)">
                    删除
                </el-button>
            </template>
        </el-table-column>

        <el-table-column label="审批结果" width="90" prop="status">
            <template #default="scope">
                {{ scope.row.status === 1 ? '同意' : '驳回' }}
            </template>
        </el-table-column>
    </el-table>

    <el-dialog title="详细内容" v-model="dialogVisible" width="50%" :data="dialogData" center>
        <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="4">事务标题:</el-col>
            <el-col :span="17">{{ dialogData.title }}</el-col>
        </el-row>
        <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="4">具体内容:</el-col>
        </el-row>
        <el-row>
            <el-col :span="7"></el-col>
            <el-col :span="17">{{ dialogData.content }}</el-col>
        </el-row>
        <el-row>
            <el-col :span="11"></el-col>
            <el-col :span="2">
                <span class="dialog-footer">
                    <el-button @click="dialogFalse">返 回</el-button>
                </span>
            </el-col>
        </el-row>
    </el-dialog>
</template>

<script lang="ts">
import request from '@/services/index.js'
import 'element-plus/theme-chalk/index.css'
export default {
    data() {
        return {
            tableData: [],
            dialogVisible: false,
            dialogData: {
                id: '',
                title: '',
                content: '',
                typeId: '',
                status: '',
                applicantId: '',
                inspectorId: '',
                createTime: new Date(),
                inspectTime: new Date(),
                priority: '',
                modifyTime: '',
                originId: '',
                old: '',
                deleted: '',
                version: ''
            },
            users: []
        }
    },
    methods: {
        handleYes(row) {
            console.log(row)
            request
                .put('/affair/yes', row)
                .then((response) => {
                    console.log(response.data)
                    this.getApproved()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        handleNo(row) {
            console.log(row)
            request
                .put('/affair/no', row)
                .then((response) => {
                    console.log(response.data)
                    this.getApproved()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        handleDelete(row) {
            console.log(row.id)
            request
                .delete('/affair/' + row.id)
                .then((response) => {
                    console.log(response.data)
                    this.getApproved()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        getUser() {
            request
                .get('/user/affair')
                .then((response) => {
                    this.users = response.data.data
                })
                .catch((reportError) => {
                    console.log(reportError)
                }) // 数据库中获取用户
        },
        getApproved() {
            request
                .get('/affair/approved')
                .then((response) => {
                    this.tableData = response.data.data
                    console.log(this.tableData)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        format(time) {
            return new Date(time).toLocaleString()
        }, // 时间格式转换
        dialogTure(data) {
            this.dialogVisible = true
            this.dialogData = data
        },
        dialogFalse() {
            this.dialogVisible = false
        }
    },
    created() {
        this.getApproved()
        this.dialogFalse()
        this.getUser()
    },
    props: ['DataToRouterView'],
    watch: {
        DataToRouterView: function (val) {
            this.getUser()
            this.tableData = val
        }
    }
}
</script>
<style></style>
