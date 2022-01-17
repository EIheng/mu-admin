<script setup lang="ts">
import { getImage } from '@/api/galleryAPI';
import { ref } from 'vue';


const props = defineProps<{path: string}>();
const emits = defineEmits(['load-success'])

const imgurl = ref("")

getImage(props.path).then((res) => {
  imgurl.value = URL.createObjectURL(res.data)
  emits("load-success", imgurl.value);
})
</script>

<template>
  <el-image :src="imgurl" fit="contain"></el-image>
</template>

<style scoped>
</style>