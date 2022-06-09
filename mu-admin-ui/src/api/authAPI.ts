import { request } from "@/util/requestUtil";
import type { AxiosPromise } from "axios";
import type { DataResult, TokenInfo } from "./model/vo";


export const AuthRequest = {
    login(data: { username: string, password: string }): AxiosPromise<DataResult<TokenInfo>> {
        return request({
            url: "/auth/login",
            method: "POST",
            data
        })
    },
    isLogin(): AxiosPromise<DataResult<boolean>> {
        return request({
            url: "/auth/is-login",
            method: "POST",
        })
    },
    logout(): AxiosPromise<DataResult<boolean>> {
        return request({
            url: "/auth/logout",
            method: "POST",
        })
    },
}