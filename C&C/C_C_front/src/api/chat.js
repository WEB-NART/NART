/*
  * @FileDescription: user related API
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 16:26
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/25 21:30
*/
import req from "@/request";

export function showFriendChatHistory(token, chatId, page) {
  let type = "friend";
  return req({
    headers: { 'Authorization': token },
    url: `/chat/history/${type}/${chatId}`,
    method: "post",
    data: {
      pageNum: page.pageNum,
      pageSize: page.pageSize
    }
  });
}

export function showGroupChatHistory(token, chatId, page) {
  let type = "group";
  return req({
    headers: { 'Authorization': token },
    url: `/chat/history/${type}/${chatId}`,
    method: "post",
    data: {
      pageNum: page.pageNum,
      pageSize: page.pageSize
    }
  });
}

export function sendFriendMsg(token, msgInfo) {
  return req({
    headers: { 'Authorization': token },
    url: "/chat/send",
    method: "post",
    data: {
      chatId: msgInfo.chatId,
      type: "friend",
      msg: msgInfo.msg,
      msgType: msgInfo.msgType,
    },
  });
}

export function sendGroupMsg(token, msgInfo) {
  return req({
    headers: { 'Authorization': token },
    url: "/chat/send",
    method: "post",
    data: {
      chatId: msgInfo.chatId,
      type: "group",
      msg: msgInfo.msg,
      msgType: msgInfo.msgType,
    },
  });
}

export function leaveRoom(token, roomId, isFriend) {
  return req({
    headers: { 'Authorization': token },
    url: `/chat/leaveRoom/${roomId}/${isFriend}`,
    method: "put"
  })
}
