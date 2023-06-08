<template>
    <el-card shadow="hover" class="homeCard">
        <template #header>
            <div class="card-header" style="height: 20px">
                <h2>日历</h2>
                <div>
                    <el-button class="button" type="primary" @click="showAttendance"
                        >打卡</el-button
                    >
                    <el-button class="button" text @click="pushTodo">查看个人考勤</el-button>
                </div>
            </div>
        </template>
        <el-calendar ref="calendar" />
    </el-card>
    <el-dialog v-model="attendanceVisible" title="打卡" style="width: 25%; min-width: 320px">
        <edit-one-attendance @setDialogVisible="closeAttendance" />
    </el-dialog>
</template>
<script lang="ts">
import EditOneAttendance from '@/components/attendance/EditOneAttendance.vue'

export default {
    components: { EditOneAttendance },
    data() {
        return {
            attendanceVisible: false
        }
    },
    methods: {
        showAttendance() {
            this.attendanceVisible = true
        },
        closeAttendance() {
            this.attendanceVisible = false
        },
        pushTodo() {
            this.$router.push('/attendance/user')
        }
    }
}
</script>

<style scoped>
.homeCard {
    height: calc((100vh - 56px - 40px - 20px) / 2);
    min-height: calc((500px - 56px - 40px - 20px) / 2);
}
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.card-text {
    font-size: 14px;
}
.homeCard /deep/ .el-calendar-table .el-calendar-day {
    height: calc(((100vh - 56px - 40px - 20px) / 2 - 56px - 40px - 24px - 28px) / 6);
}
.homeCard /deep/ .el-calendar__header {
    padding: 0;
}
.homeCard /deep/ .el-calendar__body {
    padding: 0;
}
.homeCard /deep/ .el-calendar-table thead th {
    padding: 5px;
}
</style>
