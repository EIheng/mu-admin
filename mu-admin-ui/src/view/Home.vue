<script setup lang="ts">
</script>

<template>
  <el-row :gutter="10">
    <el-col :span="6">
      <transition appear name="el-zoom-in-top">
        <el-card shadow="hover">
          <p>权限管理</p>
        </el-card>
      </transition>
    </el-col>
    <el-col :span="6">
      <transition appear name="el-zoom-in-top">
        <el-card shadow="hover">
          <p>员工管理</p>
        </el-card>
      </transition>
    </el-col>
    <el-col :span="6">
      <transition appear name="el-zoom-in-top">
        <el-card shadow="hover">
          <p>订单管理</p>
        </el-card>
      </transition>
    </el-col>
    <el-col :span="6">
      <transition appear name="el-zoom-in-top">
        <el-card shadow="hover">
          <p>一些有的没的的数据</p>
        </el-card>
      </transition>
    </el-col>
  </el-row>
</template>

<style scoped>
</style>