/*
  * @FileDescription: user related API
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 16:21
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/07 22:34
*/
import req from "@/request";

export function uploadPic(pic, album) {
  return req({
    headers: {
      "Content-Type":
        "multipart/form-data;boundary=<calculated when request is sent",
    },
    contentType: "multipart/form-data;application/json",
    url: `/upload/${album}`,
    method: "post",
    data: pic,
  });
}

export function deletePic(id) {
  return req({
    url: `/upload/delete/${id}`,
    method: "put",
    data: null,
  });
}
