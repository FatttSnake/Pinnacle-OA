<template>
    <el-row :gutter="5">
        <el-col :span="-1">
            <el-button
                bg
                style="background-color: white"
                :loading="tableLoading"
                @click="loadRoleTable"
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
            <el-form-item label="名称" class="fill-with">
                <el-input
                    v-model="inputName"
                    maxlength="20"
                    show-word-limit
                    placeholder="请输入内容"
                    @keyup.enter="handleQuery"
                />
            </el-form-item>
        </el-col>
        <el-col :span="9">
            <el-form-item label="权限" class="fill-with">
                <el-cascader
                    :options="powerOptions"
                    v-model="selectedPower"
                    :props="powerOptionProps"
                    class="fill-with"
                    clearable
                    collapse-tags
                    collapse-tags-tooltip
                    filterable
                />
            </el-form-item>
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
        :table-date="roleTable"
        :table-loading="tableLoading"
        @onEdit="handleEdit"
        @onDelete="handleDelete"
        custom-column-label_1="权限"
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
            <el-scrollbar max-height="60vh">
                <el-form
                    label-width="60px"
                    v-loading="dialogLoading"
                    :rules="rules"
                    ref="formRef"
                    :model="roleForm"
                >
                    <el-form-item label="名称" prop="inputRoleName">
                        <el-input
                            autocomplete="off"
                            v-model="roleForm.inputRoleName"
                            maxlength="20"
                            placeholder="请输入名称"
                            show-word-limit
                        />
                    </el-form-item>
                    <el-form-item label="状态">
                        <el-switch
                            v-model="roleForm.enable"
                            inline-prompt
                            active-text="启用"
                            :active-value="1"
                            inactive-text="禁用"
                            :inactive-value="0"
                        />
                    </el-form-item>
                    <el-form-item label="权限">
                        <el-tree
                            :data="powerTree"
                            node-key="powerId"
                            :props="powerTreeProps"
                            show-checkbox
                            :render-after-expand="false"
                            :default-checked-keys="defaultSelectedPower"
                            style="min-width: 120px"
                            @check-change="handleTreeSelectedPowerChange"
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
import request from '@/services'
import {
    DATABASE_DELETE_OK,
    DATABASE_SAVE_OK,
    DATABASE_SELECT_OK,
    DATABASE_UPDATE_OK
} from '@/constants/Common.constants'
import _ from 'lodash'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
    name: 'RoleManagement',
    data() {
        return {
            dialogVisible: false,
            tableLoading: true,
            dialogLoading: true,
            powerOptions: [],
            searchName: '',
            searchPower: [],
            searchEnable: -1,
            inputName: '',
            selectedPower: [],
            selectedEnable: -1,
            currentPage: 1,
            pageSize: 50,
            totalCount: 0,
            roleTable: [],
            powerTree: [],
            powerOptionProps: {
                expandTrigger: 'hover',
                multiple: true,
                label: 'name',
                value: 'powerId',
                children: 'children',
                emitPath: false
            },
            powerTreeProps: {
                label: 'name',
                children: 'children'
            },
            roleForm: {
                inputRoleName: '',
                selectedPower: new Set(),
                enable: 0
            },
            isAddNew: true,
            defaultSelectedPower: [],
            dialogTitle: '',
            editRoleId: '',
            rules: {
                inputRoleName: [
                    {
                        required: true,
                        message: '角色名称为必填项'
                    }
                ]
            }
        }
    },
    methods: {
        loadRoleTable() {
            this.tableLoading = true
            request
                .get('/role', {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    searchName: this.searchName,
                    searchPower: this.searchPower + '',
                    searchEnable: this.searchEnable
                })
                .then((res) => {
                    const response = res.data
                    if (response.code === DATABASE_SELECT_OK) {
                        const roles = response.data.records
                        this.totalCount = response.data.total
                        for (const role of roles) {
                            role.customColumn_1 = []
                            const menus = role.menus
                            const elements = role.elements
                            const operations = role.operations
                            for (const operation of operations) {
                                const element = _.find(elements, { id: operation.elementId })
                                if (element.operations === undefined) {
                                    element.operations = []
                                }
                                element.operations.push(operation)
                            }
                            for (const element of elements) {
                                const menu = _.find(menus, { id: element.menuId })
                                if (menu.elements === undefined) {
                                    menu.elements = []
                                }
                                menu.elements.push(element)

                                const operas = []
                                _.forEach(element.operations, (value) => {
                                    operas.push(value.name)
                                })
                                role.customColumn_1.push(
                                    `${menu.name}/${element.name}/${_.join(operas, ';')}`
                                )
                            }
                        }
                        this.roleTable = roles
                        this.tableLoading = false
                    } else {
                        ElMessage.error({
                            dangerouslyUseHTMLString: true,
                            message: '<strong>查询出错</strong>: ' + response.msg
                        })
                    }
                })
        },
        handleDialogOpen() {
            this.getPowers()

            if (this.isAddNew) {
                this.defaultSelectedPower = []
                this.roleForm.inputRoleName = ''
                this.roleForm.selectedPower.clear()
                this.roleForm.enable = 0
                this.dialogTitle = '添加角色'
            } else {
                this.dialogTitle = '编辑角色'
            }
        },
        getPowers() {
            this.dialogLoading = true
            request.get('/power').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    const data = response.data
                    const menuList = data.menuList
                    const elementList = data.elementList
                    const operationList = data.operationList
                    for (const operation of operationList) {
                        const element = _.find(elementList, { id: operation.elementId })
                        if (element.children === undefined) {
                            element.children = []
                        }
                        element.children.push(operation)
                    }
                    for (const element of elementList) {
                        const menu = _.find(menuList, { id: element.menuId })
                        if (menu.children === undefined) {
                            menu.children = []
                        }
                        menu.children.push(element)
                    }
                    for (const menu of menuList) {
                        if (menu.children.length === 1) {
                            menu.children = menu.children[0].children
                        }
                    }
                    this.powerOptions = data.menuList
                    this.powerTree = data.menuList
                    this.dialogLoading = false
                } else {
                    ElMessage.error({
                        dangerouslyUseHTMLString: true,
                        message: '<strong>查询出错</strong>: ' + response.msg
                    })
                }
            })
        },
        handleTreeSelectedPowerChange(data, checked, indeterminate) {
            if (data.children === undefined) {
                if (checked || indeterminate) {
                    this.roleForm.selectedPower.add(data.powerId)
                } else {
                    this.roleForm.selectedPower.delete(data.powerId)
                }
            }
        },
        handleAddBtn() {
            this.isAddNew = true
            this.dialogVisible = true
        },
        handleEdit(index, row) {
            this.roleForm.inputRoleName = row.name
            this.editRoleId = row.id
            this.roleForm.selectedPower.clear()
            this.roleForm.enable = row.enable
            this.defaultSelectedPower = []
            for (const operation of row.operations) {
                this.defaultSelectedPower.push(operation.powerId)
                this.roleForm.selectedPower.add(operation.powerId)
            }
            this.isAddNew = false
            this.dialogVisible = true
        },
        handleDelete(index, row) {
            ElMessageBox.confirm('确定删除该角色吗？', '删除').then(() => {
                this.tableLoading = true
                request
                    .delete('/role/' + row.id)
                    .then((res) => {
                        const response = res.data
                        if (response.code === DATABASE_DELETE_OK) {
                            ElMessage.success({
                                dangerouslyUseHTMLString: true,
                                message: '<strong>删除成功</strong>'
                            })
                            this.loadRoleTable()
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
                    const roleObject = {
                        id: '',
                        name: this.roleForm.inputRoleName,
                        powers: [],
                        enable: this.roleForm.enable
                    }
                    for (const powerId of this.roleForm.selectedPower) {
                        const power = {
                            id: powerId
                        }
                        roleObject.powers.push(power)
                    }
                    if (this.isAddNew) {
                        request.post('/role', roleObject).then((res) => {
                            const response = res.data
                            if (response.code === DATABASE_SAVE_OK) {
                                ElMessage.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>添加成功</strong>'
                                })
                                this.dialogVisible = false
                                this.loadRoleTable()
                            } else {
                                ElMessage.error({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>添加失败</strong>: ' + response.msg
                                })
                                this.dialogLoading = false
                            }
                        })
                    } else {
                        roleObject.id = this.editRoleId
                        request.put('/role', roleObject).then((res) => {
                            const response = res.data
                            if (response.code === DATABASE_UPDATE_OK) {
                                ElMessage.success({
                                    dangerouslyUseHTMLString: true,
                                    message: '<strong>修改成功</strong>'
                                })
                                this.dialogVisible = false
                                this.loadRoleTable()
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
            this.loadRoleTable()
        },
        handleCurrentChange(currentPage) {
            this.currentPage = currentPage
            this.loadRoleTable()
        },
        handleQuery() {
            this.searchName = _.cloneDeep(this.inputName)
            this.searchPower = _.cloneDeep(this.selectedPower)
            this.searchEnable = _.cloneDeep(this.selectedEnable)
            this.loadRoleTable()
        },
        handleClear() {
            this.inputName = ''
            this.selectedPower = []
            this.selectedEnable = -1
            this.currentPage = 1
            this.handleQuery()
        }
    },
    mounted() {
        this.loadRoleTable()
        this.getPowers()
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
