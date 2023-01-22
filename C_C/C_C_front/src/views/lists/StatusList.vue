<!--
  * @FileDescription: Status List Page, show all statuses include:
      1. The current user's statuses
      2. The current user's friends' statuses
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 12:18
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/02 15:38
-->
<template>
  <div id="larger-all">
    <el-scrollbar height="75vh" id="all">
      <ul v-infinite-scroll="load" class="infinite-list">
        <li v-for="status in statusList" :key="status.statusId">
          <status-item
            :statusId="status.statusId"
            :avatar="status.avatar"
            :uname="status.uname"
            :message="status.msg"
            :pictures="status.pics"
            :comments="status.comments"
            :heart="status.liked"
            :heartNum="status.likes"
            :date="status.createDate"
            :uid="status.uid"
          ></status-item>
        </li>
        <li v-show="nodata" id="end">{{ $t("statusList.toEnd") }}</li>
      </ul>
    </el-scrollbar>
  </div>
</template>
<script setup>
import { onMounted } from "vue";
import { reactive, ref, watch } from "vue";
import { useI18n } from "vue-i18n";
import useUserStore from "@/stores/userStore";
import { storeToRefs } from "pinia";
import StatusItem from "@/components/StatusItem.vue";
import { showAllStatusList } from "@/api/status";
import { ElMessage } from 'element-plus'

const store = useUserStore();
const { token } = storeToRefs(store);
const { t } = useI18n();
const counter = ref(0);
const statusList = reactive([]);
const loading = ref(false);
const nodata = ref(false);
const page = reactive({
  pageSize: 5,
  pageNum: 1,
});
/**
 * @description: test for load function
 */
function loadTest() {
  if (counter.value > 10) {
    nodata.value = true;
    return;
  }
  for (let i = 0; i < 5; i++) {
    const test = {
      statusId: counter.value.toString(),
      avatar:
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      uname: "testName",
      msg: "this is a new Status",
      pics: [
        "https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg",
        "https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg",
        "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
        "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg",
        "https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg",
        "https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg",
        "https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg",
      ],
      comments: [
        {
          id: (1 + counter.value + 10).toString(),
          uname: "respName",
          msg: "this is a response",
          date: "2002/09/18",
        },
        {
          id: (2 + counter.value + 10).toString(),
          uname: "respName",
          msg: "this is a response",
          date: "2002/09/18",
        },
        {
          id: (3 + counter.value + 10).toString(),
          uname: "respName",
          msg: "this is a response",
          date: "2002/09/18",
        },
        {
          id: (4 + counter.value + 10).toString(),
          uname: "respName",
          msg: "this is a response",
          date: "2002/09/18",
        },
        {
          id: (5 + counter.value + 10).toString(),
          uname: "respName",
          msg: "this is a response",
          date: "2002/09/18",
        },
        {
          id: (6 + counter.value + 10).toString(),
          uname: "respName",
          msg: "this is a response",
          date: "2002/09/18",
        },
      ],
      heart: false,
      heartNum: 100,
      createDate: "2022/09/10",
      uid: "hdsjakdhsa",
    };
    statusList.push(test);
    counter.value += 1;
  }
}
/**
 * @description: load more status that you can see
 * @return change status list
 */
function load() {
  if (!loading.value && !nodata.value) {
    loading.value = true;
    showAllStatusList(token.value, page)
      .then((res) => {
        if (res.data.success) {
          if (res.data.data.length > 0) {
            statusList.push(...res.data.data);
            page.pageNum += 1;
          } else {
            nodata.value = true;
          }
        } else {
          ElMessage({
            type: "error",
            message: t("statusList.loadError"),
            showClose: true,
            grouping: true,
          });
        }
      })
      .catch((err) => {
        ElMessage({
          type: "error",
          message: t("statusList.loadError"),
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
</script>
<style scoped>
@import url("@/assets/css/iconfont.css");
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
