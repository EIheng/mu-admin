import { RouteRecordRaw } from 'vue-router';

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: '登录',
        meta: { icon: "", disabled: false },
        component: () => import('@/view/Login.vue'),
    },
    {
        path: '/',
        name: '菜单',
        component: () => import('@/layout/index.vue'),
        redirect: '/home',
        children: [
            {
                path: '/home',
                name: '首页',
                meta: { icon: "element-home-filled", disabled: false },
                component: () => import('@/view/home.vue'),
            },
            {
                path: '/user',
                name: '用户管理',
                meta: { icon: "element-user", disabled: false },
                component: () => import('@/view/user.vue'),
            },
            {
                path: '/role',
                name: '权限管理',
                meta: { icon: "element-key", disabled: false },
                component: () => import('@/view/sub.vue'),
                redirect: "/role",
                children: [
                    {
                        path: '/role',
                        name: '用户角色',
                        meta: { icon: "element-avatar", disabled: false },
                        component: () => import('@/view/role.vue'),
                    },
                    {
                        path: '/permission',
                        name: '角色权限',
                        meta: { icon: "element-view", disabled: false },
                        component: () => import('@/view/permission.vue'),
                    },
                ]
            },
            {
                path: '/staff',
                name: '员工管理',
                meta: { icon: "element-briefcase", disabled: false },
                component: () => import('@/view/staff.vue'),
            },
            {
                path: '/order',
                name: '订单管理',
                meta: { icon: "element-list", disabled: false },
                component: () => import('@/view/order.vue'),
            },
        ],
    },
]


export {
    routes
};