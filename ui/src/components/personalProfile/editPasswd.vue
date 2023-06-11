<template>
    <el-main>
        <el-form :model="passwdForm" :rules="rules" ref="passwdForm" label-width="90px">
            <el-form-item label="旧密码" prop="oldPasswd">
                <el-input v-model="passwdForm.oldPasswd" placeholder="请输入旧密码" show-password />
            </el-form-item>
            <el-form-item label="新密码" prop="newPasswd">
                <el-input v-model="passwdForm.newPasswd" placeholder="请输入新密码" show-password />
            </el-form-item>
            <el-form-item label="确认密码" prop="selectPasswd">
                <el-input
                    v-model="passwdForm.selectPasswd"
                    placeholder="请重新确认密码"
                    show-password
                />
            </el-form-item>
        </el-form>
    </el-main>
    <el-footer class="dialog-footer" style="text-align: center">
        <el-button @click="cancelPasswd">取消</el-button>
        <el-button type="primary" @click="onSubmit(passwdForm)">确定</el-button>
    </el-footer>
</template>
<script lang="ts">
export default {
    emits: ['updatePasswd', 'cancelPasswd'],
    data() {
        return {
            passwdForm: {
                oldPasswd: '',
                newPasswd: '',
                selectPasswd: ''
            },
            rules: {
                oldPasswd: [
                    {
                        required: true,
                        message: '旧密码不能为空'
                    }
                ],
                newPasswd: [
                    {
                        required: true,
                        message: '新密码不能为空'
                    },
                    {
                        validator: this.validateSurePasswordLength,
                        trigger: 'blur'
                    }
                ],
                selectPasswd: [
                    {
                        required: true,
                        message: '确认密码不能为空',
                        trigger: 'blur'
                    },
                    {
                        validator: this.validateSurePassword,
                        trigger: 'blur'
                    },
                    {
                        validator: this.validateSurePasswordLength,
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    methods: {
        onSubmit(passwdForm) {
            // 表单校验
            this.$refs.passwdForm.validate((value) => {
                if (value) {
                    this.$emit('updatePasswd', passwdForm)
                    this.$refs.passwdForm.resetFields()
                }
            })
        },
        cancelPasswd() {
            this.$emit('cancelPasswd', null)
            this.$refs.passwdForm.resetFields()
        },
        validateSurePassword(rule, value, callback) {
            if (value !== '') {
                if (value !== this.passwdForm.newPasswd) {
                    callback(new Error('两次输入的新密码不一致'))
                    return false
                } else {
                    callback()
                }
            }
        },
        validateSurePasswordLength(rule, value, callback) {
            if (value.length < 8 || value.length > 64) {
                callback(new Error('新密码长度应为8到64位'))
                return false
            } else {
                callback()
            }
        }
    }
}
</script>
<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>
