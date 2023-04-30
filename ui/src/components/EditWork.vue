<template>
    <el-form :model="form" :rules="rules" label-width="120px" ref="ruleForm">
        <el-form-item label="工作人员" prop="worker">
            <el-select
                v-model="form.worker"
                multiple
                filterable
                :reserve-keyword="false"
                value-key="userID"
                placeholder="选择相对应的工作人员"
            >
                <el-option
                    v-for="item in workers"
                    :key="item.userID"
                    :label="item.userName"
                    :value="item"
                />
            </el-select>
        </el-form-item>
        <el-form-item label="终止时间" prop="deadLine">
            <el-col :span="11">
                <el-date-picker
                    v-model="form.deadLine"
                    type="datetime"
                    format="YYYY-MM-DD HH:mm"
                    placeholder="请选择时间"
                    style="width: 100%"
                />
            </el-col>
        </el-form-item>
        <el-form-item label="工作内容" prop="taskContent">
            <el-input v-model="form.taskContent" type="textarea" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit(form)">创建</el-button>
            <el-button @click="reset">重置</el-button>
            <el-button @click="cancel">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            form: {
                publisher_id: '',
                createTime: '',
                deadLine: '',
                taskContent: '',
                worker: []
            },
            workers: [
                {
                    userID: '',
                    userName: ''
                }
            ],
            rules: {
                worker: [
                    {
                        required: true,
                        message: '请选择相应的工作人员'
                    }
                ],
                deadLine: [
                    {
                        type: 'date',
                        required: true,
                        message: '请输入终止日期'
                    }
                ],
                taskContent: [
                    {
                        required: true,
                        message: '请输入工作内容'
                    }
                ]
            }
        }
    },
    methods: {
        getFormData() {
            axios
                .get('http://localhost:8080/user')
                .then((response) => {
                    console.log(response.data)
                    this.workers = response.data
                    console.log(this.workers)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        addWork(form) {
            console.log(form)
            axios
                .post('http://localhost:8080/work', form)
                .then((response) => {
                    console.log(response.data)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        onSubmit(form) {
            //表单校验
            this.$refs.ruleForm.validate((value) => {
                if (value) {
                    form.createTime = new Date().getTime().toString()
                    console.log(form)
                    form.publisher_id = String(1)
                    this.addWork(form)
                    this.$emit('setDialogVisible', false)
                    console.log('submit!')
                } else {
                    console.log('fault!')
                }
            })
        },
        reset() {
            this.$refs.ruleForm.resetFields()
        },
        cancel() {
            this.$emit('setDialogVisible', false)
        }
    },
    created() {
        this.getFormData()
    }
}
</script>

<style scoped></style>
