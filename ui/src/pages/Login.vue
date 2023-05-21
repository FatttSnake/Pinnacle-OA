<template>
    <div class="background" @keyup.enter="login">
        <div class="card-back">
            <div class="production-name">
                <span class="emphasize">{{ PRODUCTION_NAME() }}</span>
                <br />
                <span>自动化办公系统</span>
            </div>
            <img class="back-shape" :src="backShape" alt="back-shape" />
        </div>
        <form>
            <div class="card-front">
                <div class="login-title">登录</div>
                <div class="input-box user-name-box">
                    <div class="center-box" style="padding: 10px">
                        <el-icon size="18">
                            <icon-pinnacle-user />
                        </el-icon>
                    </div>
                    <label for="user-name"></label
                    ><input
                        type="text"
                        name="user-name"
                        id="user-name"
                        autocomplete="username"
                        v-model="userName"
                        @keyup="userName = userName.replace(/\s+/g, '')"
                        placeholder="用户名"
                    />
                </div>

                <div class="input-box password-box">
                    <div class="center-box" style="padding: 10px">
                        <el-icon size="18">
                            <icon-pinnacle-password />
                        </el-icon>
                    </div>
                    <label for="password"></label
                    ><input
                        type="password"
                        name="password"
                        id="password"
                        autocomplete="password"
                        v-model="password"
                        @keyup="password = password.replace(/\s+/g, '')"
                        placeholder="密码"
                    />
                </div>
                <div class="captcha-set">
                    <div class="captcha-box">
                        <div class="input-box" style="height: 100%">
                            <label for="captcha"></label
                            ><input
                                type="text"
                                name="captcha"
                                id="captcha"
                                v-model="captcha"
                                placeholder="验证码"
                            />
                        </div>
                    </div>
                    <img :src="captchaSrc" alt="Captcha" @click="getNewCaptcha" />
                </div>

                <ElButton
                    size="large"
                    type="primary"
                    :disabled="loggingIn"
                    id="login-bt"
                    @click="login"
                >
                    <template #default
                        ><span style="font-size: clamp(2em, 1.5vw, 2.8em)"
                            >登&ensp;&ensp;录</span
                        ></template
                    >
                </ElButton>
            </div>
        </form>
    </div>
</template>

<script lang="ts">
import { getCaptchaSrc, login, verifyCaptcha } from '@/utils/auth'
import backShape from '@/assets/svg/back-shape.svg'
import { ElMessage } from 'element-plus'
import {
    LOGIN_SUCCESS,
    LOGOUT_FAILED,
    PRODUCTION_NAME,
    USER_DISABLE
} from '@/constants/Common.constants'
import { setToken } from '@/utils/common'

export default {
    name: 'LoginPage',
    data() {
        return {
            backShape,
            captchaSrc: getCaptchaSrc(),
            userName: '',
            password: '',
            captcha: '',
            loggingIn: false
        }
    },
    methods: {
        PRODUCTION_NAME() {
            return PRODUCTION_NAME
        },
        getNewCaptcha() {
            this.captchaSrc = getCaptchaSrc()
        },
        resetLogin() {
            this.getNewCaptcha()
            this.loggingIn = false
        },
        login() {
            if (this.loggingIn) {
                return
            }
            this.loggingIn = true

            const _this = this
            if (!this.userName) {
                ElMessage.error({
                    dangerouslyUseHTMLString: true,
                    message: '<strong>用户名</strong> 为空'
                })
                this.loggingIn = false
                return
            }
            if (!this.password) {
                ElMessage.error({
                    dangerouslyUseHTMLString: true,
                    message: '<strong>密码</strong> 为空'
                })
                this.loggingIn = false
                return
            }
            if (!this.captcha) {
                ElMessage.error({
                    dangerouslyUseHTMLString: true,
                    message: '<strong>验证码</strong> 为空'
                })
                this.loggingIn = false
                return
            }
            if (!verifyCaptcha(this.captcha)) {
                ElMessage.error({
                    dangerouslyUseHTMLString: true,
                    message: '<strong>验证码</strong> 错误'
                })
                this.loggingIn = false
                return
            }
            login(this.userName, this.password).then((res) => {
                const data = res.data
                switch (data.code) {
                    case LOGIN_SUCCESS:
                        setToken(data.data.token)
                        ElMessage.success({
                            dangerouslyUseHTMLString: true,
                            message: '<strong>登录成功</strong>'
                        })
                        setTimeout(function () {
                            _this.$router.push('/')
                        }, 1500)
                        break
                    case LOGOUT_FAILED:
                        ElMessage.error({
                            dangerouslyUseHTMLString: true,
                            message: '<strong>用户名</strong> 或 <strong>密码</strong> 错误'
                        })
                        this.resetLogin()
                        break
                    case USER_DISABLE:
                        ElMessage.error({
                            dangerouslyUseHTMLString: true,
                            message: '<strong>该用户已被禁用</strong>，请联系管理员'
                        })
                        this.resetLogin()
                        break
                    default:
                        ElMessage.error({
                            dangerouslyUseHTMLString: true,
                            message: '<strong>服务器出错了</strong>，请稍后重试'
                        })
                        this.resetLogin()
                        break
                }
            })
        }
    }
}
</script>

<style scoped>
.background {
    height: 100vh;
    min-height: 500px;
    background: linear-gradient(to right, #5495f1, #82b5ff);
}

.card-back {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 65vw;
    min-width: 800px;
    height: 25vw;
    min-height: 350px;
    border-radius: 10px;
    background-color: rgba(255, 255, 255, 0.6);
    transform: translateX(-50%) translateY(-50%);
}

.production-name {
    position: absolute;
    left: 6%;
    top: 8%;
    font-size: clamp(2em, 2vw, 2.8em);
    color: var(--font-main-color);
}

.production-name .emphasize {
    font-size: 2em;
    font-weight: bold;
    color: var(--main-color);
}

.back-shape {
    position: absolute;
    height: 80%;
    left: 30%;
    top: 10%;
}

.card-front {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 25vw;
    min-width: 350px;
    height: 35vw;
    min-height: 500px;
    border-radius: 15px;
    background-color: white;
    transform: translateY(-50%);
}

.login-title {
    margin-top: 10%;
    margin-left: 10%;
    font-size: clamp(2.8em, 2.8vw, 4em);
    letter-spacing: 0.25em;
    font-weight: bold;
    color: var(--main-color);
}

.input-box {
    display: flex;
    flex-direction: row;
    background-color: #f0f0f0;
    border-radius: 5px;
    overflow: hidden;
}

.input-box input {
    flex: 1;
    font-size: 1.6em;
    background-color: transparent;
    padding-right: 10px;
    color: var(--font-main-color);
}

.user-name-box,
.password-box {
    margin: 10% auto -2% auto;
    height: 10%;
    width: 80%;
}

.captcha-set {
    display: flex;
    margin: 15% auto 12% auto;
    height: 10%;
    width: 80%;
}

.captcha-set img {
    height: 100%;
    cursor: pointer;
}

.captcha-box {
    flex: 1;
    padding-right: 10px;
    height: 100%;
}

.captcha-box input {
    padding: 0 10px;
    height: 100%;
    width: 100%;
}

#login-bt {
    display: block;
    margin: 0 auto;
    width: 80%;
    height: auto;
}
</style>
