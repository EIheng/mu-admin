<script setup lang="ts">
import { Page, SysUserVO } from '@/api/model/vo';
import { UserRequest } from '@/api/userAPI';
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';

// 查询部分
const page = reactive<Page<SysUserVO>>({
  records: [],
  total: 0,
  size: 0,
  current: 1
})

const pageForm = reactive<{
  id: number | null,
  username: string
  roleName: string
}>({
  id: null,
  username: "",
  roleName: "",
})

const submitPage = (cur: number = page.current) => {
  UserRequest.page({
    cur,
    id: pageForm.id,
    username: pageForm.username,
    roleName: pageForm.roleName
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

// 创建部分
const insertForm = {
  dialogData: reactive({
    visible: false,
    form: {
      username: "",
      roleName: "",
      password: ""
    }
  }),
  open: () => {
    insertForm.dialogData.visible = true
  },
  submit: () => {
    UserRequest.insert(insertForm.dialogData.form).then(res => {
      if (res.data.state) {
        ElMessage.success(res.data.msg)
        submitPage()
        insertForm.dialogData.visible = false
      } else {
        ElMessage.warning(res.data.msg)
      }
    })
  }
}

// 更新部分
const updateDialogVisible = ref(false)

const updateForm = reactive({
  id: -1,
  username: "",
  roleName: ""
})

const submitUpdateForm = () => {
  UserRequest.update(updateForm).then(res => {
    if (res.data.state) {
      ElMessage.success(res.data.msg)
      submitPage()
      updateDialogVisible.value = false
    } else {
      ElMessage.warning(res.data.msg)
    }
  })
}

const openUpdateDialog = (sysUserVO: SysUserVO) => {
  Object.assign(updateForm, sysUserVO)
  updateDialogVisible.value = true
}

// 删除
const deleteRequest = (id: number) => {
  UserRequest.delete({ id }).then(res => {
    if (res.data.state) {
      submitPage()
      ElMessage.success(res.data.msg)
    } else {
      ElMessage.warning(res.data.msg)
    }
  })
}

submitPage()
</script>

<template>

  <el-dialog v-model="insertForm.dialogData.visible" title="创建用户" append-to-body>
    <el-form :model="insertForm.dialogData.form" label-width="120px">
      <el-form-item label="用户名">
        <el-input v-model="insertForm.dialogData.form.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="insertForm.dialogData.form.password" />
      </el-form-item>
      <el-form-item label="角色">
        <el-input v-model="insertForm.dialogData.form.roleName" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="insertForm.dialogData.visible = false">取消</el-button>
      <el-button type="primary" @click="insertForm.submit()">提交</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="updateDialogVisible" title="修改用户" append-to-body>
    <el-form :model="updateForm" label-width="120px">
      <el-form-item label="id">
        <el-input v-model="updateForm.id" disabled />
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="updateForm.username" />
      </el-form-item>
      <el-form-item label="角色">
        <el-input v-model="updateForm.roleName" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="updateDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitUpdateForm()">提交</el-button>
    </template>
  </el-dialog>

  <el-row :gutter="10">
    <el-col :span="24">
      <transition appear name="el-zoom-in-top">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>用户管理</span>
              <el-button class="button" @click="insertForm.open()">创建</el-button>
            </div>
          </template>

          <el-form :model="pageForm" :inline="true">
            <el-form-item label="id">
              <el-input v-model="pageForm.id" />
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="pageForm.username" />
            </el-form-item>
            <el-form-item label="角色">
              <el-input v-model="pageForm.roleName" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitPage()">查询</el-button>
            </el-form-item>
          </el-form>

          <el-table :data="page.records">
            <el-table-column prop="id" label="id" />
            <el-table-column prop="username" label="用户名" />
            <el-table-column prop="roleName" label="用户角色" />
            <el-table-column label="操作">
              <template #default="scope">
                <el-button size="small" @click="openUpdateDialog(scope.row)">编辑</el-button>
                <el-popconfirm title="确认删除？" icon-color="red" @confirm="deleteRequest(scope.row.id)">
                  <template #reference>
                    <el-button type="danger" size="small">删除</el-button>
                  </template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination style="justify-content: center;" layout="prev, pager, next" @current-change="submitPage"
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