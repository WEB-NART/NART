/*
  * @FileDescription: User Store, 
      Store Current User related information
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 13:59
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/04 10:30
*/
import { defineStore } from "pinia";
import { getToken, setToken, removeToken } from "@/request/token";
import { signin, signout, register, showUserInfo, showAvatarUname, changeUserInfo} from "@/api/user.js";
import { ElMessage } from 'element-plus'
import i18n from '../locals/index.js'

const useUserStore = defineStore("user", {
  state: () => {
    return {
      token: "",
      name: "",
      avatar: "", // head image
      email: "",
      tel: "",    // user phone number
      address: "",
      birthday: "",
      // group info
      groupId: "",     // the latest access group id
      notice: "",      // the latest access group notice
      groupName: "",   // the latest access group name
      groupAvatar: "", // the latest access group head image
    };
  },
  getters :{
    getGroupId: (state) => {
      return state.groupId;
    },
    getNotice: (state) => {
      return state.notice;
    },
    getGroupName: (state) => {
      return state.groupName;
    },
    getGroupAvatar: (state) => {
      return state.groupAvatar;
    },
  },
  actions: {
    /**
      * @description: update current group information
      * @param {Object} gInfo new group information
      * @return group information change
    */
    updategroupInfo(gInfo){
      if (gInfo != null && gInfo != {}) {
        if(gInfo.gid != null && gInfo.gid != ""){
          this.groupId = gInfo.gid;
        }
        if(gInfo.notice != null && gInfo.notice != ""){
          this.notice = gInfo.note;
        }
        if(gInfo.gName != null && gInfo.gName != ""){
          this.groupName = gInfo.gName;
        }
        if(gInfo.gAvatar != null && gInfo.gAvatar != ""){
          this.groupAvatar = gInfo.gAvatar;
        }
      }
    },
    /**
      * @description: log the user in
      * @param {Object} user user information (username and password)
      * @return accept or reject
    */
    login(user) {
      return new Promise((resolve, reject) => {
        signin(user.username, user.password)
          .then((res) => {
            if (res.data.success) {
              setToken(res.data.data);
              this.token = getToken();
              let id = this.getUserInfo();
              resolve(id);
            } else {
              ElMessage({
                type: "error",
                message: res.data.msg,
                showClose: true,
                grouping: true,
              });
              reject(res.data.msg);
            }
          })
          .catch((error) => {
            ElMessage({
              type: "error",
              message: i18n.global.t('user.loginError'),
              showClose: true,
              grouping: true,
            });
            reject(error);
          });
      });
    },
    /**
      * @description: get user information
      * @return user information updated
    */
    getUserInfo() {
        showUserInfo(this.token, "-1")
          .then((res) => {
          if (res.data.success) {
                console.log("getUserInfo success!");
                this.name = res.data.data.uname;
                this.avatar = res.data.data.avatar;
                this.email = res.data.data.email;
                this.birthday = res.data.data.birthday;
                this.tel = res.data.data.phone;
                this.address = res.data.data.address;
                return res.data.data.id;
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
            message: i18n.global.t('user.infoError'),
            showClose: true,
            grouping: true,
          });
          console.log(err);
        });
    },
    /**
      * @description: change current user information
      * @param {Object} userInfo user information
      * @return user information change
    */
    async changeUserInfo(userInfo) {
      return new Promise((resolve, reject) => {
        changeUserInfo(this.token, userInfo)
          .then((res) => {
            if (res.data.success) {
              this.name = userInfo.uname;
              this.avatar = userInfo.avatar;
              this.email = userInfo.email;
              this.birthday = userInfo.birthday;
              this.tel = userInfo.phone;
              this.address = userInfo.address;
              resolve(res.data);
            } else {
              ElMessage({
                type: "error",
                message: res.data.msg,
                showClose: true,
                grouping: true,
              });
              resolve(res.data);
            }
          })
          .catch((error) => {
            ElMessage({
              type: "error",
              message: i18n.global.t('user.changeInfoError'),
              showClose: true,
              grouping: true,
            });
            reject(error);
          });
      });
    },
    /**
      * @description: log user out
      * @return user logout, all stored data deleted
    */
    logout() {
      return new Promise((resolve, reject) => {
        removeToken();
        signout(this.token)
          .then((res) => {
            if (res.data.success) {
              this.fedLogOut();
              resolve();
            } else {
              ElMessage({
                type: "error",
                message: res.data.msg,
                showClose: true,
                grouping: true,
              });
              resolve();
            }
          })
          .catch((error) => {
            ElMessage({
              type: "error",
              message: i18n.global.t('user.logoutError'),
              showClose: true,
              grouping: true,
            });
            resolve(error);
          });
      });
    },
    // front-end logout
    /**
      * @description: front end logout function
      * @return front-end user data deleted
    */
    fedLogOut() {
      return new Promise((resolve) => {
        this.name = "";
        this.avatar = "";
        this.email = "";
        this.birthday = "";
        this.tel = "";
        this.address = "";
        this.token = "";
        resolve();
      }).catch((error) => {
        reject(error);
      });
    },
    /**
      * @description: register a user
      * @param {Object} user user information (username, password, email)
      * @return accept or reject
    */
    register(user) {
      return new Promise((resolve, reject) => {
        register(user.uname, user.pwd, user.email)
          .then((res) => {
            if (res.data.success) {
              setToken(res.data.data);
              this.token = getToken();
              this.getUserInfo();
              resolve();
            } else {
              ElMessage({
                type: "error",
                message: res.data.msg,
                showClose: true,
                grouping: true,
              });
              reject(res.data.msg);
            }
          })
          .catch((error) => {
            ElMessage({
              type: "error",
              message: i18n.global.t('user.registerError'),
              showClose: true,
              grouping: true,
            });
            reject(error);
          });
      });
    },
  },
  persist: {
    enabled: true
  }
});

export default useUserStore;
