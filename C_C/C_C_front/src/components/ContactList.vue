<!--
  * @FileDescription: Contact List Component, include:
      1. item list
      each item include:
        1. avatar
        2. popout menu:
          a. name as a jump button to chat room
          b. delete friend button
          c. hide item button
          d. mute item button
          e. unset item button
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:23
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/29 16:58
-->
<template>
  <div class="all">
    <el-dialog
      v-model="dialogVisible"
      :title="$t('contactList.friend.title')"
      width="30%"
    >
      <span>{{ $t("contactList.friend.del") }}{{ selectMember.name }}</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">{{
            $t("buttons.cancel")
          }}</el-button>
          <el-button type="primary" @click="del(selectMember.id)">{{
            $t("buttons.confirm")
          }}</el-button>
        </span>
      </template>
    </el-dialog>
    <div class="main-scroll">
      <el-scrollbar
        height="77.5vh"
        id="scroll-bar"
        wrap-style="min-height:400px;"
      >
        <ul v-infinite-scroll="load" class="infinite-list">
          <li
            v-for="member in list"
            :key="member.id"
            :class="detectHidden(member.state)"
          >
            <div style="display: flex; align-items: center">
              <el-popover placement="right" :width="30" trigger="click">
                <template #reference>
                  <div class="colm">
                    <el-badge
                      is-dot
                      class="item"
                      :hidden="!member.newMsg"
                      :type="checkType(member.state)"
                    >
                      <el-avatar
                        v-if="props.isFriend"
                        :src="member.avatar"
                        @click="selectMember = member"
                      />
                      <el-avatar
                        v-else
                        size="large"
                        :src="member.avatar"
                        @click="selectMember = member"
                      />
                    </el-badge>
                  </div>
                </template>
                <div style="z-index: 500">
                  <el-button class="popLabel" text @click="toChat(member)">
                    {{ showName(member.name) }}
                  </el-button>
                  <el-button
                    type="danger"
                    :icon="Delete"
                    circle
                    class="button"
                    size="small"
                    id="b1"
                    @click="dialogVisible = true"
                  />
                  <div id="bt">
                    <el-button-group size="large">
                      <el-button
                        v-if="member.state == 1"
                        class="button"
                        round
                        type="primary"
                        :size="btnSize"
                        :id="member.id"
                        @click="unset(member)"
                        >&nbsp;{{ $t("contactList.unset") }}&nbsp;</el-button
                      >
                      <el-button
                        v-else
                        class="button"
                        round
                        type="success"
                        :size="btnSize"
                        :id="member.id"
                        @click="hide(member)"
                        >&nbsp;{{ $t("contactList.hide") }}&nbsp;</el-button
                      >
                      <el-button
                        v-if="member.state == 2"
                        class="button"
                        round
                        type="primary"
                        :size="btnSize"
                        :id="member.id+'mute'"
                        @click="unset(member)"
                        >&nbsp;{{ $t("contactList.unset") }}&nbsp;</el-button
                      >
                      <el-button
                        v-else
                        class="button"
                        round
                        type="info"
                        :size="btnSize"
                        :id="member.id+'mute'"
                        @click="mute(member)"
                        >&nbsp;{{ $t("contactList.mute") }}&nbsp;</el-button
                      >
                    </el-button-group>
                  </div>
                </div>
              </el-popover>
            </div>
          </li>
          <li @click="forceLoad">load more</li>
        </ul>
      </el-scrollbar>
    </div>
  </div>
</template>
<script setup>
import { reactive, ref, watch } from "vue";
import useUserStore from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { Delete } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import {
  showFriendList,
  hideFriend,
  muteFriend,
  delFriend,
  unsetFriend,
} from "@/api/friend";
import {
  showGroupList,
  hideGroup,
  muteGroup,
  leaveGroup,
  unsetGroup,
} from "@/api/group";
import { UserFilled } from "@element-plus/icons-vue";
import { useI18n } from "vue-i18n";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const props = defineProps({
  isFriend: Boolean,
  showAll: Boolean,
  param: {
    page: {
      pageSize: Number,
      pageNum: Number,
    },
  },
});
const loading = ref(false);
const nodata = ref(false);
const store = useUserStore();
const { token } = storeToRefs(store);
const minW = ref("60px");
const btnSize = "default";
var list = reactive([]);
var selectMember = reactive({});
const dialogVisible = ref(false);
const bgColor = reactive({
  backgroundColor: "#fef0f0",
});
const { t } = useI18n();
const emit = defineEmits(["addPage"]);
function checkType(state) {
  if (state == 2) {
    return "info";
  } else if (state == 1) {
    return "success";
  } else if (state == 0) {
    return "danger";
  }
  return "success";
}
function detectHidden(state) {
  if (!props.showAll && state == 1) {
    return "hide";
  }
  return "infinite-list-item";
}
/**
  * @description: test for load with fake data
*/
function loadTest() {
  const testList = [
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg",
      state: 2,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
      state: 1,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg",
      state: 1,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg",
      state: 1,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
    {
      id: "3642178321",
      name: "mike",
      avatar:
        "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
      state: 0,
      notice: "dhsajkdshajdklhsjlafk",
    },
  ];
  if (props.param.page.pageNum == 10) {
    return;
  }
  if (!loading.value) {
    loading.value = true;
    list.push(...testList);
    props.param.page.pageNum += 1;
  }
  loading.value = false;
}
/**
  * @description: call load function
  * @return list change
*/ 
function load() {
  if (!loading.value && !nodata) {
    forceLoad();
  }
}
/**
  * @description: load new contact member to list
  * @return list change
*/
function forceLoad() {
  loading.value = true;
  let result;
  let page = {
    pageSize: props.param.page.pageSize,
    pageNum: props.param.page.pageNum,
  };
  if (props.isFriend) {
    result = showFriendList(token.value, page);
  } else {
    result = showGroupList(token.value, page);
  }
  result
    .then((res) => {
      if (res.data.success) {
        if (res.data.data.length <= 0) {
          nodata.value = true;
        } else {
          nodata.value = false;
          list.push(...res.data.data);
          emit("addPage", props.isFriend);
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
      let msg;
      if (props.isFriend) {
        msg = t("contactList.friend.ListError");
      } else {
        msg = t("contactList.group.ListError");
      }
      ElMessage({
        type: "error",
        message: t("contactList.loadError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    })
    .finally(() => {
      loading.value = false;
    });
}
/**
 * @description: hide a contact member HTTP
 * @param {object} member contact member object
 * @return member is hidden / fail
 */
function hide(member) {
  member.state = 1;
  selectMember = {};
  let result;
  if (props.isFriend) {
    result = hideFriend(token.value, member.id);
  } else {
    result = hideGroup(token.value, member.id);
  }
  result
    .then((res) => {
      if (res.data.success) {
        member.state = 1;
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
      let msg;
      if (props.isFriend) {
        msg = t("contactList.friend.hideError");
      } else {
        msg = t("contactList.group.hideError");
      }
      ElMessage({
        type: "error",
        message: t("contactList.friend.hideError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    })
    .finally(() => {});
}
/**
 * @description: mute a contact member HTTP
 * @param {object} member contact member object
 * @return member is muted / fail
 */
function mute(member) {
  member.state = 2;
  selectMember = {};
  let result;
  if (props.isFriend) {
    result = muteFriend(token.value, member.id);
  } else {
    result = muteGroup(token.value, member.id);
  }
  result
    .then((res) => {
      if (res.data.success) {
        member.state = 2;
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
      let msg;
      if (props.isFriend) {
        msg = t("contactList.friend.muteError");
      } else {
        msg = t("contactList.group.muteError");
      }
      ElMessage({
        type: "error",
        message: t("contactList.friend.muteError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    })
    .finally(() => {});
}
/**
 * @description: unset a contact member HTTP
 * @param {object} member contact member object
 * @return member is unsetted / fail
 */
function unset(member) {
  member.state = 0;
  selectMember = {};
  let result;
  if (props.isFriend) {
    result = unsetFriend(token.value, member.id);
  } else {
    result = unsetGroup(token.value, member.id);
  }
  result
    .then((res) => {
      if (res.data.success) {
        member.state = 0;
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
      let msg;
      if (props.isFriend) {
        msg = t("contactList.friend.unsetError");
      } else {
        msg = t("contactList.group.unsetError");
      }
      ElMessage({
        type: "error",
        message: t("contactList.friend.unsetError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    })
    .finally(() => {});
}
/**
 * @description: delete a contact member HTTP
 * @param {String} id contact member id
 * @return member is deleted / fail
 */
function del(id) {
  dialogVisible.value = false;
  let result;
  if (props.isFriend) {
    result = delFriend(token.value, id);
  } else {
    result = leaveGroup(token.value, id);
  }
  result
    .then((res) => {
      if (res.data.success) {
        for (var i = 0; i < list.length; i++) {
          if (list[i].id == id) {
            list.splice(i, 1);
          }
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
      let msg;
      if (props.isFriend) {
        msg = t("contactList.friend.delError");
      } else {
        msg = t("contactList.group.delError");
      }
      ElMessage({
        type: "error",
        message: t("contactList.friend.delError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    })
    .finally(() => {});
}
/**
 * @description: jump to a contact member chat room
 * @param {object} member contact member object
 * @return page jumping / fail
 */
function toChat(member) {
  setNewMsg(member.id, false);
  if (props.isFriend) {
    router.push({ name: "chatRoom", params: { id: "f" + member.id } });
  } else {
    const info = {
      gid: member.id,
      note: member.notice,
      gName: member.name,
      gAvatar: member.avatar,
    };
    store.updategroupInfo(info);
    router.push({ name: "chatRoom", params: { id: "g" + member.id } });
  }
}
/**
  * @description: update new WebSocket Message
  * @param {Boolean} isFriend current list is friend list or not
  * @param {String} id contact member id
  * @param {Boolean} show show the message or not
  * @return show unread message
*/ 
const noticeNewMsg = (isFriend, id, show) => {
  if (isFriend) {
    if (props.isFriend) {
      setNewMsg(id, show);
    }
  } else {
    if (!props.isFriend) {
      setNewMsg(id, show);
    }
  }
};
/**
  * @description: set the given contact member with new/no new message
  * @param {String} id contact member id
  * @param {Boolean} show show the message or not
  * @return show unread message / not
*/ 
function setNewMsg(id, show) {
  for (let i = 0; i < list.length; i++) {
    if (list[i].id == id) {
      list[i].newMsg = show;
      //console.log(list[i]);
    }
  }
}
/**
  * @description: format contact member name
  * @param {String} name contact member name
  * @return formated member name
*/ 
function showName(name) {
  name = String(name);
  if (name.length > 5) {
    return name.substring(0, 4) + "...";
  }
  return name;
}
/**
  * @description: set background color when page mounted
*/
onMounted(() => {
  if (props.isFriend) {
    bgColor.backgroundColor = "#fef0f0";
  } else {
    bgColor.backgroundColor = "#faecd8";
    minW.value = "90px";
  }
  forceLoad();
});
watch(
  () => list.length,
  (length) => {
    if (length <= props.param.page.pageSize) {
      load();
    }
  }
);
defineExpose({
  noticeNewMsg,
});
</script>
<style scoped>
.main-scroll {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: column wrap;
  min-width: v-bind(minW);
  min-height: 400px;
  z-index: 5;
}
.colm {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: column wrap;
  justify-content: center;
  text-align: center;
  margin-left: -25px;
}
#scroll-bar {
  margin-top: 0%;
  min-height: 400px;
  overflow-y: visible;
  background-color: v-bind("bgColor.backgroundColor");
}
.infinite-list {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: column wrap;
  justify-content: center;
  z-index: 4;
  min-height: 400px;
}
.infinite-list-item {
  display: -webkit-flex; /* Safari */
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  min-height: v-bind(minW);
  min-width: v-bind(minW);
  background-color: v-bind("bgColor.backgroundColor");
  text-align: center;
  z-index: 4;
}
.hide {
  display: none;
}
.all {
  overflow: clip;
  min-height: 400px;
}
.popLabel {
  display: inline-block;
  font-size: 20px;
  font-weight: bolder;
  padding-right: 10px;
}
.button {
  margin: 0;
  padding: 0;
}
#bt {
  display: block;
  text-align: center;
  margin-top: 5px;
}
#b1 {
  float: right;
  margin-top: 3px;
}
.el-button:not(#b1) {
  min-width: 40px;
  width: 60px;
}
.dialog {
  z-index: 500;
}
</style>
