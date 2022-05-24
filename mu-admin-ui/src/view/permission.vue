<script setup lang="ts">
import { Page, PermissionVO, SysUserVO } from '@/api/model/vo';
import { PermissionRequest } from '@/api/permissionAPI';
import { UserRequest } from '@/api/userAPI';
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';

const updateDialogVisible = ref(false)

const queryWrapper = reactive<{
  id: number | null,
  username: string
  roleName: string
}>({
  id: null,
  username: "",
  roleName: "",
})

const page = reactive<Page<PermissionVO>>({
  records: [],
  total: 0,
  size: 0,
  current: 1
})

const onSubmit = (cur: number = page.current) => {
  PermissionRequest.page({
    cur,
  }).then(res => {
    if (res.data.state) {
      const data = res.data.data
      page.records = data.records
      page.total = data.total
      page.size = data.size
      page.current = data.current
    } else {
      ElMessage.warning(res.data.msg)
    }
  })
}
onSubmit()
</script>

<template>

  <el-dialog v-model="updateDialogVisible" title="修改用户">
    <el-form label-width="120px">
      <el-form-item label="修改角色">
        <el-input />
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-row :gutter="10">
    <el-col :span="24">
      <transition appear name="el-zoom-in-top">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>权限管理</span>
              <el-button class="button" @click="updateDialogVisible = true">创建</el-button>
            </div>
          </template>

          <el-form :model="queryWrapper" :inline="true">
            <el-form-item label="id">
              <el-input v-model="queryWrapper.id" />
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="queryWrapper.username" />
            </el-form-item>
            <el-form-item label="角色">
              <el-input v-model="queryWrapper.roleName" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit()">查询</el-button>
            </el-form-item>
          </el-form>

          <el-table :data="page.records">
            <el-table-column prop="id" label="id" />
            <el-table-column prop="permissionName" label="权限名称" />
            <el-table-column prop="permissionNote" label="" />
            <el-table-column label="操作">
              <template #default="scope">
                <el-button size="small">编辑</el-button>
                <el-button type="danger" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination style="justify-content: center;" layout="prev, pager, next" @current-change="onSubmit"
            :total="page.total" />
            
        </el-card>
      </transition>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>