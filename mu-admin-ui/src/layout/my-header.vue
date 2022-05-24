<script setup lang="ts">
import NavMenu from "@/layout/components/nav-menu.vue";
import { useRouter } from "vue-router"
import { AuthRequest } from "@/api/authAPI";

const router = useRouter();

function logout() {
  AuthRequest.logout();
  router.push("/login")
}
</script>

<template>
  <el-row style="height: 100%;">
    <el-col :span="12" class="flex-left">
      <nav-menu class="nav-menu" />
    </el-col>
    <el-col :span="12" class="flex-right">
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-icon class="setting">
            <element-setting />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="logout()">登出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
  </el-row>
</template>

<style scoped>
.nav-menu {
  width: 100%;
}

.nav-icon {
  font-size: 20px;
  margin-right: 10px;
}

.flex-left {
  display: flex;
  align-items: center;
  color: #606266;
  height: 100%;
}

.flex-right {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.setting {
  font-size: 20px;
  color: white;
}
</style>