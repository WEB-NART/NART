<!--
  * @FileDescription: Add New Friend Page, 
        accept an input, return a list of user who are not your friend yet
  * @Author: Shizhong Shang
  * @Date: 2022/12/25 12:14
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/01 12:04
-->
<template>
  <div id="all">
    <el-dialog
      v-model="dialogVisible"
      :title="t('newFriendList.inputMsg')"
      width="40%"
      :before-close="clear"
    >
      <div style="width: 40%">
        <el-input
          :placeholder="t('newFriendList.msg')"
          v-model="message"
          maxlength="20"
          show-word-limit
          type="text"
        />
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="clear">{{ $t("buttons.cancel") }}</el-button>
          <el-button type="primary" @click="add">{{ $t("buttons.confirm") }}</el-button>
        </span>
      </template>
    </el-dialog>
    <el-scrollbar id="scroll-bar">
      <ul v-infinite-scroll="load" class="infinite-list">
        <li v-for="nf in nfList" :key="nf.id">
          <result-item
            :avatar="nf.avatar"
            :id="nf.id"
            :username="nf.uname"
            :button-label="t('newFriendList.add')"
            @delItem="close"
            @btnFunc="addBtn"
          ></result-item>
        </li>
      </ul>
    </el-scrollbar>
  </div>
</template>
<script setup>
import { ref, reactive, watch, onBeforeUnmount, onMounted,  } from "vue";
import { useI18n } from "vue-i18n";
import  useUserStore  from "@/stores/userStore";
import { useNewStore } from "@/stores/newStore";
import { storeToRefs } from "pinia";
import { ElMessage } from "element-plus";
import { sendFriendRequest } from "@/api/friend";
import ResultItem from "@/components/ResultItem.vue";
import { useRoute } from "vue-router";

const userStore = useUserStore();
const newStore = useNewStore();
const { token } = storeToRefs(userStore);
const { nfList } = storeToRefs(newStore);
const { t } = useI18n();
const dialogVisible = ref(false);
const message = ref("");
var tempId = ref < Number > -1;

/**
 * @description: remove the chosen user from result list
 * @param {Number} id user id
 * @return change result list
 */
function close(id) {
  newStore.delItem(id);
}
/**
 * @description: close the popup window, and clear input
 * @return change result list
 */
function clear() {
  dialogVisible.value = false;
  message.value = "";
}
/**
 * @description: pop a window, write request message to the chosen user
 * @param {Number} id user id
 */
function addBtn(id) {
  dialogVisible.value = true;
  tempId = id;
}
/**
 * @description: Send friend request HTTP to chosen user
 * @param {String} message request message
 * @param {Number} tempId the chosen user id
 * @return close popWindow, remove chosen user from result list
 */
function add() {
  dialogVisible.value = false;
  sendFriendRequest(token.value, tempId, message.value)
    .then((res) => {
      if (res.data.success) {
        ElMessage({
          type: "success",
          message: t("newFriendList.addSuccess"),
          showClose: true,
        });
        close(tempId);
      } else {
        ElMessage({
          type: "error",
          message: t("newFriendList.addError"),
          showClose: true,
          grouping: true,
        });
      }
    })
    .catch((err) => {
      ElMessage({
        type: "error",
        message: t("newFriendList.addError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    });
}
/**
 * @description: load new results
 * @return change result list
 */
function load() {
  newStore.loadNewFriends();
}
</script>
<style scoped>
#all {
  margin-left: -1em;
  overflow: hidden;
}
@media screen and (min-height: 800px) {
  #scroll-bar {
  height: 65vh;
}
}
@media screen and (max-height: 799px) and (min-height: 680px){
  #scroll-bar {
  height: 63vh;
}
}
@media screen and (max-height: 679px) and (min-height: 580px){
  #scroll-bar {
  height: 60vh;
}
}
@media screen and (max-height: 579px) {
  #scroll-bar {
  height: 57vh;
}
}

</style>
