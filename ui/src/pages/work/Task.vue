<template>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
        <el-menu-item index="1"
            ><router-link to="/todo" style="text-decoration: none"
                >待办工作</router-link
            ></el-menu-item
        >
        <el-menu-item index="2"
            ><router-link to="complete" style="text-decoration: none"
                >已办工作</router-link
            ></el-menu-item
        >
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
    },
    created() {
        this.$router.push('/todo')
    }
}
</script>
