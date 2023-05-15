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
        noticeStore.selectAllNoticeByUserId(1)
    },
    unmounted() {
        console.log('alRead:unmounted')
    }
}
</script>

<style scoped></style>
