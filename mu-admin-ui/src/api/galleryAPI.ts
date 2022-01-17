import { request } from "@/util/requestUtil";
import { AxiosPromise } from "axios";

/**
 * 文件视图
 */
export interface PictureInfo {
    name: string;
    showName: string;
    path: string;
    url: string;
}

export function listPictureInfo(): AxiosPromise<PictureInfo[]> {
    return request({
        url: "/api/gallery/list-picture-info",
        method: "POST"
    })
}

export function getImage(path: string): AxiosPromise<any> {
    return request({
        url: "/api/gallery/get-image",
        method: "POST",
        responseType: 'blob',
        params: { path },
    })
}