<script setup lang="ts">
import NavMenu from "@/layout/NavMenu.vue";
import Header from "./Header.vue";

</script>

<template>
  <el-container class="layout">
    <el-aside width="220px">
      <el-scrollbar>
        <NavMenu />
      </el-scrollbar>
    </el-aside>
    <el-container>
      <el-header style="height: 50px; height:50px; line-height:50px;">
        <Header />
      </el-header>

      <el-divider style="margin: 0"></el-divider>
      <el-scrollbar style="height: calc(100vh - 50px);">
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-scrollbar>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.el-aside {
  background-color: #545c64;
}

.layout {
  height: 100vh;
}
</style>