import { createRouter, createWebHistory, RouteLocationRaw } from 'vue-router';
import { routes } from './routes'
import { clearToken, getTokenInfo } from '@/util/sessionStorageUtil';

const routerHistory = createWebHistory();

const router = createRouter({
    history: routerHistory,
    routes: routes
})

router.beforeEach((to, from): RouteLocationRaw | void => {

    const tokenValue = getTokenInfo();

    if (!tokenValue && to.path !== '/login') {
        return {
            params: {
                msg: "登录凭证失效，请重新登录！"
            },
            path: "/login"
        }
    } else if (to.meta.title) {
        document.title = to.name?.toString() + "-后台管理系统";
    }
})

export default router