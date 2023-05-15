<template>
    <el-container>
        <el-header>
            <notice-head></notice-head>
        </el-header>
        <el-main>
            <el-menu
                :default-active="$route.path"
                class="el-menu-demo"
                mode="horizontal"
                @select="handleSelect"
                router
            >
                <el-menu-item index="/notice/noticeView/all">所有公告</el-menu-item>
                <el-menu-item index="/notice/noticeView/alRead">已读</el-menu-item>
                <el-menu-item index="/notice/noticeView/toRead">未读</el-menu-item>
            </el-menu>
            <router-view />
        </el-main>
    </el-container>
</template>

<script lang="ts">
export default {
    name: 'NoticeView',
    data() {
        return {}
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
