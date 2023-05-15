<template>
    <el-container>
        <el-header>
            <el-button type="primary" :size="'large'"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-add /> </el-icon
                >添加类型</el-button
            >
            <el-button type="primary" :size="'large'"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-delete /> </el-icon
                >批量删除</el-button
            >
            <el-button type="primary" :size="'large'"
                ><el-icon :size="SIZE_ICON_MD()" style="color: white; margin-right: 3px">
                    <icon-pinnacle-reset /> </el-icon
                >刷新页面</el-button
            >
        </el-header>
        <el-main>
            <notice-type-table />
        </el-main>
    </el-container>
</template>
<script lang="ts">
import { SIZE_ICON_LG, SIZE_ICON_MD, SIZE_ICON_SM } from '@/constants/Common.constants'

export default {
    name: 'NoticeTypeManage',
    data() {
        return {}
    },
    methods: {
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        SIZE_ICON_LG() {
            return SIZE_ICON_LG
        },
        SIZE_ICON_SM() {
            return SIZE_ICON_SM
        }
    },
    mounted() {}
}
</script>

<style scoped></style>
