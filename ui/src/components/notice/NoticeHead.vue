<template>
    <el-form
        :inline="true"
        :model="search_info"
        class="demo-form-inline"
        label-width="auto"
        ref="searchForm"
        style="min-width: 1185px"
    >
        <el-row :span="24">
            <el-col :span="5">
                <el-form-item label="公告标题：" prop="title">
                    <el-input v-model="search_info.title" placeholder="请输入公告标题"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="5">
                <el-form-item label="公告类型：" prop="type">
                    <el-select v-model="search_info.type" placeholder="请选择公告类型">
                        <el-option
                            v-for="item in enableNoticeTypeList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.name"
                        />
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item label="日期：" prop="timeRang">
                    <el-date-picker
                        v-model="timeRang"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        style="width: auto"
                    >
                    </el-date-picker>
                </el-form-item>
            </el-col>
            <el-col :span="6">
                <el-form-item>
                    <el-button type="primary" @click="selectByCondition"
                        ><el-icon :size="SIZE_ICON_SM()" style="color: white; margin-right: 5px">
                            <icon-pinnacle-notice_search /> </el-icon
                        >查询</el-button
                    >
                    <el-button type="primary" @click="resetForm"
                        ><el-icon :size="SIZE_ICON_SM()" style="color: white">
                            <icon-pinnacle-reset /> </el-icon
                        >重置</el-button
                    >
                </el-form-item>
            </el-col>
        </el-row>
    </el-form>
</template>

<script lang="ts">
import { COLOR_PRODUCTION, SIZE_ICON_MD, SIZE_ICON_SM } from '@/constants/Common.constants'
import _ from 'lodash'
import { useNoticeTypeStore } from '@/store/notice'
import { mapState } from 'pinia'
export default {
    name: 'NoticeHead',
    data() {
        return {
            timeRang: [],
            search_info: {
                title: '',
                type: '',
                startTime: '',
                endTime: ''
            }
        }
    },
    methods: {
        SIZE_ICON_SM() {
            return SIZE_ICON_SM
        },
        COLOR_PRODUCTION() {
            return COLOR_PRODUCTION
        },
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        selectByCondition() {
            if (!_.isEmpty(this.timeRang)) {
                this.search_info.startTime = this.handleDateFormatUTC(this.timeRang[0])
                this.search_info.endTime = this.handleDateFormatUTC(this.timeRang[1])
            }
            this.$emit('selectByCond', 1, 5, this.search_info)
        },
        handleDateFormatUTC(date) {
            let newFormat = ''
            const dateParse = new Date(Date.parse(date))
            const yy = dateParse.getUTCFullYear()
            const mm = _.padStart((dateParse.getUTCMonth() + 1).toString(), 2, '0')
            const dd = _.padStart(dateParse.getUTCDate().toString(), 2, '0')
            const hh = _.padStart(dateParse.getUTCHours().toString(), 2, '0')
            const mf = _.padStart(dateParse.getUTCMinutes().toString(), 2, '0')
            const ss = _.padStart(dateParse.getUTCSeconds().toString(), 2, '0')
            newFormat = yy + '-' + mm + '-' + dd + ' ' + hh + ':' + mf + ':' + ss
            return newFormat
        },
        resetForm() {
            this.timeRang = []
            this.$refs.searchForm.resetFields()
        }
    },
    computed: {
        ...mapState(useNoticeTypeStore, ['enableNoticeTypeList'])
    }
}
</script>

<style scoped>
.el-form {
    margin-top: 15px;
}
</style>
