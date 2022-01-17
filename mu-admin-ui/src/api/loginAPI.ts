import { request } from "@/util/requestUtil";

import { AxiosPromise } from "axios";

export interface TokenInfo {
    tokenName: string;
    tokenValue: string
}

export interface LoginResult {
    loginState: boolean;
    info: string;
    tokenInfo: TokenInfo;
}

export function login(user: { username: string, password: string, }): AxiosPromise<LoginResult> {
    return request({
        url: "/api/login",
        method: "POST",
        data: user,
    })
}