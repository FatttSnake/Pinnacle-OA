<template>
    <div class="profile">
        <el-form :model="form" :rules="rules" ref="form" label-width="72px" label-position="top">
            <el-row :gutter="40">
                <el-col :span="12" style="text-align: center">
                    <el-avatar :size="128" style="margin-bottom: 20px" />
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="名字" prop="firstName">
                                <el-input v-model="form.firstName" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="姓氏" prop="lastName">
                                <el-input v-model="form.lastName" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="form.username" disabled />
                    </el-form-item>
                    <el-link
                        type="default"
                        size="default"
                        style="float: right"
                        @click="visible = true"
                        >修改密码</el-link
                    >
                </el-col>
                <el-col :span="12">
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="性别" prop="gender">
                                <el-select
                                    v-model="form.gender"
                                    size="default"
                                    style="width: 90%"
                                    value-key="value"
                                >
                                    <el-option
                                        v-for="gender in genders"
                                        :key="gender.value"
                                        :label="gender.label"
                                        :value="gender.value"
                                    />
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="生日" prop="birth">
                                <el-date-picker
                                    v-model="form.birth"
                                    size="default"
                                    placeholder="请选择日期"
                                />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="form.email" />
                    </el-form-item>
                    <el-form-item label="手机号码" prop="tel">
                        <el-input v-model="form.tel" />
                    </el-form-item>
                    <el-form-item label="联系地址" prop="address">
                        <el-input v-model="form.address" />
                    </el-form-item>
                    <el-form-item style="float: right">
                        <el-button type="info" @click="resetForm">重置</el-button>
                        <el-button type="primary" @click="onSubmit(form)">保存</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
    <el-dialog
        v-model="visible"
        width="50%"
        title="修改密码"
        :show-close="false"
        :close-on-click-modal="false"
    >
        <edit-passwd @updatePasswd="updatePasswd" @cancelPasswd="cancelPasswd"></edit-passwd>
    </el-dialog>
</template>

<script lang="ts">
import _ from 'lodash'
import request from '@/services'
import { ElMessage } from 'element-plus'
import { requestUser } from '@/utils/auth'
import { DATABASE_SELECT_OK, DATABASE_UPDATE_OK } from '@/constants/Common.constants'

export default {
    data() {
        return {
            form: {
                username: '',
                passwd: '',
                firstName: '',
                lastName: '',
                gender: '',
                birth: '',
                email: '',
                tel: '',
                address: ''
            },
            staff: {
                username: '',
                passwd: '',
                firstName: '',
                lastName: '',
                gender: '',
                birth: '',
                email: '',
                tel: '',
                address: ''
            },
            genders: [
                {
                    label: '保密',
                    value: 0
                },
                {
                    label: '男',
                    value: 1
                },
                {
                    label: '女',
                    value: 2
                }
            ],
            rules: {
                firstName: [
                    {
                        required: true,
                        message: '请输入名字'
                    }
                ],
                lastName: [
                    {
                        required: true,
                        message: '请输入姓氏'
                    }
                ],
                gender: [
                    {
                        required: true,
                        message: '请选择性别'
                    }
                ]
            },
            visible: false
        }
    },
    methods: {
        getFormData() {
            request.get('/user/info').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.staff = response.data.staff
                    this.staff.username = response.data.username
                    this.form = _.cloneDeep(this.staff)
                } else {
                    ElMessage({
                        message: '数据查询出错',
                        type: 'error'
                    })
                }
            })
        },
        putForm(form) {
            request.put('/staff/self', form).then(async (res) => {
                const response = res.data
                if (response.code === DATABASE_UPDATE_OK) {
                    ElMessage({
                        message: '修改成功',
                        type: 'success'
                    })
                    this.getFormData()
                    await requestUser()
                    location.reload()
                } else {
                    ElMessage({
                        message: '修改失败',
                        type: 'error'
                    })
                }
            })
        },
        onSubmit(form) {
            // 表单校验
            this.$refs.form.validate((value) => {
                if (value) {
                    this.putForm(form)
                    console.log('submit!')
                } else {
                    console.log('fault!')
                }
            })
        },
        resetForm() {
            this.form = _.cloneDeep(this.staff)
        },
        updatePasswd(passwdForm) {
            console.log(passwdForm)
            this.visible = false
        },
        cancelPasswd() {
            this.visible = false
        }
    },
    created() {
        this.getFormData()
    }
}
</script>
<style>
.profile {
    padding: 20px;
}
.profile .el-input {
    width: 100%;
}
</style>
