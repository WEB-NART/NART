/*
  * @FileDescription: Structure Router
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:50
  * @LastEditor: Shizhong Shang
  * @LastEditTime: 2023/01/01 22:10
*/
import { createRouter, createWebHistory } from 'vue-router'

const MainPage = () => import('@/views/MainPage.vue')
const FindSthNew = () => import('@/views/FindSthNew.vue')
const CreateGroup = () => import ('@/views/searchAndCreate/createGroup.vue')
const AddNewFriend = () => import('@/views/searchAndCreate/AddNewFriend.vue')
const SearchFriend = () => import('@/views/searchAndCreate/SearchFriendIHave.vue')
const PopWinFriendList = () => import('@/views/searchAndCreate/PopWinFriendList.vue')
const PostStatus = () => import('@/views/searchAndCreate/PostStatus.vue')
const ReqList = () => import('@/views/lists/RequestList.vue')
const InviteList = () => import('@/views/lists/InviteList.vue')
const StatusList = () => import('@/views/lists/StatusList.vue')
const CheckAllMyStatus = () => import('@/views/lists/CheckAllMyStatus.vue')
const ChatRoom = () => import('@/views/chat/ChatRoom.vue')
const CheckFriendInfo = () => import('@/views/infos/CheckFriendInfo.vue')
const EditMyInfo = () => import('@/views/infos/EditMyInfo.vue')
const testVue = () => import('@/views/infos/testVue.vue')
const GroupChatSetting = () => import('@/views/infos/GroupChatSetting.vue')
const Login = () => import('@/views/loginAndRegister/Login.vue')
const Register = () => import('@/views/loginAndRegister/Register.vue')

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/',
      name: 'main',
      component: MainPage,
      children: [
        {
          path: '/findSthNew',
          name: 'findNew',
          component: FindSthNew,
          children: [
            {
              path: '/findSthNew/createGroup',
              name: 'createGroup',
              component: CreateGroup
            },
            {
              path: '/findSthNew/searchNew',
              name: 'addNewFriend',
              component: AddNewFriend
            }
          ]
        },
        {
          path: '/friendRequests',
          name: 'reqList',
          component: ReqList
        },
        {
          path: '/groupInvites',
          name: 'inviteList',
          component: InviteList
        },
        {
          path: '/searchMyFriend',
          name: 'searchFriend',
          component: SearchFriend
        },
        {
          path: '/status',
          name: 'statusList',
          component: StatusList
        },
        {
          path: '/myStatus',
          name: 'myStatus',
          component: CheckAllMyStatus
        },
        {
          path: '/postStatus',
          name: 'postStatus',
          component: PostStatus
        },
        {
          path: '/chatRoom/:id',
          name: 'chatRoom',
          component: ChatRoom
        },
        {
          path: '/friendInfo/:id',
          name: 'friendInfo',
          component: CheckFriendInfo
        },
        {
          path: '/editMyInfo',
          name: 'editMyInfo',
          component: EditMyInfo
        },
        {
          path: '/testVue',
          name: 'testVue',
          component: testVue
        },
        {
          path: '/groupChatInfo',
          name: 'groupChatInfo',
          component: GroupChatSetting
        },
        {
          path: '/popWin',
          name: 'popWin',
          component: PopWinFriendList
        }
      ]
    }
  ]
});

router.beforeEach((to, from, next) => {
  //console.log(localStorage.getItem('token'));
	if (!localStorage.getItem('token')) {
		if (to.name == "login" || to.name == "register") {
			next();
		} else {
			router.push('login');
		}
	} else {
		next();
	}
});


export default router
