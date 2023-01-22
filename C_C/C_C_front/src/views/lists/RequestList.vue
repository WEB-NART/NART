<!--
  * @FileDescription: Request List Page, show all friend requests
  * @Author: Shizhong Shang
  * @Date: 2022/12/25 12:19
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/02 13:02
-->
<template>
  <div>
    <el-scrollbar height="75vh" id="all">
      <ul v-infinite-scroll="searchReqList" class="infinite-list">
        <li v-for="req in reqList" :key="req.id">
          <acceptable-item
            :avatar="req.friendAvatar"
            :senderName="req.friendName"
            :message="req.msg"
            :button-label1="t('reqList.accept')"
            :button-label2="t('reqList.reject')"
            :id="req.id"
            @accept="acceptFun"
            @reject="rejectFun"
          ></acceptable-item>
        </li>
      </ul>
    </el-scrollbar>
  </div>
</template>
<script setup>
import { reactive, ref, watch } from "vue";
import AcceptableItem from "@/components/AcceptableItem.vue";
import { showFriendRequests } from "@/api/friend";
import useUserStore from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { useI18n } from "vue-i18n";
import { responseFriendReq } from "@/api/friend";
import { ElMessage } from "element-plus";

const store = useUserStore();
const { token } = storeToRefs(store);
const loading = ref(false);
const nodata = ref(false);
const page = reactive({
  pageSize: 5,
  pageNum: 1,
});
const { t } = useI18n();
const reqList = reactive([]); // request list
const counter = ref(0);

/**
  * @description: test for searchReqList
*/ 
function testList() {
  const test = [
    {
      friendAvatar:
        "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
      friendName: "tony",
      msg: "respond me!!",
      id: (1 + counter.value).toString(),
    },
    {
      friendAvatar:
        "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
      friendName: "tony",
      msg: "respond me!!",
      id: (2 + counter.value).toString(),
    },
    {
      friendAvatar:
        "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
      friendName: "tony",
      msg: "respond me!!",
      id: (3 + counter.value).toString(),
    },
    {
      friendAvatar:
        "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
      friendName: "tony",
      msg: "respond me!!",
      id: (4 + counter.value).toString(),
    },
    {
      friendAvatar:
        "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100",
      friendName: "tony",
      msg: "respond me!!",
      id: (5 + counter.value).toString(),
    },
  ];
  if (counter.value < 30) {
    reqList.push(...test);
    counter.value += 5;
  }
}
/**
  * @description: remove a request according to [id]
  * @param {Number} id request id
  * @return request list change
*/ 
function removef(id) {
  for (let i = 0; i < reqList.length; i++) {
    if (id == reqList[i].id) {
      reqList.splice(i, 1);
      return;
    }
  }
}
/**
  * @description: load new request result to reqList
  * @return request list change
*/ 
function searchReqList() {
  if (!nodata.value && !loading.value) {
    loading.value = true;
    showFriendRequests(token.value, page)
      .then((res) => {
        if (res.data.success) {
          if (res.data.data.length > 0) {
            reqList.push(...res.data.data);
            page.pageNum += 1;
          } else {
            nodata.value = true;
          }
        } else {
          ElMessage({
            type: "error",
            message: t("reqList.loadError"),
            showClose: true,
            grouping: true,
          });
        }
      })
      .catch((err) => {
        ElMessage({
          type: "error",
          message: t("reqList.loadError"),
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
  * @description: accept a request according to [id]
  * @param {Number} id request id
  * @return request list change
*/ 
function acceptFun(id) {
  responseFriendReq(token.value, id, true)
    .then((res) => {
      if (res.data.success) {
        removef(id);
      } else {
        ElMessage({
          type: "error",
          message: t("reqList.acceptError"),
          showClose: true,
          grouping: true,
        });
      }
    })
    .catch((err) => {
      ElMessage({
        type: "error",
        message: t("reqList.acceptError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    })
    .finally(() => {
      return;
    });
}
/**
  * @description: reject a request according to [id]
  * @param {Number} id request id
  * @return request list change
*/ 
function rejectFun(id) {
  responseFriendReq(token.value, id, false)
    .then((res) => {
      if (res.data.success) {
        removef(id);
      } else {
        ElMessage({
          type: "error",
          message: t("reqList.rejectError"),
          showClose: true,
          grouping: true,
        });
      }
    })
    .catch((err) => {
      ElMessage({
        type: "error",
        message: t("reqList.rejectError"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    })
    .finally(() => {
      return;
    });
}
/**
  * @description:if reqList length <= 5, load again
  * @param {Array} reqList
  * @return request list may change
*/
watch(
  () => reqList.length,
  (length) => {
    if (length <= 5) {
      searchReqList();
    }
  }
);
</script>
<style scoped>
#all {
  padding: 0;
  margin: -10px 0;
}
</style>
