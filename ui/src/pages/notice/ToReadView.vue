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
        noticeStore.selectAllNoticeSelf(0)
        noticeStore.$patch((state) => {
            state.currentViewPage = 'ToRead'
        })
    }
}
</script>

<style scoped></style>
