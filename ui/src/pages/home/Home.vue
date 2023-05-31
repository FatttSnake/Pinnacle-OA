<template>
    <div style="height: calc(100vh - 56px - 40px); min-height: calc(500px - 56px - 40px)">
        <el-row :gutter="20">
            <el-col :span="12">
                <work-card :tableData="works" />
            </el-col>
            <el-col :span="12">
                <notice-card :tableData="notices" />
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <work-card :tableData="works" />
            </el-col>
            <el-col :span="12">
                <work-card :tableData="works" />
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts">
import request from '@/services'

export default {
    name: 'HomePage',
    data() {
        return {
            works: [],
            notices: []
        }
    },
    methods: {
        getTableData() {
            request
                .get('/work/card')
                .then((response) => {
                    this.works = response.data.data
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
            request
                .get('/notice/limit')
                .then((response) => {
                    this.notices = response.data.data
                })
                .catch((reportError) => {
                    console.log(reportError)
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
