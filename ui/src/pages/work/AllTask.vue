<template>
    <div class="main">
        <div class="main-table">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="content" label="内容" width="800" />
                <el-table-column prop="publisherName" label="发布者ID" width="120" />
                <el-table-column prop="worker" label="工作人员" width="200">
                    <template #default="{ row }">
                        <span v-for="item in row.worker" :key="item.userId">
                            {{ item.username }},&nbsp;
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="deadline" label="结束时间" width="200">
                    <template #default="scope">
                        {{ formatDate(scope.row.deadline) }}
                    </template>
                </el-table-column>
                <el-table-column fixed="right" prop="progress" label="进度" width="200">
                    <template #default="scope">
                        <el-progress
                            :text-inside="true"
                            :stroke-width="15"
                            :percentage="scope.row.progress"
                        />
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="200">
                    <template #default="scope">
                        <el-button link type="primary" size="large" @click="handleClick"
                            >编辑</el-button
                        >
                        <el-popconfirm
                            width="220"
                            confirm-button-text="是"
                            cancel-button-text="否"
                            :icon="InfoFilled"
                            icon-color="#00d4ff"
                            title="是否确定删除？"
                            @confirm="deleteConfirmEvent(scope.row)"
                            @cancel="deleteCancelEvent"
                        >
                            <template #reference>
                                <el-button link type="primary" size="default">删除</el-button>
                            </template>
                        </el-popconfirm>
                        <!--                        <el-popconfirm-->
                        <!--                            width="220"-->
                        <!--                            confirm-button-text="是"-->
                        <!--                            cancel-button-text="否"-->
                        <!--                            :icon="InfoFilled"-->
                        <!--                            icon-color="#00d4ff"-->
                        <!--                            title="是否确认完成？"-->
                        <!--                            @confirm="completeConfirmEvent"-->
                        <!--                            @cancel="completeCancelEvent"-->
                        <!--                        >-->
                        <!--                            <template #reference>-->
                        <!--                                <el-button link type="primary" size="default">完成</el-button>-->
                        <!--                            </template>-->
                        <!--                        </el-popconfirm>-->
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="main-add-content">
            <div class="main-add-box">
                <el-button @click="dialogFormVisible = true">添加</el-button>
            </div>
            <el-dialog v-model="dialogFormVisible" width="60%">
                <edit-work @setDialogVisible="setDialogVisible"></edit-work>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import EditWork from '@/components/EditWork.vue'

export default {
    name: 'AllTaskPage',
    data() {
        return {
            tableData: [],
            dialogFormVisible: false
        }
    },
    methods: {
        formatDate(deadline) {
            console.log(new Date(deadline).toLocaleString())
            return new Date(deadline).toLocaleString()
        },
        handleClick() {
            console.log('click')
        },
        deleteConfirmEvent(row) {
            this.deleteTableData(row)
            location.reload()
            console.log('delete confirm!')
        },
        deleteCancelEvent() {
            console.log('delete cancel!')
        },
        completeConfirmEvent() {
            console.log('complete confirm!')
        },
        completeCancelEvent() {
            console.log('complete cancel!')
        },
        getTableData() {
            axios
                .get('http://localhost:8621/work')
                .then((response) => {
                    console.log(response.data.data)
                    this.tableData = response.data.data
                    console.log(this.tableData)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        deleteTableData(row) {
            axios
                .delete('http://localhost:8621/work/' + row.id)
                .then((response) => {
                    console.log(response.data.data)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        setDialogVisible(dialogVisible) {
            console.log(dialogVisible)
            this.dialogFormVisible = dialogVisible
            location.reload()
        }
    },
    created() {
        this.getTableData()
    },
    components: {
        EditWork
    }
}
</script>

<style scoped>
@import '@/assets/css/work/work.css';
</style>
