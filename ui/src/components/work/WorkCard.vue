<template>
    <el-card shadow="hover">
        <template #header>
            <div class="card-header">
                <h2>待办工作</h2>
                <el-button class="button" text @click="pushTodo">查看更多</el-button>
            </div>
        </template>
        <div v-for="item in tableData" :key="item" class="text item">
            <el-row gutter="10">
                <el-col :xs="0" :sm="4" :md="4" :lg="3" :xl="2" style="justify-content: center">
                    <el-avatar style="background-color: #409eff" size="default">
                        <el-icon
                            color="white"
                            :size="SIZE_ICON_LG()"
                            style="vertical-align: center"
                        >
                            <icon-pinnacle-workpage />
                        </el-icon>
                    </el-avatar>
                </el-col>
                <el-col :xs="5" :sm="5" :md="5" :lg="8" :xl="10"
                    ><el-text size="large" style="color: black; line-height: 42px" truncated>{{
                        item.content
                    }}</el-text>
                </el-col>
                <el-col :xs="15" :sm="11" :md="12" :lg="10" :xl="10" style="text-align: right"
                    ><el-text type="info" style="line-height: 42px">{{
                        formatDate(item.deadline)
                    }}</el-text></el-col
                >
                <el-col :xs="4" :sm="4" :md="3" :lg="3" :xl="2" style="text-align: right"
                    ><el-text type="info" style="line-height: 42px">截止</el-text></el-col
                >
            </el-row>
        </div>
    </el-card>
</template>

<script lang="ts">
import request from '@/services'
import { SIZE_ICON_LG, SIZE_ICON_MD } from '@/constants/Common.constants'

export default {
    data() {
        return {
            tableData: []
        }
    },
    methods: {
        SIZE_ICON_LG() {
            return SIZE_ICON_LG
        },
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        formatDate(time) {
            return new Date(time).toLocaleString()
        },
        getTableData() {
            request
                .get('/work/card')
                .then((response) => {
                    this.tableData = response.data.data
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        pushTodo() {
            this.$router.push('/work/task')
        }
    },
    created() {
        this.getTableData()
    }
}
</script>

<style>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}
</style>
