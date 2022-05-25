import { request } from "@/util/requestUtil";
import { AxiosPromise } from "axios";
import { DataResult, Page, SysUserVO } from "./model/vo";


export const UserRequest = {
    getMyUserInfo(): AxiosPromise<DataResult<SysUserVO>> {
        return request({
            url: "/user/get-my-user-info",
            method: "POST",
        })
    },
    page(data: {
        cur: number,
        id: number | null,
        username: string | null,
        roleName: string | null
    }): AxiosPromise<DataResult<Page<SysUserVO>>> {
        return request({
            url: "/user/page",
            method: "POST",
            data
        })
    },
    insert(data: {
        username: string,
        roleId: number
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/user/insert",
            method: "POST",
            data
        })
    },
    update(data: {
        id: number,
        username: string,
        roleId: number
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/user/update",
            method: "POST",
            data
        })
    }, 
    delete(data: {
        id: number,
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/user/delete",
            method: "POST",
            data
        })
    },
}