<script setup lang="ts">
import MyHeader from "./my-header.vue";

</script>

<template>
  <el-container class="layout">
    <el-header>
      <my-header />
    </el-header>

    <el-divider style="margin: 0"></el-divider>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<style lang="scss" scoped>
.el-header {
  background-color: #545c64;
}

.layout {
  height: 100vh;
}
</style>