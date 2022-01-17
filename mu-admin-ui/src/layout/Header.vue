<script setup lang="ts">
import { Setting } from "@element-plus/icons-vue";
import { session } from "@/util/sessionStorageUtil";

import { useRouter } from "vue-router"
import { useStore } from "@/store";

const router = useRouter();

const store = useStore();

function logout() {
  session.clear();
  router.go(0)
}
</script>

<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <span style="color: #303133; font-size: 14px;">
        <b>首页</b>
        <span v-if="$route.name !== 'home'">
          <span style="color: #606266">{{ " / " }}</span>
          {{ $route.name?.toString() }}
        </span>
      </span>
    </el-col>
    <el-col :span="14"></el-col>
    <el-col :span="2">
      <div class="flex-right">{{ store.state.user.username }}</div>
    </el-col>
    <el-col :span="2">
      <div class="flex-right">
        <el-dropdown>
          <span class="el-dropdown-link">
            <el-icon class="setting-size">
              <setting />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="logout()">登出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped>
.flex-right {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 100%;
}

.setting-size {
  font-size: 20px;
}
</style>