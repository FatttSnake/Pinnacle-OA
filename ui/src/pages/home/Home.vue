<template>
    <el-row style="width: 100%" :gutter="20">
        <el-col :span="12">
            <work-card />
        </el-col>
        <el-col :span="12">
            <work-card />
        </el-col>
    </el-row>
    <el-row style="width: 100%" :gutter="20">
        <el-col :span="12">
            <work-card />
        </el-col>
        <el-col :span="12">
            <work-card />
        </el-col>
    </el-row>
</template>

<script>
export default {
    name: 'HomePage'
}
</script>

<style scoped>
.el-row {
    margin-bottom: 20px;
}
</style>
