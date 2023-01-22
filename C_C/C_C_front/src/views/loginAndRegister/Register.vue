<!--
  * @FileDescription: register page, accept
      1. email
      2. username
      3. password
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 12:15
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/02 10:53
-->
<template>
  <div class="h-full flex flex-col flex-nowrap justify-center items-center">
    <div
      id="shadow"
      class="border-dotted border-0 border-gray-600 bg-white/30 rounded-3xl py-12 px-12 backdrop-blur-md flex flex-col flex-nowrap justify-center items-center"
    >
      <header>
        <div class="text-white my-12 text-4xl mt-5">
          {{ $t("welcome.register") }}
        </div>
      </header>
      <div>
        <form ref="form" @submit.prevent="submit">
          <div class="flex justify-around flex-col ">
            <div class="form-floating mb-8 xl:w-80">
              <input
                type="text"
                class="form-control block w-full px-2 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                id="email"
                name="email"
                ref="email"
                v-model="email"
                :placeholder="$t('welcome.email')"
              />
            </div>
            <div class="form-floating mb-8 xl:w-80">
              <input
                type="text"
                class="form-control block w-full px-2 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                id="username"
                name="username"
                ref="username"
                v-model="username"
                :placeholder="$t('welcome.uname')"
              />
            </div>
            <div class="form-floating mb-8 xl:w-80">
              <input
                type="password"
                class="form-control block w-full px-2 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                id="password"
                name="password"
                ref="password"
                v-model="password"
                :placeholder="$t('welcome.pwd')"
              />
            </div>
            <div class="form-floating mb-8 xl:w-80">
              <input
                type="password"
                class="form-control block w-full px-2 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                id="password2"
                name="password2"
                ref="password2"
                v-model="password2"
                :placeholder="$t('welcome.pwd2')"
              />
            </div>
          </div>
        </form>
      </div>
      <div>
        <button
          @click="register"
          class="flex2 bg-rose-800 hover:bg-yellow-300 text-white font-bold py-2 px-16 rounded-2xl my-8 text-2xl"
        >
          <div>{{ $t("welcome.register") }}</div>
        </button>
        <div @click="toLogin" class="text-center hover:text-green-400">
          {{ $t("welcome.toRegister") }}
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import tool from "@/utils/tool.js";
import  useUserStore  from "@/stores/userStore";
import { ElMessage } from 'element-plus'

export default {
  data() {
    return {
      uname: "",
      pwd: "",
      pwd2: "",
      em: "",
      emReg: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
      unameReg: /^[\w-]{3,16}$/,
      pwdReg: /^[\w-]{6,18}$/,
      store: useUserStore(),
    };
  },
  methods: {
    /**
      * @description: jump to login function
    */
    toLogin: function() {
      this.$router.push('/login');
    },
    /**
      * @description: register function, require
      *   1. username cannot repeat
      *   2. pwd = pwd2
      *   3. all four fit regex
      * @param {String} uname username
      * @param {String} em user email
      * @param {String} pwd user password
      * @param {String} pwd2 user password repeat
      * @return success or fail
    */ 
    register: function () {
      let uname = this.uname;
      let em = this.em;
      let pwd2 = this.pwd2;
      let pwd = this.pwd;
      if (!tool.isNotNull(em)) {
        this.$refs.email.focus();
      } else if (!tool.isNotNull(uname)) {
        this.$refs.username.focus();
      } else if (!tool.isNotNull(pwd)) {
        this.$refs.password.focus();
      } else if (!tool.isNotNull(pwd2)) {
        this.$refs.password.focus();
      } else {
        if(pwd != pwd2) {
          ElMessage({
            type: "warning",
            message: this.$t('welcome.pwd2Error'),
            showClose: true,
            grouping: true,
          });
          return false;
        }
        let reg = new RegExp(this.emReg);
        if(!reg.test(em)) {
          ElMessage({
            type: "warning",
            message: this.$t('welcome.emailError'),
            showClose: true,
            grouping: true,
          });
          return false;
        }
        reg = new RegExp(this.unameReg);
        if(!reg.test(uname)) {
          ElMessage({
            type: "warning",
            message: this.$t('welcome.unameError'),
            showClose: true,
            grouping: true,
          });
          return false;
        }
        reg = new RegExp(this.pwdReg);
        if (!reg.test(pwd)) {
          ElMessage({
            type: "warning",
            message: this.$t('welcome.pwdError'),
            showClose: true,
            grouping: true,
          });
          return false;
        }
        let user = {
          uname: uname,
          pwd: pwd,
          email: em,
        }
        this.store.register(user);
        this.$router.push('/');
      }
    },
  },
  computed: {
    username: {
      get() {
        return this.uname;
      },
      set(val) {
        this.uname = val.trim();
      },
    },
    password: {
      get() {
        return this.pwd;
      },
      set(val) {
        this.pwd = val.trim();
      },
    },
    password2: {
      get() {
        return this.pwd2;
      },
      set(val) {
        this.pwd2 = val.trim();
      },
    },
    email: {
      get() {
        return this.em;
      },
      set(val) {
        this.em = val.trim();
      },
    },
  },
  /**
    * @description: set style and background image on body before create
  */ 
  beforeCreate() {
    const urlPath = "../../src/assets/img/bg1.png";
    document
      .querySelector("body")
      .setAttribute("style", "background-image:url('" + urlPath + "');");
    //.setAttribute('style','background-color: cyan');
    document
      .querySelector("body")
      .setAttribute("class", "bg-cover bg-no-repeat w-full h-full");
  },
  /**
    * @description: remove style on body on updated
  */ 
  Updated() {
    document.querySelector("body").removeAttribute("style");
  },
};
</script>
<style>
@font-face {
  font-family: "GMC";
  src: url("../../GenkaiMinCho.ttf");
}
#shadow:hover {
  box-shadow: -5px 15px 50px 10px white;
  /* transform: translateY(-11px); */
  /* transition: opacity 2s ease-in-out; */
  transition-duration: 0.7s;
}
</style>
