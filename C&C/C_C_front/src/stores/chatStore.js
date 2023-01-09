import { ElMessage } from "element-plus";
import { defineStore } from "pinia";
import useUserStore from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { searchFriend } from "@/api/friend";
import i18n from "../locals/index.js";
import { UserFilled } from "@element-plus/icons-vue";
import { showFriendList } from "@/api/friend";
import { showGroupList } from "@/api/group";

const store = useUserStore();
const { token } = storeToRefs(store);

export const useChatStore = defineStore("chat", {
  state: () => {
    return {
      isFriendRoom: true,
      chatroomId: "",
    };
  },
  actions: {
    setupWS(ws) {
      
      
    },
    logoutWs(ws) {
      ws = "",
      this.chatroomId = ""
    },
    checkList(isFriend) {
      let list;
      let pageSize;
      if (isFriend) {
        list = this.friendList;
        pageSize = this.friend.page.pageSize;
      } else {
        list = this.groupList;
        pageSize = this.group.page.pageSize;
      }
      let count = 0;
      for (let i = 0; i < list.length; i++) {
        if (list[i].state != 1) {
          count++;
        }
      }
      if (count < pageSize) {
        if(isFriend) {
            loadFriend();
        } else {
            loadGroup();
        }
        test(isFriend);
      }
    },
    wsSend(msg) {
        this.ws.send(JSON.stringify(msg));
    },
  },
  persist: {
    enabled: true,
  },
});

export default useChatStore;