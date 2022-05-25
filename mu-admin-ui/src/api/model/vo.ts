export interface DataResult<T> {
    state: boolean
    msg: string
    data: T
}

export interface Page<T> {
    records: T[]
    total: number
    size: number
    current: number
}

export interface TokenInfo {
    tokenName: string
    tokenValue: string
}

export interface SysUserVO {
    id: number
    username: string
    roleName: string
}

export interface RoleVO {
    id: number
    roleName: string
    roleNote: string
}

export interface PermissionVO {
    id: number
    permissionName: string
    permissionNote: string
}

export interface StaffInfo {
    id: number
    staffName: string
    duty: string
}

export interface OrderInfo {
    id: number
    orderName: string
    productNum: number
}