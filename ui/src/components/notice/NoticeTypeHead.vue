<template>
    <el-form :inline="true" :model="searchType" class="demo-form-inline">
        <el-row :gutter="5">
            <el-col :span="6">
                <el-form-item label="类型名称：" prop="name" class="fill-with">
                    <el-input v-model="searchType.name" placeholder="请输入类型名称"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="5">
                <el-form-item label="状态：" prop="enable" class="fill-with">
                    <el-select
                        v-model="searchType.enable"
                        placeholder="请选择公告类型"
                        class="fill-with"
                    >
                        <el-option label="全部" :value="-1" />
                        <el-option label="启用" :value="1" />
                        <el-option label="禁用" :value="0" />
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="-1">
                <el-form-item>
                    <el-button type="primary" @click="selectByCondition"
                        ><el-icon :size="SIZE_ICON_SM()" style="color: white; margin-right: 5px">
                            <icon-pinnacle-notice_search /> </el-icon
                        >查询</el-button
                    >
                </el-form-item>
            </el-col>
            <el-col :span="-1">
                <el-form-item>
                    <el-button type="primary" @click="resetForm"
                        ><el-icon :size="SIZE_ICON_SM()" style="color: white; margin-right: 5px">
                            <icon-pinnacle-reset /> </el-icon
                        >重置</el-button
                    >
                </el-form-item>
            </el-col>
        </el-row>
    </el-form>
</template>

<script lang="ts">
import { SIZE_ICON_SM } from '@/constants/Common.constants'
import { useNoticeTypeStore } from '@/store/notice'
import { mapState } from 'pinia'

const noticeTypeStore = useNoticeTypeStore()
export default {
    name: 'NoticeHead',
    data() {
        return {}
    },
    methods: {
        SIZE_ICON_SM() {
            return SIZE_ICON_SM
        },
        selectByCondition() {
            this.$emit('selectTypeByCond')
        },
        resetForm() {
            noticeTypeStore.$patch((state) => {
                state.searchType = {
                    name: '',
                    enable: -1
                }
            })
            this.$emit('selectTypeByCond')
        }
    },
    computed: {
        ...mapState(useNoticeTypeStore, ['enableNoticeTypeList', 'searchType'])
    }
}
</script>

<style scoped>
.el-form {
    margin-top: 15px;
}
.fill-with {
    width: 100%;
}
</style>
