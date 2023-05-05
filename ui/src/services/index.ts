import axios, { type AxiosError } from 'axios'
import { getToken, removeToken } from '@/utils/common'
import router from '@/router'

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
        return response
    },
    async (error) => {
        if (error.response != null) {
            // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
            console.log(`request error: ${error.response.code} - ${error.response.msg}`)
            switch (error.response.code) {
                case 30010:
                    removeToken()
                    await router.push({ name: 'Login' })
            }
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
