<!--
  * @FileDescription: Chat Room Page, show sent messages
      allow sending text and images
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 12:25
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/07 22:43
-->
<template>
  <div class="all flex">
    <div class="inner-all">
      <el-button
        class="to-setting"
        @click="router.push({ name: 'groupChatInfo', params: {} })"
        plain
        ><el-icon><Star /></el-icon
      ></el-button>
      <el-scrollbar height="55vh" id="all" always>
        <ul v-infinite-scroll="load" class="infinite-list">
          <li v-for="msg in msgList" :key="msg.msgId">
            <chat-message
              :avatar="msg.senderAvatar"
              :name="msg.senderName"
              :message="msg.msg"
              :type="msg.msgType"
              :time="format(msg.sentDate, false)"
              :isMe="isMe(msg.senderName)"
              :is-group="isGroup"
              :id="msg.msgId"
              :uid="msg.senderId"
            ></chat-message>
          </li>
        </ul>
      </el-scrollbar>
    </div>
    <div class="input-box">
      <chat-input-box @add-pic="addPic" @send-msg="sendMsg"> </chat-input-box>
    </div>
  </div>
</template>
<script setup>
import { ElMessage } from "element-plus";
import {
  ref,
  reactive,
  onMounted,
  onUpdated,
  onBeforeUnmount,
  watch,
} from "vue";
import { useRoute, useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { showGroupChatHistory, showFriendChatHistory } from "@/api/chat";
import useUserStore from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { sendGroupMsg, sendFriendMsg, leaveRoom } from "@/api/chat";

import ChatMessage from "@/components/ChatMessage.vue";
import ChatInputBox from "@/components/ChatInputBox.vue";
import { format } from "@/utils/time.js";

const { t } = useI18n();
const route = useRoute();
const router = useRouter();
const store = useUserStore();
const { token, avatar, name, getNewMsg } = storeToRefs(store);
const counter = ref(0);
const mins = 30;
const msgList = reactive([]); // message list
const isGroup = ref(false);
const roomId = ref("");
const loading = ref(false);
const nodata = ref(false);
const showSetting = ref("");
const showSetting2 = ref("");
const page = reactive({
  pageNum: 1,
  pageSize: 10,
});
var title = ref("");
const emit = defineEmits(["fathre"]);

/**
  * @description:receive WebSocket Message
  * @param {Object} msgInfo message information
  * @return message is add to message list
*/ 
function receiveMsg(msgInfo) {
  let date = new Date();
  let tempMsg = {
    msgId: "-1",
    senderId: msgInfo.sender,
    senderName: msgInfo.senderName,
    senderAvatar: msgInfo.senderAvatar,
    sentDate: {
      year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate(),
      hour: date.getHours(),
      min: date.getMinutes() + 1,
    },
    isMe: false,
    msg: msgInfo.msg,
    msgType: msgInfo.msgType,
  };
  msgList.push(tempMsg);
}
/**
 * @description: test for load function
 */
function loadTest() {
  if (msgList.length > 30) {
    return;
  }
  for (let i = 0; i < page.pageSize; i++) {
    let test = [
      {
        msgId: counter.value.toString(),
        senderId: "114514",
        senderName: "holk",
        senderAvatar:
          "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
        sentDate: {
          year: 2022,
          month: 8,
          day: 24,
          hour: 6,
          min: mins + counter.value,
        },
        isMe: false,
        msg: "cfgvhjbfghjkljhgfdghyjukjyhtgrthyuytrgtyuiythgrfthyjukjyhgffghjjkhgfdfghjkhjgfhdgfhgjhkgjfhdgknkljhuigyutfyrtyctvybuogvyficdtxrutcgihvoufydcitxgckhvyfditxgjhvlfyodickhyfdoitxcydidyci",
        msgType: "text",
      },
      {
        msgId: (counter.value + 1).toString(),
        senderId: "3721893",
        senderName: "zenk",
        senderAvatar: "https://s1.ax1x.com/2022/07/28/vpOkbn.jpg",
        sentDate: {
          year: 2022,
          month: 8,
          day: 24,
          hour: 6,
          min: mins + 1 + counter.value,
        },
        isMe: true,
        msg: "https://s1.ax1x.com/2022/07/28/vpOkbn.jpg",
        msgType: "img",
      },
    ];
    msgList.unshift(...test);
    counter.value += 2;
  }
}
/**
 * @description: load chat history function
 * @return message status list
 */
function load() {
  if (!nodata.value && !loading.value) {
    loading.value = true;
    let result;
    if (isGroup.value) {
      result = showGroupChatHistory(token.value, roomId.value, page);
    } else {
      result = showFriendChatHistory(token.value, roomId.value, page);
    }
    result
      .then((res) => {
        if (res.data.success) {
          if (res.data.data.length <= 0) {
            nodata.value = true;
          } else {
            nodata.value = false;
            msgList.unshift(...res.data.data.reverse());
            page.pageNum += 1;
          }
        } else {
          ElMessage({
            type: "error",
            message: t("chatRoom.LoadError"),
            showClose: true,
            grouping: true,
          });
        }
      })
      .catch((err) => {
        let msg;
        if (isGroup.value) {
          msg = t("chatRoom.friendLoadError");
        } else {
          msg = t("chatRoom.groupLoadError");
        }
        ElMessage({
          type: "error",
          message: t("chatRoom.LoadError"),
          showClose: true,
          grouping: true,
        });
        console.log(err);
      })
      .finally(() => {
        loading.value = false;
      });
  }
}
/**
  * @description: websocket send message function
  * @param {String} input input
  * @param {String} type text/url
  * @return websocket message is sent
*/ 
function wsSend(input, type) {
  let roomType = isGroup.value ? "group" : "friend";
  let json = {
    msg: input,
    msgType: type,
    sender: token.value,
    senderName: name.value,
    senderAvatar: avatar.value,
    receiver: roomId.value,
    receiverType: roomType,
  };
  emit("fathre", json);
}
/**
  * @description: overall send message function
  * @param {String} input input
  * @param {String} type text/url
  * @return message is sent and add to message list
*/ 
function sendMsg(input, type) {
  let date = new Date();
  let tempMsg = {
    msgId: "-1",
    senderId: "-1",
    senderName: name,
    senderAvatar: avatar,
    sentDate: {
      year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate(),
      hour: date.getHours(),
      min: date.getMinutes() + 1,
    },
    isMe: true,
    msg: input,
    msgType: type,
  };
  msgList.push(tempMsg);
  wsSend(input, type);
  sendToBack(input, type);
}
/**
  * @description: send message HTTP request
  * @param {String} input input
  * @param {String} type text/url
  * @return message is sent as HTTP request
*/
function sendToBack(input, type) {
  let msgInfo = {
    chatId: roomId.value,
    msg: input,
    msgType: type,
  };
  let result;
  if (isGroup.value) {
    result = sendGroupMsg(token.value, msgInfo);
  } else {
    result = sendFriendMsg(token.value, msgInfo);
  }
  result
    .then((res) => {
      if (res.data.success) {
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
        message: t("chatRoom.sendError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    })
    .finally(() => {});
}
/**
  * @description: add picture action
  * @param {String} img image url
  * @return call mainPage to add new image
*/
function addPic(img) {
  sendMsg(img, "img");
}
/**
  * @description: set inital page parameters
*/
function setParam() {
  let str = route.params.id;
  let temp = "";
  if (str[0] == "g") {
    isGroup.value = true;
    showSetting.value = "flex";
    showSetting2.value = "-webkit-flex";
  } else if (str[0] == "f") {
    isGroup.value = false;
    showSetting.value = "none";
    showSetting2.value = "none";
  } else {
    ElMessage({
      type: "error",
      message: t("chatRoom.roomTypeError"),
      showClose: true,
    });
  }
  for (let i = 1; i < str.length; i++) {
    temp += str[i];
  }
  roomId.value = temp.toString();
  title.value = isGroup.value ? "Group " : "Friend ";
  nodata.value = false;
  loading.value = false;
  page.pageNum = 1;
  page.pageSize = 10;
}
/**
  * @description: check if uname is the current user
  * @param {String} uname
  * @return {Boolean}
*/
function isMe(uname) {
  if (store.name == uname) {
    return true;
  } else {
    return false;
  }
}
/**
  * @description: leave room and update leave time HTTP
*/
function leave() {
  //console.log("leave Time!!!!!!!!!!!!!!!!!!!");
  leaveRoom(token.value, roomId.value, !isGroup.value)
    .then((res) => {
      if (res.data.success) {
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
        message: t("chatRoom.leaveError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    });
}
/**
  * @description: set initial page parameters when page mounted
*/
onMounted(() => {
  //console.log("mount");
  setParam();
});
/**
  * @description: when page updated, do:
  *   1. leave room and update leave time
  *   2. set initial page parameters
  *   3. clear message list
  *   4. load message history
*/
onUpdated(() => {
  //console.log("update");
  leave();
  setParam();
  msgList.splice(0, msgList.length);
  load();
});
/**
  * @description: leave the room and update leave time before Unmounted
*/
onBeforeUnmount(() => {
  leave();
});
/**
  * @description: when receive new message, add the message to message list
*/
watch(getNewMsg, (value) => {
  if (value != "") {
    receiveMsg(value);
    //store.setNewMsg("");
  }
});
defineExpose({
  receiveMsg,
});
</script>
<style scoped>
.all {
  width: 100%;
  height: 100%;
}
.flex {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: column nowrap;
  justify-content: flex-start;
}
#all {
  transform: rotate(180deg);
  direction: ltr;
}
.inner-all {
  width: 100%;
  height: 200px;
  flex: auto;
}
.infinite-list {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-direction: column-reverse;
  margin-left: -36px;
  margin-right: 26px;
}
.input-box {
  height: 170px;
}
.to-setting {
  display: v-bind(showSetting);
  display: v-bind(showSetting2);
  position: absolute;
  text-align: center;
  top: 50px;
  right: 50px;
  width: 25px;
  height: 25px;
  z-index: 5;
}
.el-scrollbar__bar .is-vertical {
  transform: rotate(180deg);
}
</style>
