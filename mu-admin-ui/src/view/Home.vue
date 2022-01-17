<script setup lang="ts">
</script>

<template>
  <el-row>
    <el-col :span="12">
      <div class="my-card">
        <el-empty description="无内容"></el-empty>
      </div>
    </el-col>
    <el-col :span="12">
      <div class="may-card">
        <el-timeline>
          <el-timeline-item timestamp="2021年12月2日" placement="top">
            <el-card>
              <h4>更新日志</h4>
              <p>幕冬 更新于 2021年12月2日13:35:47</p>
            </el-card>
          </el-timeline-item>
          <el-timeline-item timestamp="2021年12月2日" placement="top">
            <el-card>
              <h4>更新日志</h4>
              <p>幕冬 更新于 2021年12月2日13:35:47</p>
            </el-card>
          </el-timeline-item>
          <el-timeline-item timestamp="2021年12月2日" placement="top">
            <el-card>
              <h4>更新日志</h4>
              <p>幕冬 更新于 2021年12月2日13:35:47</p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-col>
  </el-row>
</template>



<style lang="scss" scoped>
@import "@/style/my-card.scss";

.el-progress {
  margin: 10px;
}
</style>