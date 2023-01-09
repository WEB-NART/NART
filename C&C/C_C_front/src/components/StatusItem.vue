<!--
  * @FileDescription: Status Item Component, include:
      1. sender avatar and name
      2. status text
      3. status image
      4. like and write comment button
      5. comments list
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:17
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/29 10:24
-->
<template>
  <div id="all">
    <div id="top">
      <div id="left">
        <el-avatar :src="avatar" :size="60" />
        <div class="name">{{ uname }}</div>
      </div>
      <div id="right">
        <span class="text">{{ message }}</span>
        <div class="img-box">
          <div v-for="(img, index) in props.pictures" :key="img">
            <el-image
              class="img"
              :src="img"
              :initial-index="index"
              :preview-src-list="props.pictures"
            />
          </div>
        </div>
      </div>
    </div>
    <div id="down">
      <div :class="iconClass" @click="sendLike" id="like"></div>
      <span v-show="like" style="font-weight: 100; color:black">{{ props.heartNum + 1}}</span>
      <el-button round type="primary" @click="startCom">...</el-button>
    </div>
    <div id="bottom">
      <div id="bt1">
        <el-input
          v-model="input"
          :placeholder="t('statusItem.commentPlaceHolder')"
          @change="sendComment"
        />
      </div>
    </div>
    <div id="comments">
      <comment-list :comment-List="commentList"></comment-list>
    </div>
  </div>
</template>
<script setup>
import { ElMessage } from "element-plus";
import { onMounted, computed } from "vue";
import { reactive, ref } from "vue";
import { likeStatus, dislikeStatus } from "@/api/status";
import { postComment } from "@/api/comment";
import { useI18n } from "vue-i18n";
import CommentList from "./CommentList.vue";
import useUserStore from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { now } from "@/utils/time.js";

const { t } = useI18n();
const commentDisplay = ref("none");
const props = defineProps({
  statusId: String,
  avatar: String,
  uname: String,
  message: String,
  pictures: Array,
  comments: Array,
  heart: Boolean,
  heartNum: Number,
  date: Object,
  uid: String,
});
const store = useUserStore();
const { name, token } = storeToRefs(store);
const commentList = reactive(props.comments);
const like = ref(props.heart);
const iconClass = ref("iconfont icon-aixin");
var comment = ref("");
const input = computed({
  get() {
    return comment.value;
  },
  set(newValue) {
    comment.value = newValue;
  },
});
/**
  * @description: front-end like status action
  * @return websocket message is sent
*/ 
function likeS() {
  like.value = like.value ? false : true;
  if (like.value) {
    iconClass.value = "iconfont icon-aixin_shixin like";
  } else {
    iconClass.value = "iconfont icon-aixin";
  }
}
/**
  * @description: front-end start writing comment action
*/ 
function startCom() {
  if (commentDisplay.value == "none") {
    commentDisplay.value = "";
  } else {
    commentDisplay.value = "none";
  }
}
/**
  * @description: test sendComment with fake data
*/ 
function makeCom() {
  let comment = {
    id: "738291",
    uname: name,
    msg: input.value.trim(),
    date: now(),
  };
  commentList.unshift(comment);
  input.value = "";
}
/**
  * @description: send wroten comment as HTTP
  * @return success / fail
*/ 
function sendComment() {
  postComment(token.value, props.statusId, input.value)
    .then((res) => {
      if (res.data.success) {
        ElMessage({
          type: "success",
          message: t("postComment.succeed"),
          showClose: true,
          grouping: true,
        });
        let comment = {
          id: res.data.data,
          uname: name.value,
          msg: input.value,
          date: now(),
        };
        commentList.unshift(comment);
        input.value = "";
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
      ElMessage({
        type: "error",
        message: t("postComment.fail"),
        showClose: true,
        grouping: true,
      });
      console.log(err);
    });
}
/**
  * @description: send like status as HTTP
  * @return success / fail
*/ 
function sendLike() {
  let tempLike = like.value;
  tempLike = tempLike ? false : true;
  let result, errMsg, succeedMsg;
  if (tempLike) {
    result = likeStatus(token.value, props.statusId);
    succeedMsg = t("postComment.like");
    errMsg = t("postComment.likeFail");
  } else {
    result = dislikeStatus(token.value, props.statusId);
    succeedMsg = t("postComment.dislike");
    errMsg = t("postComment.dislikeFail");
  }
  result
    .then((res) => {
      if (res.data.success) {
        ElMessage({
          type: "success",
          message: succeedMsg,
          showClose: true,
          grouping: true,
        });
        if (tempLike) {
          iconClass.value = "iconfont icon-aixin_shixin like";
        } else {
          iconClass.value = "iconfont icon-aixin";
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
      ElMessage({
        type: "error",
        message: errMsg,
        showClose: true,
        grouping: true,
      });
      console.log(err);
    });
  like.value = tempLike;
}
/**
  * @description: set heart style when mounted
*/ 
onMounted(() => {
  if (props.heart) {
    iconClass.value = "iconfont icon-aixin_shixin like";
  }
});
</script>
<style scoped>
@import url("@/assets/css/iconfont.css");
.like {
  color: red;
}
.img-box {
  display: -webkit-flex; /* Safari */
  display: flex;
  flex-flow: row wrap;
  justify-content: flex-start;
  align-items: flex-start;
  align-content: flex-start;
}
.name {
  text-align: center;
  font-size: 1em;
  font-weight: 500;
  margin-top: -0.5em;
}
#all {
  margin: 1em 0;
}
#top {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: flex-start;
}
#right {
  flex-grow: 5;
  margin-left: 20px;
}
#left {
  flex-grow: 1;
  text-align: center;
}
#down {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
#like {
  margin-right: 20px;
}
#bottom {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-around;
  width: 100%;
}
#bt1 {
  width: 80%;
  display: v-bind(commentDisplay);
}
</style>
