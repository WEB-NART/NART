<!--
  * @FileDescription: Popup window Page, which show all possible friends 
        related to the searching result
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 12:11
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/01 17:35
-->
<template>
  <el-dialog
    v-model="dialogVisible"
    :title="t('popWin.AddFriend')"
    width="60%"
    @open="openWin"
    @close="closeWin"
  >
    <div class="all">
      <div class="search">
        <el-input
          id="searchF"
          v-model="input"
          :placeholder="t('friendIHave.searchFriend')"
        >
          <template #append>
            <el-button @click="searchFr" :icon="Search" />
          </template>
        </el-input>
      </div>
      <div class="list">
        <el-scrollbar height="65vh" id="all">
          <ul
            v-infinite-scroll="tLoad"
            class="infinite-list"
            :infinite-scroll-distance="10"
          >
            <li v-for="friend in gList" :key="friend.id">
              <result-item
                v-if="friend.uname"
                :avatar="friend.avatar"
                :id="friend.id"
                :username="friend.uname"
                :button-label="t('memberList.add')"
                @delItem="close"
                @btnFunc="addBtn"
              ></result-item>
              <result-item
                v-else
                :avatar="friend.avatar"
                :id="friend.id"
                :username="friend.name"
                :button-label="t('memberList.add')"
                @delItem="close"
                @btnFunc="addBtn"
              ></result-item>
            </li>
          </ul>
        </el-scrollbar>
      </div>
    </div>
  </el-dialog>
</template>
<script setup>
import { computed, reactive, ref } from "vue";
import useUserStore from "@/stores/userStore";
import { useFriendStore } from "@/stores/friendStore.js";
import { storeToRefs } from "pinia";
import { Search } from "@element-plus/icons-vue";
import { searchFriend } from "@/api/friend.js";
import ContactList from "@/components/ContactList.vue";
import { useI18n } from "vue-i18n";
import ResultItem from "@/components/ResultItem.vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { useNewStore } from "@/stores/newStore";
import { showFriendList } from "@/api/friend";

const { t } = useI18n();

const store = useUserStore();
const Fstore = useFriendStore();
const { name, avatar, token } = storeToRefs(store);
const { gList } = storeToRefs(Fstore);
const router = useRouter();
const loading = ref(false);
const nodata = ref(false);
const page = reactive({
  pageSize: 7,
  pageNum: 1,
});
var input = ref("");
const props = defineProps({
  dialogVisible: Boolean,
  list: Array,
});
const emit = defineEmits(["closeWin", "addFun"]);
const close = ((id) => {let obj = Fstore.delGItem(id)});
const pop = (() => {dialogVisible.value = true});

/**
 * @description: Add the user, and take add action
 * @param {String} id user id
 */
function addBtn(id) {
  let obj = Fstore.delGItem(id);
  emit("addFun", obj);
}
/**
 * @description: Search Result Function
 * @param {String} input searchInput
 * @return result list change
 */
function searchFr() {
  let temp = input.value;
  //console.log("temp " + temp);
  if (temp == undefined || temp == "") {
    temp = "-1";
  }
  searchFriend(token.value, temp, page)
    .then((res) => {
      if (res.data.success) {
        Fstore.gList = getDiff(friendList, props.list);
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
        message: t("friendIHave.searchError"),
        showClose: true,
        grouping: true,
      });
    });
}
/**
 * @description: Load new results to the popWindow
 * @return result list change
 */
function tLoad() {
  //console.log("tLoad");
  return Fstore.loadNewGFriends();
}
/**
 * @description: Close the popWindow
 */
function closeWin() {
  emit("closeWin");
}
/**
 * @description: Open the popWindow action
 */
function openWin() {
  //console.log(props.list);
  Fstore.loadFirstGList(props.list);
}
</script>
<style scoped>
.all {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
.list {
  width: 100%;
}
</style>
