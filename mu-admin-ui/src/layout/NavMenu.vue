<script setup lang="ts">
import {
  Location,
  Files,
  CoffeeCup,
  Document,
  PictureFilled
} from '@element-plus/icons-vue'
</script>

<template>
  <el-menu
    :default-active="$route.path"
    :router="true"
    active-text-color="#409EFF"
    background-color="#545c64"
    text-color="#fff"
  >
    <el-menu-item index="/">
      <el-icon>
        <location />
      </el-icon>
      <span>首页</span>
    </el-menu-item>
    <el-menu-item index="/file">
      <el-icon>
        <files />
      </el-icon>
      <span>文件管理</span>
    </el-menu-item>
    <el-menu-item index="/gallery">
<el-icon><picture-filled /></el-icon>
      <span>图片管理</span>
    </el-menu-item>
    <el-menu-item index="/api" disabled>
      <el-icon>
        <coffee-cup />
      </el-icon>
      <span>接口管理</span>
    </el-menu-item>
    <el-menu-item index="/help" disabled>
      <el-icon>
        <document />
      </el-icon>
      <span>接口文档</span>
    </el-menu-item>
  </el-menu>
</template>

<style lang="scss" scoped>
.el-icon {
  margin-right: 5px;
  width: 24px;
  text-align: center;
  font-size: 20px;
  top: -3px;
}

.el-menu {
  --el-menu-border-color: none;
}
</style>