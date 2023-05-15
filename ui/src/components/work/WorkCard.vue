<template>
    <el-card style="min-width: 640px">
        <template #header>
            <div class="card-header">
                <h2>待办工作</h2>
                <el-button class="button" text @click="pushTodo">查看更多</el-button>
            </div>
        </template>
        <div v-for="item in tableData" :key="item" class="text item">
            <el-row style="width: 100%">
                <el-col :span="1" />
                <el-col :span="1">
                    <el-avatar style="background-color: #5495f1">
                        <el-icon
                            color="white"
                            :size="SIZE_ICON_LG()"
                            style="vertical-align: center"
                        >
                            <icon-pinnacle-workpage />
                        </el-icon>
                    </el-avatar>
                </el-col>
                <el-col :span="1" />
                <el-col :span="7"
                    ><el-text size="large" style="color: black">{{ item.content }}</el-text>
                </el-col>
                <el-col :span="4" />
                <el-col :span="7" style="text-align: right"
                    ><el-text type="info">{{ formatDate(item.deadline) }}</el-text></el-col
                >
                <el-col :span="2" style="text-align: right"
                    ><el-text type="info">截止</el-text></el-col
                >
                <el-col :span="1" />
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
