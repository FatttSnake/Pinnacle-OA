<template>
    <el-button bg style="background-color: white" :loading="tableLoading" @click="loadGroupTable">
        <template #icon>
            <el-icon>
                <icon-pinnacle-refresh />
            </el-icon>
        </template>
    </el-button>
    <el-button type="primary" @click="handleAddBtn">
        <template #icon>
            <el-icon>
                <icon-pinnacle-plus />
            </el-icon>
        </template>
        <template #default> 添加 </template>
    </el-button>
    <common-table
        :table-date="groupTable"
        :table-loading="tableLoading"
        @onEdit="handleEdit"
        @onDelete="handleDelete"
        custom-column-label_1="角色"
    />
    <el-dialog
        :title="dialogTitle"
        :close-on-click-modal="false"
        draggable
        v-model="dialogVisible"
        @open="handleDialogOpen"
    >
        <template #default>
            <el-form
                label-width="100px"
                v-loading="dialogLoading"
                :rules="rules"
                ref="formRef"
                :model="groupForm"
            >
                <el-form-item label="用户组名称" prop="inputGroupName">
                    <el-input autocomplete="off" v-model="groupForm.inputGroupName" />
                </el-form-item>
                <el-form-item label="用户组角色">
                    <el-select v-model="groupForm.selectedRoles" multiple style="width: 100%">
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
            <el-button @click="handleCancel">取消</el-button>
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

export default {
    name: 'GroupManagement',
    data() {
        return {
            dialogVisible: false,
            tableLoading: true,
            dialogLoading: true,
            groupTable: [],
            roles: [],
            groupForm: {
                inputGroupName: '',
                selectedRoles: []
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
            this.tableLoading = true
            request.get('/group').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    const groups = response.data
                    for (const group of groups) {
                        group.customColumn_1 = []
                        const roles = group.roles
                        for (const role of roles) {
                            group.customColumn_1.push(role.name)
                        }
                    }
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
                this.dialogTitle = '添加用户组'
            } else {
                this.dialogTitle = '编辑用户组'
            }
        },
        getRoles() {
            this.dialogLoading = true
            request.get('/role').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
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
            for (const role of row.roles) {
                this.groupForm.selectedRoles.push(role.id)
            }
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
                        roles: []
                    }
                    for (const roleId of this.groupForm.selectedRoles) {
                        const role = {
                            id: roleId
                        }
                        groupObject.roles.push(role)
                    }
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
        }
    },
    mounted() {
        this.loadGroupTable()
    }
}
</script>

<style scoped></style>
