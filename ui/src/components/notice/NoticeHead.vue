<template>
    <div class="notice-head-layout">
        <el-row>
            <el-col :span="2" :xs="3" :sm="2"
                ><el-text
                    class="mx-1"
                    size="large"
                    style="color: rgba(71, 138, 173, 0.85); font-weight: bolder"
                    >公告标题：</el-text
                ></el-col
            >
            <el-col :span="4">
                <el-input v-model="search_info.title" placeholder="请输入公告标题"> </el-input>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="2" :xs="3" :sm="2"
                ><el-text
                    class="mx-1"
                    size="large"
                    style="color: rgba(71, 138, 173, 0.85); font-weight: bolder"
                    >公告类型：</el-text
                ></el-col
            >
            <el-col :span="4">
                <el-input v-model="search_info.type" placeholder="请输入公告类型"> </el-input>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="5">
                <el-date-picker
                    v-model="timeRang"
                    type="datetimerange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                >
                </el-date-picker>
            </el-col>
            <el-col :span="2"></el-col>
            <el-col :span="3">
                <el-button type="primary" @click="selectByCondition">
                    <el-icon :size="SIZE_ICON_SM()" style="color: white; margin-right: 5px">
                        <icon-pinnacle-notice_search />
                    </el-icon>
                    搜索
                </el-button>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts">
import { COLOR_PRODUCTION, SIZE_ICON_MD, SIZE_ICON_SM } from '@/constants/Common.constants'
import _ from 'lodash'

export default {
    name: 'NoticeHead',
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
            console.log(this.timeRang)
            if (!_.isEmpty(this.timeRang)) {
                this.search_info.startTime = this.handleDateFormatUTC(this.timeRang[0])
                this.search_info.endTime = this.handleDateFormatUTC(this.timeRang[1])
            }
            this.$emit('selectByCond', this.search_info)
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
        }
    },
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
    }
}
</script>

<style scoped>
.el-row {
    height: 80%;
    margin-top: 15px;
}

.el-row:last-child {
    margin-bottom: 0;
}

.el-col {
    border-radius: 4px;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}
</style>
