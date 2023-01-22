<template>
  <div>
    <el-button @click="wSend">
      send
    </el-button>
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
import { onMounted, onUpdated, reactive, ref } from "vue";
import { RouterLink, RouterView } from "vue-router";
import  useUserStore  from "@/stores/userStore";
import  useChatStore  from "@/stores/chatStore";
import { storeToRefs } from "pinia";

const router = useRouter();
const store = useUserStore();
const chatStore = useChatStore();
const { token, avatar, name } = storeToRefs(store);
// const rv = ref(null);
// const fnoticeNew = ref(null);
// const gnoticeNew = ref(null);

// function wSend(input) {
//   console.log(input);
//   ws.send(JSON.stringify(input));
// }
function wsSend(input, type) {
  let roomType = "friend";
  let json = {
    "msg": input,
    "msgType": type,
    "sender": token.value,
    "senderName": name.value,
    "senderAvatar": avatar.value,
    "receiver": "1606447871244648449",
    "receiverType": roomType
  };
  return json;
}

function wSend() {
  let msg = wsSend("just Test", "text")
  chatStore.wsSend(msg);
  // ws.onopen = () =>ws.send(JSON.stringify(msg));
}


// ws.onmessage = function (evt) {
//   alert("onMessage");
//   var dataStr = evt.data;

//   var res = JSON.parse(dataStr);
//   let rType = res.receiverType;
//   let gid = res.receiver;
//   let sid = res.sender;

//   if (rType == "friend") {
//     fnoticeNew.value.noticeNewMsg(true, sid, true);
//   } else {
//     gnoticeNew.value.noticeNewMsg(false, gid, true);
//   }

//   if (router.currentRoute.value.name == "chatRoom") {
//     let str = router.currentRoute.value.params.id;
//     let type = str[0];
//     let roomId = str.slice(1);
//     if (type == "f") {
//       if (sid == roomId && rType == "friend") {
//         fnoticeNew.value.noticeNewMsg(true, sid, false);
//         rv.value.receiveMsg(res);
//       }
//     } else {
//       if (gid == roomId && rType == "group") {
//         gnoticeNew.value.noticeNewMsg(false, gid, false);
//         rv.value.receiveMsg(res);
//       }
//     }
//   }
// };
</script>
<style scoped></style>
