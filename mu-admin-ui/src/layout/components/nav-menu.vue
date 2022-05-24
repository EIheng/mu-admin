<script setup lang="ts">
import { routes } from '@/router/routes';


const navRoutes = routes.find(el => {
  return el.path == "/"
})?.children;

</script>

<template>
  <el-menu mode="horizontal" active-text-color="#ffd04b" background-color="#545c64" text-color="#fff" :default-active="$route.path"
    :router="true">
    <template v-for="item in navRoutes" :key="item.path">
      <template v-if="item.children && item.children.length > 0">
        <el-sub-menu :index="item.path" :disabled="item.meta?.disabled">
          <template #title>
            <el-icon>
              <component :is="item.meta?.icon"></component>
            </el-icon>
            <span>{{ item.name }}</span>
          </template>
          <template v-for="itemChildren in item.children" :key="itemChildren.path">
            <el-menu-item :index="itemChildren.path" :disabled="itemChildren.meta?.disabled">
              <el-icon>
                <component :is="itemChildren.meta?.icon"></component>
              </el-icon>
              <span>{{ itemChildren.name }}</span>
            </el-menu-item>
          </template>
        </el-sub-menu>
      </template>
      <template v-else>
        <el-menu-item :index="item.path" :disabled="item.meta?.disabled">
          <el-icon>
            <component :is="item.meta?.icon"></component>
          </el-icon>
          <span>{{ item.name }}</span>
        </el-menu-item>
      </template>
    </template>
  </el-menu>
</template>

<style lang="scss" scoped>
.el-menu {
  --el-menu-border-color: none;
}

.el-menu-item {
  font-size: 14px;
}
</style>