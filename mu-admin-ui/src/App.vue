<script setup lang="ts">
import { useUserStore } from './store/user';

const userStore = useUserStore()
userStore.initData()
</script>

<template>
  <router-view></router-view>
</template>

<style>
body {
  margin: 0px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.el-row {
  margin-bottom: 8px;
}
</style>