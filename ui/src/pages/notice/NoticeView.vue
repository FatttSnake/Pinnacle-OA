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
            <notice-view-table
                :selectData="selectData"
                :getLoading="getLoading"
            ></notice-view-table>
        </el-main>
    </el-container>
</template>

<script lang="ts">
import request from '@/services'

export default {
    name: 'NoticeView',
    data() {
        return {
            activeIndex: '1',
            selectData: [],
            getLoading: true
        }
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath)
        },
        selectAllNoticeByUserId() {
            request.get('http://localhost:8621/notice/ByUserId').then((response) => {
                this.selectData = response.data.data
                if (this.selectData) {
                    this.getLoading = false
                }
            })
        }
    },
    mounted() {
        this.selectAllNoticeByUserId()
    }
}
</script>

<style scoped></style>
