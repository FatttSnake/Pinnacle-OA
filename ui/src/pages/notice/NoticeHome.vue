<template>
    <div class="notice-home-layout">
        <el-container>
            <el-header>
                <notice-head @selectByTitle="selectByTitle"></notice-head>
            </el-header>
            <el-main>
                <notice-table
                    :selectData="selectData"
                    @handleDelete="handleDelete"
                    @clearFilter="clearFilter"
                ></notice-table>
            </el-main>
        </el-container>
    </div>
</template>

<script lang="ts">
import axios from 'axios'

export default {
    name: 'NoticeHome',
    data() {
        return {
            selectData: []
        }
    },
    methods: {
        selectByTitle(searchTitle) {
            axios.get('http://localhost:8621/notice?title=' + searchTitle).then((response) => {
                this.selectData = response.data.data
            })
        },
        selectAllNotice() {
            axios.get('http://localhost:8621/notice').then((response) => {
                this.selectData = response.data.data
            })
        },
        handleDelete(deleteID) {
            axios.delete('http://localhost:8621/notice/' + deleteID).then((response) => {
                this.selectAllNotice()
            })
        },
        clearFilter() {
            this.selectAllNotice()
        }
    },
    mounted() {
        this.selectAllNotice()
    }
}
</script>

<style scoped>
.el-container {
}
.el-header {
    background-color: #fff;
    //border: #9e9e9e solid 1px;
}
.el-main {
    padding: 0px;
    margin-top: 20px;
}
</style>
