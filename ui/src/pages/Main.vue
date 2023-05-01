<template>
    <el-backtop target=".main-box" :right="80" :bottom="80" />
    <div class="background">
        <el-container class="fill">
            <el-aside width="collapse" class="background-white aside">
                <el-scrollbar>
                    <el-menu
                        :collapse="isCollapsed"
                        :unique-opened="true"
                        :default-active="$route.path"
                        :router="true"
                        class="menu"
                        :text-color="COLOR_FONT_MAIN()"
                        :active-text-color="COLOR_PRODUCTION()"
                    >
                        <el-menu-item
                            @mousedown.left="isCollapsed = !isCollapsed"
                            :disabled="true"
                            style="cursor: pointer; opacity: 1; border-bottom: 1px #ddd solid"
                        >
                            <el-icon :size="SIZE_ICON_LG()">
                                <icon-pinnacle-pinnacle :color="COLOR_PRODUCTION()" />
                            </el-icon>
                            <template #title>
                                <span class="menu-production-name">
                                    {{ PRODUCTION_NAME() }}
                                </span>
                            </template>
                        </el-menu-item>
                        <template v-for="(route, index) in routes">
                            <el-menu-item
                                v-if="!route.children"
                                :key="index"
                                :index="route.path ?? ''"
                            >
                                <el-icon>
                                    <component :is="route.meta.icon" />
                                </el-icon>
                                <template #title>{{ route.meta.title }}</template>
                            </el-menu-item>
                            <el-sub-menu
                                v-if="route.children"
                                :key="index"
                                :index="route.path ?? ''"
                            >
                                <template #title>
                                    <el-icon>
                                        <component :is="route.meta.icon" />
                                    </el-icon>
                                    <span>{{ route.meta.title }}</span>
                                </template>
                                <el-menu-item
                                    v-for="(sub, index) in route.children"
                                    :key="index"
                                    :index="sub.path ?? ''"
                                >
                                    <el-icon>
                                        <component :is="sub.meta.icon" />
                                    </el-icon>
                                    <template #title>{{ sub.meta.title }}</template>
                                </el-menu-item>
                            </el-sub-menu>
                        </template>
                    </el-menu>
                </el-scrollbar>
            </el-aside>
            <el-container>
                <el-header height="56px" class="background-white main-header">
                    <el-badge is-dot>
                        <el-icon
                            :size="SIZE_ICON_MD()"
                            :color="COLOR_PRODUCTION()"
                            style="cursor: pointer"
                        >
                            <icon-pinnacle-chat />
                        </el-icon>
                    </el-badge>
                    <el-badge is-dot>
                        <el-icon
                            :size="SIZE_ICON_MD()"
                            :color="COLOR_PRODUCTION()"
                            style="cursor: pointer"
                        >
                            <icon-pinnacle-notice />
                        </el-icon>
                    </el-badge>
                    <el-divider direction="vertical" />
                    <el-popover
                        transition="el-zoom-in-top"
                        popper-style="box-shadow: rgb(14 18 22 / 20%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px;"
                    >
                        <template #reference>
                            <div style="display: flex">
                                <div class="user-head">
                                    <el-avatar>
                                        <el-icon :size="SIZE_ICON_SM()" :color="COLOR_FONT_MAIN()">
                                            <icon-pinnacle-user />
                                        </el-icon>
                                    </el-avatar>
                                </div>
                                <div class="user-info">
                                    <div class="user-name">
                                        <span>用户名</span>
                                    </div>
                                    <div class="user-desc">
                                        <span>用户介绍</span>
                                    </div>
                                </div>
                            </div>
                        </template>
                        <template #default>
                            <div style="display: flex; gap: 10px; flex-direction: column">
                                <div>
                                    <el-button style="width: 100%">个人档案</el-button>
                                </div>
                                <div>
                                    <el-button style="width: 100%">退出</el-button>
                                </div>
                            </div>
                        </template>
                    </el-popover>
                </el-header>
                <ElScrollbar v-if="$route.meta.requiresScrollbar">
                    <ElMain class="main-box" :class="{ noPadding: !$route.meta.requiresPadding }">
                        <ElBacktop :right="100" :bottom="100" />
                        <RouterView></RouterView>
                    </ElMain>
                </ElScrollbar>
                <ElMain
                    v-else
                    class="main-box"
                    :class="{ noPadding: !$route.meta.requiresPadding }"
                >
                    <ElBacktop :right="100" :bottom="100" />
                    <RouterView></RouterView>
                </ElMain>
            </el-container>
        </el-container>
    </div>
</template>

<script lang="ts">
import {
    COLOR_FONT_MAIN,
    COLOR_PRODUCTION,
    PRODUCTION_NAME,
    SIZE_ICON_LG,
    SIZE_ICON_MD,
    SIZE_ICON_SM
} from '@/constants/Common.constants.js'
import _ from 'lodash'

export default {
    name: 'MainFrame',
    methods: {
        SIZE_ICON_LG() {
            return SIZE_ICON_LG
        },
        PRODUCTION_NAME() {
            return PRODUCTION_NAME
        },
        SIZE_ICON_SM() {
            return SIZE_ICON_SM
        },
        SIZE_ICON_MD() {
            return SIZE_ICON_MD
        },
        COLOR_PRODUCTION() {
            return COLOR_PRODUCTION
        },
        COLOR_FONT_MAIN() {
            return COLOR_FONT_MAIN
        }
    },
    data() {
        return {
            routes: _.filter(_.get(this.$router, 'options.routes[0].children'), 'meta.title'),
            isCollapsed: false
        }
    },
    mounted() {
        console.log(this.routes)
    }
}
</script>

<style scoped>
.background {
    width: 100vw;
    min-width: 900px;
    height: 100vh;
    min-height: 500px;
    background: var(--background-color);
}

.aside {
    border-right: 1px #ddd solid;
    box-shadow: 0 0 1em #ddd;
}

.menu:not(.el-menu--collapse) {
    width: 245px;
}

.menu-top > * {
    padding: 0;
}

.menu-production-name {
    width: 100%;
    padding: 0 10px;
    font-size: 1.2em;
    color: var(--main-color);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.main-header {
    display: flex;
    align-items: center;
    justify-content: right;
    border-bottom: 1px #ddd solid;
    box-shadow: 0 0 1em #ddd;
}

.main-header > *:not(:last-child) {
    margin-right: 15px;
}

.user-head {
    display: inline-block;
}

.user-head > span {
    font-size: 0.8em;
}

.user-info {
    margin-left: 10px;
}

.user-name {
    color: var(--main-color);
    font-size: 1.2em;
    font-weight: bold;
}

.user-desc {
    color: var(--font-secondary-color);
}

.noPadding {
    padding: 0;
}
</style>
