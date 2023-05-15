<template>
    <el-row :span="24">
        <el-col :span="18">
            <div class="mt-4">
                <el-input v-model="input3" placeholder="查询事务" class="input-with-select">
                    <template #prepend>
                        <el-select v-model="select" placeholder="查询方式">
                            <el-option label="事务编号" value="1" />
                            <el-option label="事务名称" value="2" />
                            <el-option label="日期" value="3" />
                        </el-select>
                    </template>
                    <template #append>
                        <el-button>查询</el-button>
                    </template>
                </el-input>
            </div>
        </el-col>

        <el-col :span="4">
            <el-button type="warning" round>待审批</el-button>
            <el-button type="success" round>已审批</el-button>
        </el-col>
    </el-row>

    <el-table :data="tableData" style="width: 100%">
        <el-table-column label="事务编号" prop="id" />

        <el-table-column label="事务名称" prop="title" />

        <el-table-column label="事务类型" prop="typeId">
            <template #default="scope">
                {{
                    scope.row.typeId === 1
                        ? '事假'
                        : scope.row.typeId === 2
                        ? '病假'
                        : scope.row.typeId === 3
                        ? '财务报销'
                        : '调休'
                }}
            </template>
        </el-table-column>

        <el-table-column label="申请者" prop="applicantId">
            <template #default="scope">
                {{
                    scope.row.applicantId === 1
                        ? 'ggb'
                        : scope.row.applicantId === 1652714496280469506
                        ? 'cyb'
                        : scope.row.applicantId === 1654151146072145921
                        ? 'syf'
                        : scope.row.applicantId === 1654151877520973826
                        ? 'gzw'
                        : 'yrm'
                }}
            </template>
        </el-table-column>
        <el-table-column label="提交日期" prop="createTime">
            <template #default="scope">
                {{ format(scope.row.createTime) }}
            </template>
        </el-table-column>

        <el-table-column label="审批日期" prop="inspectTime">
            <template #default="scope">
                {{ format(scope.row.inspectTime) }}
            </template>
        </el-table-column>

        <el-table-column label="操作" key="slot" width="180" prop="content">
            <template #default="scope">
                <el-button size="small" type="text" @click="dialogTure(scope.row)"
                    >具体内容
                </el-button>
                <el-button size="small" type="danger" @click="handleDelete(scope.row)">
                    删除
                </el-button>
            </template>
        </el-table-column>

        <el-table-column label="审批结果" width="90" prop="status">
            <template #default="scope">
                {{ scope.row.status === 1 ? '同意' : '驳回' }}
            </template>
        </el-table-column>
    </el-table>

    <el-dialog title="详细内容" v-model="dialogVisible" width="50%" :data="dialogData" center>
        <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="4">事务标题:</el-col>
            <el-col :span="17">{{ dialogData.title }}</el-col>
        </el-row>
        <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="4">具体内容:</el-col>
        </el-row>
        <el-row>
            <el-col :span="7"></el-col>
            <el-col :span="17">{{ dialogData.content }}</el-col>
        </el-row>
        <el-row>
            <el-col :span="11"></el-col>
            <el-col :span="2">
                <span class="dialog-footer">
                    <el-button @click="dialogFalse">返 回</el-button>
                </span>
            </el-col>
        </el-row>
    </el-dialog>

    <el-divider :data="labelData">
        <div class="block">
            <el-pagination
                style="color: #888888"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                v-model:current-page="currentPage3"
                :page-size="100"
                layout="prev, pager, next, jumper"
                :total="1000"
            >
            </el-pagination>
        </div>
    </el-divider>
</template>

<script>
import request from '@/services'
import 'element-plus/theme-chalk/index.css'
export default {
    data() {
        return {
            tableData: [
                {
                    id: '',
                    title: '',
                    content: '',
                    type_id: '',
                    status: '',
                    applicant_id: '',
                    inspector_id: '',
                    create_time: '',
                    inspect_time: '',
                    priority: '',
                    modify_time: '',
                    origin_id: '',
                    old: '',
                    deleted: '',
                    version: ''
                }
            ],
            //     }],

            labelData: [
                {
                    currentPage1: 5,
                    currentPage2: 5,
                    currentPage3: 5,
                    currentPage4: 4
                }
            ],
            dialogVisible: false,
            dialogData: [
                {
                    id: '',
                    title: '',
                    content: '',
                    type_id: '',
                    status: '',
                    applicant_id: '',
                    inspector_id: '',
                    create_time: '',
                    inspect_time: '',
                    priority: '',
                    modify_time: '',
                    origin_id: '',
                    old: '',
                    deleted: '',
                    version: ''
                }
            ]
        }
    },
    methods: {
        handleYes(row) {
            console.log(row)
            request
                .put('http://localhost:8621/affair/yes', row)
                .then((response) => {
                    console.log(response.data)
                    this.getApproed()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        handleNo(row) {
            console.log(row)
            request
                .put('http://localhost:8621/affair/no', row)
                .then((response) => {
                    console.log(response.data)
                    this.getApproed()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        handleDelete(row) {
            console.log(row.id)
            request
                .delete('http://localhost:8621/affair/' + row.id)
                .then((response) => {
                    console.log(response.data)
                    this.getApproed()
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },

        handleSizeChange(val) {
            console.log(`每页 ${val} 条`)
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`)
        },
        getApproed() {
            request
                .get('http://localhost:8621/affair/Approved')
                .then((response) => {
                    this.tableData = response.data.data
                    console.log(this.tableData)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        format(time) {
            return new Date(time).toLocaleString()
        }, // 时间格式转换
        dialogTure(data) {
            this.dialogVisible = true
            this.dialogData = data
        },
        dialogFalse() {
            this.dialogVisible = false
        }
    },
    created() {
        this.getApproed()
        this.dialogFalse()
        console.log(this.tableData)
    }
}
</script>
<style></style>
