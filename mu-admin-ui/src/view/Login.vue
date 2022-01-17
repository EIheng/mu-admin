<script setup lang="ts">
import { reactive, ref } from "vue";
import { UserFilled, Unlock } from "@element-plus/icons-vue"

import { session } from "@/util/sessionStorageUtil";
import { useRouter } from "vue-router";
import { useStore } from "@/store";
import { login } from "@/api/loginAPI";


const router = useRouter();
const store = useStore();

const user = reactive({
  username: "",
  password: "",
});
const msg = ref("");

function submitLogin() {
  login(user).then(res => {
    const resData = res.data;
    if (resData.loginState) {
      store.state.user.username = user.username;
      session.set("tokenInfo", resData.tokenInfo);
      router.push("/");
    } else {
      msg.value = resData.info;
    }
  })
}

</script>

<template>
  <div class="login-container">
    <div class="login-logo">
      <span>幕冬</span>
    </div>
    <div class="login-content">
      <div class="login-content-main">
        <h4 class="login-content-title">后台模板</h4>
        <el-form v-model="user" style="height: 80%; margin-top: 100px;">
          <el-form-item label="账号" prop="username">
            <el-input v-model="user.username">
              <template #prefix>
                <el-icon class="el-input__icon">
                  <user-filled />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="user.password" type="password">
              <template #prefix>
                <el-icon class="el-input__icon">
                  <unlock />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <div style="text-align: center; color: #F56C6C; height: 30px;">{{ msg }}</div>

          <el-form-item style="text-align: center;">
            <el-button @click="submitLogin()" type="primary">登录</el-button>
          </el-form-item>
        </el-form>

        <div class="mt10">
          <el-button type="text" size="small">找回密码</el-button>
          <el-button type="text" size="small">注册</el-button>
        </div>
        <div class="login-content-main-sacn">
          <i class="iconfont"></i>
          <div class="login-content-main-sacn-delta"></div>
        </div>
      </div>
    </div>
    <div class="login-copyright">
      <div class="mb5 login-copyright-company">Copyright</div>
      <div class="login-copyright-msg">域名备案</div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.login-container {
  width: 100%;
  height: 100vh;
  background: url("@/assets/bg-login.png");
  background-size: 100% 100%;
  .login-logo {
    position: absolute;
    top: 30px;
    left: 50%;
    height: 50px;
    display: flex;
    align-items: center;
    font-size: 20px;
    color: var(--el-color-primary);
    letter-spacing: 2px;
    width: 90%;
    transform: translateX(-50%);
  }
  .login-content {
    width: 500px;
    padding: 20px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%) translate3d(0, 0, 0);
    background-color: rgba(255, 255, 255, 0.99);
    border: 5px solid var(--el-color-primary-light-8);
    border-radius: 4px;
    transition: height 0.2s linear;
    height: 480px;
    overflow: hidden;
    z-index: 1;
    .login-content-main {
      margin: 0 auto;
      width: 80%;
      .login-content-title {
        color: #333;
        font-weight: 500;
        font-size: 22px;
        text-align: center;
        letter-spacing: 4px;
        margin: 15px 0 30px;
        white-space: nowrap;
        z-index: 5;
        position: relative;
        transition: all 0.3s ease;
      }
    }
    .login-content-main-sacn {
      position: absolute;
      top: 0;
      right: 0;
      width: 50px;
      height: 50px;
      overflow: hidden;
      cursor: pointer;
      transition: all ease 0.3s;
      &-delta {
        position: absolute;
        width: 35px;
        height: 70px;
        z-index: 2;
        top: 2px;
        right: 21px;
        background: var(--el-color-white);
        transform: rotate(-45deg);
      }
      &:hover {
        opacity: 1;
        transition: all ease 0.3s;
        color: var(--el-color-primary);
      }
      i {
        width: 47px;
        height: 50px;
        display: inline-block;
        font-size: 48px;
        position: absolute;
        right: 2px;
        top: -1px;
      }
    }
  }
  .login-copyright {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    bottom: 30px;
    text-align: center;
    color: var(--el-bg-color-base);
    font-size: 12px;
    opacity: 0.8;
    .login-copyright-company {
      white-space: nowrap;
    }
    .login-copyright-msg {
      @extend .login-copyright-company;
    }
  }
}
</style>
