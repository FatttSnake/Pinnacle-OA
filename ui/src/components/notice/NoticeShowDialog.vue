<template>
    <el-descriptions direction="vertical" :column="3" border>
        <template #title
            ><h2>{{ noticeShowData.title }}</h2></template
        >
        <el-descriptions-item label="发布者" width="120"
            ><el-tag size="large" type="success">{{
                noticeShowData.sender.username
            }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="生效时间" width="180">{{
            formatDate(noticeShowData.sendTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="公告类型"
            ><el-tag size="large">{{
                noticeShowData.noticeType.name
            }}</el-tag></el-descriptions-item
        >
        <el-descriptions-item label="优先级">{{ noticeShowData.priority }} </el-descriptions-item>
        <el-descriptions-item label="失效时间">{{
            formatDate(noticeShowData.endTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="公告内容">{{ noticeShowData.content }}</el-descriptions-item>
    </el-descriptions>
    <el-button
        type="primary"
        @click="handleShowDialog(noticeShowData.id, noticeShowData.isRead)"
        style="margin: 50px 400px"
        >确 定</el-button
    >
</template>
<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeStore } from '@/store/notice'
const noticeStore = useNoticeStore()

export default {
    name: 'NoticeShowDialog',
    computed: {
        ...mapState(useNoticeStore, ['noticeShowData', 'dialogShowVisible', 'currentViewPage'])
    },
    data() {
        return {}
    },
    methods: {
        handleShowDialog(nid, isRead) {
            noticeStore.$patch((state) => {
                state.dialogShowVisible = false
            })
            if (isRead === 0) {
                noticeStore.modifyNoticeIsRead(nid, 1)
                let flag = 0
                if (this.currentViewPage === 'All') {
                    flag = -1
                } else if (this.currentViewPage === 'ToRead') {
                    flag = 0
                } else if (this.currentViewPage === 'AlRead') {
                    flag = 1
                }
                setTimeout(() => {
                    noticeStore.selectAllNoticeByUserId(flag)
                }, 100)
            }
        },
        formatDate(date) {
            if (date == null) return null
            return new Date(date).toLocaleString()
        }
    }
}
</script>
<style scoped>
h2 {
    margin-left: 20px;
    margin-bottom: 10px;
    font-size: 22px;
    font-weight: bold;
}
</style>
