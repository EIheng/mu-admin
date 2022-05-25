<script setup lang="ts">
import { Page, PermissionVO } from '@/api/model/vo';
import { PermissionRequest } from '@/api/permissionAPI';
import { ElMessage } from 'element-plus';
import { reactive } from 'vue';

// 查
const page = reactive<Page<PermissionVO>>({
  records: [],
  total: 0,
  size: 0,
  current: 1
})
const pageForm = reactive({
  id: null as null | number,
  permissionName: "",
  permissionNote: "",
})
const onPage = (cur: number = page.current) => {
  PermissionRequest.page({
    cur,
    id: pageForm.id,
    permissionName: pageForm.permissionName,
    permissionNote: pageForm.permissionNote
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
// 增
const insertForm = {
  dialogData: reactive({
    visible: false,
    form: {
      permissionName: "",
      permissionNote: "",
    }
  }),
  open: () => {
    insertForm.dialogData.visible = true
  },
  submit: () => {
    PermissionRequest.insert(insertForm.dialogData.form).then(res => {
      if (res.data.state) {
        ElMessage.success(res.data.msg)
        onPage()
        insertForm.dialogData.visible = false
      } else {
        ElMessage.warning(res.data.msg)
      }
    })
  }
}


// 更
const updateForm = {
  dialogData: reactive({
    visible: false,
    form: {
      id: -1,
      permissionName: "",
      permissionNote: "",
    }
  }),
  open: (roleVO: PermissionVO) => {
    Object.assign(updateForm.dialogData.form, roleVO)
    updateForm.dialogData.visible = true
  },
  submit: () => {
    PermissionRequest.update(updateForm.dialogData.form).then(res => {
      if (res.data.state) {
        ElMessage.success(res.data.msg)
        onPage()
        updateForm.dialogData.visible = false
      } else {
        ElMessage.warning(res.data.msg)
      }
    })
  }
}

// 删除
const deleteRequest = (id: number) => {
  PermissionRequest.delete({ id }).then(res => {
    if (res.data.state) {
      onPage()
      ElMessage.success(res.data.msg)
    } else {
      ElMessage.warning(res.data.msg)
    }

  })
}
onPage()
</script>

<template>

  <el-dialog v-model="insertForm.dialogData.visible" title="创建权限" append-to-body>
    <el-form :model="insertForm.dialogData.form" label-width="120px">
      <el-form-item label="权限键值">
        <el-input v-model="insertForm.dialogData.form.permissionName" />
      </el-form-item>
      <el-form-item label="权限名称">
        <el-input v-model="insertForm.dialogData.form.permissionNote" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="insertForm.dialogData.visible = false">取消</el-button>
      <el-button type="primary" @click="insertForm.submit()">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="updateForm.dialogData.visible" title="修改权限" append-to-body>
    <el-form :model="updateForm.dialogData.form" label-width="120px">
      <el-form-item label="id">
        <el-input v-model="updateForm.dialogData.form.id" disabled />
      </el-form-item>
      <el-form-item label="权限键值">
        <el-input v-model="updateForm.dialogData.form.permissionName" />
      </el-form-item>
      <el-form-item label="权限名称">
        <el-input v-model="updateForm.dialogData.form.permissionNote" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="updateForm.dialogData.visible = false">取消</el-button>
      <el-button type="primary" @click="updateForm.submit()">提交</el-button>
    </template>
  </el-dialog>

  <el-row :gutter="10">
    <el-col :span="24">
      <transition appear name="el-zoom-in-top">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>权限管理</span>
              <el-button class="button" @click="insertForm.open()">创建</el-button>
            </div>
          </template>

          <el-form :inline="true">
            <el-form-item label="id">
              <el-input v-model="pageForm.id" />
            </el-form-item>
            <el-form-item label="权限键值">
              <el-input v-model="pageForm.permissionName" />
            </el-form-item>
            <el-form-item label="权限名称">
              <el-input v-model="pageForm.permissionNote" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onPage()">查询</el-button>
            </el-form-item>
          </el-form>

          <el-table :data="page.records">
            <el-table-column prop="id" label="id" />
            <el-table-column prop="permissionName" label="权限键值" />
            <el-table-column prop="permissionNote" label="权限名称" />
            <el-table-column label="操作">
              <template #default="scope">
                <el-button size="small" @click="updateForm.open(scope.row)">编辑</el-button>
                <el-popconfirm title="确认删除？" icon-color="red" @confirm="deleteRequest(scope.row.id)">
                  <template #reference>
                    <el-button type="danger" size="small">删除</el-button>
                  </template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination style="justify-content: center;" layout="prev, pager, next" @current-change="onPage"
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