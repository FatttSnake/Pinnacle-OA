<template>
    <el-row :gutter="15">
        <el-col :span="-1">
            <el-button
                bg
                style="background-color: white"
                :loading="tableLoading"
                @click="loadDepartmentTable"
            >
                <template #icon>
                    <el-icon>
                        <icon-pinnacle-refresh />
                    </el-icon>
                </template>
            </el-button>
        </el-col>
        <el-col :span="-1">
            <el-button type="primary" @click="handleAddBtn">
                <template #icon>
                    <el-icon>
                        <icon-pinnacle-plus />
                    </el-icon>
                </template>
            </el-button>
        </el-col>
        <el-col :span="16">
            <el-form-item :error="isRegexLegal ? '' : '非法正则表达式'">
                <el-input
                    v-model="inputInput"
                    class="fill-with"
                    placeholder="请输入内容"
                    clearable
                    @keyup.enter="handleQuery"
                    @change="handleInputChange"
                >
                    <template #prepend>
                        <el-select v-model="selectedType" style="width: 100px">
                            <el-option label="综合搜索" :value="0" />
                            <el-option label="名称" :value="1" />
                            <el-option label="电话" :value="2" />
                            <el-option label="地址" :value="3" />
                        </el-select>
                    </template>
                    <template #suffix>
                        <el-tooltip content="正则表达式">
                            <el-checkbox
                                v-model="checkedRegex"
                                label=".*"
                                :true-label="1"
                                @change="handleInputChange"
                            />
                        </el-tooltip>
                    </template>
                </el-input>
            </el-form-item>
        </el-col>
        <el-col :span="-1">
            <el-button type="primary" @click="handleQuery">查询</el-button>
            <el-button @click="handleClear">清空</el-button>
        </el-col>
    </el-row>

    <el-table
        :data="departmentTable"
        v-loading="tableLoading"
        element-loading-text="Loading..."
        style="margin-top: 10px"
    >
        <el-table-column prop="name" label="名称" width="160" align="center" />
        <el-table-column prop="tel" label="电话" align="center" width="160" />
        <el-table-column prop="address" label="地址" />
        <el-table-column label="操作" width="160" align="center">
            <template #default="scope">
                <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                <el-button type="danger" size="small" @click="handleDelete(scope.row)"
                    >删除</el-button
                >
            </template>
        </el-table-column>
    </el-table>
    <div class="pagination">
        <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[50, 100, 200, 500, 1000]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalCount"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
    </div>

    <el-dialog
        :title="dialogTitle"
        :close-on-click-modal="false"
        draggable
        v-model="dialogVisible"
        @open="handleDialogOpen"
    >
        <template #default>
            <el-form
                label-width="60px"
                :rules="rules"
                ref="formRef"
                :model="departmentForm"
                v-loading="dialogLoading"
            >
                <el-form-item label="名称" prop="inputName">
                    <el-input v-model="departmentForm.inputName" maxlength="50" show-word-limit />
                </el-form-item>
                <el-form-item label="电话" prop="inputTel">
                    <el-input v-model="departmentForm.inputTel" maxlength="20" show-word-limit />
                </el-form-item>
                <el-form-item label="地址" prop="inputAddress">
                    <el-input
                        v-model="departmentForm.inputAddress"
                        type="textarea"
                        resize="none"
                        maxlength="100"
                        show-word-limit
                    />
                </el-form-item>
            </el-form>
        </template>
        <template #footer>
            <el-button type="primary" @click="handleSubmit" :disabled="dialogLoading"
                >提交
            </el-button>
            <el-button @click="handleCancel" :disabled="dialogLoading">取消</el-button>
        </template>
    </el-dialog>
</template>

<script lang="ts">
import _ from 'lodash'
import request from '@/services'
import {
    DATABASE_DELETE_OK,
    DATABASE_SAVE_OK,
    DATABASE_SELECT_OK,
    DATABASE_UPDATE_OK
} from '@/constants/Common.constants'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
    name: 'DepartmentManagement',
    data() {
        return {
            dialogTitle: '',
            dialogVisible: false,
            tableLoading: true,
            dialogLoading: false,
            searchType: 0,
            selectedType: 0,
            searchInput: '',
            inputInput: '',
            searchRegex: 0,
            checkedRegex: 0,
            isRegexLegal: true,
            currentPage: 1,
            pageSize: 50,
            totalCount: 0,
            departmentTable: [],
            editDepartmentId: '',
            rules: {
                inputName: [
                    {
                        required: true,
                        message: '名称为必填项'
                    }
                ]
            },
            departmentForm: {
                inputName: '',
                inputTel: '',
                inputAddress: ''
            },
            isAddNew: true
        }
    },
    methods: {
        loadDepartmentTable() {
            if (!this.isRegexLegal) {
                ElMessage.error({
                    dangerouslyUseHTMLString: true,
                    message: '<strong>非法正则表达式</strong>，请重新输入'
                })
                return
            }
            this.tableLoading = true
            request
                .get('/department', {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    searchType: this.searchType,
                    searchInput: this.searchInput,
                    searchRegex: this.searchRegex ?? 0
                })
                .then((res) => {
                    const response = res.data
                    if (response.code === DATABASE_SELECT_OK) {
                        this.departmentTable = response.data.records
                        this.totalCount = response.data.total
                        this.tableLoading = false
                    } else {
                        ElMessage.error({
                            dangerouslyUseHTMLString: true,
                            message: '<strong>查询出错</strong>: ' + response.msg
                        })
                    }
                })
        },
        handleEdit(row) {
            this.editDepartmentId = row.id
            this.departmentForm.inputName = row.name
            this.departmentForm.inputTel = row.tel
            this.departmentForm.inputAddress = row.address
            this.isAddNew = false
            this.dialogVisible = true
        },
        handleAddBtn() {
            this.isAddNew = true
            this.dialogVisible = true
        },
        handleDialogOpen() {
            if (this.isAddNew) {
                this.departmentForm.inputName = ''
                this.departmentForm.inputTel = ''
                this.departmentForm.inputAddress = ''
                this.dialogTitle = '添加部门'
            } else {
                this.dialogTitle = '编辑部门'
            }
            this.dialogLoading = false
        },
        async handleSubmit() {
            await this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.dialogLoading = true
                    const departmentObject = {
                        id: '',
                        name: this.departmentForm.inputName,
                        tel: this.departmentForm.inputTel,
                        address: this.departmentForm.inputAddress
                    }
                    if (this.isAddNew) {
                        request.post('/department', departmentObject).then((res) => {
                            const response = res.data
                            if (response.code === DATABASE_SAVE_OK) {
                                ElMessage.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>添加成功</strong>'
                                })
                                this.dialogVisible = false
                                this.loadDepartmentTable()
                            } else {
                                ElMessage.error({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>添加失败</strong>: ' + response.msg
                                })
                                this.dialogLoading = false
                            }
                        })
                    } else {
                        departmentObject.id = this.editDepartmentId
                        request.put('/department', departmentObject).then((res) => {
                            const response = res.data
                            if (response.code === DATABASE_UPDATE_OK) {
                                ElMessage.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>修改成功</strong>'
                                })
                                this.dialogVisible = false
                                this.loadDepartmentTable()
                            } else {
                                ElMessage.error({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>修改失败</strong>: ' + response.msg
                                })
                                this.dialogLoading = false
                            }
                        })
                    }
                }
            })
        },
        handleDelete(row) {
            ElMessageBox.confirm('确定删除该部门吗？', '删除').then(() => {
                this.tableLoading = true
                request
                    .delete('/department/' + row.id)
                    .then((res) => {
                        const response = res.data
                        if (response.code === DATABASE_DELETE_OK) {
                            ElMessage.success({
                                dangerouslyUseHTMLString: true,
                                message: '<strong>删除成功</strong>'
                            })
                            this.loadDepartmentTable()
                        } else {
                            ElMessage.error({
                                dangerouslyUseHTMLString: true,
                                message: '<strong>删除失败</strong>: ' + response.msg
                            })
                            this.tableLoading = false
                        }
                    })
                    .catch(() => {
                        this.tableLoading = false
                    })
            })
        },
        handleCancel() {
            this.dialogVisible = false
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize
            this.loadDepartmentTable()
        },
        handleCurrentChange(currentPage) {
            this.currentPage = currentPage
            this.loadDepartmentTable()
        },
        handleQuery() {
            this.searchType = _.cloneDeep(this.selectedType)
            this.searchInput = _.cloneDeep(this.inputInput)
            this.searchRegex = _.cloneDeep(this.checkedRegex)
            this.currentPage = 1
            this.handleInputChange()
            this.loadDepartmentTable()
        },
        handleClear() {
            this.selectedType = 0
            this.inputInput = ''
            this.checkedRegex = 0
            this.handleQuery()
        },
        handleInputChange() {
            if (this.checkedRegex) {
                try {
                    RegExp(this.inputInput)
                    this.isRegexLegal = !(
                        this.inputInput.includes('{}') || this.inputInput.includes('[]')
                    )
                } catch (e) {
                    this.isRegexLegal = false
                }
            } else {
                this.isRegexLegal = true
            }
        }
    },
    mounted() {
        this.loadDepartmentTable()
    }
}
</script>

<style scoped>
.pagination {
    display: flex;
    margin-top: 10px;
    justify-content: center;
}
</style>
