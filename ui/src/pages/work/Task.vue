<template>
    <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" router>
        <el-menu-item index="/work/task/todo">待办工作</el-menu-item>
        <el-menu-item index="/work/task/complete">已办工作</el-menu-item>
    </el-menu>
    <router-view></router-view>
</template>

<script lang="ts">
export default {
    name: 'TaskPage',
    data() {
        return {
            activeIndex: 'todo'
        }
    }
}
</script>
