/*
  * @FileDescription: user related API
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 16:20
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2022/12/25 20:10
*/
import req from "@/request";

export function signin(uname, pwd) {
    const data = {
        uname,
        pwd
    }
  return req({
    method: "put",
    url: "/user/login",
    data
  });
}

export function signout(token) {
    return req({
        headers: {'Authorization': token},
        url: '/user/logout',
        method: 'put'
    });
}

export function register(uname, pwd, email) {
    const data = {
        uname,
        pwd,
        email
    }
    return req({
        method: 'post',
        url: '/user/register',
        data
    });
}

export function showAvatarUname(token) {
    return req({
        headers: {'Authorization':token},
        method: 'get',
        url: '/user'
    });
}

export async function showUserInfo(token, id) {
    return req({
        headers: {'Authorization':token},
        method: 'get',
        url: `/user/info/${id}`
    });
}

export function changeUserInfo(token, userInfo) {
    return req({
        headers: {'Authorization':token},
        method: 'put',
        url: '/user/changeInfo',
        data: {
            avatar: userInfo.avatar,
            uname: userInfo.uname,
            oldPwd: userInfo.oldPwd,
            pwd: userInfo.pwd,
            email: userInfo.email,
            birthday: userInfo.birthday,
            phone: userInfo.phone,
            address: userInfo.address
        }
    });
}

