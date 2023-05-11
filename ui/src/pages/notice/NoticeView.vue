<template>
    <el-container>
        <el-header>
            <notice-head></notice-head>
        </el-header>
        <el-main>
            <el-menu
                :default-active="activeIndex"
                class="el-menu-demo"
                mode="horizontal"
                @select="handleSelect"
            >
                <el-menu-item index="1">所有公告</el-menu-item>
                <el-menu-item index="2">已读</el-menu-item>
                <el-menu-item index="3"><a href="#">未读</a></el-menu-item>
            </el-menu>
            <notice-view-table></notice-view-table>
        </el-main>
    </el-container>
</template>

<script lang="ts">
import { useNoticeViewStore } from '@/store/notice-view'
const noticeViewStore = useNoticeViewStore()
export default {
    name: 'NoticeView',
    data() {
        return {
            activeIndex: '1'
        }
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath)
        }
    },
    mounted() {}
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
