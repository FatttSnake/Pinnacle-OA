<template>
    <notice-view-card />
</template>
<script lang="ts">
import { useNoticeStore } from '@/store/notice'
const noticeStore = useNoticeStore()
export default {
    data() {
        return {}
    },
    methods: {},
    mounted() {
        noticeStore.$patch((state) => {
            state.showLoading = true
            state.currentViewPage = 'AlRead'
        })
        noticeStore.selectAllNoticeSelf(1)
    }
}
</script>

<style scoped></style>
