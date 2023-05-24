<template>
    <el-container>
        <el-header>
            <notice-head></notice-head>
        </el-header>
        <el-main>
            <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" router>
                <el-menu-item index="/notice/view/all">所有公告</el-menu-item>
                <el-menu-item index="/notice/view/toRead">未读</el-menu-item>
                <el-menu-item index="/notice/view/alRead">已读</el-menu-item>
            </el-menu>
            <router-view />
        </el-main>
    </el-container>
</template>

<script lang="ts">
import { useNoticeTypeStore } from '@/store/notice'
const noticeTypeStore = useNoticeTypeStore()
export default {
    name: 'NoticeView',
    data() {
        return {}
    },
    methods: {},
    mounted() {
        noticeTypeStore.selectEnableNoticeType()
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
