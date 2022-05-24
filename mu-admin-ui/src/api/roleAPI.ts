import { request } from "@/util/requestUtil";
import { AxiosPromise } from "axios";
import { DataResult, Page, RoleVO } from "./model/vo";


export const RoleRequest = {
    page(data: { cur: number }): AxiosPromise<DataResult<Page<RoleVO>>> {
        return request({
            url: "/role/page",
            method: "POST",
            data
        })
    },
}