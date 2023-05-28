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
                </el-col>
                <el-col :span="12">
                    <el-form-item label="性别">
                        <el-select placeholder="Select" size="large">
                            <el-option />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="生日">
                        <el-date-picker />
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input />
                    </el-form-item>
                    <el-form-item label="手机号码">
                        <el-input />
                    </el-form-item>
                    <el-form-item label="联系地址">
                        <el-input />
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script lang="ts">
export default {
    data() {
        return {
            form: {
                userId: '',
                firstName: '',
                lastName: '',
                gender: '',
                birth: '',
                email: '',
                tel: '',
                address: ''
            }
        }
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
