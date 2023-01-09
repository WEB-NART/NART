<!--
  * @FileDescription: Comment List Component, include:
        comment list
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:24
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/29 17:57
-->
<template>
  <div id="all">
    <div v-for="comment in props.commentList" :key="comment.id" class="each">
      <span class="text first">{{ comment.uname }}: </span>
      <span class="text">{{ comment.msg }}</span>
      <span id="date">{{ comment.commnetDate }}</span>
    </div>
  </div>
</template>
<script setup>
const props = defineProps({
  commentList: Array,
});
</script>
<style scoped>
#all {
  background-color: #dedfe0;
  border-radius: 20px;
  padding: 10px 0;
}
#date {
  font-size: medium;
  font-weight: 200;
  color: #606266;
  margin-left: 30px;
}
.each {
  margin: 5px 0 0 20px;
}
.text {
  font-size: large;
  font-weight: 500;
}
</style>
