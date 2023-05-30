<template>
    <div class="profile">
        <el-form :model="form" label-width="72px" label-position="top">
            <el-row :gutter="40">
                <el-col :span="12" style="text-align: center">
                    <el-avatar :size="128" style="margin-bottom: 20px" />
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="名字">
                                <el-input v-model="form.firstName" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="姓氏">
                                <el-input v-model="form.lastName" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="用户ID">
                        <el-input v-model="form.userId" />
                    </el-form-item>
                    <el-link type="default" size="default" style="float: right">修改密码</el-link>
                </el-col>
                <el-col :span="12">
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="性别">
                                <el-select v-model="form.gender" size="default" style="width: 90%">
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
                            <el-form-item label="生日">
                                <el-date-picker
                                    v-model="form.birth"
                                    size="default"
                                    placeholder="请选择日期"
                                />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="邮箱">
                        <el-input v-model="form.email" />
                    </el-form-item>
                    <el-form-item label="手机号码">
                        <el-input v-model="form.tel" />
                    </el-form-item>
                    <el-form-item label="联系地址">
                        <el-input v-model="form.address" />
                    </el-form-item>
                    <el-form-item style="float: right">
                        <el-button type="info">重置</el-button>
                        <el-button type="primary">保存</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script lang="ts">
import request from '@/services'

export default {
    data() {
        return {
            form: {
                userId: '',
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
                    label: '未知',
                    value: '0'
                },
                {
                    label: '男',
                    value: '1'
                },
                {
                    label: '女',
                    value: '2'
                }
            ]
        }
    },
    methods: {
        getFormData() {
            request
                .get('/user/info')
                .then((response) => {
                    this.form = response.data.data.staff
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
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
