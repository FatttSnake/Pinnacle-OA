<template>
    <el-table
        v-loading="loading"
        element-loading-text="加载中..."
        ref="tableRef"
        :data="selectData"
        border
        highlight-current-row
        @selection-change="handleSelectionChange"
        @row-contextmenu="openClickMenu"
        :header-cell-style="{
            background: 'darksalmon',
            'text-align': 'center',
            color: '#fff',
            'font-size': '16px'
        }"
        @filter-change="handleFilterChange"
    >
        <el-table-column type="selection" align="center" />
        <el-table-column type="index" width="80" label="序号" align="center">
            <template #default="scope">
                <el-icon :size="SIZE_ICON_XS()" :color="COLOR_TOP()" v-if="scope.row.top === 1">
                    <icon-pinnacle-top />
                </el-icon>
                {{ (this.currentPage - 1) * this.pageSize + scope.$index + 1 }}
            </template>
        </el-table-column>
        <el-table-column
            prop="title"
            label="公告标题"
            min-width="160"
            show-overflow-tooltip
            align="center"
        >
            <template #default="scope"> {{ formatterTitle(scope.row.title) }}</template>
        </el-table-column>
        <el-table-column prop="noticeType.name" label="公告类别" width="100" align="center">
            <template #default="scope">
                <el-tag
                    disable-transitions
                    size="default"
                    :type="
                        scope.row.noticeType.name === '通知公告'
                            ? 'warning'
                            : scope.row.noticeType.name === '紧急公告'
                            ? 'danger'
                            : 'success'
                    "
                >
                    {{ scope.row.noticeType.name }}
                </el-tag>
            </template>
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间"
            sortable
            width="160"
            :formatter="formatDate"
            align="center"
        />
        <el-table-column prop="priority" label="优先级" width="80" align="center">
            <template #default="scope"
                ><el-tag
                    disable-transitions
                    :type="
                        scope.row.priority <= 4
                            ? 'success'
                            : scope.row.priority <= 10
                            ? 'warning'
                            : 'danger'
                    "
                    effect="plain"
                >
                    {{
                        scope.row.priority <= 4
                            ? '普通'
                            : scope.row.priority <= 10
                            ? '优先'
                            : '紧急'
                    }}
                </el-tag></template
            >
        </el-table-column>
        <el-table-column
            prop="sendTime"
            label="生效时间"
            sortable
            width="160"
            :formatter="formatDate"
            align="center"
        />
        <el-table-column
            prop="endTime"
            label="失效时间"
            sortable
            width="160"
            :formatter="formatDate"
            align="center"
        />
        <el-table-column
            prop="sender.username"
            label="发布者"
            width="100"
            column-key="senderId"
            :filters="senderList"
            filter-placement="bottom-end"
            align="center"
        >
            <template #default="scope">
                <el-tag disable-transitions>{{ scope.row.sender.username }} </el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="200px" align="center">
            <template #default="scope">
                <el-button size="small" color="#626aef" @click="handleShow(scope.row)"
                    >查看
                </el-button>
                <el-button size="small" type="primary" @click="handleEdit(scope.row)"
                    >编辑
                </el-button>
                <el-button size="small" type="danger" @click="handleDeleteById(scope.row.id)"
                    >删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
    <!--    分页条-->
    <div class="pagination">
        <el-pagination
            style="text-align: center"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            layout="total, sizes, prev, pager, next, jumper"
            background
            :page-sizes="[10, 20, 50, 100]"
            :total="total"
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
        >
        </el-pagination>
    </div>
    <!--        编辑会话框-->
    <el-dialog
        v-model="dialogEditVisible"
        center
        v-if="hackReset"
        :close-on-click-modal="false"
        :before-close="handleDialogClose"
    >
        <template #header>
            <h2 style="color: red">编辑公告</h2>
        </template>
        <notice-commit-form ref="editForm" />
    </el-dialog>
    <!--        查看会话框-->
    <el-dialog
        v-model="dialogShowVisible"
        center
        :close-on-click-modal="false"
        :before-close="handleDialogClose"
    >
        <template #header>
            <h2 style="color: red">查看公告</h2>
        </template>
        <notice-show-dialog />
    </el-dialog>
    <!-- 表格鼠标右击下拉菜单-->
    <ul
        v-show="rightClickVisible"
        :style="{ left: left + 'px', top: top + 'px' }"
        class="contextmenu"
    >
        <li v-if="rightClickNotice.top === 0" @click.stop="modifyTop(rightClickNotice)">
            <el-icon :size="SIZE_ICON_MD()">
                <icon-pinnacle-top />
            </el-icon>
            置顶
        </li>
        <li v-if="rightClickNotice.top === 1" @click.stop="modifyTop(rightClickNotice)">
            <el-icon :size="28">
                <icon-pinnacle-cancel-top />
            </el-icon>
            取消置顶
        </li>
    </ul>
</template>

<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeStore } from '@/store/notice'
import { COLOR_TOP, SIZE_ICON_MD, SIZE_ICON_SM, SIZE_ICON_XS } from '@/constants/Common.constants'

const noticeStore = useNoticeStore()

export default {
    computed: {
        ...mapState(useNoticeStore, [
            'total',
            'selectData',
            'loading',
            'dialogShowVisible',
            'noticeShowData',
            'dialogEditVisible',
            'hackReset',
            'currentPage',
            'pageSize',
            'multiDeleteSelection',
            'search',
            'senderList'
        ])
    },
    emits: ['handleDeleteById', 'getNoticeSender', 'filterSender'],
    data() {
        return {
            rightClickVisible: false,
            rightClickNotice: {
                id: '',
                top: 0,
                isRead: 0
            },
            top: 0,
            left: 0
        }
    },
    methods: {
        SIZE_ICON_SM() {
            return SIZE_ICON_SM
        },
        SIZE_ICON_XS() {
            return SIZE_ICON_XS
        },
        COLOR_TOP() {
            return COLOR_TOP
        },
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        handleSelectionChange(val) {
            // val的值为所勾选行的数组对象
            noticeStore.$patch((state) => {
                state.multiDeleteSelection = val
            })
        },
        formatterTitle(title) {
            if (title.length > 10) {
                return title.substring(0, 10) + ' ...'
            } else {
                return title
            }
        },
        formatDate(row, column) {
            // 获取单元格数据
            const data = row[column.property]
            if (data == null) return '暂无数据'
            return new Date(data).toLocaleString()
        },
        handleEdit(row) {
            noticeStore.$patch((state) => {
                state.hackReset = true
                state.noticeShowData = row
                state.editFlag = true
                state.dialogEditVisible = true
            })
        },
        handleDialogClose() {
            noticeStore.$patch((state) => {
                state.dialogEditVisible = false
                state.dialogAddVisible = false
                state.dialogShowVisible = false
                state.editFlag = false
                state.hackReset = false
            })
            this.$refs.editForm.$refs.addData.resetFields()
        },
        handleShow(row) {
            noticeStore.$patch((state) => {
                state.dialogShowVisible = true
                state.noticeShowData = row
            })
        },
        handleDeleteById(deleteId) {
            this.$emit('handleDeleteById', deleteId)
        },
        handleSizeChange(pageSize) {
            // pageSize：每页多少条数据
            noticeStore.$patch((state) => {
                state.pageSize = pageSize
            })
            noticeStore.selectAllNotice(
                this.currentPage,
                parseInt(pageSize),
                this.search.title,
                this.search.type,
                this.search.startTime,
                this.search.endTime,
                this.search.userIdList
            )
        },
        handleCurrentChange(currentPage) {
            // currentPage：当前第几页
            noticeStore.$patch((state) => {
                state.currentPage = currentPage
            })
            noticeStore.selectAllNotice(
                parseInt(currentPage),
                this.pageSize,
                this.search.title,
                this.search.type,
                this.search.startTime,
                this.search.endTime,
                this.search.userIdList
            )
        },
        handleFilterChange(filters) {
            noticeStore.$patch((state) => {
                state.search.userIdList = filters.senderId
            })
            this.$emit('filterSender')
        },
        openClickMenu(row, column, event) {
            // 阻止元素发生默认的行为
            event.preventDefault()
            this.left = event.pageX
            this.top = event.pageY
            this.rightClickNotice = row
            this.rightClickVisible = true
        },
        async modifyTop(notice) {
            await noticeStore.modifyTop(notice)
            this.closeMenu()
            await noticeStore.selectAllNotice(this.currentPage, this.pageSize, '', '', '', '', [])
        },
        // 关闭菜单
        closeMenu() {
            this.rightClickVisible = false
        }
    },
    watch: {
        //   监听属性对象，newValue为新的值，也就是改变后的值
        rightClickVisible(newValue, oldValue) {
            if (newValue) {
                // 菜单显示的时候
                // 在body上添加事件处理程序
                document.body.addEventListener('click', this.closeMenu)
            } else {
                // 菜单隐藏的时候
                // 移除body上添加的事件处理程序
                document.body.removeEventListener('click', this.closeMenu)
            }
        }
    },
    mounted() {
        noticeStore.selectAllNotice(this.currentPage, this.pageSize, '', '', '', '', [])
    },
    updated() {
        this.$emit('getNoticeSender')
    }
}
</script>

<style scoped>
.el-table {
    margin-top: 10px;
}

.pagination {
    display: flex;
    margin: 20px 0;
    justify-content: center;
}
.contextmenu {
    background: #fff;
    z-index: 3000;
    position: fixed;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 16px;
    font-weight: 500;
    color: #333;
    border: 1px solid #dadadc;
}

.contextmenu li {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 7px 16px;
    cursor: pointer;
}

.contextmenu li:hover {
    background: #eee;
}
</style>
