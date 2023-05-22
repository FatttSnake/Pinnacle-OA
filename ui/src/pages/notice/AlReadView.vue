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
        noticeStore.selectAllNoticeSelf(1)
        noticeStore.$patch((state) => {
            state.currentViewPage = 'AlRead'
        })
    }
}
</script>

<style scoped></style>
