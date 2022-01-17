import { RouteRecordRaw } from 'vue-router';


const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        meta: { title: "登录-后台管理系统" },
        component: () => import('@/view/Login.vue'),
    },
    {
        path: '/',
        name: '',
        component: () => import('@/layout/Main.vue'),
        children: [
            {
                path: '/',
                name: '首页',
                meta: { title: "首页-后台管理系统" },
                component: () => import('@/view/Home.vue'),
            },
            {
                path: '/file',
                name: '文件管理',
                meta: { title: "文件管理-后台管理系统" },
                component: () => import('@/view/FileManage.vue'),
            },
            {
                path: '/gallery',
                name: '图片管理',
                meta: { title: "画廊-后台管理系统" },
                component: () => import('@/view/Gallery.vue'),
            },
        ],
    },
]

export default routes;