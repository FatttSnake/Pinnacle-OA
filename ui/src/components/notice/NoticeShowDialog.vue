<template>
    <el-descriptions direction="vertical" :column="3" border>
        <template #title>{{ this.noticeShowData.title }}</template>
        <el-descriptions-item label="发布人"
            ><el-tag size="large" type="success">{{
                this.noticeShowData.sender.username
            }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="生效时间">{{
            formatDate(this.noticeShowData.sendTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="优先级" :span="12">{{
            this.noticeShowData.priority
        }}</el-descriptions-item>
        <el-descriptions-item label="公告类型">
            <el-tag size="large">{{ this.noticeShowData.noticeType.name }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="失效时间">{{
            formatDate(this.noticeShowData.endTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="公告内容">{{
            this.noticeShowData.content
        }}</el-descriptions-item>
    </el-descriptions>
    <el-button type="primary" @click="handleShowDialog" style="margin: 50px 400px">确 定</el-button>
</template>
<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeStore } from '@/store/notice'
const noticeStore = useNoticeStore()

export default {
    name: 'NoticeShowDialog',
    computed: {
        ...mapState(useNoticeStore, ['noticeShowData', 'dialogShowVisible'])
    },
    data() {
        return {}
    },
    methods: {
        handleShowDialog() {
            noticeStore.$patch((state) => {
                state.dialogShowVisible = false
            })
        },
        formatDate(date) {
            if (date == null) return null
            return new Date(date).toLocaleString()
        }
    }
}
</script>
<style scoped></style>
