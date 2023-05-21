<template>
    <el-table
        :data="tableDate"
        v-loading="tableLoading"
        element-loading-text="Loading..."
        style="margin-top: 10px"
    >
        <el-table-column type="selection" />
        <el-table-column prop="name" label="名称" min-width="20" />
        <el-table-column v-if="customColumnLabel_1" :label="customColumnLabel_1">
            <template #default="scope">
                <el-tag
                    v-if="!scope.row.customColumn_1 || scope.row.customColumn_1.length === 0"
                    type="info"
                    >无</el-tag
                >
                <el-tag v-for="(column, index) in scope.row.customColumn_1" :key="index">{{
                    column
                }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column v-if="customColumnLabel_2" :label="customColumnLabel_2">
            <template #default="scope">
                <el-tag
                    v-if="!scope.row.customColumn_2 || scope.row.customColumn_2.length === 0"
                    type="info"
                    >无</el-tag
                >
                <el-tag v-for="(column, index) in scope.row.customColumn_2" :key="index">{{
                    column
                }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="状态" align="center">
            <template #default="scope">
                <el-tag type="success" v-if="scope.row.enable">启用</el-tag>
                <el-tag type="info" v-else>禁用</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
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
        customColumnLabel_1: String,
        customColumnLabel_2: String
    }
}
</script>

<style scoped></style>
