import { request } from "@/util/requestUtil";
import { AxiosPromise } from "axios";
import { DataResult, Page, PermissionVO } from "./model/vo";


export const PermissionRequest = {
    page(data: { cur: number }): AxiosPromise<DataResult<Page<PermissionVO>>> {
        return request({
            url: "/permission/page",
            method: "POST",
            data
        })
    },
}