import router from '@/router';
import axios from 'axios';
import { session } from './sessionStorageUtil';

const headers: any = {
    'Content-Type': "application/json;charset=utf-8",
}

const request = axios.create({
    baseURL: "http://localhost:9908/",
    timeout: 5000,
    headers,
});

export function getHeadersWithToken() {
    const tokenInfo = session.get("tokenInfo");
    if (tokenInfo == null) {
        return headers;
    }
    headers[tokenInfo.tokenName] = tokenInfo.tokenValue;
    return headers;
}

request.interceptors.request.use((config) => {
    const tokenInfo = session.get("tokenInfo");
    if (tokenInfo == null) {
        return config;
    }
    headers[tokenInfo.tokenName] = tokenInfo.tokenValue;
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
    session.clear();
    router.push("/login");

    return Promise.reject(error);
});

export {
    request
};