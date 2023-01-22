<!--
  * @FileDescription: Create New Group Page, accept:
      1. Group Name
      2. Group Avatar
      3. Friends who are invited to the group
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 12:13
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/01 10:24
-->
<template>
  <div>
    <el-container>
      <el-header class="header hh">
        <div class="header h1">
          <div class="header-avatar">
            <el-avatar :size="100" :src="img" />
          </div>
          <div class="header-edit">
            <div class="text">{{ $t("createGroup.avatarLabel") }}</div>
            <el-upload
              ref="uploadRef"
              :on-change="handleChange"
              :on-exceed="handleExceed"
              class="upload-demo"
              accept="image/jpeg,image/png,image/jpg"
              action="#"
              :file-list="file"
              :limit="1"
              :auto-upload="false"
              :show-file-list="false"
            >
              <template #trigger>
                <el-button type="primary" round class="header-btn">{{
                  $t("createGroup.addAvatar")
                }}</el-button>
              </template>
              <template #tip>
                <div class="el-upload__tip">
                  {{ $t("buttons.picInfo") }}
                </div>
              </template>
            </el-upload>
          </div>
        </div>
        <div class="header h2">
          <div class="text header-label">{{ $t("createGroup.groupName") }}</div>
          <div class="header-input">
            <el-input
              v-model="groupName"
              :placeholder="pholder"
              size="large"
            ></el-input>
          </div>
        </div>
      </el-header>
      <el-main class="main">
        <div class="scroll">
          <el-scrollbar class="scroll-main-bar">
            <div class="scroll-bar">
              <div
                v-for="member in inviteList"
                :key="member.id"
                class="scroll-item"
              >
                <el-avatar
                  :size="100"
                  src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                />
                <div class="memberName">{{ member.name }}</div>
              </div>
            </div>
          </el-scrollbar>
        </div>
        <div class="main-btn">
          <el-button type="success" round @click="toPopWin">{{
            $t("createGroup.addMember")
          }}</el-button>
          <el-button type="primary" round @click="create">{{
            $t("createGroup.create")
          }}</el-button>
        </div>
      </el-main>
    </el-container>
    <PopWinFriendList
      :dialog-visible="dialogFormVisible"
      :list="inviteList"
      @closeWin="closePop"
      @addFun="addToList"
    ></PopWinFriendList>
  </div>
</template>
<script setup lang="ts">
import { computed, reactive, ref, watch } from "vue";
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { uploadPic, deletePic } from "@/api/upload";
import { createNewGroup, sendGroupInvite, changeGroupInfo } from "@/api/group";
import { storeToRefs } from "pinia";
import useUserStore from "@/stores/userStore";
import { ElMessage } from "element-plus";
import type { UploadInstance, UploadProps, UploadRawFile } from "element-plus";
import PopWinFriendList from "./PopWinFriendList.vue";
import { genFileId } from "element-plus";

const dialogFormVisible = ref(false);
const store = useUserStore();
const router = useRouter();
const { token } = storeToRefs(store);
const { t } = useI18n();
const img = ref(
  "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
);
interface member {
  id: String,
  name: String,
  avatar: String,
  state: Number
}
const inviteList = reactive<member[]>([]);
const groupId = ref("");
const groupName = ref("");
const pholder = computed<String>({
  get() {return t("createGroup.groupNameHolder")},
  set(val) {return},
});
const uploadRef = ref<UploadInstance>();
const file = reactive([]);
var flag = false;

/**
 * @description: image list exceed limit action
 * @param {Array} files uploaded image list
 */
const handleExceed: UploadProps["onExceed"] = (files) => {
  uploadRef.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  uploadRef.value!.handleStart(file);
};
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
  uploadFun();
}
/**
 * @description: delete the status from server if exists
 * @param {String} url Status Text
 */
function del(url: String) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (
        url ==
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
      ) {
        resolve("No need to delete");
      }
      if (!url.startsWith("https://s1.ax1x.com")) {
        resolve("Cannot Delete");
      }
      let head = url.lastIndexOf("/") + 1;
      let tail = url.lastIndexOf(".");
      let id = url.substring(head, tail);

      deletePic(id)
        .then((res) => {
          if (res.data.success) {
            ElMessage({
              type: "success",
              message: t("chatInputBox.deletePicSuccess"),
              showClose: true,
              grouping: true,
            });
            resolve("Delete Success");
          } else {
            ElMessage({
              type: "error",
              message: res.data.msg,
              showClose: true,
              grouping: true,
            });
            resolve("Delete Fail");
          }
        })
        .catch((err) => {
          ElMessage({
            type: "error",
            message: t("chatInputBox.deletePicError"),
            showClose: true,
            grouping: true,
          });
          console.log(err);
          resolve("Delete Error");
        });
    }, 500);
  });
}
/**
 * @description: upload new image to server
 * @return: get the image url from server
 */
function submitUpload() {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      let formData = new FormData();
      let f = file;
      for (let i = 0; i < f.length; i++) {
        formData.append("file", f[i]);
      }

      // send request
      uploadPic(formData, 2)
        .then((res) => {
          if (res.data.success) {
            img.value = res.data.data;
            resolve("Upload New Avatar Success!");
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
    }, 500);
  });
}
/**
 * @description: overall upload image function
 */
async function uploadFun() {
  await del(img.value);
  await submitUpload();
  file.length = 0;
  uploadRef.value!.clearFiles();
}
/**
 * @description: send creat Group HTTP request
 * @param {String} groupName Group Name
 */
function createGroup() {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      flag = false;
      createNewGroup(token.value, groupName.value.trim())
        .then((res) => {
          if (res.data.success) {
            groupId.value = res.data.data;
            flag = true;
            resolve("got groupId");
          } else {
            ElMessage({
              type: "error",
              message: res.data.msg,
              showClose: true,
              grouping: true,
            });
            reject;
          }
        })
        .catch((err) => {
          ElMessage({
            type: "error",
            message: t("createGroup.createError"),
            showClose: true,
            grouping: true,
          });
          console.log(err);
          reject;
        });
    }, 500);
  });
}
/**
 * @description: send a member HTTP invite
 * @param {Array} inviteInfo the member's invitation information
 */
function sendInvite(inviteInfo) {
  sendGroupInvite(token.value, inviteInfo)
    .then((res) => {
      if (!res.data.success) {
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
        message: t("createGroup.inviteError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    });
}
/**
 * @description: change group avatar HTTP
 * @param {String} successMsg request success message
 * @param {String} errorMsg request fail message
 */
function changeInfo(successMsg, errorMsg) {
  const gInfo = {
    id: groupId.value,
    name: groupName.value.trim(),
    avatar: img.value,
    notice: "",
  };
  changeGroupInfo(token.value, gInfo)
    .then((res) => {
      if (res.data.success) {
        ElMessage({
          type: "success",
          message: successMsg,
          showClose: true,
          grouping: true,
        });
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
        message: errorMsg,
        showClose: true,
        grouping: true,
      });
      console.log(err);
    });
}
/**
 * @description: overall create group function
 */
async function create() {
  await createGroup();
  console.log(groupId.value);
  changeInfo(
    t("groupSetting.changeAvatar"),
    t("groupSetting.changeAvatarError")
  );

  for (let i = 0; i < inviteList.length; i++) {
    let inviteInfo = {
      groupId: groupId.value,
      receiverId: inviteList[i].id,
      message: "",
    };
    sendInvite(inviteInfo);
  }
  if (flag) {
    const info = {
      gid: groupId.value,
      note: "",
      gName: groupName.value.trim(),
      gAvatar: img.value,
    };
    store.updategroupInfo(info);
    router.push({ name: "chatRoom", params: { id: "g" + groupId.value } });
  }
}
/**
 * @description: Add friend to invite list
 * @param {object} obj friend object
 * @return invite list change
 */
function addToList(obj) {
  inviteList.push(obj);
}
/**
 * @description: open popWindow
 */
function toPopWin() {
  dialogFormVisible.value = true;
}
/**
 * @description: close popWindow
 */
function closePop() {
  dialogFormVisible.value = false;
}
</script>
<style scoped>
.header {
  display: -webkit-flex; /* Safari */
  display: flex;
  align-items: center;
}
.hh {
  margin-top: 3vh;
  justify-content: space-between;
}
.h1 {
  flex: 1 1 auto;
}
.h2 {
  flex: 2 2 auto;
  justify-content: flex-end;
}
.header-edit {
  margin-left: 2vw;
}
.header-btn {
  margin-top: 1vh;
}
.header-input {
  margin-left: 2vw;
  width: 20vw;
}
.main {
  margin-top: 3vh;
}
.scroll {
  background-color: bisque;
  border-radius: 20px;
}
.scroll-bar {
  width: 100%;
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: row wrap;
  justify-content: flex-start;
  align-items: flex-start;
}
.scroll-item {
  margin-left: 10px;
  margin-top: 5px;
}
.main-btn {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-between;
  align-items: center;
  top: 10px;
  margin: 0;
  padding: 0;
}
.text {
  font-family: "Helvetica Neue";
  font-size: xx-large;
}
.memberName {
  text-align: center;
}
@media screen and (min-height: 850px) {
  .scroll-main-bar {
    height: 45vh;
  }
}
@media screen and (max-height: 849px) and (min-height: 720px) {
  .scroll-main-bar {
    height: 40vh;
  }
}
@media screen and (max-height: 719px) and (min-height: 620px) {
  .scroll-main-bar {
    height: 35vh;
  }
}
@media screen and (max-height: 619px) {
  .scroll-main-bar {
    height: 30vh;
  }
  .main-btn {
    top: 5px;
  }
}
</style>
