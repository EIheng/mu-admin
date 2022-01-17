import { createRouter, createWebHistory } from 'vue-router';
import routes from './routes'
import { session } from '@/util/sessionStorageUtil';
import { TokenInfo } from '@/api/loginAPI';

const routerHistory = createWebHistory();

const router = createRouter({
    history: routerHistory,
    routes: routes
})

router.beforeEach((to, from, next) => {

    if (to.meta.title) {
        document.title = to.meta.title as string
    }
    const tokenInfo: TokenInfo = session.get("tokenInfo");

    if (to.path === '/login' && !tokenInfo) {
        next();
    } else {
        if (!tokenInfo) {
            session.clear();
            next('/login');
        } else if (to.path === '/login' && tokenInfo) {
            next('/');
        } else {
            next();
        }
    }

})

export default router