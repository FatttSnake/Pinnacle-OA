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
                </el-select>
            </el-col>
        </el-form-item>

        <el-form-item label="事务类型:">
            <el-col :span="8">
                <el-radio-group v-model="form.typeId">
                    <el-radio :label="1" name="type">事假</el-radio>
                    <el-radio :label="2" name="type">病假</el-radio>
                    <el-radio :label="3" name="type">财务报销</el-radio>
                    <el-radio :label="4" name="type">调休</el-radio>
                </el-radio-group>
            </el-col>
        </el-form-item>

        <el-form-item label="发送日期:">
            <el-col :span="1"><i class="el-icon-date"></i></el-col>
            <el-col :span="5">
                <el-date-picker
                    v-model="form.createTime"
                    type="date"
                    placeholder="请选择要发送日期"
                    style="width: 100%"
                />
            </el-col>
        </el-form-item>

        <el-form-item label="具体内容:">
            <el-row :span="20">
                <el-col :span="40">
                    <el-input
                        v-model="form.content"
                        type="textarea"
                        class="textarea"
                        rows="15"
                        cols="20"
                    />
                </el-col>
            </el-row>
        </el-form-item>

        <el-form-item>
            <el-col :span="6">
                <el-button type="primary" @click="onSubmit(form)">提交</el-button>
                <el-button type="danger" @click="getApproed()">重置</el-button>
            </el-col>
        </el-form-item>
    </el-form>
</template>

<script>
import 'element-plus/theme-chalk/index.css'
import request from '@/services'
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
        onSubmit(form) {
            console.log(form)
            request
                .post('http://localhost:8621/affair/add', form)
                .then((response) => {
                    console.log(response.data)
                    this.getApproed()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        getApproed() {
            history.go(0)
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
