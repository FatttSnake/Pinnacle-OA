<template>
    <div class="myTimeline">
        <el-timeline>
            <el-timeline-item
                v-for="notice in selectData"
                :key="notice.id"
                :timestamp="formatDate(notice.sendTime)"
                placement="top"
            >
                <template #dot v-if="notice.isRead === 0">
                    <el-badge is-dot class="item" />
                </template>
                <el-card @click="showNoticeDetail(notice)">
                    <h4></h4>
                    <p>Tom committed 2018/4/12 20:46</p>
                </el-card>
            </el-timeline-item>
        </el-timeline>
        <!--        查看会话框-->
        <el-dialog v-model="dialogShowVisible" center>
            <template #header>
                <h2 style="color: red">公告详情</h2>
            </template>
            <notice-show-dialog />
        </el-dialog>
        <!--        <el-collapse-->
        <!--            @change="handleChange"-->
        <!--            v-loading="this.loading"-->
        <!--            element-loading-text="加载中..."-->
        <!--        >-->
        <!--            <el-collapse-item v-for="notice in selectData" :key="notice.id" :name="notice.id">-->
        <!--                <template #title>-->
        <!--                    <el-tag-->
        <!--                        size="small"-->
        <!--                        :type="-->
        <!--                            notice.noticeType.name === '通知公告'-->
        <!--                                ? 'warning'-->
        <!--                                : notice.noticeType.name === '紧急公告'-->
        <!--                                ? 'danger'-->
        <!--                                : 'success'-->
        <!--                        "-->
        <!--                        disable-transitions-->
        <!--                        style="margin-right: 30px"-->
        <!--                    >-->
        <!--                        {{ notice.noticeType.name }}-->
        <!--                    </el-tag>-->
        <!--                    <h3>{{ notice.title }}</h3>-->
        <!--                </template>-->
        <!--                <div>-->
        <!--                    <el-card class="box-card" shadow="always">-->
        <!--                        <template #header>-->
        <!--                            <div class="card-header">-->
        <!--                                <h4>优先级：</h4>-->
        <!--                                <el-tag effect="light" size="large">{{ notice.priority }}</el-tag>-->
        <!--                            </div>-->
        <!--                            <div class="card-header">-->
        <!--                                <h4>发布者：</h4>-->
        <!--                                <el-tag effect="light" size="large">{{-->
        <!--                                    notice.sender.username-->
        <!--                                }}</el-tag>-->
        <!--                            </div>-->
        <!--                        </template>-->
        <!--                        <h2 class="contentTitle">公告内容：</h2>-->
        <!--                        <div class="content">-->
        <!--                            {{ notice.content }}-->
        <!--                        </div>-->
        <!--                        <div class="date">-->
        <!--                            <div class="sendTime">-->
        <!--                                <h4>生效日期：</h4>-->
        <!--                                <el-tag effect="plain" size="large" type="success">{{-->
        <!--                                    formatDate(notice.sendTime)-->
        <!--                                }}</el-tag>-->
        <!--                            </div>-->
        <!--                            <div class="endTime">-->
        <!--                                <h4>失效日期：</h4>-->
        <!--                                <el-tag effect="plain" size="large" type="info">{{-->
        <!--                                    formatDate(notice.endTime)-->
        <!--                                }}</el-tag>-->
        <!--                            </div>-->
        <!--                        </div>-->
        <!--                    </el-card>-->
        <!--                </div>-->
        <!--            </el-collapse-item>-->
        <!--        </el-collapse>-->
    </div>
    <!--    <el-table-->
    <!--        v-loading="this.loading"-->
    <!--        element-loading-text="加载中..."-->
    <!--        ref="tableRef"-->
    <!--        :data="this.selectData"-->
    <!--        style="font-size: 18px"-->
    <!--        border-->
    <!--        highlight-current-row-->
    <!--        :header-cell-style="{-->
    <!--            background: 'darksalmon',-->
    <!--            'text-align': 'center',-->
    <!--            color: '#fff',-->
    <!--            'font-size': '20px'-->
    <!--        }"-->
    <!--    >-->
    <!--        <el-table-column-->
    <!--            prop="title"-->
    <!--            label="公告标题"-->
    <!--            width="230"-->
    <!--            :formatter="formatter"-->
    <!--            show-overflow-tooltip-->
    <!--            align="center"-->
    <!--        />-->
    <!--        <el-table-column prop="noticeType.name" label="公告类别" width="180" align="center">-->
    <!--            <template #default="scope">-->
    <!--                <el-tag-->
    <!--                    size="default"-->
    <!--                    :type="scope.row.noticeType.name === '通知公告' ? 'warning' : 'success'"-->
    <!--                    disable-transitions-->
    <!--                >-->
    <!--                    {{ scope.row.noticeType.name }}-->
    <!--                </el-tag>-->
    <!--            </template>-->
    <!--        </el-table-column>-->
    <!--        <el-table-column prop="priority" label="优先级" width="100" align="center" />-->
    <!--        <el-table-column prop="isRead" label="公告状态" width="180" align="center">-->
    <!--            <template #default="scope">-->
    <!--                <el-tag-->
    <!--                    size="large"-->
    <!--                    :type="scope.row.isRead === 0 ? 'danger' : 'success'"-->
    <!--                    disable-transitions-->
    <!--                >-->
    <!--                    {{ scope.row.isRead === 0 ? '未读' : '已读' }}-->
    <!--                </el-tag>-->
    <!--            </template>-->
    <!--        </el-table-column>-->
    <!--        <el-table-column-->
    <!--            prop="sendTime"-->
    <!--            label="生效时间"-->
    <!--            sortable-->
    <!--            width="250"-->
    <!--            :formatter="formatDate"-->
    <!--            align="center"-->
    <!--        />-->
    <!--        <el-table-column-->
    <!--            prop="endTime"-->
    <!--            label="失效时间"-->
    <!--            sortable-->
    <!--            width="250"-->
    <!--            :formatter="formatDate"-->
    <!--            align="center"-->
    <!--        />-->
    <!--        <el-table-column-->
    <!--            prop="sender.username"-->
    <!--            label="发布人"-->
    <!--            width="130"-->
    <!--            column-key="senderName"-->
    <!--            :filters="filterSenderName"-->
    <!--            :filter-method="filterTag"-->
    <!--            filter-placement="bottom-end"-->
    <!--            align="center"-->
    <!--        >-->
    <!--            <template #default="scope">-->
    <!--                <el-tag-->
    <!--                    :type="scope.row.sender.username === 'cyb' ? '' : 'success'"-->
    <!--                    disable-transitions-->
    <!--                    >{{ scope.row.sender.username }}-->
    <!--                </el-tag>-->
    <!--            </template>-->
    <!--        </el-table-column>-->
    <!--        <el-table-column label="操作" align="center">-->
    <!--            <template #default="scope">-->
    <!--                <el-button size="small" color="#626aef" @click="handleShow(scope.$index, scope.row)"-->
    <!--                    >查看-->
    <!--                </el-button>-->
    <!--                <el-button size="small" type="danger" @click="modifyStatus(scope.row)"-->
    <!--                    >标记为{{ scope.row.isRead === 0 ? '已读' : '未读' }}-->
    <!--                </el-button>-->
    <!--            </template>-->
    <!--        </el-table-column>-->
    <!--    </el-table>-->
    <!--    &lt;!&ndash;        查看会话框&ndash;&gt;-->
    <!--    <el-dialog v-model="dialogShowVisible" center>-->
    <!--        <template #header>-->
    <!--            <h2 style="color: red">查看公告</h2>-->
    <!--        </template>-->
    <!--        <notice-show-dialog />-->
    <!--    </el-dialog>-->
</template>

<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeStore } from '@/store/notice'
const noticeStore = useNoticeStore()

export default {
    data() {
        return {
            filterSenderName: []
        }
    },
    props: [],
    methods: {
        showNoticeDetail(data) {
            noticeStore.$patch((state) => {
                state.dialogShowVisible = true
                state.noticeShowData = data
            })
        },
        formatDate(date) {
            if (date == null) return null
            return new Date(date).toLocaleString()
        },
        filterTag(value, row) {
            return row.sender.username === value
        },
        modifyStatus(row) {
            console.log(row)
        },
        handleShow(index, row) {
            noticeStore.$patch((state) => {
                state.dialogShowVisible = true
                state.noticeShowData = row
            })
        }
    },
    mounted() {},
    updated() {},
    computed: {
        ...mapState(useNoticeStore, [
            'selectData',
            'loading',
            'dialogShowVisible',
            'noticeShowData'
        ])
    }
}
</script>

<style scoped>
.myTimeline {
    margin-top: 20px;
}
.el-timeline {
    --el-timeline-node-size-normal: 16px;
    --el-timeline-node-size-large: 16px;
}
/deep/ .el-badge__content.is-dot {
    height: 16px;
    width: 16px;
}
/deep/ .el-timeline-item__timestamp.is-top {
    font-size: 16px;
    font-weight: 600;
}
.el-collapse {
    --el-collapse-header-height: 68px;
}
/deep/ .el-collapse-item__header {
    padding-left: 25px;
    font-weight: 400;
    border-bottom: 2px solid #dcdfe6;
}
.box-card {
    font-size: 16px;
}
/deep/ .el-card__header {
    border-bottom: 1px dashed #6bd4ff;
}
.card-header {
    display: inline-block;
    margin-right: 40px;
}
.el-tag {
    font-size: 15px;
}
.contentTitle {
    font-size: 20px;
    font-weight: bold;
}
.content {
    margin-top: 20px;
    margin-left: 20px;
    height: fit-content;
}
.date {
    margin-top: 50px;
}
.sendTime {
    display: inline-block;
    margin-right: 25px;
}
.endTime {
    display: inline-block;
}
h3 {
    font-weight: 550;
    font-size: 18px;
}
h4 {
    font-weight: 500;
    display: inline-block;
}
</style>
