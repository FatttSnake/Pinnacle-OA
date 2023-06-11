<template>
    <notice-view-card />
</template>
<script lang="ts">
import { useNoticeStore } from '@/store/notice'
import { mapState } from 'pinia'
const noticeStore = useNoticeStore()
export default {
    data() {
        return {}
    },
    methods: {},
    mounted() {
        noticeStore.$patch((state) => {
            state.showLoading = true
            state.currentViewPage = 'All'
        })
        noticeStore.selectAllNoticeSelf(-1, '', '', '', '')
        noticeStore.$patch((state) => {
            state.searchBySelf = {
                title: '',
                type: '',
                startTime: '',
                endTime: ''
            }
        })
    },
    computed: {
        ...mapState(useNoticeStore, ['showLoading'])
    }
}
</script>

<style scoped></style>
