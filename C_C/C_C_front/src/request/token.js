/*
  * @FileDescription: Token Related Functions
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:10
  * @LastEditor: Zirui Qiao
  * @LastEditTime: 2023/01/01 21:50
*/
export function getToken() {
  //console.log("get token: " + localStorage.token);
  return localStorage.token;
}

export function setToken(token) {
  //console.log("set token: " + token);
  return (localStorage.token = token);
}

export function removeToken() {
  //console.log("remove token: ");
  return localStorage.removeItem("token");
}

export const url = 'zirui-site.com:8888';
