import { defineStore } from 'pinia'
import request from '@/services'

export const useNoticeManageStore = defineStore('notice-manage', {
    state: () => {
        return {
            selectData: [],
            loading: true
        }
    },
    getters: {},
    actions: {
        selectAllNotice() {
            void request.get('http://localhost:8621/notice').then((response) => {
                this.selectData = response.data.data
                if (this.selectData.length !== 0) {
                    this.loading = false
                }
            })
        }
    }
})
