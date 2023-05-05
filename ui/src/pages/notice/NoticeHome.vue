<template>
    <div class="notice-home-layout">
        <el-container>
            <el-header>
                <notice-head @selectByCond="selectByCond"></notice-head>
            </el-header>
            <el-main>
                <el-button
                    size="large"
                    style="background-color: rgba(71, 138, 173, 0.85); color: white"
                    @click="dialogAddVisible = true"
                    >发布公告</el-button
                >
                <!-- 添加公告对话框-->
                <el-dialog v-model="dialogAddVisible" center>
                    <template #header>
                        <h2 style="color: red">发布公告</h2>
                    </template>
                    <commitForm
                        :noticeTypeList="this.noticeTypeList"
                        :departmentList="this.departmentList"
                        @handleAddNotice="handleAddNotice"
                    ></commitForm>
                </el-dialog>
                <notice-table
                    :selectData="selectData"
                    :noticeTypeList="noticeTypeList"
                    :departmentList="departmentList"
                    :dialogUpdateVisible="dialogUpdateVisible"
                    :getLoading="getLoading"
                    @handleDelete="handleDelete"
                    @clearFilter="clearFilter"
                    @handleUpdateNotice="handleUpdateNotice"
                ></notice-table>
            </el-main>
        </el-container>
    </div>
</template>

<script lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import 'element-plus/theme-chalk/el-message-box.css'
import request from '@/services'

export default {
    name: 'NoticeHome',
    data() {
        return {
            selectData: [],
            noticeTypeList: [],
            dialogAddVisible: false,
            dialogUpdateVisible: false,
            departmentList: [],
            getLoading: true
        }
    },
    methods: {
        selectByCond(search) {
            request
                .get('http://localhost:8621/notice', {
                    title: search.title,
                    type: search.type,
                    startTime: search.startTime,
                    endTime: search.endTime
                })
                .then((response) => {
                    if (response.data.code === 20021) {
                        this.selectData = response.data.data
                        ElMessage({
                            message: '查询成功.',
                            type: 'success'
                        })
                    } else if (response.data.code === 20031) {
                        ElMessage({
                            message: response.data.msg,
                            type: 'error'
                        })
                    }
                })
        },
        selectAllNotice() {
            request.get('http://localhost:8621/notice').then((response) => {
                this.selectData = response.data.data
                if (this.selectData) {
                    this.getLoading = false
                }
            })
        },
        handleDelete(deleteID) {
            ElMessageBox.confirm('确定是否要删除？该操作将无法回退', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '我再想想',
                type: 'warning'
            })
                .then(() => {
                    request.delete('http://localhost:8621/notice/' + deleteID).then((response) => {
                        if (response.data.code === 20024) {
                            this.dialogAddVisible = false
                            ElMessage({
                                message: '删除成功.',
                                type: 'success'
                            })
                        } else if (response.data.code === 20034) {
                            ElMessage({
                                message: response.data.msg,
                                type: 'error'
                            })
                        }
                    })
                    this.$router.go(0)
                })
                .catch(() => {})
        },
        selectNoticeType() {
            request.get('http://localhost:8621/noticeType').then((response) => {
                this.noticeTypeList = response.data.data
            })
        },
        selectDepartment() {
            request.get('http://localhost:8621/department').then((response) => {
                this.departmentList = response.data.data
            })
        },
        handleAddNotice(addFormData) {
            request.post('http://localhost:8621/notice', addFormData).then((response) => {
                if (response.data.code === 20022) {
                    this.dialogAddVisible = false
                    ElMessage({
                        message: '发布成功.',
                        type: 'success'
                    })
                } else if (response.data.code === 20032) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            this.$router.go(0)
        },
        handleUpdateNotice(updateNotice) {
            request.put('http://localhost:8621/notice', updateNotice).then((response) => {
                if (response.data.code === 20023) {
                    this.dialogUpdateVisible = false
                    ElMessage({
                        message: '发布成功.',
                        type: 'success'
                    })
                } else if (response.data.code === 20033) {
                    ElMessage({
                        message: response.data.msg,
                        type: 'error'
                    })
                }
            })
            this.$router.go(0)
        },
        clearFilter() {
            // this.selectAllNotice()
            // location.reload()
            this.$router.go(0)
        }
    },
    mounted() {
        this.selectAllNotice()
        this.selectNoticeType()
        this.selectDepartment()
    }
}
</script>

<style scoped>
.el-container {
}
.el-header {
    background-color: #fff;
    //border: #9e9e9e solid 1px;
}
.el-main {
    padding: 0px;
    margin-top: 20px;
}
</style>
