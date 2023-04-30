<template>
    <el-descriptions
        title="Vertical list with border"
        direction="vertical"
        :column="4"
        :size="size"
        :data="taskData"
        border
    >
        <el-descriptions-item label="创建者ID">{{ taskData.publisher_id }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ taskData.createTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ taskData.deadLine }}</el-descriptions-item>
        <el-descriptions-item label="状态">
            <template #default> {{}} </template>
        </el-descriptions-item>
        <el-descriptions-item label="负责员工" property="worker" />
        <el-descriptions-item label="工作信息">{{ taskData.taskContent }}</el-descriptions-item>
    </el-descriptions>
</template>

<script>
export default {
    data() {
        return {
            taskData: {
                publisher_id: '1',
                createTime: '1',
                deadLine: '1',
                taskStatus: false,
                worker: '',
                taskContent: '213'
            }
        }
    }
}
</script>

<style scoped></style>
