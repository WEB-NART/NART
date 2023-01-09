<!--
  * @FileDescription: Friend Information Page, show friend's:
      1. Avatar
      2. Name
      3. Email
      4. post Statuses
  * @Author: Shizhong Shang
  * @Date: 2022/12/25 12:24
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/03 15:59
-->
<template>
  <div class="all">
    <div class="top">
      <div class="avatar">
        <el-avatar class="img" :src="friendAvatar" />
      </div>
      <div class="info">
        <div class="uname">
          <info-item :label="t('infoItem.userName')" :value="friendName"></info-item>
        </div>
        <div class="uemail">
          <info-item :label="t('infoItem.email')" :value="friendEmail"></info-item>
        </div>
      </div>
    </div>
      <el-divider/>
    <div class="bottom">
      <el-scrollbar id="all" wrap-style="height:50vh;">
        <ul v-infinite-scroll="load" class="infinite-list">
        <el-timeline>
          <el-timeline-item
            v-for="status in myStatusList"
            :key="status.statusId"
            :timestamp="format(status.createDate)"
            size="large"
            color="#D9F2E3"
            hollow
            center
          >
          <li>
            <my-status-item
              :isMine="status.isMine"
              :message="status.msg"
              :pictures="status.pics"
              :id="status.statusId"
            ></my-status-item>
          </li>
          </el-timeline-item>
        </el-timeline>
          <li v-show="nodata" id="end">{{ $t("myStatusList.toEnd") }}</li>
        </ul>
      </el-scrollbar>
    </div>
  </div>
</template>
<script setup>
import { onMounted } from "vue";
import { reactive, ref, watch } from "vue";
import { useI18n } from "vue-i18n";
import  useUserStore  from "@/stores/userStore";
import { storeToRefs } from "pinia";
import MyStatusItem from "@/components/MyStatusItem.vue";
import InfoItem from "@/components/InfoItem.vue";
import { showMyStatusList } from "@/api/status";
import { format } from "@/utils/time.js";
import { useRoute } from "vue-router";
import { showUserInfo } from "../../api/user";
import { ElMessage } from "element-plus";

const store = useUserStore();
const { token } = storeToRefs(store);
const { t } = useI18n();
const route = useRoute();
const myStatusList = reactive([]);
const counter = ref(0);
const loading = ref(false);
const nodata = ref(false);
const page = reactive({
  pageSize: 5,
  pageNum: 1,
});
const friendName = ref("FriendName");
const friendEmail = ref("FriendEmail@gmail.com");
const friendAvatar = ref("test.jpg");

/**
  * @description: get friend information
  * @return friend information updated
*/
function getFriendInfo() {
  showUserInfo(token.value, route.params.id)
  .then((res) => {
        if (res.data.success) {
          friendName.value = res.data.data.uname;
          friendEmail.value = res.data.data.email;
          friendAvatar.value = res.data.data.avatar;
        } else {
          ElMessage({
            type: "error",
            message: res.data.msg,
            showClose: true,
          });
        }
      })
      .catch((err) => {
        ElMessage({
          type: "error",
          message: t("myStatusList.loadError"),
          showClose: true,
        });
        console.log(err);
      });
}
/**
 * @description: test for load function
 */
function loadTest() {
  if (counter.value > 10) {
    nodata.value = true;
    return;
  }
  const test = [
    {
      statusId: counter.value.toString,
      msg: "this is a new Status",
      isMine: false,
      createDate: {
        year: 2022,
        month: 8,
        day: 23,
        hour: 23,
        min: 8
      },
      pics: [
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      ],
    },
  ];
  for (let i = 0; i < 5; i++) {
    myStatusList.push(...test);
    counter.value += 1;
  }
}
/**
 * @description: load more friend status
 * @return change my Status List
 */
function load() {
  if (!loading.value && !nodata.value) {
    loading.value = true;
    showMyStatusList(token.value, "-1", page)
      .then((res) => {
        if (res.data.success) {
          if (res.data.data.length > 0) {
            myStatusList.push(...res.data.data);
            page.pageNum += 1;
          } else {
            nodata.value = true;
          }
        } else {
          ElMessage({
            type: "error",
            message: res.data.msg,
            showClose: true,
          });
        }
      })
      .catch((err) => {
        ElMessage({
          type: "error",
          message: t("myStatusList.loadError"),
          showClose: true,
        });
        console.log(err);
      })
      .finally(() => {
        loading.value = false;
      });
  }
}
onMounted(() => {
  getFriendInfo();
})
</script>
<style scoped>
.all {
  width: 100%;
  height: 100%;
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: column nowrap;
  justify-content: flex-start;
}
.top {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
   -webkit-flex: none;
          flex: none;
}
.info {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-around;
  width: 100%;
}
.bottom {
  -webkit-flex: auto;
          flex: auto;
  overflow: hidden;
}
.infinite-list {
  list-style: none;
}
#larger-all {
  padding: 0;
  margin: -20px 0;
  overflow: hidden;
}
#end {
  text-align: center;
  font-size: xx-large;
  font-weight: 600;
}
</style>
