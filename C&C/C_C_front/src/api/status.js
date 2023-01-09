/*
  * @FileDescription: user related API
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 16:22
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/25 20:20
*/
import req from "@/request";

export function postStatus(token, msg, pics) {
    return req({
        headers: {'Authorization': token},
        method: 'post',
        url: '/status/post',
        data: {
            msg,
            pics
        }
    })
}

export function showMyStatusList(token, uid, page) {
    let type = "my";
    return req({
        headers: {'Authorization': token},
        method: 'post',
        url: `/status/list/${type}/${uid}`,
        data: {
            pageSize: page.pageSize,
            pageNum: page.pageNum
        }
    })
}

export function showAllStatusList(token, page) {
    let type = "all";
    let uid = "-1";
    return req({
        headers: {'Authorization': token},
        method: 'post',
        url: `/status/list/${type}/${uid}`,
        data: {
            pageSize: page.pageSize,
            pageNum: page.pageNum
        }
    })
}

export function deleteStatus(token, statusId) {
    return req({
        headers: {'Authorization': token},
        method: 'delete',
        url: `/status/del/${statusId}`,
    })
}

export function likeStatus(token, statusId) {
    let like = true;
    return req({
        headers: {'Authorization': token},
        method: 'put',
        url: `/status/like/${statusId}/${like}`
    })
}

export function dislikeStatus(token, statusId) {
    let like = false;
    return req({
        headers: {'Authorization': token},
        method: 'put',
        url: `/status/like/${statusId}/${like}`
    })
}