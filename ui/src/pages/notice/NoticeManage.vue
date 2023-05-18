<template>
    <el-container>
        <el-header>
            <notice-head @selectByCond="selectByCond"></notice-head>
        </el-header>
        <el-main>
            <el-button
                size="large"
                type="primary"
                @click="openAddNoticeDialog"
                style="margin-right: 15px"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-add /> </el-icon
                >发布公告</el-button
            >
            <!-- 添加公告对话框-->
            <el-dialog
                v-model="dialogAddVisible"
                center
                :close-on-click-modal="false"
                :before-close="handleDialogClose"
            >
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
import { SIZE_ICON_MD } from '@/constants/Common.constants'
const noticeStore = useNoticeStore()

export default {
    name: 'NoticeHome',
    data() {
        return {}
    },
    methods: {
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
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
        handleDialogClose() {
            noticeStore.$patch((state) => {
                state.dialogEditVisible = false
                state.dialogAddVisible = false
                state.dialogShowVisible = false
                state.editFlag = false
                state.hackReset = false
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
