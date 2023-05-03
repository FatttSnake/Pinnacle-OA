<template>
    <el-descriptions
        title="Vertical list with border"
        direction="vertical"
        :column="4"
        :size="5"
        :data="taskData"
        border
    >
        <el-descriptions-item label="创建者ID">{{ taskData.publisherId }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ taskData.createTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ taskData.deadline }}</el-descriptions-item>
        <el-descriptions-item label="状态">
            <template #default> {{}} </template>
        </el-descriptions-item>
        <el-descriptions-item label="负责员工" property="worker" />
        <el-descriptions-item label="工作信息">{{ taskData.content }}</el-descriptions-item>
    </el-descriptions>
</template>

<script>
export default {
    data() {
        return {
            taskData: {
                publisherId: '1',
                createTime: '1',
                deadline: '1',
                status: false,
                worker: '',
                content: '213'
            }
        }
    }
}
</script>

<style scoped></style>
