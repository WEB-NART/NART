<!--
  * @FileDescription: My Status Item Component, include:
      1. text
      2. images
      3. delete button
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:19
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/29 13:53
-->
<template>
  <div class="mall">
    <div id="left">
      <div>
        <span class="text">{{ message }}</span>
      </div>
      <div id="mid">
        <div v-for="(pic, index) in props.pictures" :key="pic">
          <el-image
            class="img"
            :initial-index="index"
            :src="pic"
            :preview-src-list="props.pictures"
          />
        </div>
      </div>
    </div>
    <div id="right">
      <el-icon id="icon" v-show="isMine" :size="40" @click="delStatus"
        ><Delete
      /></el-icon>
    </div>
  </div>
</template>
<script setup="props">
const props = defineProps({
  isMine: Boolean,
  message: String,
  pictures: Array,
  id: String,
});
const emit = defineEmits(["del"]);
/**
  * @description: delete status action
  * @param {String} id current status id
  * @return delete current status
*/
function delStatus() {
  emit("del", props.id);
}
</script>
<style scoped>
.mall {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-between;
  flex-flow: row nowrap;
  align-items: center;
  width: 100%;
}

#mid {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: flex-start;
  flex-flow: row wrap;
  align-items: center;
  width: 100%;
}
#icon {
  color: red;
  margin-right:40px;
}
#icon:hover {
  color: black;
}
</style>
