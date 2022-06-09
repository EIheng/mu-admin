import { request } from "@/util/requestUtil";
import type { AxiosPromise } from "axios";
import type { DataResult, Page, PermissionVO } from "./model/vo";


export const PermissionRequest = {
    list(): AxiosPromise<DataResult<PermissionVO[]>> {
        return request({
            url: "/permission/list",
            method: "POST"
        })
    },
    page(data: {
        cur: number,
        id: number | null,
        permissionName: string,
        permissionNote: string
    }): AxiosPromise<DataResult<Page<PermissionVO>>> {
        return request({
            url: "/permission/page",
            method: "POST",
            data
        })
    },
    insert(data: {
        permissionName: string,
        permissionNote: string
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/permission/insert",
            method: "POST",
            data
        })
    },
    update(data: {
        id: number,
        permissionName: string,
        permissionNote: string
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/permission/update",
            method: "POST",
            data
        })
    },
    delete(data: { id: number }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/permission/delete",
            method: "POST",
            data
        })
    },
}