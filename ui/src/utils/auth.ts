import type { Captcha } from './common'
import {
    getCaptcha,
    getLocalStorage,
    getToken,
    removeLocalStorage,
    setLocalStorage,
    setToken
} from './common'
import { TOKEN_NAME } from '@/constants/Common.constants'
import _ from 'lodash'
import request from '@/services'

let captcha: Captcha

async function login(username: string, passwd: string): Promise<boolean> {
    removeLocalStorage('username')
    await request.post('/login', { username, passwd }).then((res: any) => {
        const response = res.data
        if (response.code === 20010) {
            setToken(response.data.token)
        }
    })

    return !_.isEmpty(getToken())
}

function logout(): void {
    removeLocalStorage(TOKEN_NAME)
    removeLocalStorage('username')
}

function getLoginStatus(): boolean {
    return getLocalStorage(TOKEN_NAME) != null
}

async function getUsername(): Promise<string | null> {
    if (!_.isEmpty(getLocalStorage('username'))) {
        return getLocalStorage('username')
    }

    let username = ''

    await request.get('/userInfo').then((res) => {
        username = res.data.data.user.username
    })

    setLocalStorage('username', username)
    return username
}

function getCaptchaSrc(): string {
    captcha = getCaptcha(300, 150, 4)
    return captcha.base64Src
}

function verifyCaptcha(value: string): boolean {
    return captcha.value === value.replace(/\s*/g, '').toUpperCase()
}

export { login, logout, getLoginStatus, getUsername, getCaptchaSrc, verifyCaptcha }
