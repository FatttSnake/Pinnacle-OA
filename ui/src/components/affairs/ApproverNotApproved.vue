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
                    scope.row.applicantId === 1
                        ? 'ggb'
                        : scope.row.applicantId === 1652714496280469506
                        ? 'cyb'
                        : scope.row.applicantId === 1654151146072145921
                        ? 'syf'
                        : scope.row.applicantId === 1654151877520973826
                        ? 'gzw'
                        : 'yrm'
                }}
            </template>
        </el-table-column>

        <el-table-column label="提交时间" prop="createTime">
            <template #default="scope">
                {{ format(scope.row.createTime) }}
            </template>
        </el-table-column>

        <el-table-column label="操作" width="240" prop="content">
            <template #default="scope">
                <el-button size="small" type="text" @click="dialogTure(scope.row)"
                    >具体内容
                </el-button>

                <el-button size="small" type="success" @click="handleYes(scope.row)"
                    >同意
                </el-button>

                <el-button size="small" type="danger" @click="handleNo(scope.row)">
                    驳回
                </el-button>
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
                inspectTime: '',
                priority: '',
                modifyTime: '',
                originId: '',
                old: '',
                deleted: '',
                version: ''
            }
        }
    },
    methods: {
        handleYes(row) {
            console.log(row)
            row.inspectTime = new Date()
            request
                .put('/affair/yes', row)
                .then((response) => {
                    console.log(response.data)
                    this.getApproved()
                })
                .catch((reportError) => {
                    console.log(reportError)
                }) // 执行审批同意
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
                }) // 审批驳回
        },
        getApproved() {
            request
                .get('/affair/not_approved')
                .then((response) => {
                    this.tableData = response.data.data
                    console.log(this.tableData)
                })
                .catch((reportError) => {
                    console.log(reportError)
                }) // 获取事务信息或者重新刷新页面
        },
        format(time) {
            return new Date(time).toLocaleString()
        }, // 时间格式转换
        dialogTure(data) {
            this.dialogVisible = true
            this.dialogData = data
        }, // 弹出框显示
        dialogFalse() {
            this.dialogVisible = false
        } // 关闭弹出框
    },
    created() {
        this.getApproved()
        this.dialogFalse()
    }, // 获取事务信息
    props: ['DataToRouterView'],
    watch: {
        DataToRouterView: function (val) {
            this.tableData = val
        }
    }
}
</script>
<style></style>
