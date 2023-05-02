<template>
    <div class="notice-head-layout">
        <el-row :gutter="20">
            <el-col :span="5">
                <el-input v-model="search_title" placeholder="请输入公告标题">
                    <template #prefix>
                        <el-icon :size="SIZE_ICON_MD()" :color="COLOR_PRODUCTION()">
                            <icon-pinnacle-notice_search />
                        </el-icon>
                    </template>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-button type="primary" @click="this.$emit('selectByTitle', search_title)"
                    >搜索</el-button
                >
            </el-col>
            <el-col :span="8">
                <div class="gutter" />
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts">
import { COLOR_PRODUCTION, SIZE_ICON_MD } from '@/constants/Common.constants'

export default {
    name: 'NoticeHead',
    methods: {
        COLOR_PRODUCTION() {
            return COLOR_PRODUCTION
        },
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        }
    },
    data() {
        return {
            search_title: ''
        }
    }
}
</script>

<style scoped>
.el-row {
    height: 80%;
    margin-top: 15px;
}

.el-row:last-child {
    margin-bottom: 0;
}

.el-col {
    border-radius: 4px;
    margin-left: 20px;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}
</style>
