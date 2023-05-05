<template>
    <el-descriptions direction="vertical" :column="3" border>
        <template #title>{{ this.noticeShow.title }}</template>
        <el-descriptions-item label="发布人"
            ><el-tag size="large" type="success">{{
                this.noticeShow.sender.username
            }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="生效时间">{{
            formatDate(this.noticeShow.sendTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="优先级" :span="12">{{
            this.noticeShow.priority
        }}</el-descriptions-item>
        <el-descriptions-item label="公告类型">
            <el-tag size="large">{{ this.noticeShow.noticeType.name }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="失效时间">{{
            formatDate(this.noticeShow.endTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="公告内容">{{ this.noticeShow.content }}</el-descriptions-item>
    </el-descriptions>
    <el-button type="primary" @click="handleShowDialog" style="margin: 50px 400px">确 定</el-button>
</template>
<script lang="ts">
export default {
    name: 'NoticeShowDialog',
    props: ['noticeShow'],
    data() {
        return {
            showDialogVisible: false
        }
    },
    methods: {
        handleShowDialog() {
            this.$emit('showDialogVisible', this.showDialogVisible)
        },
        formatDate(date) {
            if (date == null) return null
            return new Date(date).toLocaleString()
        }
    }
}
</script>
<style scoped></style>
