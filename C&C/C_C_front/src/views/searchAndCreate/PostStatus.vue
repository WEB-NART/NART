<!--
  * @FileDescription: Post Status page, accept
      1. text
      2. images
  * @Author: Shizhong Shang
  * @Date: 2022/12/25 12:10
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/01 15:43
-->
<template>
  <div class="all">
    <div class="mainpart">
      <div class="top">
        <textarea
          rows="6"
          v-model="msg"
          style="
            font-size: 16px;
            color: red;
            font-family: Arial;
            font-weight: bold;
            outline: none;
          "
          :placeholder="t('postStatus.placeHolder')"
        ></textarea>
      </div>
    </div>
    <div class="pics">
      <div class="mid">
        <span>{{ $t("postStatus.putPic") }}</span>
      </div>
      <el-upload
        v-model:file-list="file"
        ref="upload"
        action="#"
        list-type="picture-card"
        accept="image/png,image/gif,image/jpg,image/jpeg"
        mutiple
        show-file-list
        :auto-upload="false"
        :on-preview="handlePictureCardPreview"
        :on-remove="handleRemove"
        :on-exceed="handleExceed"
        :on-change="handleChange"
        :limit="9"
      >
        <el-icon><Plus /></el-icon>
      </el-upload>

      <el-dialog v-model="dialogVisible">
        <el-image fit="fill" :src="dialogImageUrl" alt="Preview Image" />
      </el-dialog>
    </div>
    <div class="bottom">
      <el-button round type="primary" id="post-btn" @click="post">{{
        $t("postStatus.post")
      }}</el-button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from "vue";
import { Plus } from "@element-plus/icons-vue";
import { useI18n } from "vue-i18n";
import useUserStore from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { postStatus } from "@/api/status";
import { uploadPic } from "@/api/upload";
import { ElMessage } from "element-plus";

import type { UploadProps, UploadUserFile, UploadInstance } from "element-plus";

const store = useUserStore();
const { token } = storeToRefs(store);
const { t } = useI18n();
const counter = ref(0);

const file = reactive([]);
const upload = ref<UploadInstance>();
var pics : string[] = []
var msg = ref("");

/**
 * @description: images list length change action
 * @param {byte} f the new loaded image
 * @param {Array} fileList the image list
 */
function handleChange(f, fileList) {
  let reader = new FileReader();
  reader.readAsDataURL(f.raw);
  reader.onload = (e) => {
    file.push({ name: f.raw.name, url: e.target.result });
  };
  file.push(f.raw);
  fileList = file;
}
/**
 * @description: upload all images to server
 * @return: get the image url from server
 */
function uploadPics() {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log("length: " + file.length);
      console.log(file);
      let f = file;
      for (let i = 0; i < f.length; i = i+2) {
        let formData = new FormData();
        formData.append("file", f[i]);
        formData.append("file", f[i+1]);
        uploadPic(formData, 0)
          .then((res) => {
            if (res.data.success) {
              pics.push(res.data.data);
              if(i+2 >= f.length){
                resolve('搞定！');
              }
            } else {
              ElMessage({
                type: "error",
                message: res.data.msg,
                showClose: true,
                grouping: true,
              });
            }
          })
          .catch((err) => {
            ElMessage({
              type: "error",
              message: t("chatInputBox.uploadPicError"),
              showClose: true,
              grouping: true,
            });
            console.log(err);
          });
      }
    }, 1000);
  });
}
/**
 * @description: post the status
 * @param {String} msg Status Text
 * @param {Array} pics Images url list
 */
function postAll() {
  console.log(pics);

  postStatus(token.value, msg.value, pics)
    .then((res) => {
      if (res.data.success) {
        alert(t("postStatus.succeed"));
      } else {
        ElMessage({
          type: "error",
          message: t("postStatus.err"),
          showClose: true,
        });
      }
    })
    .catch((err) => {
      ElMessage({
        type: "error",
        message: t("postStatus.err"),
        showClose: true,
      });
      console.log(err);
    })
    .finally(() => {});
  file.length = 0;
  pics.length = 0;
  msg.value = '';
  upload.value!.clearFiles();
}
/**
 * @description: overall post function
 */
async function post() {
  await uploadPics();
  postAll();
}
const dialogImageUrl = ref("");
const dialogVisible = ref(false);

/**
 * @description: remove image from list action
 * @param {byte} uploadFile removed image
 * @param {Array} uploadFiles image lists
 */
const handleRemove: UploadProps["onRemove"] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
};
/**
 * @description: image list preview action
 * @param {byte} uploadFile the chosen image
 */
const handlePictureCardPreview: UploadProps["onPreview"] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisible.value = true;
};
/**
 * @description: image list exceed limit action
 * @param {Array} uploadFile uploaded image list
 */
const handleExceed: UploadProps["onExceed"] = (uploadFile) => {
  console.log(uploadFile);
};
</script>
<style scoped>
.all {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-around;
  flex-flow: column nowrap;
  align-items: center;
  width: 100%;
  height: 100%;
}
.mainpart {
  flex: 3 3 auto;
  margin-left: 10%;
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-between;
  flex-flow: column nowrap;
  align-items: center;
  width: 100%;
}
.top {
  width: 100%;
}
.pics {
  flex: 3 3 auto;
  margin-left: 10%;
  width: 100%;
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: column nowrap;
  justify-content: flex-start;
  align-items: flex-start;
}
textarea:valid {
  border: 2px solid transparent;
  background-color: transparent;
  width: 100%;
  height: 100%;
  resize: none;
}
::placeholder {
  color: darkgray;
}
.bottom {
  flex: auto;
  padding: 10px;
  width: 100%;
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: center;
}
#post-btn {
  width: 10%;
  min-width: 60px;
  height: 50%;
  min-height: 30px;
}
</style>
