<script setup lang="ts">
import { reactive, ref } from 'vue';
import { Back, Folder, Document, ArrowRight, InfoFilled, Download } from '@element-plus/icons-vue';
import {
  FileListResVO,
  FileInfo,
  getDirectoryInfo,
  deleteFile as deleteFileRequest,
  download as downloadRequest,
  createFile as createFileRequest,
  upload as uploadRequest
} from "@/api/fileAPI";
import { ElMessage } from 'element-plus';
import type { ElUpload } from 'element-plus'
import { session } from '@/util/sessionStorageUtil';

// 文件处理
const uploadRef = ref<InstanceType<typeof ElUpload>>()
const dialogVisible = ref(false);
const dialogVisible2 = ref(false);
const radio1 = ref("1");


// 上传文件
const submitUpload = () => {
  const headers: any = uploadRef.value?.headers;
  if (headers == undefined) {
    return;
  }
  const tokenInfo = session.get("tokenInfo");
  if (tokenInfo == null) {
    return;
  }

  // headers["Content-Type"] = "multipart/form-data";
  headers[tokenInfo.tokenName] = tokenInfo.tokenValue;
  uploadRef.value?.submit();
}

/**
 * 展示于页面中的数据)
 */
const showData: FileListResVO = reactive<FileListResVO>({
  curFileInfo: {
    fileName: "",
    path: "",
    directory: true,
    length: 0,
    lastUpdateTime: "",
  },
  fileInfoList: [],
  fileInfoPathList: []
});

const createFileName = ref("");

// 创建
function createFile() {
  createFileRequest(showData.curFileInfo.path, createFileName.value, (radio1.value == "1" ? false : true)).then(() => {
    updateCurFilePath();
    dialogVisible2.value = false;
  })
}

/**
 * 根据当前路径刷新页面
 */
function updateCurFilePath() {
  updateByFileInfo(showData.curFileInfo);
}

/**
 * 上一文件夹
 */
function backUpdate() {
  const l = showData.fileInfoPathList.length;
  if (l == 0) {
    return;
  } else if (l == 1) {
    updateByPath("");
  } else {
    updateByFileInfo(showData.fileInfoPathList[l - 2]);
  }
}

/**
 * 删除文件
 * @param fileInfo 
 */
function deleteFile(fileInfo: FileInfo) {
  deleteFileRequest(fileInfo.path).then((res) => {
    if (res.data) {
      ElMessage.success("删除成功");
      updateCurFilePath()
    } else {
      ElMessage.error("删除失败");
    }
  })
}

/**
 * 更新页面
 * @param fileInfo
 */
function updateByFileInfo(fileInfo: FileInfo) {
  if (!fileInfo.directory) {
    return;
  }
  updateByPath(fileInfo.path);
}

/**
 * 更新页面
 * @param path 
 */
function updateByPath(path: string) {
  getDirectoryInfo(path).then(res => {
    showData.fileInfoList = res.data.fileInfoList.sort((a, b) => {
      return (b.directory as any) - (a.directory as any);
    });
    showData.fileInfoPathList = res.data.fileInfoPathList;
  })
}

function download(fileInfo: FileInfo) {
  downloadRequest(fileInfo.path).then(res => {
    const blob = new Blob([res.data]);
    var downloadElement = document.createElement("a");
    var href = window.URL.createObjectURL(blob);
    downloadElement.href = href;
    downloadElement.download = decodeURIComponent(fileInfo.fileName);
    document.body.appendChild(downloadElement);
    downloadElement.click();
    document.body.removeChild(downloadElement);
    window.URL.revokeObjectURL(href);
  })
}

// 初始化
updateByFileInfo(showData.curFileInfo);
</script>

<template>
  <!-- 导航 -->
  <div class="my-card breadcrumb">
    <div class="file-back click" @click="backUpdate()">
      <el-icon>
        <back />
      </el-icon>
    </div>
    <div class="file-path-item click" @click="updateByPath('')">{{ "根目录" }}</div>
    <div class="file-path-next">
      <el-icon>
        <arrow-right />
      </el-icon>
    </div>
    <template v-for="item in showData.fileInfoPathList">
      <div class="file-path-item click" @click="updateByFileInfo(item)">{{ item.fileName }}</div>
      <div class="file-path-next">
        <el-icon>
          <arrow-right />
        </el-icon>
      </div>
    </template>
  </div>

  <!-- 功能 -->
  <div class="my-card">
    <el-button size="mini" @click="dialogVisible = true">上传</el-button>
    <el-button size="mini">下载</el-button>
    <el-button size="mini" @click="dialogVisible2 = true">创建</el-button>
  </div>

  <!-- 列表 -->
  <div class="my-card">
    <el-table :data="showData.fileInfoList">
      <el-table-column prop="directory" label="类型" width="50">
        <template #default="scope">
          <template v-if="scope.row.directory">
            <el-icon>
              <folder />
            </el-icon>
          </template>
          <template v-else>
            <el-icon>
              <document />
            </el-icon>
          </template>
        </template>
      </el-table-column>
      <el-table-column prop="fileName" label="文件名" width="300">
        <template #default="scope">
          <span
            :class="{ click: scope.row.directory }"
            @click="updateByFileInfo(scope.row)"
          >{{ scope.row.fileName }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="length" label="大小" width="180">
        <template #default="scope">
          <template v-if="!scope.row.directory">{{ (scope.row.length / 1024).toFixed(2) + "KB" }}</template>
        </template>
      </el-table-column>
      <el-table-column prop="lastUpdateTime" label="修改时间" />
      <el-table-column fixed="right" label="操作" width="180">
        <template #default="scope">
          <el-popconfirm
            title="确认下载？"
            :icon="Download"
            icon-color="#409EFF"
            @confirm="download(scope.row)"
          >
            <template #reference>
              <el-button :disabled="scope.row.directory" size="mini">下载</el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm
            title="确认删除？"
            :icon="InfoFilled"
            icon-color="red"
            @confirm="deleteFile(scope.row)"
          >
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 上传文件 -->
  <el-dialog v-model="dialogVisible" title="文件上传" destroy-on-close>
    <el-upload
      ref="uploadRef"
      class="upload-demo"
      :action="'http://localhost:9908/api/file/upload?path=' + showData.curFileInfo.path"
      :auto-upload="false"
      :on-success="updateCurFilePath"
    >
      <template #trigger>
        <el-button style="margin-right: 10px;" size="small" type="primary">选择文件</el-button>
      </template>
      <el-button size="small" class="ml-3" type="success" @click="submitUpload()">上传</el-button>
      <template #tip>
        <div class="el-upload__tip">单个文件不允许大于500KB</div>
      </template>
    </el-upload>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 创建文件 -->
  <el-dialog v-model="dialogVisible2" title="创建文件">
    <el-radio-group v-model="radio1" size="small">
      <el-radio-button label="1">
        <el-icon>
          <document />
        </el-icon>文件
      </el-radio-button>
      <el-radio-button label="2">
        <el-icon>
          <folder />
        </el-icon>文件夹
      </el-radio-button>
    </el-radio-group>
    <el-input v-model="createFileName"></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click="createFile()">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
@import "@/style/my-card.scss";

.breadcrumb {
  height: auto;
  font-size: 14px;
  padding: 0px;
  display: flex;
  align-items: center;
}

.click:hover {
  cursor: pointer;
  color: #409eff;
}

.file-back {
  font-size: 20px;
  padding: 5px;
  height: 20px;
  width: 20px;
  border-right: 1px solid var(--el-border-color-base);
}

.file-path-item {
  padding: 5px;
  height: 20px;
  width: auto;
  border-right: 1px solid var(--el-border-color-base);
}

.file-path-next {
  font-size: 16px;
  padding: 4px, 0px;
  height: 20px;
  width: 16px;
  border-right: 1px solid var(--el-border-color-base);
}
</style>