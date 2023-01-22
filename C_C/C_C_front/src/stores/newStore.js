/*
  * @FileDescription: New Friends Store, 
      store New Searching Friends related Information
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:01
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/04 12:21
*/
import { ElMessage } from "element-plus";
import { defineStore } from "pinia";
import { searchNewFriend } from "@/api/friend";
import  useUserStore  from "@/stores/userStore";
import { storeToRefs } from "pinia";
import i18n from '../locals/index.js'

const store = useUserStore();
const { token } = storeToRefs(store);

export const useNewStore = defineStore("newFriend", {
  state: () => {
    return {
      searchHistory: "", // search new friend related search history
      loading: false,    // new friend is loading?
      nodata: false,     // no more data for new friend from server?
      nfList: [],        // new friend list
      counter: 0,        // tests used counter
      page: {
        pageSize: 7,     // page size for each new friend load request
        pageNum: 1,      // current page number for new friend load
      },
    };
  },
  actions: {
    /**
      * @description: load new friend results
      * @param {Object} searchHistory search input
      * @return {Array} [nfList] new friend lists change
    */
    loadNewFriends() {
      if (!this.loading && !this.nodata) {
        this.loading = true;
        console.log(token.value);
        searchNewFriend(token.value, this.searchHistory, this.page)
          .then((res) => {
            if (res.data.success) {
              if (res.data.data.length > 0) {
                if (this.nfList == undefined) {
                    this.nfList = res.data.data;
                } else {
                    this.nfList.push(...res.data.data);
                }
                this.page.pageNum += 1;
              } else {
                this.nodata = true;
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
            this.loading = false;
          });
      }
    },
    /**
      * @description: do the very first loadNewFriends 
      * @return {Array} [nfList] friend lists change
    */
    loadFirstList() {
      this.loading = false;
      this.nodata = false;
      this.page.pageNum = 1;
      this.counter = 0;
      if (this.nfList != undefined) {
        this.nfList.splice(0, this.nfList.length);
      }
      this.loadNewFriends();
    },
    /**
      * @description: test for loadNewFriends
    */
    tList() {
      let tempName = this.searchHistory;
      for (let i = 0; i < 7; i++) {
        let test = [
          {
            id: this.counter.toString(),
            uname: tempName + this.counter.toString(),
            avatar:
              "https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg",
          },
        ];
        if (this.nfList == undefined) {
          this.nfList = test;
        } else {
          this.nfList.push(...test);
        }
        this.counter += 1;
      }
    },
    /**
      * @description: remove item from [nfList] according to [id]
      * @param {Number} id item id
      * @return {Array} change [nfList]
    */
    delItem(id) {
      for (let i = 0; i < this.nfList.length; i++) {
        if (id == this.nfList[i].id) {
            this.nfList.splice(i, 1);
            if(this.nfList.length <= 6) {
                this.loadNewFriends();
            }
            return;
        }
      }
    },
  },
});
