<!--
  * @FileDescription: Information Item Component, include:
      1. info title
      2. info value
      3. change button
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:21
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/29 14:57
-->
<template>
  <div class="all flex">
    <el-dialog
      v-model="dialogVisible"
      :title="t('infoItem.title')"
      width="40%"
      :before-close="clearBtn"
    >
      <div style="width: 40%">
        <el-input
          :placeholder="t('infoItem.oldPwd')"
          v-model="oldPwd"
          type="password"
        />
        <el-input
          :placeholder="t('infoItem.newPwd')"
          v-model="newPwd"
          type="password"
        />
        <el-input
          :placeholder="t('infoItem.newPwd2')"
          v-model="newPwd2"
          @input="checkPwd"
          type="password"
        />
        <div class="error3">{{ $t("infoItem.notMatch") }}</div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="clearBtn">{{ $t("buttons.cancel") }}</el-button>
          <el-button type="primary" @click="changeBtn" :disabled="confirm">{{
            $t("buttons.confirm")
          }}</el-button>
        </span>
      </template>
    </el-dialog>
    <div class="top flex fullW">
      <span class="text title">{{ props.label }}</span>
      <hr class="fullW" />
    </div>
    <div class="bottom fullW">
      <div v-if="props.isPwd" class="pwde flex fullW">
        <div>
          <el-button round type="primary" @click="dialogVisible = true">{{
            $t("buttons.change")
          }}</el-button>
        </div>
      </div>
      <div v-else class="elements flex fullW">
        <div>
          <el-input
            v-if="changeValue"
            v-model="input"
            @change="checkInput"
            @blur="checkInput"
          ></el-input>
          <span v-else class="text cb">{{ v }}</span>
        </div>
        <div class="el-right">
          <el-button
            round
            type="primary"
            v-show="canChange"
            @click="changeBtn"
            >{{ $t("buttons.change") }}</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, watch } from "vue";
import { useI18n } from "vue-i18n";
import { ElMessage } from "element-plus";

const { t } = useI18n();
const dialogVisible = ref(false);
const error3Display = ref("none");
const changeValue = ref(false);
const confirm = ref(false);

const emit = defineEmits(["changeFun", "changePwd"]);
const props = defineProps({
  isPwd: Boolean,
  label: String,
  lb: String,
  value: String,
  reg: RegExp,
  canChange: Boolean,
  // matchFormat: String,
});
// info value
var v = ref(props.value);
const input = computed({
  get() {
    return v.value;
  },
  set(newValue) {
    v.value = newValue.trim();
  },
});
// old password
var oldP = ref("");
const oldPwd = computed({
  get() {
    return oldP.value;
  },
  set(newValue) {
    oldP.value = newValue.trim();
  },
});
// new password
var newP = ref("");
const newPwd = computed({
  get() {
    return newP.value;
  },
  set(newValue) {
    newP.value = newValue.trim();
  },
});
// new password input twice
var newP2 = ref("");
const newPwd2 = computed({
  get() {
    return newP2.value;
  },
  set(newValue) {
    newP2.value = newValue.trim();
  },
});
/**
  * @description: clear all input function
*/
function clearBtn() {
  dialogVisible.value = false;
  oldP.value = "";
  newP.value = "";
  newP2.value = "";
}
/**
  * @description: check if input format fit the given regex
  * @param {String} changeValue input value
  * @return fail / send change value request
*/
function checkInput() {
  changeValue.value = false;
  let errorClass = "";
  if (props.lb == "1") {
    errorClass = "uname";
  } else {
    errorClass = props.label.toLowerCase();
  }
  let regexp = new RegExp(props.reg);
  if (!regexp.test(v.value)) {
    ElMessage({
      type: "error",
      message: t("welcome." + errorClass + "Error"),
      showClose: true,
      grouping: true,
    });
    v.value = props.value;
  } else {
    change();
  }
}
/**
  * @description: check if password format fit the given regex
  * @param {String} newP password 1
  * @param {String} newP2 password 2
  * @return change button unlock / lock
*/
function checkPwd() {
  if (newP.value == newP2.value) {
    error3Display.value = "none";
  } else {
    error3Display.value = "";
  }
  let regexp = new RegExp(/^[\w-]{6,18}$/);
  if (!regexp.test(newP.value)) {
    ElMessage({
      type: "warning",
      message: t("welcome.pwdError"),
      showClose: true,
      grouping: true,
    });
    confirm.value = true;
  } else {
    confirm.value = false;
  }
}
/**
  * @description: change button action, front-end
  * @param {String} newP password 1
  * @param {String} newP2 password 2
  * @return change button unlock / lock
*/
function changeBtn() {
  if (props.isPwd) {
    dialogVisible.value = dialogVisible.value == true ? false : true;
  } else {
    changeValue.value = changeValue.value == true ? false : true;
  }
  change();
}
/**
  * @description: call change info value function
  * @param {Boolean} isPwd is current info is password or not
*/
function change() {
  if (!props.isPwd) {
    emit("changeFun", props.lb, input.value);
  } else {
    emit("changePwd", oldP.value, newP.value);
  }
}
watch(
  () => props.value,
  (newValue) => {
    v.value = newValue;
  },
  {
    deep: true,
  }
);
</script>
<style scoped>
.error3 {
  display: v-bind(error3Display);
  color: red;
}
.flex {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-between;
  flex-flow: column nowrap;
  align-items: center;
}
.fullW {
  width: 250px;
}
.top {
  align-items: flex-start;
  width: 250px;
}
.pwde {
  justify-content: flex-end;
  flex-flow: row nowrap;
}
.elements {
  flex-flow: row nowrap;
}
.cb {
  font-size: large;
}
</style>
