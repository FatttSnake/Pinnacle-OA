import { defineStore } from 'pinia'
import request from '@/services'

export const useNoticeViewStore = defineStore('notice-view', {
    state: () => {
        return {
            selectData: [],
            getLoading: true
        }
    },
    getters: {},
    actions: {
        selectAllNoticeByUserId(): void {
            void request.get('http://localhost:8621/notice/ByUserId').then((response) => {
                this.selectData = response.data.data
                if (this.selectData.length !== 0) {
                    this.getLoading = false
                }
            })
        }
    }
})
