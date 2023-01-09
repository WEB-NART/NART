<!--
  * @FileDescription: Group Setting Page, show:
      1. Group Name
      2. Group Avatar
      3. Group Notice
      4. ALL Group Members
    allows:
      1. Change Group Name
      2. Change Group Avatar
      3. Change Group Notice
      4. Invite Friends to join the Groups
  * @Author: Shizhong Shang
  * @Date: 2022/12/25 12:22
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/03 13:04
-->
<template>
  <div class="all flex">
    <div class="avatar-and-members flex">
      <div class="avatar flex">
        <div class="this-font">{{ $t("groupSetting.groupAvatar") }}</div>
        <div class="g-avatar">
          <el-image
            class="g-avatar"
            :src="gAvatar"
            :preview-src-list="[gAvatar]"
            fit="cover"
          />
        </div>
        <div class="add-btn">
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
              <el-button type="primary" round class style="margin-top: 1vh">{{
                $t("groupSetting.addAvatar")
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
      <div class="member flex">
        <div class="this-font">{{ $t("groupSetting.groupMember") }}</div>
        <el-scrollbar wrap-class="member-scroll-bar" class="scroll">
          <div class="scroll-bar flex">
            <div
              v-for="member in memberList"
              :key="member.id"
              class="scroll-item"
            >
              <div :class="imgParent" @click="onDel(member.id)">
                <img :src="member.avatar" class="imgClass" />
              </div>
              <div class="memberName">{{ member.uname }}</div>
            </div>
            <div>
              <el-avatar
                :size="100"
                :icon="Plus"
                @click="toPopWin"
                class="scroll-item oper"
              />
            </div>
            <div>
              <el-avatar
                :size="100"
                :icon="Minus"
                @click="delMember"
                class="scroll-item oper"
                style="display: none"
              />
            </div>
          </div>
        </el-scrollbar>
      </div>
    </div>
    <div class="changeable">
      <div class="item flex i1">
        <div class="change flex">
          <div class="this-font">{{ $t("groupSetting.groupName") }}</div>
          <el-input
            v-model="gName"
            class="text name-bar"
            :maxlength="20"
            :minlength="1"
            show-word-limit
            size="large"
            @change="changeGName"
          ></el-input>
        </div>
      </div>

      <div class="item flex i2">
        <div class="change flex">
          <div class="this-font">{{ $t("groupSetting.importantNotice") }}</div>
          <el-input
            v-model="gNotice"
            :rows="4"
            resize="none"
            type="textarea"
            :maxlength="200"
            :minlength="1"
            show-word-limit
            class="text notice-bar"
            @change="changeGNotice"
          ></el-input>
        </div>
      </div>
    </div>
    <PopWinFriendList
      :dialog-visible="dialogFormVisible"
      :list="inviteList"
      @closeWin="closePop"
      @addFun="addToList"
    ></PopWinFriendList>
  </div>
</template>
<script setup lang="ts">
import { onMounted } from "vue";
import { reactive, ref, watch } from "vue";
import { useI18n } from "vue-i18n";
import useUserStore from "@/stores/userStore";
import { storeToRefs } from "pinia";
import InfoItem from "@/components/InfoItem.vue";
import { format } from "@/utils/time.js";
import {
  showMemberList,
  changeGroupInfo,
  sendGroupInvite,
} from "@/api/group.js";
import { ElMessage } from "element-plus";
import { Plus, Minus } from "@element-plus/icons-vue";
import { uploadPic, deletePic } from "@/api/upload";
import PopWinFriendList from "@/views/searchAndCreate/PopWinFriendList.vue";
import type { UploadInstance, UploadProps, UploadRawFile } from "element-plus";
import { genFileId } from "element-plus";

const store = useUserStore();
const { token } = storeToRefs(store);
const gId = ref(store.getGroupId);
const gName = ref(store.getGroupName);
const gNotice = ref(store.getNotice);
const gAvatar = ref(store.getGroupAvatar);
const { t } = useI18n();
const counter = ref(0);
interface member {
  id: String,
  avatar: String,
  uname: String
}
const memberList = reactive<member[]>([]);
const inviteList = reactive([]);
var imgParent = ref("circle");
const dialogFormVisible = ref(false); 

const uploadRef = ref<UploadInstance>();
const file = reactive([]);

function toPopWin() {
  dialogFormVisible.value = true;
}
function closePop() {
  dialogFormVisible.value = false;
}
// test group members with fake data
function test() {
  const testList = [
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg",
      state: 2,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
      state: 1,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg",
      state: 1,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg",
      state: 1,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      uname: "mike",
      avatar:
        "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
  ];
  for (let i = 0; i < 5; i++) {
    memberList.push(...testList);
  }
}
/**
 * @description: image list exceed limit 1 action
 * @param {byte} uploadFile the recent uploaded image
 */
const handleExceed: UploadProps["onExceed"] = (files) => {
  uploadRef.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  uploadRef.value!.handleStart(file);
}
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
        "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
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
            gAvatar.value = res.data.data;
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
  await del(gAvatar.value);
  await submitUpload();
  file.length = 0;
  uploadRef.value!.clearFiles();
  store.groupAvatar = gAvatar.value;
  changeGAvatar();
}
/**
 * @description: load all group members by HTTP request
 * @return change member List
 */
function getMember() {
  showMemberList(token.value, gId.value)
    .then((res) => {
      if (res.data.success) {
        memberList.push(...res.data.data);
        for(let i=0; i<memberList.length; i++) {
          inviteList.push(memberList[i]);
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
        message: t("groupSetting.getMemberError"),
        showClose: true,
        grouping: true,
      });
    });
}
/**
  * @description: change group name
  * @return group information change
*/
function changeGName() {
  changeInfo(t("groupSetting.changeName"), t("groupSetting.changeNameError"));
}
/**
  * @description: change group notice
  * @return group information change
*/
function changeGNotice() {
  changeInfo(
    t("groupSetting.changeNotice"),
    t("groupSetting.changeNoticeError")
  );
}
/**
  * @description: change group avatar
  * @return group information change
*/
function changeGAvatar() {
  changeInfo(
    t("groupSetting.changeAvatar"),
    t("groupSetting.changeAvatarError")
  );
}
/**
  * @description: overall function which send HTTP changeGroupInfo
  * @param {String} successMsg Success message
  * @param {String} ErrorMsg Error message
  * @return group information change
*/
function changeInfo(successMsg, ErrorMsg) {
  const gInfo = {
    id: gId.value,
    name: gName.value,
    avatar: gAvatar.value,
    notice: gNotice.value,
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
        message: ErrorMsg,
        showClose: true,
        grouping: true,
      });
      console.log(err);
    });
}
/**
  * @description: kickout group members [not used]
*/
function delMember() {
  imgParent.value =
    imgParent.value == "circle" ? "del-circle circle" : "circle";
}
/**
  * @description: to member kickout mode [not used]
*/
function onDel(id: String) {
  if (imgParent.value == "circle") {
    return;
  }
}
/**
  * @description: send a friend group invite list
  * @param {Object} obj friend information
  * @return success / fail
*/
function addToList(obj) {
  inviteList.push(obj);
  let inviteInfo = {
    groupId: gId.value,
    receiverId: obj.id,
    message: "",
  };
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
  * @description: test hover function
*/
function showHover() {
  alert("hover");
}
/**
  * @description: get all group member when page mounted
*/
onMounted(() => {
  getMember();
});
</script>
<style scoped>
.this-font {
  font-size: xx-large;
}
.flex {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}
.avatar {
  justify-content: flex-start;
  height: 100%;
}
.all {
  height: 100%;
}
.member {
  justify-content: flex-start;
  margin-left: 5vw;
  margin-right: 20px;
}
@media screen and (min-width: 1100px) {
  .member {
    width: 60vw;
  }
}
@media screen and (min-height: 800px) {
  .member {
    height: 400px;
  }
}
@media screen and (max-height: 799px) and (min-height: 700px) {
  .member {
    height: 300px;
  }
}
@media screen and (max-height: 699px) and (min-height: 600px) {
  .member {
    height: 200px;
  }
}
@media screen and (max-height: 599px) {
  .member {
    height: 200px;
    margin-top: -50px;
  }
}
.item {
  width: 100%;
}
.change {
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
  height: 10%;
  width: 100%;
}
.avatar-and-members {
  flex-flow: row nowrap;
}
@media screen and (max-height: 540px) {
  .avatar-and-members {
    margin-top: -20px;
  }
}
.memberName {
  text-align: center;
}
.scroll-bar {
  flex-flow: row wrap;
  justify-content: flex-start;
  align-items: flex-start;
}
.scroll {
  background-color: bisque;
  padding: 10px;
  border-radius: 20px;
  min-width: 300px;
  width: 100%;
}
.member-scroll-bar {
  width: 50vw;
}
.scroll-item {
  margin-left: 10px;
}
.oper:hover {
  background-color: antiquewhite;
}
img {
  height: 100%;
  width: 100%;
}
.circle {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
  text-align: center;
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.del-circle:hover .imgClass {
  content: url(https://cdn-icons-png.flaticon.com/512/56/56889.png);
  height: 50%;
  width: 70%;
}
.del-circle {
  background-color: #dedfe0;
}
.g-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}
.add-btn {
  text-align: center;
}
.name-bar {
  width: 33%;
}
.notice-bar {
  width: 50%;
}
@media screen and (min-height: 910px) {
  .changeable {
    width: 100%;
    padding: 0;
    margin: 0 0;
    display: block;
  }
  .name-bar {
    width: 33%;
  }
  .notice-bar {
    width: 50%;
  }
  .i1 {
    top: -40px;
  }
}
@media screen and (max-height: 909px) {
  .changeable {
    width: 100%;
    padding: 0;
    margin: 0;
    display: -webkit-flex; /* Safari */
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    align-items: center;
  }
  .name-bar {
    width: 66%;
  }
  .notice-bar {
    width: 100%;
  }
  @media screen and (max-height: 599px) {
    .changeable {
      width: 100%;
      padding: 0;
      margin-top: -50px;
    }
  }
}
</style>
