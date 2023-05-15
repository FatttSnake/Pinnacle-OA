<template>
    <el-config-provider :locale="zhCn()">
        <router-view></router-view>
    </el-config-provider>
</template>

<script lang="ts">
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

export default {
    methods: {
        zhCn() {
            return zhCn
        }
    }
}
</script>

<style scoped></style>
