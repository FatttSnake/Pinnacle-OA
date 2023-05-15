import axios, { type AxiosError } from 'axios'
import { clearLocalStorage, getToken } from '@/utils/common'
import router from '@/router'
import { TOKEN_HAS_EXPIRED, TOKEN_IS_ILLEGAL, UNAUTHORIZED } from '@/constants/Common.constants'
import { ElMessage } from 'element-plus'

const service = axios.create({
    baseURL: 'http://localhost:8621',
    timeout: 10000,
    withCredentials: false
})

service.interceptors.request.use(
    (config) => {
        const token = getToken()
        if (token != null) {
            config.headers.set('token', token)
        }
        return config
    },
    async (error) => {
        return await Promise.reject(error)
    }
)

service.interceptors.response.use(
    (response) => {
        switch (response.data.code) {
            case UNAUTHORIZED:
            case TOKEN_IS_ILLEGAL:
            case TOKEN_HAS_EXPIRED:
                clearLocalStorage()
                ElMessage.error({
                    dangerouslyUseHTMLString: true,
                    message: '<strong>登录已过期</strong>'
                })
                setTimeout(function () {
                    router.go(0)
                }, 1500)
        }
        return response
    },
    async (error) => {
        if (error.response != null) {
            /* empty */
        }
        return await Promise.reject(error?.response?.data)
    }
)

const request = {
    async get<T = any>(url: string, data?: any): Promise<T> {
        return await request.request('GET', url, { params: data })
    },
    async post<T = any>(url: string, data?: any): Promise<T> {
        return await request.request('POST', url, { data })
    },
    async put<T = any>(url: string, data?: any): Promise<T> {
        return await request.request('PUT', url, { data })
    },
    async delete<T = any>(url: string, data?: any): Promise<T> {
        return await request.request('DELETE', url, { params: data })
    },
    async request<T = any>(method = 'GET', url: string, data?: any): Promise<T> {
        return await new Promise((resolve, reject) => {
            service({ method, url, ...data })
                .then((res) => {
                    resolve(res as unknown as Promise<T>)
                })
                .catch((e: Error | AxiosError) => {
                    reject(e)
                })
        })
    }
}

export default request
