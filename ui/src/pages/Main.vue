<template>
    <el-backtop target=".main-box" :right="80" :bottom="80" />
    <el-scrollbar style="height: 100vh; width: 100vw">
        <div class="body">
            <div class="background">
                <el-container class="fill">
                    <el-aside width="collapse" class="background-white aside">
                        <el-scrollbar>
                            <el-menu
                                :collapse="isCollapsed"
                                :unique-opened="true"
                                :default-active="
                                    this.$route.path.indexOf(
                                        '/',
                                        this.$route.path.indexOf('/') + 1
                                    ) !== -1
                                        ? this.$route.path.indexOf(
                                              '/',
                                              this.$route.path.indexOf(
                                                  '/',
                                                  this.$route.path.indexOf('/') + 1
                                              ) + 1
                                          ) !== -1
                                            ? this.$route.path.substring(
                                                  0,
                                                  this.$route.path.indexOf(
                                                      '/',
                                                      this.$route.path.indexOf(
                                                          '/',
                                                          this.$route.path.indexOf('/') + 1
                                                      ) + 1
                                                  )
                                              )
                                            : this.$route.path
                                        : this.$route.path
                                "
                                :router="true"
                                class="menu"
                                :text-color="COLOR_FONT_MAIN()"
                                :active-text-color="COLOR_PRODUCTION()"
                            >
                                <el-menu-item
                                    @mousedown.left="changeCollapsed"
                                    :disabled="true"
                                    style="
                                        cursor: pointer;
                                        opacity: 1;
                                        border-bottom: 1px #ddd solid;
                                    "
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
                                            :index="
                                                sub.path
                                                    ? route.path
                                                        ? route.path + '/' + sub.path
                                                        : ''
                                                    : ''
                                            "
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
                                                <el-icon
                                                    :size="SIZE_ICON_SM()"
                                                    :color="COLOR_FONT_MAIN()"
                                                >
                                                    <icon-pinnacle-user />
                                                </el-icon>
                                            </el-avatar>
                                        </div>
                                        <div class="user-info">
                                            <div class="user-name">
                                                <span>{{ username }}</span>
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
                                            <el-button @click="profile" style="width: 100%"
                                                >个人档案</el-button
                                            >
                                        </div>
                                        <div>
                                            <el-button @click="logout" style="width: 100%"
                                                >退出</el-button
                                            >
                                        </div>
                                    </div>
                                </template>
                            </el-popover>
                        </el-header>
                        <ElScrollbar v-if="$route.meta.requiresScrollbar">
                            <ElMain
                                class="main-box"
                                :class="{ noPadding: !$route.meta.requiresPadding }"
                            >
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
        </div>
    </el-scrollbar>
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
import { getUser, getUsername, logout } from '@/utils/auth'
import { ElMessage } from 'element-plus'
import { getLocalStorage, setLocalStorage } from '@/utils/common'

export default {
    name: 'MainFrame',
    data() {
        return {
            routes: [],
            isCollapsed: getLocalStorage('menuCollapsed') === 'true',
            username: ''
        }
    },
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
        },
        logout() {
            logout()
            ElMessage.success({
                dangerouslyUseHTMLString: true,
                message: '<strong>退出登录</strong>'
            })
            setTimeout(() => {
                this.$router.push({ name: 'Login' })
            }, 1500)
        },
        changeCollapsed() {
            this.isCollapsed = !this.isCollapsed
            setLocalStorage('menuCollapsed', this.isCollapsed.toString())
        },
        profile() {
            this.$router.push('/profile')
        }
    },
    async mounted() {
        this.username = await getUsername()
        const allRoutes = _.cloneDeep(
            _.filter(_.get(this.$router, 'options.routes[0].children'), 'meta.requiresMenu')
        )

        const user = await getUser()
        const menus = user.menus
        this.routes = allRoutes.filter((level1) => {
            if (level1.meta.requiresAuth) {
                for (const menu of menus) {
                    if (_.startsWith(menu.url, level1.path)) {
                        let hasChildren = false
                        if (level1.children === undefined) {
                            return true
                        }
                        level1.children = level1.children.filter((level2) => {
                            if (!level2.meta.requiresMenu) {
                                return false
                            }
                            for (const menu_ of menus) {
                                if (_.startsWith(menu_.url, level1.path + '/' + level2.path)) {
                                    hasChildren = true
                                    return true
                                }
                            }
                            return false
                        })
                        return hasChildren
                    }
                }
                return false
            } else {
                let hasChildren = false
                if (level1.children === undefined) {
                    return true
                }
                level1.children = level1.children.filter((level2) => {
                    if (!level2.meta.requiresMenu) {
                        return false
                    }
                    if (!level2.meta.requiresAuth) {
                        hasChildren = true
                        return true
                    }
                    for (const menu_ of menus) {
                        if (_.startsWith(menu_.url, level1.path + '/' + level2.path)) {
                            hasChildren = true
                            return true
                        }
                    }
                    return false
                })
                return hasChildren
            }
        })
    }
}
</script>

<style scoped>
.background {
    height: 100vh;
    min-height: 500px;
    background: var(--background-color);
}

.aside {
    border-right: 1px #ddd solid;
    box-shadow: 0 0 1em #ddd;
}

.menu:not(.el-menu--collapse) {
    width: calc(10rem + 5vw);
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
