<template>
    <el-descriptions
        title="工作详细信息"
        direction="vertical"
        :column="3"
        size="large"
        :data="taskData"
        border
    >
        <el-descriptions-item label="创建者">{{ taskData.publisherName }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{
            formatDate(taskData.createTime)
        }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{
            formatDate(taskData.deadline)
        }}</el-descriptions-item>
        <el-descriptions-item label="状态">
            <el-progress :text-inside="true" :stroke-width="16" :percentage="taskData.progress" />
        </el-descriptions-item>
        <el-descriptions-item label="负责员工">
            <el-tag v-for="item in taskData.worker" style="margin-right: 10px">{{
                item.username
            }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="工作信息">{{ taskData.content }}</el-descriptions-item>
    </el-descriptions>
</template>

<script>
export default {
    props: {
        taskData: {
            publisherId: '',
            createTime: '',
            deadline: '',
            content: '',
            progress: '',
            worker: []
        }
    },
    data() {
        return {
            size: '10'
        }
    },
    methods: {
        formatDate(time) {
            return new Date(time).toLocaleString()
        }
    }
}
</script>

<style scoped></style>
