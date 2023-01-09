<!--
  * @FileDescription: Result Item Component, include:
      1. user avatar
      2. user name
      3. action button
      4. close button
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:18
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/29 12:03
-->
<template>
  <div>
    <div class="all">
      <div id="left">
        <el-avatar :size="80" :src="props.avatar" alt="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"/>
        <span class="text">{{ props.username }}</span>
      </div>
      <div id="mid">
        <el-button round type="primary" size="large" @click="pressBtn">{{
          props.buttonLabel
        }}</el-button>
      </div>
      <div id="right">
        <el-icon @click="deleteItem" color="red" :size="30" class="icon"
          ><Close
        /></el-icon>
      </div>
    </div>
  </div>
</template>
<script setup="props">
const props = defineProps({
  avatar: String,
  username: String,
  buttonLabel: String,
  id: String,
});
const emit = defineEmits(["delItem", "btnFunc"]);
/**
  * @description: delete item action
  * @param {String} id current item id
  * @return delete current item
*/
function deleteItem() {
  emit("delItem", props.id);
}
/**
  * @description: do press button action
  * @param {String} id current item id
*/
function pressBtn() {
  emit("btnFunc", props.id);
}
</script>
<style scoped>
.all {
  background-color: #d9ecff;
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-between;
  align-items: center;
}
#mid {
  flex-grow: 1;
}
#right {
  flex-grow: 1;
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: center;
}
#left {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 10px 0;
  margin-left: 20%;
  flex-grow: 6;
}
.text {
  margin-left: 20px;
  font-size: x-large;
  font-weight: 600;
  color: black;
}
.icon:active {
    color: black;
}
</style>
