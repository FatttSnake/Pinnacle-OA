<template>
    <el-table
        :data="tableDate"
        v-loading="tableLoading"
        element-loading-text="Loading..."
        style="margin-top: 10px"
    >
        <el-table-column type="selection" />
        <el-table-column type="index" label="序号" />
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="menus" :label="customColumnLabel">
            <template #default="scope">
                <el-tag
                    v-if="!scope.row.customColumn || scope.row.customColumn.length === 0"
                    type="info"
                    >无</el-tag
                >
                <el-tag v-for="(column, index) in scope.row.customColumn" :key="index">{{
                    column
                }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作">
            <template #default="scope">
                <el-button size="small" @click="$emit('onEdit', scope.$index, scope.row)"
                    >编辑
                </el-button>
                <el-button
                    size="small"
                    type="danger"
                    @click="$emit('onDelete', scope.$index, scope.row)"
                    >删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
export default {
    name: 'PowerTable',
    props: {
        tableDate: Array,
        tableLoading: Boolean,
        customColumnLabel: String
    }
}
</script>

<style scoped></style>
