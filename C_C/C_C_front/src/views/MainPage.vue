<!--
  * @FileDescription: The main frame of C&C
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 12:07
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/07 20:26
-->
<template>
  <div id="overall">
    <el-container>
      <el-header>
        <div class="header-row">
          <div class="col1">
            <el-image
              src="https://s1.ax1x.com/2023/03/12/ppKvjns.png"
              fit="fill"
              style="width: 100px; height: 50px; margin-left: -6%; margin-right: 30px; border-radius: 10px;"
            />
          </div>
          <div class="col2">
            <el-input
              v-model="searchInput"
              size="large"
              class="col2-input"
              :placeholder="t('findSthNew.placeholder')"
              @change="toSearch"
              :suffix-icon="Search"
            ></el-input>
            <el-button
              :icon="Search"
              class="col2-btn"
              @click="toSearch"
              circle
            />
          </div>
          <div class="col3">
            <el-dropdown trigger="click">
              <span class="el-dropdown-link">
                <el-avatar
                  :src="avatar"
                  fit="contain"
                  style="width: 50px; height: 50px"
                />
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="changeLang()">{{
                    $t("changeLang")
                  }}</el-dropdown-item>
                  <el-dropdown-item
                    @click="router.push({ name: 'postStatus', params: {} })"
                    >{{ $t("main.menu.item1") }}</el-dropdown-item
                  >
                  <el-dropdown-item @click="router.push({ name: 'myStatus' })">
                    <!-- myStatus -->
                    {{ $t("main.menu.item2") }}</el-dropdown-item
                  >
                  <el-dropdown-item
                    v-if="friendShowAll"
                    @click="showAllFriends"
                    >{{ $t("main.menu.item3_hide") }}</el-dropdown-item
                  >
                  <el-dropdown-item v-else @click="showAllFriends">{{
                    $t("main.menu.item3")
                  }}</el-dropdown-item>
                  <el-dropdown-item
                    v-if="groupShowAll"
                    @click="showAllGroups"
                    >{{ $t("main.menu.item4_hide") }}</el-dropdown-item
                  >
                  <el-dropdown-item v-else @click="showAllGroups">{{
                    $t("main.menu.item4")
                  }}</el-dropdown-item>
                  <el-dropdown-item @click="editPInfo">{{
                    $t("main.menu.item5")
                  }}</el-dropdown-item>
                  <el-dropdown-item @click="logout">{{
                    $t("main.menu.item6")
                  }}</el-dropdown-item>
                  <!-- <el-dropdown-item @click="router.push({ name: 'testVue' })">
                    test websocket</el-dropdown-item
                  > -->
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-main class="first-main">
        <el-container>
          <el-header class="header">
            <div class="header-text t1">{{ $t("main.bar.item1") }}</div>
            <div class="header-text t2">{{ $t("main.bar.item2") }}</div>
            <div class="header-menu">
              <el-menu
                class="el-menu-demo"
                mode="horizontal"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                :ellipsis="false"
                @select="menuClick"
              >
                <el-menu-item index="findNew" class="menuItem">{{
                  $t("main.bar.item3")
                }}</el-menu-item>
                <el-menu-item index="reqList" class="menuItem">{{
                  $t("main.bar.item4")
                }}</el-menu-item>
                <el-menu-item index="inviteList" class="menuItem">{{
                  $t("main.bar.item5")
                }}</el-menu-item>
                <el-menu-item index="statusList" class="menuItem">{{
                  $t("main.bar.item6")
                }}</el-menu-item>
              </el-menu>
            </div>
          </el-header>
          <el-main class="main-main">
            <el-container>
              <el-aside width="10vw" id="mainAside">
                <div class="aside-main">
                  <div class="main-bar" id="m1">
                    <contact-list
                      isFriend
                      :showAll="friendShowAll"
                      :param="friendParam"
                      @addPage="PagePlus"
                      ref="fnoticeNew"
                    ></contact-list>
                  </div>
                  <div class="main-bar" id="m2">
                    <contact-list
                      :isFriend="false"
                      :showAll="groupShowAll"
                      :param="groupParam"
                      @addPage="PagePlus"
                      ref="gnoticeNew"
                    ></contact-list>
                  </div>
                </div>
              </el-aside>
              <el-main width="90vw" class="mainPart">
                <router-view @wSend="wSend" v-slot="{ Component }">
                  <component ref="order" :is="Component" />
                </router-view>
              </el-main>
            </el-container>
          </el-main>
        </el-container>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { onMounted, onUpdated, reactive, ref } from "vue";
import useUserStore from "@/stores/userStore";
import useChatStore from "@/stores/chatStore";
import { useFriendStore } from "@/stores/friendStore.js";
import { storeToRefs } from "pinia";
import { Search } from "@element-plus/icons-vue";
import { searchFriend } from "@/api/friend";
import ContactList from "@/components/ContactList.vue";
import { RouterLink, RouterView } from "vue-router";
import { useI18n } from "vue-i18n";
import AcceptableItem from "@/components/AcceptableItem.vue";
import ResultItem from "@/components/ResultItem.vue";
import StatusItem from "@/components/StatusItem.vue";
import ChatMessage from "@/components/ChatMessage.vue";
import MyStatusItem from "@/components/MyStatusItem.vue";
import InfoItem from "@/components/InfoItem.vue";
import ChatRoom from "@/views/chat/ChatRoom.vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { url } from "@/request/token";

const pics = reactive([
  "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
  "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
  "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
  "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
  "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
]);

var ws = "";
const router = useRouter();
const store = useUserStore();
const chatStore = useChatStore();
const Fstore = useFriendStore();
const { name, avatar, token } = storeToRefs(store);

var searchInput = ref("");
var friendParam = reactive({
  page: {
    pageSize: 10,
    pageNum: 1,
  },
});
var groupParam = reactive({
  page: {
    pageSize: 10,
    pageNum: 1,
  },
});
var lang = "zh";
var friendShowAll = ref(false);
var groupShowAll = ref(false);
const { t } = useI18n();
const { locale } = useI18n();
const fnoticeNew = ref();
const gnoticeNew = ref();
const order = ref();

let screenWidth = ref(document.body.clientWidth);
let screenHeight = ref(document.body.clientHeight);
let ellipsisBool = screenWidth.value < 850;
// watch(document.body.clientWidth, () => {
//   console.log(document.body.clientWidth);
// })

/**
 * @description: change Website Language
 */
function changeLang() {
  locale.value = lang;
  localStorage.setItem("lang", lang);
  lang = lang == "en" ? "zh" : "en";
}
/**
 * @description: Show all hiden friends
 */
function showAllFriends() {
  friendShowAll.value = friendShowAll.value == true ? false : true;
}
/**
 * @description: Show all hiden groups
 */
function showAllGroups() {
  groupShowAll.value = groupShowAll.value == true ? false : true;
}
/**
 * @description: logout from the website
 */
function logout() {
  store.logout();
  ws.close();
  router.push({ name: "login" });
}
/**
 * @description: Search from My friends according to searchInput
 * @param {String} searchInput
 * @return To SearchFriendIHave Page
 */
function toSearch() {
  let temp = searchInput.value;

  if (temp == "") {
    temp = "";
  }
  if (Fstore.fSearchHistory != temp) {
    Fstore.fSearchHistory = temp;
    Fstore.loadFirstList();
  }
  router.push({
    name: "searchFriend",
  });
}
/**
 * @description: General Page Jumping function
 * @param {String} index page name
 */
function menuClick(index) {
  router.push({
    name: index,
  });
}
/**
 * @description: FriendList or GroupList Page + 1
 * @param {Boolean} isFriend
 */
function PagePlus(isFriend) {
  if (isFriend) {
    friendParam.page.pageNum += 1;
  } else {
    groupParam.page.pageNum += 1;
  }
}
/**
 * @description: Jump to Editing My friends
 */
function editPInfo() {
  //store.getUserInfo();
  router.push({ name: "editMyInfo" });
}
/**
 * @description: Websocket send function
 * @param {String} input
 */
function wSend(input) {
  //console.log(input);
  ws.send(JSON.stringify(input));
}
var lockReconnect = false;
var wsUrl = "ws://" + url + "/chat/" + token.value;
function createWebSocket(url) {
  try {
    if ("WebSocket" in window) {
      ws = new WebSocket(url);
    }
    initEventHandle();
  } catch (e) {
    reconnect(url);
    console.log(e);
  }
}
function reconnect(url) {
  if (lockReconnect) return;
  lockReconnect = true;
  setTimeout(function () {
    //没连接上会一直重连，设置延迟避免请求过多
    createWebSocket(url);
    lockReconnect = false;
  }, 2000);
}
var heartCheck = {
  timeout: 1000*60, //1分钟发一次心跳
  timeoutObj: null,
  serverTimeoutObj: null,
  reset: function () {
    clearTimeout(this.timeoutObj);
    clearTimeout(this.serverTimeoutObj);
    return this;
  },
  start: function () {
    var self = this;
    this.timeoutObj = setTimeout(function () {
      //这里发送一个心跳，后端收到后，返回一个心跳消息，
      //onmessage拿到返回的心跳就说明连接正常
      ws.send("ping");
      console.log("ping!");
      self.serverTimeoutObj = setTimeout(function () {
        //如果超过一定时间还没重置，说明后端主动断开了
        ws.close(); //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
      }, self.timeout);
    }, this.timeout);
  },
};
function initEventHandle() {
  ws.onclose = function () {
    reconnect(wsUrl);
    console.log("llws close!" + new Date().toLocaleString());
  };
  ws.onerror = function (e) {
    reconnect(wsUrl);
    console.log("llws error! " + e.code);
  };
  ws.onopen = function () {
    heartCheck.reset().start(); //心跳检测重置
    console.log("llws success!" + new Date().toLocaleString());
  };
  ws.onmessage = function (evt) {
    //如果获取到消息，心跳检测重置
    heartCheck.reset().start(); //拿到任何消息都说明当前连接是正常的
    console.log("llws get message:" + evt.data);
    if (evt.data != "pong") {
      //alert("onMessage");
      var dataStr = evt.data;

      var res = JSON.parse(dataStr);
      let rType = res.receiverType;
      let rid = res.receiver;
      let sid = res.sender;

      if (rType == "friend") {
        fnoticeNew.value.noticeNewMsg(true, sid, true);
      } else {
        gnoticeNew.value.noticeNewMsg(false, rid, true);
      }

      if (router.currentRoute.value.name == "chatRoom") {
        let str = router.currentRoute.value.params.id;
        let type = str[0];
        let roomId = str.slice(1);

        if (type == "f") {
          if (sid == roomId && rType == "friend") {
            fnoticeNew.value.noticeNewMsg(true, sid, false);
            //store.setNewMsg(res);
            order.value.receiveMsg(res);
          }
        } else {
          if (rid == roomId && rType == "group") {
            gnoticeNew.value.noticeNewMsg(false, rid, false);
            //store.setNewMsg(res);
            order.value.receiveMsg(res);
          }
        }
      }
    }
  };
}
/**
 * @description: actions when pages mounted
 *    1. setup websocket object
 *    2. mount logout function on window closing action
 */
onMounted(() => {
  createWebSocket(wsUrl); //连接ws
  window.onbeforeunload = (e) => {
    e = e || window.event;
    // if(e) {
    //   e.returnValue = 'Close Notice';
    // }
    logout().then(() => {
      return "Close Notice";
    });
  };
  console.log("ellipsisBool: ", ellipsisBool);
  console.log("screenWidth:", screenWidth.value);
});
</script>
<style scoped>
.el-main {
  overflow: visible;
}
.header-row {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 0px;
  padding-left: 10px;
}
.col2-input {
  width: 60vw;
  min-width: 200px;
}
.aside-main {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: flex-start;
}
#mainAside {
  min-width: 150px;
  min-height: 400px;
}
.main-bar {
  min-height: 400px;
}
#m1 {
  width: 50%;
  min-width: 70px;
  flex: 1 1 auto;
}
#m2 {
  width: 50%;
  min-width: 80px;
  flex: 2 2 auto;
}
.header-text {
  color: #fff;
  background-color: #545c64;
  width: 5vw;
  height: 7vh;
  min-height: 40px;
  max-height: 70px;
  text-align: center;
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-left: 0;
  padding: 0;
}
.t1 {
  min-width: 60px;
}
.t2 {
  min-width: 90px;
}
.header {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  padding: 0;
  margin-bottom: -1vh;
  min-width: 810px;
}
.el-menu-demo {
  height: 7vh;
  min-height: 40px;
}
@media screen and (min-width: 660px) {
  .el-menu-demo {
    max-height: 70px;
    width: 100%;
  }
}
.menuItem {
  width: 25%;
  min-width: 120px;
}
.mainPart {
  flex: auto;
  min-width: 660px;
  min-height: 400px;
  height: 78vh;
  overflow: visible;
  margin-top: 0;
  margin-left: 1px;
  background-color: #fafafa;
}
.main-main {
  margin-top: 1vh;
  margin-left: 0;
  padding: 0;
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  align-content: flex-start;
  min-width: 660px;
  min-height: 400px;
}
.el-dropdown-link {
  margin-left: -5vw;
}
.col2 {
  flex: auto;
  display: -webkit-flex; /* Safari */
  display: flex;
  min-width: 300px;
}
.col2-btn {
  margin-left: -40px;
  margin-top: 5px;
}
.col3 {
  padding-left: 3.5em;
}
.header-menu {
  flex: auto;
}
#overall {
  min-height: 500px;
}
@media screen and (min-height: 635px) {
  #overall {
    padding-top: 20px;
    padding-right: 20px;
  }
}
@media screen and (max-height: 634px) and (min-height: 590px) {
  #overall {
    padding-top: 10px;
    padding-right: 10px;
  }
}
@media screen and (max-height: 589px) {
  #overall {
    padding-top: 0px;
    padding-right: 0px;
  }
}
@media screen and (max-height: 816px) and (min-height: 727px) {
  .first-main {
    padding-top: 0;
    margin-top: 0;
  }
}
@media screen and (max-height: 726px) and (min-height: 544px) {
  .first-main {
    padding-top: 0;
    margin-top: -20px;
  }
}
@media screen and (max-height: 543px) {
  .first-main {
    padding-top: 0;
    margin-top: -30px;
  }
}
</style>
