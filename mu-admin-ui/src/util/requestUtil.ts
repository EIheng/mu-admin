import { AuthRequest } from '@/api/authAPI';
import router from '@/router';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { getTokenInfo } from './sessionStorageUtil';

const headers: any = {
    'Content-Type': "application/json;charset=utf-8",
}

let baseURL: string;
if (import.meta.env.DEV) {
    baseURL = "http://localhost:9908"
} else {
    baseURL = "http://chat.mudongheng.com:12358"
}

const request = axios.create({
    baseURL,
    timeout: 50000,
    headers,
});

request.interceptors.request.use((config) => {
    const tokenInfo = getTokenInfo();
    if (tokenInfo) {
        headers[tokenInfo.tokenName] = tokenInfo.tokenValue;
    }
    config.headers = headers;
    return config;
})

request.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    if (error.response) {
        // 请求成功，但服务器失败，需要手动处理异常
        // 认证造成的异常
        if (error.response.status == 401) {
            console.log("有人认证失败...")
            router.push({ name: "登录", params: { msg: error.response.data.msg } })
        }
    } else if (error.request) {
        // 请求已经成功发起，但没有收到响应
        // `error.request` 在浏览器中是 XMLHttpRequest 的实例，
        // 而在node.js中是 http.ClientRequest 的实例
        console.log("服务器未响应！")
        ElMessage.error("服务器未响应！")
    } else {
        console.log("请求出了点问题...")
        ElMessage.error("请求出了点问题...")

    }
    return Promise.reject(error)
})

export { request, baseURL }