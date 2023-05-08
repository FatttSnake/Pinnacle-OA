import type { Captcha } from './common'
import { clearLocalStorage, getCaptcha, getLocalStorage, setLocalStorage } from './common'
import { TOKEN_NAME } from '@/constants/Common.constants'
import _ from 'lodash'
import request from '@/services'

let captcha: Captcha

async function login<T = any>(username: string, passwd: string): Promise<T> {
    return await request.post('/login', { username, passwd })
}

function logout(): void {
    void request.get('/logout').finally(() => {
        clearLocalStorage()
    })
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
