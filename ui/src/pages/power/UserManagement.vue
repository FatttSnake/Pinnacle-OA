<template>
    <el-button bg style="background-color: white" :loading="tableLoading" @click="loadUserTable">
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
        :table-date="userTable"
        :table-loading="tableLoading"
        @onEdit="handleEdit"
        @onDelete="handleDelete"
        custom-column-label_1="角色"
        custom-column-label_2="用户组"
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
                :model="userForm"
            >
                <el-form-item label="用户名" prop="inputUsername">
                    <el-input
                        autocomplete="off"
                        v-model="userForm.inputUsername"
                        placeholder="请输入用户名"
                        minlength="3"
                        maxlength="20"
                    />
                </el-form-item>
                <el-form-item label="密码" prop="inputPassword" :required="isAddNew">
                    <el-input
                        show-password
                        autocomplete="off"
                        v-model="userForm.inputPassword"
                        :placeholder="isAddNew ? '请输入密码' : '留空则不修改密码'"
                        minlength="8"
                        maxlength="64"
                    />
                </el-form-item>
                <el-form-item label="角色">
                    <el-select
                        :disabled="disableSelectRoles"
                        v-model="userForm.selectedRoles"
                        multiple
                        style="width: 100%"
                    >
                        <el-option
                            v-for="role in roles"
                            :key="role.id"
                            :label="role.name"
                            :value="role.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="用户组">
                    <el-select
                        :disabled="disableSelectGroups"
                        v-model="userForm.selectedGroups"
                        multiple
                        style="width: 100%"
                    >
                        <el-option
                            v-for="group in groups"
                            :key="group.id"
                            :label="group.name"
                            :value="group.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch
                        v-model="userForm.enable"
                        inline-prompt
                        active-text="启用"
                        :active-value="1"
                        inactive-text="禁用"
                        :inactive-value="0"
                    />
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
    name: 'UserManagement',
    data() {
        const checkPassword = (rule, value, callback) => {
            if (this.isAddNew && !value) {
                return callback(new Error('密码不能为空'))
            } else {
                return callback()
            }
        }
        return {
            dialogVisible: false,
            tableLoading: true,
            dialogLoading: true,
            userTable: [],
            roles: [],
            groups: [],
            userForm: {
                inputUsername: '',
                inputPassword: '',
                selectedRoles: [],
                selectedGroups: [],
                enable: 0
            },
            isAddNew: true,
            dialogTitle: '',
            editUserId: '',
            rules: {
                inputUsername: [
                    {
                        required: true,
                        message: '用户名不能为空'
                    },
                    {
                        min: 3,
                        message: '用户名必须大于3个字符'
                    },
                    {
                        max: 20,
                        message: '用户名不能大于20个字符'
                    }
                ],
                inputPassword: [
                    {
                        validator: checkPassword,
                        message: '密码不能为空'
                    },
                    {
                        min: 8,
                        message: '密码必须大于8个字符'
                    },
                    {
                        max: 64,
                        message: '密码不能大于64个字符'
                    }
                ]
            },
            disableSelectRoles: false,
            disableSelectGroups: false
        }
    },
    methods: {
        loadUserTable() {
            this.tableLoading = true
            request.get('/user').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    const users = response.data
                    for (const user of users) {
                        user.name = user.username
                        user.customColumn_1 = []
                        user.customColumn_2 = []
                        const roles = user.roles
                        for (const role of roles) {
                            user.customColumn_1.push(role.name)
                        }
                        const groups = user.groups
                        for (const group of groups) {
                            user.customColumn_2.push(group.name)
                        }
                    }
                    this.userTable = users
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
        getRoles() {
            this.dialogLoading = true
            request.get('/role').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.roles = response.data
                    this.getGroups()
                } else {
                    ElMessage.error({
                        dangerouslyUseHTMLString: true,
                        message: '<strong>查询出错</strong>: ' + response.msg
                    })
                }
            })
        },
        getGroups() {
            request.get('/group').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.groups = response.data
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
            this.userForm.inputUsername = row.name
            this.userForm.inputPassword = ''
            this.editUserId = row.id
            this.userForm.selectedRoles = []
            this.userForm.selectedGroups = []
            for (const role of row.roles) {
                if (role.id === '0') {
                    this.userForm.selectedRoles.push(role.name)
                } else {
                    this.userForm.selectedRoles.push(role.id)
                }
            }
            for (const group of row.groups) {
                if (group.id === '0') {
                    this.userForm.selectedGroups.push(group.name)
                } else {
                    this.userForm.selectedGroups.push(group.id)
                }
            }
            this.userForm.enable = row.enable
            this.isAddNew = false
            this.dialogVisible = true
        },
        handleDelete(index, row) {
            ElMessageBox.confirm('确定删除该用户吗？', '删除').then(() => {
                this.tableLoading = true
                request
                    .delete('/user/' + row.id)
                    .then((res) => {
                        const response = res.data
                        if (response.code === DATABASE_DELETE_OK) {
                            ElMessage.success({
                                dangerouslyUseHTMLString: true,
                                message: '<strong>删除成功</strong>'
                            })
                            this.loadUserTable()
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
        handleDialogOpen() {
            this.getRoles()

            this.disableSelectRoles = false
            this.disableSelectGroups = false

            if (this.isAddNew) {
                this.userForm.inputUsername = ''
                this.userForm.inputPassword = ''
                this.userForm.selectedRoles = []
                this.userForm.selectedGroups = []
                this.userForm.enable = 0
                this.dialogTitle = '添加用户'
            } else {
                this.dialogTitle = '编辑用户'
                if (this.editUserId === '1') {
                    this.disableSelectRoles = true
                    this.disableSelectGroups = true
                }
            }
        },
        async handleSubmit() {
            await this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.dialogLoading = true
                    const userObject = {
                        id: '',
                        username: this.userForm.inputUsername,
                        passwd: this.userForm.inputPassword,
                        roles: [],
                        groups: [],
                        enable: this.userForm.enable
                    }
                    if (this.editUserId !== '1') {
                        for (const roleId of this.userForm.selectedRoles) {
                            const role = {
                                id: roleId
                            }
                            userObject.roles.push(role)
                        }
                        for (const groupId of this.userForm.selectedGroups) {
                            const group = {
                                id: groupId
                            }
                            userObject.groups.push(group)
                        }
                    }

                    if (this.isAddNew) {
                        request.post('/user', userObject).then((res) => {
                            const response = res.data
                            if (response.code === DATABASE_SAVE_OK) {
                                ElMessage.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>添加成功</strong>'
                                })
                                this.dialogVisible = false
                                this.loadUserTable()
                            } else {
                                ElMessage.error({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>添加失败</strong>: ' + response.msg
                                })
                                this.dialogLoading = false
                            }
                        })
                    } else {
                        userObject.id = this.editUserId
                        request.put('/user', userObject).then((res) => {
                            const response = res.data
                            if (response.code === DATABASE_UPDATE_OK) {
                                ElMessage.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>修改成功</strong>'
                                })
                                this.dialogVisible = false
                                this.loadUserTable()
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
        this.loadUserTable()
    }
}
</script>

<style scoped></style>
