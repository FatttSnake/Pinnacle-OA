import type { Captcha } from './common'
import { clearLocalStorage, getCaptcha, getLocalStorage, getToken } from './common'
import { TOKEN_NAME } from '@/constants/Common.constants'
import request from '@/services'
import jwtDecode, { type JwtPayload } from 'jwt-decode'
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

function getUsername(): string {
    const token = getToken()

    if (token === null) {
        logout()
        return ''
    }

    const jwtPayload: JwtPayload = jwtDecode(token)
    const user = JSON.parse(jwtPayload.sub ?? '')
    return user.staff != null
        ? `${_.toString(user.staff.lastName)}${_.toString(user.staff.firstName)}`
        : user.username
}

function getCaptchaSrc(): string {
    captcha = getCaptcha(300, 150, 4)
    return captcha.base64Src
}

function verifyCaptcha(value: string): boolean {
    return captcha.value === value.replace(/\s*/g, '').toUpperCase()
}

export { login, logout, getLoginStatus, getUsername, getCaptchaSrc, verifyCaptcha }
