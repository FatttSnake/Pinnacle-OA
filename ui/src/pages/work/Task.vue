<template>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" router>
        <el-menu-item index="1" route="/work/task/todo">待办工作</el-menu-item>
        <el-menu-item index="2" route="/work/task/complete">已办工作</el-menu-item>
    </el-menu>
    <router-view></router-view>
</template>

<script>
export default {
    name: 'TaskPage',
    data() {
        return {
            activeIndex: '1'
        }
    }
}
</script>
