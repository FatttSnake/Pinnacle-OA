<template>
    <el-row :gutter="5">
        <el-col :span="-1">
            <el-button
                bg
                style="background-color: white"
                :loading="tableLoading"
                @click="loadGroupTable"
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
        <el-col :span="5">
            <el-form-item
                label="名称"
                class="fill-with"
                :error="isRegexLegal ? '' : '非法正则表达式'"
            >
                <el-input
                    v-model="inputName"
                    show-word-limit
                    placeholder="请输入内容"
                    @keyup.enter="handleQuery"
                    @change="handleInputChange"
                >
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
        <el-col :span="9">
            <el-select
                v-model="selectedRole"
                class="fill-with"
                clearable
                collapse-tags
                collapse-tags-tooltip
                multiple
                filterable
            >
                <el-option
                    v-for="item in roleOptions"
                    :key="item.id"
                    :value="item.id"
                    :label="item.name"
                />
            </el-select>
        </el-col>
        <el-col :span="3">
            <el-form-item label="状态" class="fill-with">
                <el-select v-model="selectedEnable" class="fill-with">
                    <el-option label="全部" :value="-1" />
                    <el-option label="启用" :value="1" />
                    <el-option label="禁用" :value="0" />
                </el-select>
            </el-form-item>
        </el-col>
        <el-col :span="-1">
            <el-button type="primary" @click="handleQuery">查询</el-button>
            <el-button @click="handleClear">清空</el-button>
        </el-col>
    </el-row>
    <common-table
        :table-date="groupTable"
        :table-loading="tableLoading"
        @onEdit="handleEdit"
        @onDelete="handleDelete"
        custom-column-label_1="角色"
    />

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
                v-loading="dialogLoading"
                :rules="rules"
                ref="formRef"
                :model="groupForm"
            >
                <el-form-item label="名称" prop="inputGroupName">
                    <el-input
                        autocomplete="off"
                        v-model="groupForm.inputGroupName"
                        maxlength="30"
                        placeholder="请输入名称"
                        show-word-limit
                    />
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch
                        v-model="groupForm.enable"
                        inline-prompt
                        active-text="启用"
                        :active-value="1"
                        inactive-text="禁用"
                        :inactive-value="0"
                    />
                </el-form-item>
                <el-form-item label="角色">
                    <el-select
                        v-model="groupForm.selectedRoles"
                        multiple
                        style="width: 100%"
                        filterable
                        clearable
                    >
                        <el-option
                            v-for="role in roles"
                            :key="role.id"
                            :label="role.name"
                            :value="role.id"
                        />
                    </el-select>
                </el-form-item>
            </el-form>
        </template>
        <template #footer>
            <el-button type="primary" @click="handleSubmit" :disabled="dialogLoading"
                >提交</el-button
            >
            <el-button @click="handleCancel" :disabled="dialogLoading">取消</el-button>
        </template>
    </el-dialog>
</template>

<script lang="ts">
import request from '@/services/index.js'
import {
    DATABASE_DELETE_OK,
    DATABASE_SAVE_OK,
    DATABASE_SELECT_OK,
    DATABASE_UPDATE_OK
} from '@/constants/Common.constants.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import _ from 'lodash'

export default {
    name: 'GroupManagement',
    data() {
        return {
            dialogVisible: false,
            tableLoading: true,
            dialogLoading: true,
            roleOptions: [],
            searchName: '',
            inputName: '',
            searchRegex: 0,
            checkedRegex: 0,
            isRegexLegal: true,
            searchRole: [],
            selectedRole: [],
            searchEnable: -1,
            selectedEnable: -1,
            currentPage: 1,
            pageSize: 50,
            totalCount: 0,
            groupTable: [],
            roles: [],
            groupForm: {
                inputGroupName: '',
                selectedRoles: [],
                enable: 0
            },
            isAddNew: true,
            dialogTitle: '',
            editGroupId: '',
            rules: {
                inputGroupName: [
                    {
                        required: true,
                        message: '用户组名称为必填项'
                    }
                ]
            }
        }
    },
    methods: {
        loadGroupTable() {
            if (!this.isRegexLegal) {
                ElMessage.error({
                    dangerouslyUseHTMLString: true,
                    message: '<strong>非法正则表达式</strong>，请重新输入'
                })
                return
            }
            this.tableLoading = true
            request
                .get('/group', {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    searchName: this.searchName,
                    searchRole: this.searchRole + '',
                    searchEnable: this.searchEnable,
                    searchRegex: this.searchRegex ?? 0
                })
                .then((res) => {
                    const response = res.data
                    if (response.code === DATABASE_SELECT_OK) {
                        const groups = response.data.records
                        this.totalCount = response.data.total
                        groups.forEach((group) => {
                            group.customColumn_1 = []
                            group.roles.forEach((role) => {
                                group.customColumn_1.push(role.name)
                            })
                        })
                        this.groupTable = groups
                        this.tableLoading = false
                    } else {
                        ElMessage.error({
                            dangerouslyUseHTMLString: true,
                            message: '<strong>查询出错</strong>: ' + response.msg
                        })
                    }
                })
        },
        handleAddBtn() {
            this.isAddNew = true
            this.dialogVisible = true
        },
        handleDialogOpen() {
            this.getRoles()

            if (this.isAddNew) {
                this.groupForm.inputGroupName = ''
                this.groupForm.selectedRoles = []
                this.groupForm.enable = 0
                this.dialogTitle = '添加用户组'
            } else {
                this.dialogTitle = '编辑用户组'
            }
        },
        getRoles() {
            this.dialogLoading = true
            request.get('/role/list').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.roleOptions = response.data
                    this.roles = response.data
                    this.dialogLoading = false
                } else {
                    ElMessage.error({
                        dangerouslyUseHTMLString: true,
                        message: '<strong>查询出错</strong>: ' + response.msg
                    })
                }
            })
        },
        handleEdit(index, row) {
            this.groupForm.inputGroupName = row.name
            this.editGroupId = row.id
            this.groupForm.selectedRoles = []
            row.roles.forEach((role) => {
                this.groupForm.selectedRoles.push(role.id)
            })
            this.groupForm.enable = row.enable
            this.isAddNew = false
            this.dialogVisible = true
        },
        handleDelete(index, row) {
            ElMessageBox.confirm('确定删除该用户组吗？', '删除').then(() => {
                this.tableLoading = true
                request
                    .delete('/group/' + row.id)
                    .then((res) => {
                        const response = res.data
                        if (response.code === DATABASE_DELETE_OK) {
                            ElMessage.success({
                                dangerouslyUseHTMLString: true,
                                message: '<strong>删除成功</strong>'
                            })
                            this.loadGroupTable()
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
        async handleSubmit() {
            await this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.dialogLoading = true
                    const groupObject = {
                        id: '',
                        name: this.groupForm.inputGroupName,
                        roles: [],
                        enable: this.groupForm.enable
                    }
                    this.groupForm.selectedRoles.forEach((roleId) => {
                        groupObject.roles.push({
                            id: roleId
                        })
                    })
                    if (this.isAddNew) {
                        request.post('/group', groupObject).then((res) => {
                            const response = res.data
                            if (response.code === DATABASE_SAVE_OK) {
                                ElMessage.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>添加成功</strong>'
                                })
                                this.dialogVisible = false
                                this.loadGroupTable()
                            } else {
                                ElMessage.error({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>添加失败</strong>: ' + response.msg
                                })
                                this.dialogLoading = false
                            }
                        })
                    } else {
                        groupObject.id = this.editGroupId
                        request.put('/group', groupObject).then((res) => {
                            const response = res.data
                            if (response.code === DATABASE_UPDATE_OK) {
                                ElMessage.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>修改成功</strong>'
                                })
                                this.dialogVisible = false
                                this.loadGroupTable()
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
        handleCancel() {
            this.dialogVisible = false
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize
            this.loadGroupTable()
        },
        handleCurrentChange(currentPage) {
            this.currentPage = currentPage
            this.loadGroupTable()
        },
        handleQuery() {
            this.searchName = this.inputName
            this.searchRegex = _.cloneDeep(this.checkedRegex)
            this.searchRole = this.selectedRole
            this.searchEnable = this.selectedEnable
            this.currentPage = 1
            this.handleInputChange()
            this.loadGroupTable()
        },
        handleClear() {
            this.inputName = ''
            this.checkedRegex = 0
            this.selectedRole = []
            this.selectedEnable = -1
            this.handleQuery()
        },
        handleInputChange() {
            if (this.checkedRegex) {
                try {
                    RegExp(this.inputName)
                    this.isRegexLegal = !(
                        this.inputName.includes('{}') || this.inputName.includes('[]')
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
        this.loadGroupTable()
        this.getRoles()
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
