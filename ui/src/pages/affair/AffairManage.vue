<template>
    <el-container>
        <el-header>
            <el-col :span="24">
                <el-menu
                    :default-active="$route.path"
                    class="el-menu-demo"
                    mode="horizontal"
                    router
                    background-color="white"
                >
                    <el-menu-item index="/affair/manage/toApprove"> 待审批 </el-menu-item>
                    <el-menu-item index="/affair/manage/Approved"> 已审批 </el-menu-item>
                </el-menu>
            </el-col>
        </el-header>

        <el-main>
            <el-col :span="24">
                <el-form :model="formData">
                    <el-row :span="24">
                        <el-col :span="5">
                            <el-form-item label="事务标题:"
                                ><el-input v-model="formData.title"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="1"></el-col>
                        <el-col :span="4">
                            <el-form-item label="事务类型:"
                                ><el-select v-model="formData.typeId" clearable>
                                    <el-option label="事假" name="type1" value="1"></el-option>
                                    <el-option label="病假" name="type2" value="2"></el-option>
                                    <el-option label="财务报销" name="type3" value="3"></el-option>
                                    <el-option label="调休" name="type4" value="4"></el-option>
                                </el-select> </el-form-item
                        ></el-col>
                        <el-col :span="1"></el-col>
                        <!--                        <el-col :xs="6" :sm="6" :md="5" :lg="8" :xl="10">-->
                        <!--                            <el-form-item label="日期：" prop="timeRang">-->
                        <!--                                <el-date-picker-->
                        <!--                                    v-model="forTime"-->
                        <!--                                    type="datetimerange"-->
                        <!--                                    range-separator="至"-->
                        <!--                                    start-placeholder="开始日期"-->
                        <!--                                    end-placeholder="结束日期"-->
                        <!--                                    style="width: auto"-->
                        <!--                                >-->
                        <!--                                </el-date-picker>-->
                        <!--                            </el-form-item>-->
                        <!--                        </el-col>-->
                        <el-col :span="1"></el-col>
                        <el-col :span="3">
                            <el-button type="primary" @click="onSubmit">查询</el-button>
                            <el-button type="primary" @click="resetForm">重置</el-button>
                        </el-col>
                    </el-row>
                </el-form>
            </el-col>
            <router-view :DataToRouterView="DataToRouterView"></router-view>
        </el-main>
    </el-container>
</template>

<script lang="ts">
import request from '@/services'

export default {
    name: 'AffairManage',
    data() {
        return {
            formView: true,
            formData: {
                title: '',
                typeId: '',
                status: '',
                startTime: '',
                endTime: ''
            },
            DataToRouterView: {}
        }
    },
    methods: {
        turnFormViewFalse() {
            this.formView = false
        },
        turnFormViewTrue() {
            this.formView = true
        },
        onSubmit() {
            request
                .get('/affair/not_approved_FuzzyQueries', this.formData)
                .then((response) => {
                    this.DataToRouterView = response.data.data
                    console.log(response.data)
                })
                .catch((reportError) => {
                    console.log(reportError)
                })
        },
        resetForm() {
            this.$router.go()
        }
    }
}
</script>

<style scoped>
.el-menu--horizontal > .el-menu-item {
    border-bottom: none;
    text-decoration: none;
}
</style>
