<template>
    <el-form :model="form" :rules="rules" label-width="120px" ref="ruleForm">
        <el-form-item label="工作人员" prop="worker">
            <el-select
                v-model="form.worker"
                multiple
                filterable
                :reserve-keyword="false"
                value-key="username"
                placeholder="选择相对应的工作人员"
            >
                <el-option
                    v-for="item in workers"
                    :key="item.userId"
                    :label="item.username"
                    :value="item"
                />
            </el-select>
        </el-form-item>
        <el-form-item label="终止时间" prop="deadline">
            <el-col :span="10">
                <el-date-picker
                    v-model="form.deadline"
                    type="datetime"
                    format="YYYY-MM-DD HH:mm"
                    placeholder="请选择时间"
                    style="width: 100%"
                />
            </el-col>
        </el-form-item>
        <el-form-item label="工作内容" prop="content">
            <el-input v-model="form.content" type="textarea" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit(form)">
                <template #default> {{ this.commitButton }}</template>
            </el-button>
            <el-button @click="reset">重置</el-button>
            <el-button @click="cancel">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts">
import axios from 'axios'
export default {
    props: {
        editForm: {
            publisherId: '',
            createTime: '',
            deadline: '',
            content: '',
            worker: []
        }
    },
    data() {
        return {
            commitButton: '创建',
            form: {
                publisherId: '',
                createTime: '',
                deadline: '',
                content: '',
                worker: []
            },
            workers: [
                {
                    userId: '',
                    username: ''
                }
            ],
            rules: {
                worker: [
                    {
                        required: true,
                        message: '请选择相应的工作人员'
                    }
                ],
                deadline: [
                    {
                        required: true,
                        message: '请输入终止日期'
                    }
                ],
                content: [
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
                .get('http://localhost:8621/user')
                .then((response) => {
                    console.log(response.data.data)
                    this.workers = response.data.data
                    console.log(this.workers)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        onSubmit(form) {
            // 表单校验
            this.$refs.ruleForm.validate((value) => {
                if (value) {
                    console.log(form.deadline)
                    console.log(typeof form.deadline)
                    form.publisherId = String(1)
                    if (this.editForm) {
                        this.$emit('updateWork', form)
                    } else {
                        this.$emit('addWork', form)
                    }
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
    updated() {
        if (this.editForm) {
            this.form = this.editForm
            this.commitButton = '保存'
        }
        this.getFormData()
    },
    created() {
        if (this.editForm) {
            this.form = this.editForm
            this.commitButton = '保存'
        }
        this.getFormData()
    }
}
</script>

<style scoped></style>
