<template>
    <el-form :model="form" label-width="120px">
        <el-form-item label="事务名称:">
            <el-col :span="4">
                <el-input v-model="form.title" placeholder="请输入事务名称" class="longInput" />
            </el-col>
        </el-form-item>

        <el-form-item label="申请者:">
            <el-col :span="3">
                <el-input v-model="form.applicantId" class="shortInput" />
            </el-col>
        </el-form-item>

        <el-form-item label="审批者:">
            <el-col :span="4">
                <el-select v-model="form.inspectorId" placeholder="请选择审批者">
                    <el-option value="1" label="ggb" />
                    <el-option value="1652714496280469506" label="cyb" />
                    <el-option value="1654151146072145921" label="syf" />
                    <el-option value="1654151877520973826" label="gzw" />
                    <el-option value="1654151930402746370" label="yrm" />
                </el-select>
            </el-col>
        </el-form-item>

        <el-form-item label="事务类型:">
            <el-col :span="8">
                <el-radio-group v-model="form.typeId">
                    <el-radio label="1" name="type1">事假</el-radio>
                    <el-radio label="2" name="type2">病假</el-radio>
                    <el-radio label="3" name="type3">财务报销</el-radio>
                    <el-radio label="4" name="type4">调休</el-radio>
                </el-radio-group>
            </el-col>
        </el-form-item>

        <el-form-item v-model="form.createTime" label="发送日期:">
            <el-col :span="5">
                <el-date-picker
                    v-model="form.createTime"
                    type="datetime"
                    placeholder="请选择要发送日期"
                    style="width: 100%"
                />
            </el-col>
        </el-form-item>

        <el-form-item label="具体内容:">
            <el-input v-model="form.content" type="textarea" class="textarea" rows="15" cols="20" />
        </el-form-item>
        <el-form-item>
            <el-col :span="6">
                <el-button type="primary" @click="onSubmit(form)">提交</el-button>
                <el-button type="danger" @click="this.resetForm">重置</el-button>
            </el-col>
        </el-form-item>
    </el-form>
</template>

<script>
import 'element-plus/theme-chalk/index.css'
import request from '@/services'
import _ from 'lodash'
import { ElMessage } from 'element-plus'
export default {
    data() {
        return {
            form: {
                id: '',
                title: '',
                content: '',
                typeId: '',
                status: '',
                applicantId: '',
                inspectorId: '',
                createTime: '',
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
    // created: {
    //     // request(form) {
    //     //     post("localhost:8686/addAffairs",
    //     //         {
    //     //             affairsName: form.affairsName,
    //     //             Applicants: form.Applicants,
    //     //             Approver: form.Approver,
    //     //             affairsType: form.affairsType,
    //     //             Date: form.Date,
    //     //             Time: form.Time,
    //     //             affairsContent: form.affairsContent
    //     //         },
    //     //     )
    //     //     get("localhost:8686/addAffairs")
    //     // }
    // },
    methods: {
        onSubmit: function (form) {
            console.log(form)
            if (
                !_.isEmpty(form.title) &&
                !_.isEmpty(form.content) &&
                !_.isEmpty(form.typeId) &&
                !_.isEmpty(form.applicantId) &&
                !_.isEmpty(form.inspectorId)
            ) {
                request
                    .post('http://localhost:8621/affair/add', form)
                    .then((response) => {
                        console.log(response.data)
                        this.resetForm()
                    })
                    .catch((reportError) => {
                        this.resetForm()
                        console.log(reportError)
                    })
                this.$router.go()
            } else {
                if (_.isEmpty(form.title)) {
                    ElMessage({
                        message: '错误！事务标题不能为空！',
                        type: 'error'
                    })
                }
                if (_.isEmpty(form.content)) {
                    ElMessage({
                        message: '错误！事务内容不能为空!',
                        type: 'error'
                    })
                }
                if (_.isEmpty(form.typeId)) {
                    ElMessage({
                        message: '错误！请选择事务类型!',
                        type: 'error'
                    })
                }
                if (_.isEmpty(form.applicantId)) {
                    ElMessage({
                        message: '错误！请输入申请者id!',
                        type: 'error'
                    })
                }
                if (_.isEmpty(form.inspectorId)) {
                    ElMessage({
                        message: '错误！请选择审批者!',
                        type: 'error'
                    })
                }
                if (_.isEmpty(form.createTime)) {
                    ElMessage({
                        message: '错误！发送时间不能为空！',
                        type: 'error'
                    })
                }
            }
        },
        resetForm() {
            this.$nextTick(() => {
                this.$refs.ruleForm.resetFields()
            })
        }
    }
}
</script>

<style>
.longInput {
    width: 99%;
}

.shortInput {
    width: 150px;
}

.textarea {
    height: 70%;
    width: 70%;
}
</style>
