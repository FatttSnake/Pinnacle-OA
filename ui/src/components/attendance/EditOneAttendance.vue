<template>
    <el-form ref="ruleForm" :model="form" :label-width="formLabelWidth">
        <el-form-item label="考勤时间" prop="attTime">
            <el-date-picker v-model="form.attTime" type="datetime" disabled style="width: 200px" />
        </el-form-item>
    </el-form>
    <div style="text-align: center">
        <span class="dialog-footer">
            <el-button type="primary" @click="submitForm()">确认</el-button>
            <el-button @click="cancel">取消</el-button>
        </span>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import _ from 'lodash'
import request from '@/services'
import { DATABASE_SAVE_OK } from '@/constants/Common.constants'

export default {
    name: 'EditOneAttendance',
    emits: ['setDialogVisible'],
    data() {
        return {
            formLabelWidth: '80px',
            form: {
                attTime: new Date()
            },
            nowTime: new Date()
        }
    },
    props: {
        formData: {}
    },
    methods: {
        submitForm() {
            this.doSave()
        },
        // 点击取消
        cancel() {
            ElMessage({
                message: '取消操作',
                type: 'warning'
            })
            this.$emit('setDialogVisible', false)
        },
        alarm() {
            setInterval(() => {
                this.form.attTime = new Date()
            }, 500)
        },
        doSave() {
            request
                .post('/attendance/saveOneAttendance', this.form)
                .then((response) => {
                    if (response.data.code === DATABASE_SAVE_OK) {
                        ElMessage({
                            message: '打卡成功',
                            type: 'success'
                        })
                        this.$emit('setDialogVisible', false)
                    }
                })
                .catch((reportError) => {
                    ElMessage({
                        message: '打卡失败',
                        type: 'warning'
                    })
                })
        }
    },
    created() {
        this.alarm()
        if (this.formData) {
            this.form = _.cloneDeep(this.formData)
        }
    }
}
</script>

<style scoped></style>
