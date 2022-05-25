import { request } from "@/util/requestUtil";
import { AxiosPromise } from "axios";
import { DataResult, Page, StaffInfo } from "./model/vo";


export const StaffRequest = {
    page(data: {
        cur: number,
        id: number | null
        staffName: string
        duty: string
    }): AxiosPromise<DataResult<Page<StaffInfo>>> {
        return request({
            url: "/staff/page",
            method: "POST",
            data
        })
    },
    insert(data: {
        staffName: string,
        duty: string
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/staff/insert",
            method: "POST",
            data
        })
    },
    update(data: {
        id: number,
        staffName: string,
        duty: string
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/staff/update",
            method: "POST",
            data
        })
    },
    delete(data: { id: number }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/staff/delete",
            method: "POST",
            data
        })
    },
}