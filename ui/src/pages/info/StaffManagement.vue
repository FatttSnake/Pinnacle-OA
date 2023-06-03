<template>
    <el-row :gutter="15">
        <el-col :span="-1">
            <el-button
                bg
                style="background-color: white"
                :loading="tableLoading"
                @click="loadStaffTable"
            >
                <template #icon>
                    <el-icon>
                        <icon-pinnacle-refresh />
                    </el-icon>
                </template>
            </el-button>
        </el-col>
        <el-col :span="6">
            <el-input
                v-model="inputInput"
                class="fill-with"
                placeholder="请输入内容"
                clearable
                @keyup.enter="handleQuery"
            >
                <template #prepend>
                    <el-select v-model="selectedType" style="width: 100px">
                        <el-option label="综合搜索" :value="0" />
                        <el-option label="用户名" :value="1" />
                        <el-option label="姓名" :value="2" />
                        <el-option label="邮箱" :value="3" />
                        <el-option label="手机号码" :value="4" />
                        <el-option label="联系地址" :value="5" />
                    </el-select>
                </template>
            </el-input>
        </el-col>
        <el-col :span="4">
            <el-form-item label="性别" class="fill-with">
                <el-select v-model="selectedGender" class="fill-with">
                    <el-option label="全部" :value="-1" />
                    <el-option label="未知" :value="0" />
                    <el-option label="男" :value="1" />
                    <el-option label="女" :value="2" />
                </el-select>
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="生日">
                <el-date-picker
                    type="daterange"
                    v-model="selectedBirth"
                    value-format="YYYY-MM-DD"
                    class="fill-with"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                />
            </el-form-item>
        </el-col>
        <el-col :span="-1">
            <el-button type="primary" @click="handleQuery">查询</el-button>
            <el-button @click="handleClear">清空</el-button>
        </el-col>
    </el-row>

    <el-table
        :data="staffTable"
        v-loading="tableLoading"
        element-loading-text="Loading..."
        style="margin-top: 10px"
    >
        <el-table-column prop="username" label="用户名" width="120" align="center" fixed="left" />
        <el-table-column label="姓名" align="center" width="120" fixed="left">
            <template #default="scope">
                {{
                    scope.row.staff !== null
                        ? scope.row.staff.lastName + scope.row.staff.firstName
                        : ''
                }}
            </template>
        </el-table-column>
        <el-table-column label="性别" align="center" :formatter="genderFormatter" width="60" />
        <el-table-column prop="staff.birth" label="生日" align="center" width="110" />
        <el-table-column prop="staff.email" label="邮箱" align="center" width="160" />
        <el-table-column prop="staff.tel" label="手机" align="center" width="160" />
        <el-table-column prop="staff.address" label="地址" min-width="200" />
        <el-table-column
            prop="department.name"
            label="部门"
            align="center"
            fixed="right"
            width="100"
        />
        <el-table-column label="操作" width="80" align="center" fixed="right">
            <template #default="scope">
                <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
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

    <el-dialog title="编辑员工信息" :close-on-click-modal="false" draggable v-model="dialogVisible">
        <template #default>
            <el-scrollbar height="45vh" style="padding: 0 20px">
                <el-form
                    label-width="60px"
                    :rules="rules"
                    ref="formRef"
                    :model="userForm"
                    v-loading="dialogLoading"
                >
                    <el-form-item label="用户名" prop="inputUsername">
                        <el-input v-model="userForm.inputUsername" disabled />
                    </el-form-item>
                    <el-form-item label="部门" prop="selectedDepartment">
                        <el-select v-model="userForm.selectedDepartment">
                            <el-option
                                v-for="item in departments"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id"
                            />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="姓氏" prop="inputLastName">
                        <el-input v-model="userForm.inputLastName" maxlength="20" show-word-limit />
                    </el-form-item>
                    <el-form-item label="名字" prop="inputFirstName">
                        <el-input
                            v-model="userForm.inputFirstName"
                            maxlength="20"
                            show-word-limit
                        />
                    </el-form-item>
                    <el-form-item label="性别" prop="selectedGender">
                        <el-select v-model="userForm.selectedGender">
                            <el-option label="未知" :value="0" />
                            <el-option label="男" :value="1" />
                            <el-option label="女" :value="2" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="生日" prop="inputBirth">
                        <el-date-picker v-model="userForm.inputBirth" value-format="YYYY-MM-DD" />
                    </el-form-item>
                    <el-form-item label="邮箱" prop="inputEmail">
                        <el-input v-model="userForm.inputEmail" maxlength="50" show-word-limit />
                    </el-form-item>
                    <el-form-item label="手机" prop="inputTel">
                        <el-input v-model="userForm.inputTel" maxlength="20" show-word-limit />
                    </el-form-item>
                    <el-form-item label="地址" prop="inputAddress">
                        <el-input
                            v-model="userForm.inputAddress"
                            type="textarea"
                            resize="none"
                            maxlength="100"
                            show-word-limit
                        />
                    </el-form-item>
                </el-form>
            </el-scrollbar>
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
import { DATABASE_SELECT_OK, DATABASE_UPDATE_OK } from '@/constants/Common.constants'
import { ElMessage } from 'element-plus'

export default {
    name: 'StaffManagement',
    data() {
        return {
            dialogVisible: false,
            tableLoading: true,
            dialogLoading: true,
            searchType: 0,
            selectedType: 0,
            searchInput: '',
            inputInput: '',
            searchGender: -1,
            selectedGender: -1,
            searchBirth: [],
            selectedBirth: [],
            currentPage: 1,
            pageSize: 50,
            totalCount: 0,
            staffTable: [],
            editUserId: '',
            rules: {
                inputFirstName: [
                    {
                        required: true,
                        message: '名字为必填项'
                    }
                ],
                inputLastName: [
                    {
                        required: true,
                        message: '姓氏为必填项'
                    }
                ]
            },
            departments: [],
            userForm: {
                inputUsername: '',
                selectedDepartment: null,
                inputFirstName: '',
                inputLastName: '',
                selectedGender: 0,
                inputBirth: '',
                inputEmail: '',
                inputTel: '',
                inputAddress: ''
            }
        }
    },
    methods: {
        loadStaffTable() {
            this.tableLoading = true
            request
                .get('/staff', {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    searchType: this.searchType,
                    searchInput: this.searchInput,
                    searchGender: this.searchGender,
                    searchBirthFrom: this.searchBirth ? this.searchBirth[0] : null,
                    searchBirthTo: this.searchBirth ? this.searchBirth[1] : null
                })
                .then((res) => {
                    const response = res.data
                    if (response.code === DATABASE_SELECT_OK) {
                        this.staffTable = response.data.records
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
        genderFormatter(row) {
            if (row.staff === null) {
                return ''
            }
            switch (row.staff.gender) {
                case 0:
                    return '未知'
                case 1:
                    return '男'
                case 2:
                    return '女'
                default:
                    return ''
            }
        },
        handleEdit(row) {
            this.editUserId = row.id
            this.userForm.inputUsername = row.username
            this.userForm.selectedDepartment = row.departmentId
            this.userForm.inputFirstName = row.staff?.firstName
            this.userForm.inputLastName = row.staff?.lastName
            this.userForm.selectedGender = row.staff?.gender || 0
            this.userForm.inputBirth = row.staff?.birth
            this.userForm.inputEmail = row.staff?.email
            this.userForm.inputTel = row.staff?.tel
            this.userForm.inputAddress = row.staff?.address
            this.dialogLoading = true
            this.dialogVisible = true
            this.getDepartments()
        },
        async handleSubmit() {
            await this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.dialogLoading = true
                    const userObject = {
                        id: this.editUserId,
                        departmentId: this.userForm.selectedDepartment,
                        staff: {
                            firstName: this.userForm.inputFirstName,
                            lastName: this.userForm.inputLastName,
                            gender: this.userForm.selectedGender,
                            birth: this.userForm.inputBirth,
                            email: this.userForm.inputEmail,
                            tel: this.userForm.inputTel,
                            address: this.userForm.inputAddress
                        }
                    }
                    request.put('/staff', userObject).then((res) => {
                        const response = res.data
                        if (response.code === DATABASE_UPDATE_OK) {
                            ElMessage.success({
                                dangerouslyUseHTMLString: true,
                                message: '<strong>修改成功</strong>'
                            })
                            this.dialogVisible = false
                            this.loadStaffTable()
                        } else {
                            ElMessage.error({
                                dangerouslyUseHTMLString: true,
                                message: '<strong>修改失败</strong>: ' + response.msg
                            })
                            this.dialogLoading = false
                        }
                    })
                }
            })
        },
        getDepartments() {
            request.get('/department/list').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.departments = response.data
                    this.dialogLoading = false
                    if (!_.find(this.departments, { id: this.userForm.selectedDepartment })) {
                        this.userForm.selectedDepartment = null
                    }
                } else {
                    ElMessage.error({
                        dangerouslyUseHTMLString: true,
                        message: '<strong>查询出错</strong>: ' + response.msg
                    })
                }
            })
        },
        handleCancel() {
            this.dialogVisible = false
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize
            this.loadStaffTable()
        },
        handleCurrentChange(currentPage) {
            this.currentPage = currentPage
            this.loadStaffTable()
        },
        handleQuery() {
            this.searchType = _.cloneDeep(this.selectedType)
            this.searchInput = _.cloneDeep(this.inputInput)
            this.searchGender = _.cloneDeep(this.selectedGender)
            this.searchBirth = _.cloneDeep(this.selectedBirth)
            this.currentPage = 1
            this.loadStaffTable()
        },
        handleClear() {
            this.selectedType = 0
            this.inputInput = ''
            this.selectedGender = -1
            this.selectedBirth = []
            this.handleQuery()
        }
    },
    mounted() {
        this.loadStaffTable()
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
