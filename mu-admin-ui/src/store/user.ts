import { AuthRequest } from '@/api/authAPI';
import { UserRequest } from '@/api/userAPI';
import router from '@/router';
import { defineStore } from 'pinia'

interface StateType {
    id: number
    username: string
    roleName: string
}

export const useUserStore = defineStore('user', {
    state: (): StateType => ({
        id: -1,
        username: "",
        roleName: "",
    }),
    actions: {
        async initData() {
            const loginState = await AuthRequest.isLogin().then(res => {
                console.log("登录认证中...")
                if (res.data.data) {
                    return true;
                } else {
                    router.push("/login")
                    return false;
                }
            })
            if (!loginState) {
                router.push("/login")
                return;
            }
            this.initUserInfo()
        },
        async initUserInfo() {
            UserRequest.getMyUserInfo().then(res => {
                if (res.data.state) {
                    let data = res.data.data
                    this.id = data.id;
                    this.username = data.username
                    this.roleName = data.roleName
                }
            })
        }
    },
})