<template>
    <el-form :model="addData" :rules="rules" ref="addData" label-width="100px">
        <el-form-item label="公告标题" prop="title">
            <el-input v-model="addData.title"></el-input>
        </el-form-item>
        <el-form-item label="公告类型" prop="typeId">
            <el-select v-model="addData.typeId" filterable placeholder="请选择公告类型">
                <el-option
                    v-for="item in noticeTypeList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                >
                </el-option>
            </el-select>
        </el-form-item>
        <el-row>
            <el-form-item label="生效时间" required>
                <el-col>
                    <el-form-item prop="sendTime">
                        <el-date-picker
                            type="datetime"
                            placeholder="选择生效日期"
                            v-model="addData.sendTime"
                            style="width: 100%"
                            size="large"
                            :picker-options="pickerOptions"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-form-item>
            <el-col :span="2"></el-col>
            <el-form-item label="失效时间" required>
                <el-col>
                    <el-form-item prop="endTime">
                        <el-date-picker
                            type="datetime"
                            placeholder="选择失效日期"
                            v-model="addData.endTime"
                            style="width: 100%"
                            size="large"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-form-item>
        </el-row>
        <el-form-item label="是否置顶" prop="top">
            <el-switch v-model="addData.top" inline-prompt active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item label="公告优先级" prop="priority">
            <el-slider v-model="addData.priority" show-input show-stops :max="15" size="large" />
        </el-form-item>
        <el-form-item label="发送至：" prop="receivers">
            <el-cascader
                v-model="addData.receivers"
                collapse-tags
                clearable
                :options="departmentList"
                :props="{
                    multiple: true,
                    value: 'id',
                    label: 'name',
                    children: 'userList'
                }"
                placeholder="选择公告接收者"
            >
                <template #default="scope">
                    <span v-if="scope.node.level == 1">{{
                        ((scope.node.value = scope.data.id), (scope.node.label = scope.data.name))
                    }}</span>
                    <span v-if="scope.node.level == 2">{{
                        ((scope.node.value = scope.data.id),
                        (scope.node.label = scope.data.username))
                    }}</span>
                </template>
            </el-cascader>
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
            <el-input type="textarea" v-model="addData.content"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm('addData')">发布</el-button>
            <el-button @click="resetForm()">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="js">
export default {
    props:{
        departmentList: [],
        noticeTypeList: [],
        noticeEdit:{}
    },
    data() {
        return {
            addData: {
                title: '',
                typeId: '',
                sendTime: '',
                endTime: '',
                top: false,
                priority: 1,
                content: '',
                receivers:[]
            },
            pickerOptions: {},
            rules: {
                title: [
                    { required: true, message: '请输入公告标题', trigger: 'blur' },
                    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
                ],
                type: [
                    { type:'array',required: true, message: '请选择公告类型', trigger: 'change' }
                ],
                startTime: [
                    { type: 'date', required: true, message: '请选择生效时间', trigger: 'change' }
                ],
                endTime: [
                    { type: 'date', required: true, message: '请选择失效时间', trigger: 'change' }
                ],
                content: [
                    { required: true, message: '请填写公告内容', trigger: 'blur' }
                ],
                receivers: [
                    { type:'array',required: true, message: '请选择公告接收者', trigger: 'change' }
                ]
            }
        }

    },
    methods: {
        submitForm(formtitle) {
            this.addData.top=this.addData.top?1:0;
            const receiveId=[]
            for (let i = 0; i < this.addData.receivers.length; i++) {
                receiveId.push(this.addData.receivers[i][1])
            }
            this.addData.receivers=receiveId
            this.$refs.addData.validate((valid) => {
                if (valid) {
                    if (this.noticeEdit){
                        console.log("edit")
                        this.$emit("handleUpdateNotice",this.addData)
                    }else {
                        console.log("add")
                        this.$emit("handleAddNotice",this.addData)
                    }
                } else {
                    return false;
                }
            });
        },
        resetForm() {
            this.$refs.addData.resetFields();
        }
    },
    created() {
        this.pickerOptions.disabledDate=(time)=> {
            if (this.addData.sendTime !== '') {
                const tempTime = 3600 * 1000 * 24 * 2;
                const timer = new Date(this.currentDate).getTime();
                const minTime = timer - tempTime;
                const maxTime = timer + tempTime;
                return time.getTime() < minTime || time.getTime() > Date.now() || time.getTime() > maxTime;
            }
        }
        if (this.noticeEdit) {
            this.addData = this.noticeEdit
            console.log(this.addData)
        }
    },
    updated() {
        if (this.noticeEdit) {
            this.addData = this.noticeEdit
            console.log(this.addData)
        }
    },
    mounted() {
        console.log("mounted")
    }

}
</script>
<style scoped>
.el-button {
    margin: 0 auto;
}
.el-button--text {
    margin-right: 15px;
}
.el-select {
    width: 300px;
}
.el-input {
    width: 300px;
}
.el-slider {
    margin-left: 20px;
}
</style>
