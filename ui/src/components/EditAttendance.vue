<template>
    <el-form ref="ruleForm" :rules="rules" :model="form" :label-width="formLabelWidth">
        <el-form-item label="用户名" prop="userId">
            <el-select
                v-model="form.userId"
                filterable
                :disabled="isDisabled"
                :reserve-keyword="false"
                placeholder="选择相对应的工作人员"
            >
                <el-option
                    v-for="item in users"
                    :key="item.id"
                    :value="item.id"
                    :label="item.username"
                />
            </el-select>
        </el-form-item>
        <el-form-item label="考勤状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择考勤状态" style="width: 200px">
                <el-option label="签到" value="1" />
                <el-option label="签退" value="2" />
                <el-option label="迟到" value="3" />
            </el-select>
        </el-form-item>

        <el-form-item label="考勤时间" v-model="form.attTime" prop="attTime">
            <div class="block">
                <el-date-picker
                    v-model="form.attTime"
                    +
                    type="datetime"
                    format="YYYY-MM-DD HH:mm:ss"
                    placeholder="请选择考勤时间"
                    style="width: 200px"
                />
            </div>
        </el-form-item>
    </el-form>
    <el-footer style="text-align: center">
        <el-button type="primary" @click="submitForm(form)">确认</el-button>
        <el-button @click="cancel">取消</el-button>
    </el-footer>
</template>

<script>
import { ElMessage } from 'element-plus'

export default {
    name: 'EditAttendance',
    props: {
        users: {},
        formData: {},
        isDisabled: Boolean
    },
    data() {
        return {
            form: {
                userId: '',
                userName: '',
                status: '',
                attTime: ''
            },
            rules: {
                userId: [{ required: true, message: '请输入用户编号', trigger: 'change' }],
                status: [{ required: true, message: '请选择考勤状态', trigger: 'change' }],
                attTime: [{ required: true, message: '请选择考勤时间', trigger: 'change' }]
            },
            formLabelWidth: '80px',
            isdisabled: false
        }
    },
    methods: {
        submitForm(form) {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.$emit('addAttendance', form)
                    this.resetForm()
                    ElMessage({
                        message: '操作成功',
                        type: 'success'
                    })
                    this.$emit('setDialogVisible', false)
                } else {
                    ElMessage.error('操作失败')
                    this.$emit('setDialogVisible', false)
                    return false
                }
            })
        },
        cancel() {
            this.resetForm()
            ElMessage({
                message: '取消操作',
                type: 'warning'
            })
            this.$emit('setDialogVisible', false)
        },
        resetForm() {
            this.$nextTick(() => {
                this.$refs.ruleForm.resetFields()
            })
        }
    },
    created() {
        if (this.formData) {
            this.form = this.formData
        }
    },
    updated() {
        if (this.formData) {
            this.form = this.formData
        }
    }
}
</script>

<style scoped></style>
