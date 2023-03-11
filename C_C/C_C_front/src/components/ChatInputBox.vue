<!--
  * @FileDescription: Chat Input Box Component, include:
      1. type area
      2. send button
      3. add image button
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:26
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/07 23:33
-->
<template>
  <div class="all">
    <div id="left">
      <textarea
        @keydown.enter="keyDown"
        rows="6"
        v-model="input"
        style="outline: none; color: black"
        :placeholder="t('chatInputBox.chat')"
      ></textarea>
    </div>
    <div id="right">
      <div class="top">
        <div>
          <el-upload
            ref="uploadRef"
            :limit="1"
            action="#"
            accept="image/png,image/gif,image/jpg,image/jpeg"
            :file-list="file"
            class="upload-demo"
            :on-change="handleChange"
            :auto-upload="false"
          >
            <template #trigger>
              <el-icon class="addp" :size="25"><Picture /></el-icon></template
          ></el-upload>
        </div>
      </div>
      <div class="bottom">
        <el-button round type="primary" @click="sendMsg">{{
          $t("chatInputBox.send")
        }}</el-button>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref, watch, reactive } from "vue";
import type { UploadInstance } from "element-plus";
import { uploadPic } from "@/api/upload.js";
import { ElMessage } from "element-plus";
import { useI18n } from "vue-i18n";

const emit = defineEmits(["addPic", "sendMsg"]);
const { t } = useI18n();
const input = ref("");
const uploadRef = ref<UploadInstance>();
const uploaded = ref(false);
const img = ref(
  "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
);
let file = reactive([]);

let picSet: String; // url sets
/**
 * @description: images list length change action
 * @param {byte} f the new loaded image
 * @param {Array} fileList the image list
 */
async function handleChange(f, fileList) {
  let reader = new FileReader();
  reader.readAsDataURL(f.raw);
  reader.onload = (e) => {
    file.push({ name: f.raw.name, url: e.target.result });
  };
  file.push(f.raw);
  submitUpload();
}
/**
 * @description: clear input
 * @param {String} input input text
 */
function clearText() {
  input.value = "";
}
/**
 * @description: call send message function, and clear input
 * @param {String} input input text
 */
function sendMsg() {
  input.value = input.value.trim();
  if (input.value.length == 0) {
    return;
  }
  emit("sendMsg", input.value, "text");
  clearText();
}
/**
 * @description: if [ctrl] + [Enter], then send the message
 * @param e pressed key
 */
function keyDown(e) {
  if (e.ctrlKey && e.keyCode == 13) {
    sendMsg();
  }
}
/**
 * @description: upload new image to server
 * @return: get the image url from server
 */
function submitUpload() {
    let formData = new FormData();
    let f = file;
    for (let i = 0; i < f.length; i++) {
      formData.append("file", f[i]);
    }
    // send request
    uploadPic(formData, 3)
      .then((res) => {
        if (res.data.success) {
          picSet = res.data.data;
          console.log("picSet: " + picSet);
          emit("addPic", picSet);
          file.length = 0;
          uploadRef.value!.clearFiles();
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
</script>
<style scoped>
.all {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 75vw;
  height: 100%;
}
#left {
  background-color: transparent;
  width: 100%;
  height: 90%;
}
#right {
  height: 80%;
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: column wrap;
  justify-content: space-between;
}
.top {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: row nowrap;
  justify-content: flex-end;
  width: 100%;
}
.bottom {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: row nowrap;
  justify-content: flex-end;
  width: 100%;
}
.addp {
  size: 500px;
  color: cadetblue;
}
textarea:valid {
  border: 2px solid transparent;
  background-color: transparent;
  font-size: larger;
  width: 100%;
  height: 100%;
  resize: none;
}
::placeholder {
  color: darkgray;
}
</style>
