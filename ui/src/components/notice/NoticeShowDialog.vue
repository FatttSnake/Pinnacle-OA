<template>
    <el-descriptions direction="vertical" :column="3" border>
        <template #title
            ><h2>{{ noticeShowData.title }}</h2></template
        >
        <el-descriptions-item label="发布者" width="120">
            <el-tag size="large">{{ noticeShowData.sender.username }} </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="生效时间" width="180"
            >{{ formatDate(noticeShowData.sendTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="公告类型">
            <el-tag
                disable-transitions
                size="default"
                :type="
                    noticeShowData.noticeType.name === '通知公告'
                        ? 'warning'
                        : noticeShowData.noticeType.name === '紧急公告'
                        ? 'danger'
                        : 'success'
                "
            >
                {{ noticeShowData.noticeType.name }}
            </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="优先级"
            ><el-tag
                :type="
                    noticeShowData.priority <= 4
                        ? 'success'
                        : noticeShowData.priority <= 10
                        ? 'warning'
                        : 'danger'
                "
                effect="plain"
            >
                {{
                    noticeShowData.priority <= 4
                        ? '普通'
                        : noticeShowData.priority <= 10
                        ? '优先'
                        : '紧急'
                }}
            </el-tag></el-descriptions-item
        >
        <el-descriptions-item label="失效时间"
            >{{ formatDate(noticeShowData.endTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="公告内容">{{ noticeShowData.content }}</el-descriptions-item>
    </el-descriptions>
    <el-button type="primary" @click="handleShowDialog(noticeShowData)" style="margin: 50px 400px"
        >确 定
    </el-button>
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
        async handleShowDialog(notice) {
            noticeStore.$patch((state) => {
                state.dialogShowVisible = false
            })
            if (notice.isRead === 0) {
                await noticeStore.modifyNoticeIsRead(notice)
                let flag = 0
                if (this.currentViewPage === 'All') {
                    flag = -1
                } else if (this.currentViewPage === 'ToRead') {
                    flag = 0
                } else if (this.currentViewPage === 'AlRead') {
                    flag = 1
                }
                await noticeStore.selectAllNoticeSelf(flag, '', '', '', '')
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
