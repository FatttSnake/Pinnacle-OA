<template>
    <el-row :span="24">
        <el-col :span="18">
            <div class="mt-4">
                <el-input placeholder="查询事务" class="input-with-select">
                    <template #prepend>
                        <el-select placeholder="查询方式">
                            <el-option label="事务编号" value="1" />
                            <el-option label="事务名称" value="2" />
                            <el-option label="日期" value="3" />
                        </el-select>
                    </template>
                    <template #append>
                        <el-button>查询</el-button>
                    </template>
                </el-input>
            </div>
        </el-col>

        <el-col :span="6">
            <el-menu
                :default-active="$route.path"
                class="el-menu-demo"
                mode="horizontal"
                router
                background-color="white"
            >
                <el-menu-item index="/affair/manage/toApprove">
                    <el-button type="warning" round>待审批</el-button>
                </el-menu-item>
                <el-menu-item index="/affair/manage/Approved">
                    <el-button type="success" round>已审批</el-button>
                </el-menu-item>
            </el-menu>
        </el-col>
        <router-view></router-view>
    </el-row>
</template>

<script lang="ts">
import { COLOR_BACKGROUND } from '@/constants/Common.constants'

export default {
    name: 'AffairManage',
    methods: {
        COLOR_BACKGROUND() {
            return COLOR_BACKGROUND
        }
    }
}
</script>

<style scoped>
.el-menu--horizontal > .el-menu-item {
    border-bottom: none;
    text-decoration: none;
}
</style>
