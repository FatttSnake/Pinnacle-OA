<template>
    <el-form :model="form" label-width="90px">
        <el-form-item label="旧密码" prop="oldPasswd">
            <el-input v-model="form.oldPasswd" placeholder="请输入旧密码" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPasswd">
            <el-input v-model="form.newPasswd" placeholder="请输入新密码" show-password />
        </el-form-item>
        <el-form-item label="验证密码" prop="selectPasswd">
            <el-input v-model="form.selectPasswd" placeholder="请重新输入新密码" show-password />
        </el-form-item>
    </el-form>
</template>
<script lang="ts">
export default {
    data() {
        return {
            form: {
                oldPasswd: '',
                newPasswd: '',
                selectPasswd: ''
            }
        }
    }
}
</script>
<style scoped></style>
