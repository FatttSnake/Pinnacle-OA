<template>
    <el-container>
        <el-header style="width: 100%">
            <notice-view-head @selectSelfByCond="getLoading" />
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
import { useNoticeTypeStore, useNoticeStore } from '@/store/notice'
import { mapState } from 'pinia'
const noticeTypeStore = useNoticeTypeStore()
const noticeStore = useNoticeStore()

export default {
    name: 'NoticeView',
    computed: {
        ...mapState(useNoticeStore, ['showLoading', 'searchBySelf'])
    },
    data() {
        return {}
    },
    methods: {
        getLoading(status) {
            noticeStore.showLoading = true
            noticeStore.selectAllNoticeSelf(
                status,
                this.searchBySelf.title,
                this.searchBySelf.type,
                this.searchBySelf.startTime,
                this.searchBySelf.endTime
            )
        }
    },
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
