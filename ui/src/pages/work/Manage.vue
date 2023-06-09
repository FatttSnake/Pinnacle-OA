<template>
    <div class="head">
        <el-row style="width: 640px">
            <el-col :span="17">
                <el-input
                    v-model="searchContent"
                    placeholder="请输入查询的工作内容"
                    size="default"
                    clearable
                />
            </el-col>
            <el-col :span="1" />
            <el-col :span="3">
                <el-button
                    size="default"
                    type="primary"
                    @click="searchByContent(this.searchContent.trim())"
                    >搜索</el-button
                >
            </el-col>
            <el-col :span="3">
                <el-button size="default" @click="addVisible = true">添加</el-button>
            </el-col>
        </el-row>
    </div>
    <div class="main">
        <div class="main-table">
            <el-table
                :data="tableData"
                style="width: 100%"
                v-loading="loading"
                element-loading-text="加载中..."
            >
                <el-table-column prop="content" label="内容" width="800" />
                <el-table-column prop="publisherName" label="发布者" width="120" />
                <el-table-column prop="worker" label="工作人员" width="200">
                    <template #default="{ row }">
                        <el-tag
                            v-for="item in row.worker"
                            :key="item.userId"
                            size="small"
                            round
                            style="margin-right: 10px"
                        >
                            {{
                                item.staff.lastName +
                                item.staff.firstName +
                                '(' +
                                item.username +
                                ')'
                            }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="deadline" label="结束时间" width="200" sortable>
                    <template #default="scope">
                        {{ formatDate(scope.row.deadline) }}
                    </template>
                </el-table-column>
                <el-table-column fixed="right" prop="progress" label="进度" width="200" sortable>
                    <template #default="scope">
                        <el-progress
                            :text-inside="true"
                            :stroke-width="15"
                            :percentage="scope.row.progress"
                        />
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="150">
                    <template #default="scope">
                        <el-button link type="primary" size="large" @click="handleClick(scope.row)"
                            >编辑</el-button
                        >
                        <el-popconfirm
                            width="220"
                            confirm-button-text="是"
                            cancel-button-text="否"
                            icon-color="#00d4ff"
                            title="是否确定删除？"
                            @confirm="deleteConfirmEvent(scope.row)"
                            @cancel="deleteCancelEvent"
                        >
                            <template #reference>
                                <el-button link type="primary" size="default">删除</el-button>
                            </template>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
            v-model="addVisible"
            width="60%"
            :show-close="false"
            :close-on-click-modal="false"
        >
            <edit-work
                :workers="workers"
                @setDialogVisible="setDialogVisible"
                @addWork="addWork"
            ></edit-work>
        </el-dialog>
        <el-dialog
            v-model="editVisible"
            width="60%"
            :show-close="false"
            :close-on-click-modal="false"
        >
            <edit-work
                :editForm="rowData"
                :workers="workers"
                @setDialogVisible="setDialogVisible"
                @updateWork="updateWork"
            ></edit-work>
        </el-dialog>
    </div>
</template>

<script lang="ts">
import _ from 'lodash'
import request from '@/services'
import EditWork from '@/components/work/EditWork.vue'
import { ElMessage } from 'element-plus'
import {
    DATABASE_DELETE_OK,
    DATABASE_SAVE_OK,
    DATABASE_SELECT_OK,
    DATABASE_UPDATE_OK
} from '@/constants/Common.constants'

export default {
    name: 'AllTaskPage',
    data() {
        return {
            tableData: [],
            rowData: [],
            addVisible: false,
            editVisible: false,
            loading: true,
            searchContent: null,
            workers: []
        }
    },
    methods: {
        handleClick(row) {
            this.rowData = row
            this.editVisible = true
        },
        deleteConfirmEvent(row) {
            this.deleteTableData(row)
        },
        deleteCancelEvent() {},
        completeConfirmEvent() {},
        completeCancelEvent() {},
        getTableData() {
            request.get('/work').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.tableData = _.cloneDeep(response.data)
                    if (this.tableData) {
                        this.loading = false
                    }
                } else {
                    ElMessage({
                        message: '数据查询出错',
                        type: 'error'
                    })
                }
            })
        },
        deleteTableData(row) {
            request.delete('/work/' + row.id).then((res) => {
                const response = res.data
                if (response.code === DATABASE_DELETE_OK) {
                    this.getTableData()
                } else {
                    ElMessage({
                        message: '删除失败',
                        type: 'error'
                    })
                }
            })
        },
        setDialogVisible(dialogVisible) {
            this.addVisible = dialogVisible
            this.editVisible = dialogVisible
            this.getTableData()
        },
        updateWork(form) {
            request.put('/work', form).then((res) => {
                const response = res.data
                if (response.code === DATABASE_UPDATE_OK) {
                    this.editVisible = false
                    this.getTableData()
                    ElMessage({
                        message: '修改成功',
                        type: 'success'
                    })
                } else {
                    ElMessage({
                        message: '修改失败',
                        type: 'error'
                    })
                }
            })
        },
        addWork(form) {
            request.post('/work', form).then((res) => {
                const response = res.data
                if (response.code === DATABASE_SAVE_OK) {
                    this.addVisible = false
                    this.getTableData()
                    ElMessage({
                        message: '添加成功',
                        type: 'success'
                    })
                } else {
                    ElMessage({
                        message: '添加失败',
                        type: 'error'
                    })
                }
            })
        },
        formatDate(time) {
            return new Date(time).toLocaleString()
        },
        searchByContent(content) {
            request.get('/work', { content }).then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.tableData = _.cloneDeep(response.data)
                    if (this.tableData) {
                        this.loading = false
                    }
                } else {
                    ElMessage({
                        message: '查询出错',
                        type: 'error'
                    })
                }
            })
        },
        getWorkers() {
            request.get('/user/department').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.workers = response.data
                }
            })
        }
    },
    mounted() {
        this.getTableData()
        this.getWorkers()
    },
    components: {
        EditWork
    }
}
</script>

<style scoped>
@import '@/assets/css/work/work.css';
</style>
