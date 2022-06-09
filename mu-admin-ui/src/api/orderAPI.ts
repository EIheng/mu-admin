import { request } from "@/util/requestUtil";
import type { AxiosPromise } from "axios";
import type { DataResult, Page, OrderInfo } from "./model/vo";


export const OrderRequest = {
    page(data: {
        cur: number
        id: number | null
        orderName: string
    }): AxiosPromise<DataResult<Page<OrderInfo>>> {
        return request({
            url: "/order/page",
            method: "POST",
            data
        })
    },
    insert(data: {
        orderName: string
        productNum: number
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/order/insert",
            method: "POST",
            data
        })
    },
    update(data: {
        id: number
        orderName: string
        productNum: number
    }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/order/update",
            method: "POST",
            data
        })
    },
    delete(data: { id: number }): AxiosPromise<DataResult<null>> {
        return request({
            url: "/order/delete",
            method: "POST",
            data
        })
    },
}