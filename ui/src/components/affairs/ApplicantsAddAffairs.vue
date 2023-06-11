<template>
    <el-form :model="form" :rules="rules" ref="formRules" label-width="120px">
        <el-form-item label="事务标题:" prop="title">
            <el-col :span="4">
                <el-input
                    v-model.trim="form.title"
                    placeholder="请输入事务标题"
                    class="longInput"
                />
            </el-col>
        </el-form-item>

        <el-form-item label="申请者:" v-if="grant" prop="applicantId">
            <el-col :span="4">
                <el-input
                    v-model="form.applicantId"
                    class="LongInput"
                    disabled
                    :placeholder="currentUser.username"
                />
            </el-col>
        </el-form-item>

        <el-form-item label="申请者:" v-if="!grant" prop="applicantId">
            <el-col :span="4">
                <el-select
                    v-model="form.applicantId"
                    :placeholder="currentUser.username"
                    filterable
                    ref="fieldSelect"
                    popper-class="roleSelect"
                >
                    <el-option
                        v-for="sameDepartmentUser in sameDepartmentUsers"
                        :label="sameDepartmentUser.username"
                        :value="sameDepartmentUser.id"
                        :key="sameDepartmentUser.id"
                    />
                </el-select>
            </el-col>
        </el-form-item>

        <el-form-item label="审批者:" prop="inspectorId">
            <el-col :span="4">
                <el-select
                    v-model="form.inspectorId"
                    placeholder="请选择审批者"
                    filterable
                    ref="fieldSelect"
                    popper-class="roleSelect"
                >
                    <el-option
                        v-for="user in grantUsers"
                        :label="user.username"
                        :value="user.id"
                        :key="user.id"
                    />
                </el-select>
            </el-col>
        </el-form-item>

        <el-form-item label="事务类型:" prop="typeId">
            <el-col :span="8">
                <el-radio-group v-model="form.typeId">
                    <el-radio label="1" name="type1">事假</el-radio>
                    <el-radio label="2" name="type2">病假</el-radio>
                    <el-radio label="3" name="type3">财务报销</el-radio>
                    <el-radio label="4" name="type4">调休</el-radio>
                </el-radio-group>
            </el-col>
        </el-form-item>

        <el-form-item v-model="form.createTime" label="创建时间:" prop="createTime">
            <el-col :span="5">
                <el-date-picker
                    v-model="form.createTime"
                    type="datetime"
                    placeholder="请选择要发送日期"
                    style="width: 100%"
                />
            </el-col>
        </el-form-item>

        <el-form-item label="具体内容:" prop="content">
            <el-input
                v-model.trim="form.content"
                type="textarea"
                class="textarea"
                rows="15"
                cols="20"
            />
        </el-form-item>
        <el-form-item>
            <el-col :span="6">
                <el-button type="primary" @click="onSubmit(form)">提交</el-button>
                <el-button type="danger" @click="this.resetForm">重置</el-button>
            </el-col>
        </el-form-item>
    </el-form>
</template>

<script lang="ts">
import 'element-plus/theme-chalk/index.css'
import request from '@/services/index.js'

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
                createTime: new Date(),
                inspectTime: new Date(),
                priority: '',
                modifyTime: '',
                originId: '',
                old: '',
                deleted: '',
                version: ''
            },
            grantUsers: [],
            currentUser: {
                id: '',
                username: '',
                department_id: ''
            },
            rules: {
                title: [
                    {
                        required: true,
                        message: '错误！事务标题不能为空！'
                    }
                ],
                content: [
                    {
                        required: true,
                        message: '错误！事务内容不能为空！'
                    }
                ],
                typeId: [
                    {
                        required: true,
                        message: '错误！请选择事务类型!'
                    }
                ],
                applicantId: [
                    {
                        required: true,
                        message: '错误！请输入申请者id!'
                    }
                ],
                inspectorId: [
                    {
                        required: true,
                        message: '错误！请选择审批者!'
                    }
                ]
            },
            sameDepartmentUsers: [],
            grant: true,
            formView: false
        }
    },
    methods: {
        onSubmit: function (form) {
            this.form.applicantId = this.currentUser.id
            this.$refs.formRules.validate((value) => {
                if (value) {
                    request
                        .post('/affair/add', form)
                        .then((response) => {
                            this.getPersonalAffair()
                            this.resetForm()
                        })
                        .catch((reportError) => {
                            this.resetForm()
                        })
                    this.getPersonalAffair()
                    this.$router.go(-1)
                }
            })
        }, // 表单提交及验证
        resetForm() {
            this.$nextTick(() => {
                this.$refs.ruleForm.resetFields()
            })
        }, // 重置页面
        alarm() {
            setInterval(() => {
                this.form.createTime = new Date()
            }, 500)
        }, // 动态时钟
        getGrantUser() {
            request.get('/user/affair').then((response) => {
                this.grantUsers = response.data.data
            })
        },
        getCurrentUser() {
            request.get('/user/info').then((response) => {
                this.currentUser = response.data.data
            })
        },
        selectGrant() {
            for (let i = 0; i < this.grantUsers.length; i++) {
                if (this.currentUser.id === this.grantUsers[i].id) {
                    this.grant = false
                }
            }
        },
        getSameDepartmentUser() {
            request.get('/user/department').then((response) => {
                this.sameDepartmentUsers = response.data.data
            })
        },
        getPersonalAffair() {
            request.get('/affair/personal_affairs').then((response) => {
                this.grantUsers = response.data.data
            })
        }
    },
    created() {
        this.alarm()
        this.getGrantUser()
        this.getCurrentUser()
        this.selectGrant()
        this.getSameDepartmentUser()
    },
    mounted() {
        this.$nextTick(function () {
            this.$refs.fieldSelect.$refs.scrollbar.$el.classList.add('scroll-opacity')
        })
    }
}
</script>

<style>
.longInput {
    width: 99%;
}

.textarea {
    height: 70%;
    width: 70%;
}
.roleSelect {
    height: 100px;
}
</style>
