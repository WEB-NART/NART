<!--
  * @FileDescription: Searching Friends I Have Page
        show all possible friends related to the searching result
  * @Author: Shizhong Shang
  * @Date: 2022/12/25 12:09
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/01 14:32
-->
<template>
  <div id="all">
    <el-scrollbar height="75vh" id="all">
      <ul v-infinite-scroll="load" class="infinite-list">
        <li v-for="f in fList" :key="f.id">
          <result-item
            v-if="f.uname"
            :avatar="f.avatar"
            :id="f.id"
            :username="f.uname"
            :button-label="t('friendIHave.chat')"
            @delItem="close"
            @btnFunc="chatBtn"
          ></result-item>
          <result-item
            v-else
            :avatar="f.avatar"
            :id="f.id"
            :username="f.name"
            :button-label="t('friendIHave.chat')"
            @delItem="close"
            @btnFunc="chatBtn"
          ></result-item>
        </li>
      </ul>
    </el-scrollbar>
  </div>
</template>
<script setup>
import { ref, reactive, watch, onBeforeUnmount, onMounted,  } from "vue";
import { useI18n } from "vue-i18n";
import useUserStore from "@/stores/userStore";
import { useFriendStore } from "@/stores/friendStore";
import { storeToRefs } from "pinia";
import { ElMessage } from "element-plus";
import { sendFriendRequest } from "@/api/friend";
import ResultItem from "@/components/ResultItem.vue";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const friendStore = useFriendStore();
const router = useRouter();
const { token } = storeToRefs(userStore);
const { fList } = storeToRefs(friendStore);
const { t } = useI18n();
const dialogVisible = ref(false);
const message = ref("");
/**
 * @description: remove the a result item
 * @param {String} id the result item id
 */
function close(id) {
  friendStore.delItem(id);
}
/**
 * @description: jump to the chatroom of the friend
 * @param {String} id the friend id
 */
function chatBtn(id) {
  router.push({ name: "chatRoom", params: { id: "f" + id } });
}
/**
 * @description: load new results
 */
function load() {
  friendStore.loadNewFriends();
  console.log(fList);
}
</script>
<style scoped>
#all {
  padding: 0;
  margin: -10px 0;
}
</style>
