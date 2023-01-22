/*
  * @FileDescription: Tools
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 13:56
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/25 15:34
*/
const tool = {

    // netty服务后端发布url的地址
    // nettyServerUrl: 'ws://172.20.10.8:8088/ws',

    // 后端服务器发布的url地址
    // serverUrl: 'http://172.20.10.4:8080/im_bird_sys/',

    // 图片服务器的url地址
    // imgServerUrl: 'http://192.168.1.70:88/wdzl/',

    /**
      * @description: check if [str] is null
      * @param {String} str string
      * @return true/false
    */
    isNotNull: function(str) {
        if (str!=null && str !="" && str != undefined) {
            return true;
        }
        return false;
    }
};
export default tool