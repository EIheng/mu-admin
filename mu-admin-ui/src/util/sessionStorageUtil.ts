import { TokenInfo } from "@/api/model/vo";

/**
 * window.localStorage 浏览器永久缓存
 * @method set 设置永久缓存
 * @method get 获取永久缓存
 * @method remove 移除永久缓存
 * @method clear 移除全部永久缓存
 */
export const LocalUtil = {
	// 设置永久缓存
	set(key: string, val: any) {
		window.localStorage.setItem(key, JSON.stringify(val));
	},
	// 获取永久缓存
	get(key: string) {
		const json: any = window.localStorage.getItem(key);
		return JSON.parse(json);
	},
	// 移除永久缓存
	remove(key: string) {
		window.localStorage.removeItem(key);
	},
	// 移除全部永久缓存
	clear() {
		window.localStorage.clear();
	},
};

/**
 * window.sessionStorage 浏览器临时缓存
 * @method set 设置临时缓存
 * @method get 获取临时缓存
 * @method remove 移除临时缓存
 * @method clear 移除全部临时缓存
 */
export const SessionUtil = {
	// 设置临时缓存
	set(key: string, val: any) {
		window.sessionStorage.setItem(key, JSON.stringify(val));
	},
	// 获取临时缓存
	get(key: string) {
		const json: any = window.sessionStorage.getItem(key);
		return JSON.parse(json);
	},
	// 移除临时缓存
	remove(key: string) {
		window.sessionStorage.removeItem(key);
	},
	// 移除全部临时缓存
	clear() {
		window.sessionStorage.clear();
	},
};

/**
 * 获取token信息
 * @returns token信息，可能为null
 */
export function getTokenInfo(): TokenInfo {
	try {
		return LocalUtil.get("tokenInfo");
	} catch (error) {
		return { tokenName: "token", tokenValue: "" };
	}
}

export function clearToken() {
	LocalUtil.remove("token")
}