<template>
    <notice-view-table />
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
        noticeStore.selectAllNoticeByUserId(0)
    },
    unmounted() {
        console.log('toRead:unmounted')
    }
}
</script>

<style scoped></style>
