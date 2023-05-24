import type { Captcha } from './common'
import { clearLocalStorage, getCaptcha, getLocalStorage, setLocalStorage } from './common'
import { DATABASE_SELECT_OK, TOKEN_NAME } from '@/constants/Common.constants'
import request from '@/services'
import _ from 'lodash'

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

async function getUser(): Promise<any> {
    if (getLocalStorage('userInfo') !== null) {
        return JSON.parse(getLocalStorage('userInfo') as string)
    }
    return await requestUser()
}

async function requestUser(): Promise<any> {
    let user
    await request.get('/user/info').then((res) => {
        const response = res.data
        if (response.code === DATABASE_SELECT_OK) {
            user = response.data
            setLocalStorage('userInfo', JSON.stringify(user))
        }
    })
    return user
}

async function getUsername(): Promise<string> {
    const user = await getUser()

    return user.staff != null
        ? `${_.toString(user.staff.lastName)}${_.toString(user.staff.firstName)}`
        : user.username
}

function getCaptchaSrc(): string {
    captcha = getCaptcha(300, 150, 4)
    return captcha.base64Src
}

function verifyCaptcha(value: string): boolean {
    return captcha.value.toLowerCase() === value.replace(/\s*/g, '').toLowerCase()
}

export { login, logout, getLoginStatus, getUser, getUsername, getCaptchaSrc, verifyCaptcha }
