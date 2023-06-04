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
            state.currentViewPage = 'ToRead'
        })
        noticeStore.selectAllNoticeSelf(0, '', '', '', '')
        noticeStore.$patch((state) => {
            state.searchBySelf = {
                title: '',
                type: '',
                startTime: '',
                endTime: ''
            }
        })
    }
}
</script>

<style scoped></style>
