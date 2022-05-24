<script setup lang="ts">
import { Page, RoleVO } from '@/api/model/vo';
import { RoleRequest } from '@/api/roleAPI';
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';

const page = reactive<Page<RoleVO>>({
  records: [],
  total: 0,
  size: 0,
  current: 1
})

const updateDialogVisible = ref(false)

const onSubmit = (cur: number = page.current) => {
  RoleRequest.page({
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

const showPermissionList = (data: string[]): string => {
  let l = data.length > 5 ? 5 : data.length
  let res = ""
  let count = 0
  while (count < l) {
    res += data[count] + "、"
    count++
  }

  if (res.length == 0) {
    return "没有任何权限..."
  }
  res = res.substring(0, res.length - 1)
  if (data.length > 5) {
    res += " 等权限..."
  }
  return res
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
              <span>角色管理</span>
              <el-button class="button" @click="updateDialogVisible = true">尝试编辑</el-button>
            </div>
          </template>

          <el-form :inline="true">
            <el-form-item label="id">
              <el-input />
            </el-form-item>
            <el-form-item label="角色名">
              <el-input />
            </el-form-item>
            <el-form-item label="权限">
              <el-input />
            </el-form-item>
            <el-form-item>
              <el-button type="primary">查询</el-button>
            </el-form-item>
          </el-form>

          <el-table :data="page.records">
            <el-table-column prop="id" label="id" />
            <el-table-column prop="roleName" label="角色名" />
            <el-table-column prop="permissionList" label="权限">
              <template #default="scope">
                {{ showPermissionList(scope.row.permissionList) }}
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default="scope">
                <el-button size="small">编辑</el-button>
                <el-button type="danger" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
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