<script setup lang="ts">
import { listPictureInfo, PictureInfo } from '@/api/galleryAPI';
import { reactive } from 'vue';
import LoadImage from '@/components/LoadImage.vue';

interface ShowData {
  pictureInfoList: PictureInfo[];
  iamgeBoxVisible: boolean;
  selectImage: string;
}

const showData = reactive<ShowData>({
  pictureInfoList: [],
  iamgeBoxVisible: false,
  selectImage: ""
})

function selectImage(url: string) {
  showData.selectImage = url;
  showData.iamgeBoxVisible = true;
}

listPictureInfo().then(res => {
  res.data.forEach(el => {
    if (el.name.length > 20) {
      el.showName = el.name.substring(0, 15) + "..."
    } else {
      el.showName = el.name;
    }
  })
  showData.pictureInfoList = res.data;
})

function myClick() {
  console.log('666')
}
</script>

<template>
  <!-- 图片预览 -->
  <transition name="fade">
    <div
      class="preview-image click"
      @click="showData.iamgeBoxVisible = false"
      v-if="showData.iamgeBoxVisible"
    >
      <img class="none-cursor" @click.stop :src="showData.selectImage" />
    </div>
  </transition>

  <!-- 图片列表 -->
  <div class="my-card">
    <div class="image-box" v-for="item in showData.pictureInfoList">
      <load-image
        @click="selectImage(item.url)"
        class="image click"
        :path="item.path"
        @load-success="item.url = $event"
      ></load-image>
      <div class="text" :title="item.name">{{ item.showName }}</div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "@/style/my-card.scss";
@import "@/style/click.scss";

.fade-enter-active {
  transition: all 0.3s ease-out;
}

.fade-leave-active {
  transition: all 0.3s ease-out;
}

.fade-enter-from,
.fade-leave-to {
  transform: translateY(-20px);
  opacity: 0;
}

.preview-image {
  position: fixed;
  z-index: 1;
  margin: 0 auto;
  left: 0;
  right: 0;
  top: 0;
  width: 100%;
  height: 120vh;
  background-color: #0000005e;
  text-align: center;
  img {
    margin-top: 5%;
    height: calc(80% - 20vh);
    z-index: 3;
    border: 1px solid #c0c4cc;
  }
}

.image-box {
  width: 20%;
  display: inline-block;
  text-align: center;
  .text {
    font-size: 10px;
    font-family: "Consolas 微软雅黑";
    color: #606266;
  }
}

.image {
  width: 100px;
  height: 100px;
  border: 1px solid var(--el-border-color-base);
  margin: 10px;
}
</style>