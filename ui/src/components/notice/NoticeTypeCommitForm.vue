<template>
    <el-form :model="addTypeData" :rules="typeRule" ref="addTypeData" label-width="150px">
        <el-form-item label="类型名称" prop="name">
            <el-input v-model="addTypeData.name" autocomplete="off" clearable />
        </el-form-item>
        <el-form-item label="是否启用" prop="enable">
            <el-switch
                v-model="addTypeData.enable"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #afb2b8"
                active-text="启用"
                :active-value="1"
                inactive-text="禁用"
                :inactive-value="0"
            />
        </el-form-item>
    </el-form>
</template>
<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeTypeStore } from '@/store/notice'

const noticeTypeStore = useNoticeTypeStore()

export default {
    name: 'NoticeTypeCommitForm',
    computed: {
        ...mapState(useNoticeTypeStore, [
            'noticeTypeList',
            'dataLoading',
            'addTypeData',
            'editFlag',
            'showTypeData'
        ])
    },
    data() {
        return {
            typeRule: {
                name: [
                    { required: true, message: '请输入类型名称', trigger: 'blur' },
                    { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {},
    created() {
        // 编辑操作
        if (noticeTypeStore.editFlag === true) {
            noticeTypeStore.addTypeData = this.showTypeData
        }
    }
}
</script>

<style scoped></style>
