function i(r){return r.year+"/"+r.month+"/"+r.day}function h(r){return i(r)+" "+r.hour}function f(r){return r.month+"/"+r.day+" "+e(r)}function o(r,t){return t?"\u6628\u5929 "+e(r):"yesterday "+e(r)}function e(r){return r.hour+":"+r.min}function y(r,t){let n=new Date;if(r.year==n.getFullYear())if(r.month==n.getMonth()+1){let u=n.getDate()-r.day;if(u<=7)return r.day==n.getDate()?e(r):u==1&&r.hour>n.getHours()?o(r,t):f(r)}else{let u=31-r.day+n.getDate();if(u<=7)return u==1&&r.hour>n.getHours()?o(r,t):f(r)}else if(r.month==12&&n.getMonth()==0){let u=31-r.day+n.getDate();if(u<=7)return r.day==n.getDate()?e(r):u==1&&r.hour>n.getHours()?o(r,t):h(r)}return i(r)}function g(){return"just now!"}export{y as f,g as n};
