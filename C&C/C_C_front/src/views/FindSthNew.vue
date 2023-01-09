<!--
  * @FileDescription: Find something page, include:
      1. search New Friends Page
      2. create New Group Page
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 12:08
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/01 20:32
-->
<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div class="flex">
          <div class="input">
            <el-input v-model="input" size="large" :placeholder="t('findSthNew.placeholder')" />
          </div>
          <div class="btn1">
            <el-button type="primary" size="large" round @click="searchNew">{{
              $t("findSthNew.search")
            }}</el-button>
          </div>
          <div class="btn2">
            <el-button type="primary" size="large" round @click="createGroup">{{
              $t("findSthNew.create")
            }}</el-button>
          </div>
        </div>
        <el-divider />
      </el-header>
      <el-main id="main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useNewStore } from "@/stores/newStore";
import { useI18n } from "vue-i18n";

const {t} = useI18n();
const store = useNewStore();
const router = useRouter();
const page = {
  loading: false,
  nodata: false,
  pageSize: 6,
  pageNum: 1,
};
var input = ref("");
/**
 * @description: Search Users who are not your friends, 
 *     and jump to Add New Friend Page
 * @param {String} input searching input
 */
function searchNew() {
  let temp = input.value;
  if (store.searchHistory != temp) {
    store.searchHistory = temp;
    store.loadFirstList();
  }
  router.push({
    name: "addNewFriend"
  });
}
/**
 * @description: Jump to Create New Group Page
 */
function createGroup() {
  router.push({ path: "/findSthNew/createGroup" });
}
</script>
<style scoped>
.flex {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 10px;
}
@media screen and (max-height: 550px) {
  .flex {
    margin-top: -10px;
  }
  #main {
    margin-top:-10px;
  }
}
.input {
  -webkit-flex: initial;
  flex: initial;
  width: 50vw;
  min-width: 200px;
}
.el-main {
  overflow: hidden;
}
</style>
