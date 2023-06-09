<template>
    <div class="myTimeline" v-loading="showLoading" element-loading-text="加载中...">
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
                <el-card
                    @click="showNoticeDetail(notice)"
                    @contextmenu.prevent="openMenu($event, notice)"
                    class="noticeCard"
                >
                    <template #header>
                        <div class="top">
                            <el-icon
                                :size="SIZE_ICON_MD()"
                                :color="COLOR_TOP()"
                                v-if="notice.top === 1"
                            >
                                <icon-pinnacle-top />
                            </el-icon>
                            <el-icon :size="SIZE_ICON_MD()" v-if="notice.top === 0">
                                <icon-pinnacle-noticeItem />
                            </el-icon>
                            <el-tag
                                size="small"
                                :type="
                                    notice.noticeType.name === '通知公告'
                                        ? 'warning'
                                        : notice.noticeType.name === '紧急公告'
                                        ? 'danger'
                                        : 'success'
                                "
                                disable-transitions
                                style="margin-right: 20px; margin-left: 10px"
                            >
                                {{ notice.noticeType.name }}
                            </el-tag>
                            <h4>{{ notice.title }}</h4>
                            <el-icon class="senderIcon">
                                <icon-pinnacle-user />
                            </el-icon>
                            发布者：
                            <span class="sender">{{ notice.sender.username }}</span>
                        </div>
                    </template>
                    <p class="content">{{ contentSubstr(notice.content) }}</p>
                </el-card>
                <!-- 鼠标右击下拉菜单-->
                <ul
                    v-show="rightClickVisible"
                    :style="{ left: left + 'px', top: top + 'px' }"
                    class="contextmenu"
                >
                    <li
                        v-if="rightClickNotice.isRead === 0"
                        @click.stop="modifyStatus(rightClickNotice)"
                    >
                        <el-icon :size="SIZE_ICON_SM()">
                            <icon-pinnacle-flag />
                        </el-icon>
                        标为已读
                    </li>
                    <li
                        v-if="rightClickNotice.isRead === 1"
                        @click.stop="modifyStatus(rightClickNotice)"
                    >
                        <el-icon :size="SIZE_ICON_SM()">
                            <icon-pinnacle-flag />
                        </el-icon>
                        标为未读
                    </li>
                </ul>
            </el-timeline-item>
        </el-timeline>
        <!--        查看会话框-->
        <el-dialog
            v-model="dialogShowVisible"
            center
            :close-on-click-modal="false"
            :before-close="handleDialogClose"
        >
            <template #header>
                <h2 style="color: red">公告详情</h2>
            </template>
            <notice-show-dialog />
        </el-dialog>
        <!--        空状态-->
        <el-empty v-if="selectData.length === 0" :image-size="200" />
    </div>
</template>

<script lang="ts">
import { mapState } from 'pinia'
import { useNoticeStore } from '@/store/notice'
import { COLOR_TOP, SIZE_ICON_LG, SIZE_ICON_MD, SIZE_ICON_SM } from '@/constants/Common.constants'

const noticeStore = useNoticeStore()

export default {
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
    props: [],
    methods: {
        COLOR_TOP() {
            return COLOR_TOP
        },
        SIZE_ICON_LG() {
            return SIZE_ICON_LG
        },
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        SIZE_ICON_SM() {
            return SIZE_ICON_SM
        },
        showNoticeDetail(data) {
            noticeStore.$patch((state) => {
                state.dialogShowVisible = true
                state.noticeShowData = data
            })
        },
        contentSubstr(title) {
            if (title.length > 20) {
                return title.substring(0, 20) + ' ...'
            } else {
                return title
            }
        },
        formatDate(date) {
            if (date == null) return null
            return new Date(date).toLocaleString()
        },
        async modifyStatus(notice) {
            await noticeStore.modifyNoticeIsRead(notice)
            this.closeMenu()
            let flag = 0
            if (this.currentViewPage === 'All') {
                flag = -1
            } else if (this.currentViewPage === 'ToRead') {
                flag = 0
            } else if (this.currentViewPage === 'AlRead') {
                flag = 1
            }
            await noticeStore.selectAllNoticeSelf(flag, '', '', '', '')
        },
        handleDialogClose() {
            noticeStore.$patch((state) => {
                state.dialogEditVisible = false
                state.dialogAddVisible = false
                state.dialogShowVisible = false
                state.editFlag = false
                state.hackReset = false
            })
        },
        // 鼠标右击事件
        openMenu(e, notice) {
            this.left = e.pageX
            this.top = e.pageY
            this.rightClickNotice = notice
            this.rightClickVisible = true
        },
        // 关闭菜单
        closeMenu() {
            this.rightClickVisible = false
        }
    },
    mounted() {},
    updated() {},
    computed: {
        ...mapState(useNoticeStore, [
            'selectData',
            'loading',
            'dialogShowVisible',
            'noticeShowData',
            'currentViewPage',
            'showLoading'
        ])
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
    }
}
</script>

<style scoped>
.myTimeline {
    margin-top: 20px;
}

.el-timeline {
    font-size: 18px;
    --el-timeline-node-size-normal: 16px;
    --el-timeline-node-size-large: 16px;
}

:deep(.el-badge__content.is-dot) {
    height: 16px;
    width: 16px;
}

:deep(.el-timeline-item__timestamp.is-top) {
    font-size: 16px;
    font-weight: 600;
}

.noticeCard {
    --el-card-padding: 15px;
    height: 120px;
}

:deep(.el-card__header) {
    border-bottom: 2px solid #e4e7ed;
}

.el-tag {
    font-size: 13px;
}

.contentTitle {
    font-size: 20px;
    font-weight: bold;
}

.content {
    margin-left: 15px;
    height: fit-content;
}

h4 {
    font-weight: 600;
    display: inline-block;
}

.senderIcon {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: #cdd0d6;
    margin-left: 40px;
    margin-top: 2px;
}

.sender {
    display: inline-block;
    margin-top: -6px;
    width: fit-content;
    padding: 8px;
    height: fit-content;
    line-height: 14px;
    border-radius: 5px;
    border: 1px solid cadetblue;
    font-weight: 500;
    text-align: center;
}

.top {
    display: flex;
    align-items: center;
}

.check {
    margin-left: auto;
    margin-right: 20px;
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
