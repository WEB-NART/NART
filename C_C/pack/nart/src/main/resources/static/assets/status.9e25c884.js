import{r as s}from"./userStore.df57d0cc.js";function r(e,t,a){return s({headers:{Authorization:e},method:"post",url:"/status/post",data:{msg:t,pics:a}})}function o(e,t,a){return s({headers:{Authorization:e},method:"post",url:`/status/list/my/${t}`,data:{pageSize:a.pageSize,pageNum:a.pageNum}})}function l(e,t){return s({headers:{Authorization:e},method:"post",url:"/status/list/all/-1",data:{pageSize:t.pageSize,pageNum:t.pageNum}})}function n(e,t){return s({headers:{Authorization:e},method:"delete",url:`/status/del/${t}`})}function d(e,t){return s({headers:{Authorization:e},method:"put",url:`/status/like/${t}/${!0}`})}function h(e,t){return s({headers:{Authorization:e},method:"put",url:`/status/like/${t}/${!1}`})}export{o as a,n as b,h as d,d as l,r as p,l as s};
