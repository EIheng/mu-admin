import { request } from "@/util/requestUtil";
import type { AxiosPromise } from "axios";
import type { DataResult, Page, RoleVO } from "./model/vo";


export const RoleRequest = {
    list(): AxiosPromise<DataResult<RoleVO[]>> {
        return request({
            url: "/role/list",
            method: "POST"
        })
    },
    page(data: {
        cur: number,
        id: number | null,
        roleName: string,
        roleNote: string
    }): AxiosPromise<DataResult<Page<RoleVO>>> {
        return request({
            url: "/role/page",
            method: "POST",
            data
        })
    },
    listRolePermissionById(data: { id: number }): AxiosPromise<DataResult<RoleVO[]>> {
        return request({
            url: "/role/list-role-permission-by-id",
            method: "POST",
            data
        })
    },
    insert(data: {
        roleName: string,
        roleNote: string
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/role/insert",
            method: "POST",
            data
        })
    },
    update(data: {
        id: number,
        roleName: string,
        roleNote: string
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/role/update",
            method: "POST",
            data
        })
    },
    updatePermission(data: {
        roleId: number,
        permissionIdList: number[]
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/role/update-permission",
            method: "POST",
            data
        })
    },
    delete(data: { id: number }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/role/delete",
            method: "POST",
            data
        })
    },
}