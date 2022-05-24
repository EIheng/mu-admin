<script setup lang="ts">
import { reactive, ref } from "vue";

import { LocalUtil } from "@/util/sessionStorageUtil";
import { useRoute, useRouter } from "vue-router";
import { AuthRequest } from "@/api/authAPI";
import { ElMessage } from "element-plus";
import { UserFilled, Unlock } from "@element-plus/icons-vue";

const router = useRouter();
const route = useRoute();

if (typeof route.params['msg'] == "string") {
  ElMessage.warning(route.params['msg'])
}

const loginForm = {
  loading: ref(false),
  user: reactive({
    username: "",
    password: "",
  }),
  submitLogin() {
    if (!this.user.username || !this.user.password) {
      ElMessage.warning("请输入账号密码！")
      return
    }
    this.loading.value = true;

    AuthRequest.login(this.user).then(async res => {
      if (res.data.state) {
        LocalUtil.set("tokenInfo", res.data.data);
        setTimeout(() => {
          loginForm.loading.value = false
          router.push("/")
        }, 500)
      } else {
        setTimeout(() => {
          loginForm.loading.value = false
          ElMessage.error(res.data.msg)
          this.user.username = ""
          this.user.password = ""
        }, 2000)
      }
    }).catch(res => {
      setTimeout(() => {
        loginForm.loading.value = false
        ElMessage.error(res.response.data.msg)
        this.user.username = ""
        this.user.password = ""
      }, 2000)
    })
  }
}
</script>

<template>
  <div class="login-container">
    <el-row justify="center">
      <el-col :xs="20" :sm="4">
        <el-card class="login-content" v-loading="loginForm.loading.value">
          <h2>后台管理系统</h2>
          <el-form v-model="loginForm.user">
            <el-form-item label="账号" prop="username">
              <el-input v-model="loginForm.user.username" :prefix-icon="UserFilled" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input @keydown.enter="loginForm.submitLogin()" v-model="loginForm.user.password" type="password"
                :prefix-icon="Unlock" />
            </el-form-item>
            <div style="text-align: center;">
              <el-button @click="loginForm.submitLogin()" type="primary" :disabled="loginForm.loading.value">登录
              </el-button>
            </div>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.login-container {
  width: 100%;
  height: 100vh;
  background-color: #e5e5e5;

  .login-content {
    margin-top: 30vh;

    h2 {
      text-align: center;
      color: #303133;
    }
  }

  @media screen and (max-width: 768px) {
    .login-content {
      margin-top: 20vh;
    }
  }


}
</style>
