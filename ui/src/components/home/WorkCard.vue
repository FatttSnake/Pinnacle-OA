<template>
    <el-card shadow="hover" class="homeCard">
        <template #header>
            <div class="card-header" style="height: 20px">
                <h2>待办工作</h2>
                <el-button text @click="pushTodo">查看更多</el-button>
            </div>
        </template>
        <div v-for="item in tableData" :key="item" class="card-text card-item">
            <el-row :gutter="10">
                <el-col :xs="0" :sm="4" :md="4" :lg="3" :xl="2" style="justify-content: center">
                    <el-avatar style="background-color: #409eff" :size="32">
                        <el-icon color="white" :size="22" style="vertical-align: center">
                            <icon-pinnacle-workpage />
                        </el-icon>
                    </el-avatar>
                </el-col>
                <el-col :xs="5" :sm="5" :md="5" :lg="8" :xl="10"
                    ><el-text size="large" style="color: black; line-height: 32px" truncated>{{
                        item.content
                    }}</el-text>
                </el-col>
                <el-col :xs="15" :sm="11" :md="12" :lg="10" :xl="10" style="text-align: right"
                    ><el-text type="info" style="line-height: 32px">{{
                        formatDate(item.deadline)
                    }}</el-text></el-col
                >
                <el-col :xs="4" :sm="4" :md="3" :lg="3" :xl="2" style="text-align: right"
                    ><el-text type="info" style="line-height: 32px">截止</el-text></el-col
                >
            </el-row>
        </div>
        <el-empty v-if="tableData?.length === 0" description="暂无数据" image-size="0" />
    </el-card>
</template>

<script lang="ts">
export default {
    props: {
        tableData: {
            type: Array
        }
    },
    methods: {
        formatDate(time) {
            return new Date(time).toLocaleString()
        },
        pushTodo() {
            this.$router.push('/work/task')
        }
    }
}
</script>

<style>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.card-text {
    font-size: 14px;
}

.card-item {
    margin-bottom: 5px;
    height: calc(((100vh - 56px - 40px - 20px) / 2 - 56px - 40px) / 5);
    min-height: 32px;
}
.homeCard {
    height: calc((100vh - 56px - 40px - 20px) / 2);
    min-height: calc((500px - 56px - 40px - 20px) / 2);
}
</style>
