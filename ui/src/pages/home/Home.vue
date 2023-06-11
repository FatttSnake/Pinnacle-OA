<template>
    <div style="height: calc(100vh - 56px - 40px); min-height: calc(500px - 56px - 40px)">
        <el-row :gutter="20">
            <el-col :span="12">
                <work-card :tableData="works" />
            </el-col>
            <el-col :span="12">
                <attendence-card />
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <affairs-card :tableData="affairs" />
            </el-col>
            <el-col :span="12"> <notice-card :tableData="notices" /></el-col>
        </el-row>
    </div>
</template>

<script lang="ts">
import request from '@/services'
import AffairsCard from '@/components/home/AffairsCard.vue'
import { DATABASE_SELECT_OK } from '@/constants/Common.constants'
import { ElMessage } from 'element-plus'
import AttendenceCard from '@/components/home/AttendanceCard.vue'

export default {
    name: 'HomePage',
    components: { AttendenceCard, AffairsCard },
    data() {
        return {
            works: [],
            notices: [],
            affairs: []
        }
    },
    methods: {
        getTableData() {
            request.get('/work/card').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.works = response.data
                } else {
                    ElMessage({
                        message: '工作事项数据查询出错',
                        type: 'error'
                    })
                }
            })
            request.get('/notice/limit').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.notices = response.data
                } else {
                    ElMessage({
                        message: '公告数据查询出错',
                        type: 'error'
                    })
                }
            })
            request.get('/affair/personal_affairs_limit').then((res) => {
                const response = res.data
                if (response.code === DATABASE_SELECT_OK) {
                    this.affairs = response.data
                } else {
                    ElMessage({
                        message: '个人事务数据查询出错',
                        type: 'error'
                    })
                }
            })
        }
    },
    created() {
        this.getTableData()
    },
    updated() {
        this.getTableData()
    }
}
</script>

<style scoped>
.el-row {
    width: 100%;
    height: calc((100vh - 56px - 40px - 20px) / 2);
    min-height: calc((500px - 56px - 40px - 20px) / 2);
    margin-bottom: 20px;
}
</style>
