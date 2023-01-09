/*
  * @FileDescription: Friend and Group Store,
      stores friends and group related Informations
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:05
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/04 14:22
*/
import { ElMessage } from "element-plus";
import { defineStore } from "pinia";
import  useUserStore  from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { searchFriend } from "@/api/friend";
import i18n from '../locals/index.js'

const store = useUserStore();
const { token } = storeToRefs(store);

export const useFriendStore = defineStore("friends", {
  state: () => {
    return {
      fSearchHistory: "", // search friend related search history
      gSearchHistory: "", // search group friend related search history
      fLoading: false,    // friend is loading?
      fNodata: false,     // no more data for friend from server?
      gLoading: false,    // group friend is loading?
      gNodata: false,     // no more data for group friend from server?
      fList: [],          // friend list
      gList: [],          // group friend list
      counter: 0,         // tests used counter
      fPage: {  
        pageSize: 7,      // page size for each friend load request
        pageNum: 1,       // current page number for friend load
      },
      gPage: {
        pageSize: 7,      // page size for each group friend load request
        pageNum: 1,       // current page number for group friend load
      },
    };
  },
  actions: {
    /**
      * @description: load my friend results
      * @param {Object} fSearchHistory search input
      * @return {Array} [fList] friend lists change
    */
    loadNewFriends() {
      if (!this.fLoading && !this.fNodata) {
        this.fLoading = true;
        let temp = this.fSearchHistory;
        if(temp == undefined || temp == "") {
          temp = "-1";
        }
        searchFriend(token.value, temp, this.fPage)
          .then((res) => {
            if (res.data.success) {
              if (res.data.data.length > 0) {
                if (this.fList == undefined) {
                    this.fList = res.data.data;
                } else {
                    this.fList.push(...res.data.data);
                }
                this.fPage.pageNum += 1;
              } else {
                this.fNodata = true;
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
                message: i18n.global.t("newFriendList.loadError"),
                showClose: true,
                grouping: true,
              });
              console.log(err);
            })
          .finally(() => {
            this.fLoading = false;
          });
      }
    },
    /**
      * @description: load old friend results which is not in array
      * @param {Object} array a list of friends
      * @return {Array} [gList] friend lists change
    */
    loadNewGFriends(array) {
      if (!this.gLoading && !this.gNodata) {
        this.gLoading = true;
        let temp = this.gSearchHistory;
        if(temp == undefined || temp == "") {
          temp = "-1";
        }
        searchFriend(token.value, temp, this.gPage)
          .then((res) => {
            if (res.data.success) {
              if (res.data.data.length > 0) {
                if (this.gList == undefined) {
                    this.gList = this.getDiff(res.data.data, array);
                } else {
                    this.gList.push(...res.data.data);
                    this.gList = this.getGList(array);
                    //console.log(this.gList);
                }
                this.gPage.pageNum += 1;
              } else {
                this.gNodata = true;
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
                message: i18n.global.t("newFriendList.loadError"),
                showClose: true,
                grouping: true,
              });
              console.log(err);
            })
          .finally(() => {
            this.gLoading = false;
          });
      }
    },
    /**
      * @description: do the very first loadNewFriends 
      * @return {Array} [fList] friend lists change
    */
    loadFirstList() {
      this.fLoading = false;
      this.fNodata = false;
      this.fPage.pageNum = 1;
      this.counter = 0;
      if (this.fList != undefined) {
        this.fList.splice(0, this.fList.length);
      }
      this.loadNewFriends();
    },
    /**
      * @description: test for loadNewFriends
    */
    loadNewFriendsTest() {
      let tempName = this.fSearchHistory;
      for (let i = 0; i < this.fPage.pageSize; i++) {
        let test = [
          {
            id: this.counter.toString(),
            uname: tempName + this.counter.toString(),
            avatar:
              "https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg",
          },
        ];
        if (this.fList == undefined) {
          this.fList = test;
        } else {
          this.fList.push(...test);
        }
        this.counter += 1;
      }
    },
    /**
      * @description: do the very first loadNewGFriends
      * @return {Array} [gList] friend lists change
    */
    loadFirstGList(array) {
      this.gLoading = false;
      this.gNodata = false;
      this.gPage.pageNum = 1;
      this.counter = 0;
      if (this.gList != undefined) {
        this.gList.splice(0, this.gList.length);
      }
      this.loadNewGFriends(array);
      if(this.gList.length < 7) {
        return;
      }
      this.gList = this.getGList(array);
      while(this.gList.length <= 7) {
        this.loadNewGFriends();
        this.gList = this.getGList(array);
      }
    },
    /**
      * @description: test for loadNewGFriends
    */
    loadNewGFriendsTest() {
      let tempName = this.gSearchHistory;
      for (let i = 0; i < this.gPage.pageSize; i++) {
        let test = [
          {
            id: this.counter.toString(),
            uname: tempName + this.counter.toString(),
            avatar:
              "https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg",
          },
        ];
        if (this.gList == undefined) {
          this.gList = test;
        } else {
          this.gList.push(...test);
        }
        this.counter += 1;
      }
    },
    /**
      * @description: find the difference between [gList] and [array]
      * @param {Object} array a list of friends
      * @param {Object} gList a list of friends
      * @return {Array} difference between the two lists
    */
    getGList(array) {
      return this.getDiff(this.gList, array);
    },
    /**
      * @description: find the difference between the two lists
      * @param {Object} arr1 a list
      * @param {Object} arr2 a list
      * @return {Array} difference between the two lists
    */
    getDiff(arr1, arr2) {
      let difference = arr1.filter(function(obj) {
        return !arr2.some(function(obj2) {
          return obj.id == obj2.id;
        })
      });
      return difference;
    },
    /**
      * @description: remove item from [gList] according to [id]
      * @param {Number} id item id
      * @return {Array} change [gList]
    */
    delGItem(id) {
      for (let i = 0; i < this.gList.length; i++) {
        if (id == this.gList[i].id) {
            let temp = this.gList[i];
            this.gList.splice(i, 1);
            if(this.gList.length <= 7) {
                this.loadNewGFriends();
            }
            return temp;
        }
      }
    },
    /**
      * @description: remove item from [fList] according to [id]
      * @param {Number} id item id
      * @return {Array} change [fList]
    */
    delItem(id) {
      for (let i = 0; i < this.fList.length; i++) {
        if (id == this.fList[i].id) {
            this.fList.splice(i, 1);
            if(this.fList.length <= 7) {
                this.loadNewFriends();
            }
            return;
        }
      }
    },
  },
});
