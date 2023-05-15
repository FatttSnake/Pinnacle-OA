<template>
    <el-container>
        <el-header>
            <notice-head @selectByCond="selectByCond"></notice-head>
        </el-header>
        <el-main>
            <el-button
                size="large"
                style="background-color: rgba(71, 138, 173, 0.85); color: white"
                @click="openAddNoticeDialog"
                >发布公告</el-button
            >
            <!-- 添加公告对话框-->
            <el-dialog v-model="dialogAddVisible" center>
                <template #header>
                    <h2 style="color: red">发布公告</h2>
                </template>
                <commitForm />
            </el-dialog>
            <notice-manage-table
                @handleDeleteById="handleDeleteById"
                @clearFilter="clearFilter"
            ></notice-manage-table>
        </el-main>
    </el-container>
</template>

<script lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import 'element-plus/theme-chalk/el-message-box.css'
import request from '@/services'
import { useNoticeStore } from '@/store/notice'
import { mapState } from 'pinia'
const noticeStore = useNoticeStore()

export default {
    name: 'NoticeHome',
    data() {
        return {}
    },
    methods: {
        selectByCond(currentPage, pageSize, search) {
            request
                .get('/notice/page', {
                    currentPage,
                    pageSize,
                    title: search.title,
                    type: search.type,
                    startTime: search.startTime,
                    endTime: search.endTime
                })
                .then((response) => {
                    if (response.data.code === 20021) {
                        noticeStore.selectData = response.data.data
                        noticeStore.total = parseInt(response.data.msg)
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
        handleDeleteById(deleteID) {
            ElMessageBox.confirm('确定是否要删除？该操作将无法回退', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '我再想想',
                type: 'warning'
            })
                .then(() => {
                    request.delete('/notice/' + deleteID).then((response) => {
                        if (response.data.code === 20024) {
                            ElMessage({
                                message: '删除成功.',
                                type: 'success'
                            })
                            noticeStore.selectAllNotice(1, 5)
                        } else if (response.data.code === 20034) {
                            ElMessage({
                                message: response.data.msg,
                                type: 'error'
                            })
                        }
                    })
                })
                .catch(() => {})
        },
        openAddNoticeDialog() {
            noticeStore.$patch((state) => {
                state.dialogAddVisible = true
                state.editFlag = false
            })
        },
        clearFilter() {
            // this.selectAllNotice()
            // location.reload()
            this.$router.go(0)
        }
    },
    mounted() {
        noticeStore.selectEnableNoticeType()
    },
    computed: {
        ...mapState(useNoticeStore, ['dialogAddVisible'])
    }
}
</script>

<style scoped>
.el-header {
    background-color: #fff;
}
.el-main {
    padding: 0;
    margin-top: 20px;
}
</style>
