import { request } from "@/util/requestUtil";
import { AxiosPromise } from "axios";

/**
 * 文件视图
 */
export interface FileInfo {
    fileName: string;
    path: string;
    directory: boolean;
    length: number;
    lastUpdateTime: string;
}

export interface FileListResVO {
    curFileInfo: FileInfo;
    fileInfoList: FileInfo[];
    fileInfoPathList: FileInfo[];
}

export function hello(): AxiosPromise<string> {
    return request({
        url: "/api/file/hello",
        method: "GET"
    })
}

export function deleteFile(path: string): AxiosPromise<boolean> {
    return request({
        url: "/api/file/delete-file",
        method: "POST",
        params: {
            path
        }
    })
}

export function getDirectoryInfo(path: string): AxiosPromise<FileListResVO> {
    return request({
        url: "/api/file/get-directory-info",
        method: "POST",
        params: {
            path
        }
    })
}

export function download(path: string): AxiosPromise<any> {
    return request({
        url: "/api/file/download",
        method: "POST",
        params: {
            path
        },
        responseType: "blob",
    })
}

export function upload(file: any, path: string): AxiosPromise<any> {
    let formData = new FormData();
    formData.append("file", file);
    formData.append("path", path);
    return request({
        url: "/api/file/upload",
        method: "POST",
        params: formData
    })
}

export function createFile(path: string, fileName: string, directory: boolean): AxiosPromise<boolean> {
    return request({
        url: "/api/file/create-file",
        method: "POST",
        params: {
            path,
            fileName,
            directory
        },
    })
}