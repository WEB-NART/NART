#  ** API**

# User Operation



## 1. sign in API

API URL: /user/login

corresponding method: UserController -> login()

request type: PUT

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| uname      | String     | username    |
| pwd        | String     | password    |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": "token"
}
```



## 2. sign out API

API URL: /user/logout

corresponding method: UserController -> logout()

request type: PUT

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
|            |            |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 3. register API

API URL: /user/register

corresponding method: UserController -> register()

request type: POST

request params:

| param name | param type | description   |
| ---------- | ---------- | ------------- |
| uname      | String     | username      |
| pwd        | String     | password      |
| email      | String     | email address |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": "token"
}
```



## 4. showAvatar&Uname API

API URL: /user

corresponding method: UserController -> showUnameAvatar()

request type: GET

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
|            |            |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        "avatar": String,
        "uname": String,
        "pwd": null,
        "email": null,
        "birthday": null,
        "phone": null,
        "address": null
    }
}
```



## 5. showUserInfo API

API URL: /user/info

corresponding method: UserController -> showUserInfo()

request type: GET

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
|            |            |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        "avatar": String,
        "uname": String,
        "pwd": null,
        "email": String,
        "birthday": String,
        "phone": String,
        "address": String
    }
}
```



## 6. changeUserInfo API

API URL: /user/changeInfo

corresponding method: UserController -> changeUserInfo()

request type: PUT

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| avatar     | String     |             |
| uname      | String     |             |
| oldPwd     | String     |             |
| pwd        | String     |             |
| email      | String     |             |
| birthday   | String     |             |
| phone      | String     |             |
| address    | String     |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



# Group Operation



## 7. showGroupList API

API URL: /group/list

corresponding method: GroupController -> showGroupList()

request type: GET

request params:

| param name | param type | description                                       |
| ---------- | ---------- | ------------------------------------------------- |
| pageSize   | int        | page size                                         |
| pageNum    | int        | current page num                                  |
| showAll    | Boolean    | **true**: ignore state; **false**: consider state |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id": String,
        	"avatar": String,
        	"name": String,
        	"state": int,
        	"notice": String
    	},
    	{
    		"id": String,
        	"avatar": String,
        	"name": String,
        	"state": int,
        	"notice": String
		}
    }
}
```



## 8. showGroupInvitions API

API URL: /group/inviteList

corresponding method: GroupController -> showInviteList()

request type: GET

request params:

| param name | param type | description      |
| ---------- | ---------- | ---------------- |
| pageSize   | int        | page size        |
| pageNum    | int        | current page num |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id": String,
        	"groupId": String,
        	"msg": String,
        	"groupName": String,
        	"groupAvatar": String,
        	"senderName": String,
    	},
    	{
    		"id": String,
        	"groupId": String,
        	"msg": String,
        	"groupName": String,
        	"groupAvatar": String,
        	"senderName": String,
    	}
    }
}
```



## 9. hideGroup API

API URL: /group/state

corresponding method: GroupController -> changeGroupState()

request type: PUT

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| groupId    | String     | group's id  |
| state      | int        | 1: hide     |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 10. muteGroup API

API URL: /group/state

corresponding method: GroupController -> changeGroupState()

request type: PUT

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| groupId    | String     | group's id  |
| state      | int        | 2: mute     |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 11. leaveGroup API

API URL: /group/del

corresponding method: GroupController -> leaveGroup()

request type: DELETE

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| groupId    | String     | group's id  |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 12. showMemberList API

API URL: /group/memberList

corresponding method: GroupController -> showGroupMemberList()

request type: GET

request params:

| param name | param type | description      |
| ---------- | ---------- | ---------------- |
| groupId    | String     | group's id       |
| pageSize   | int        | page size        |
| pageNum    | int        | current page num |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id":int,
        	"uname":String,
        	"avatar":String
    	},
    	{
        	"id":int,
        	"uname":String,
        	"avatar":String
    	}
    }
}
```



## 13. createNewGroup API

API URL: /group/create

corresponding method: GroupController -> createGroup()

request type: POST

request params:

| param name | param type | description  |
| ---------- | ---------- | ------------ |
| groupName  | String     | group's name |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": groupId
}
```



## 14. changeGroupInfo API

API URL: /group/changeInfo

corresponding method: GroupController -> changeGroupInfo()

request type: PUT

request params:

| param name  | param type | description |
| ----------- | ---------- | ----------- |
| groupId     | String     | group's id  |
| groupName   | String     |             |
| groupAvatar | String     |             |
| notice      | String     |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 15. sendGroupInvite API

API URL: /group/send

corresponding method: GroupController ->sendInvite()

request type: POST

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| groupId    | String     | group's id  |
| receiverId | String     |             |
| msg        | String     |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 16. responseGroupInvite API

API URL: /group/resp

corresponding method: GroupController -> respGroupInvite()

request type: PUT

request params:

| param name | param type | description           |
| ---------- | ---------- | --------------------- |
| inviteId   | String     | group invitation's id |
| agree      | Boolean    | accept/reject         |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



# Friend Operation



## 17. showFriendList API

API URL: /friend/list

corresponding method: FriendController -> showFriendList()

request type: GET

request params:

| param name | param type | description                                       |
| ---------- | ---------- | ------------------------------------------------- |
| pageSize   | int        | page size                                         |
| pageNum    | int        | current page num                                  |
| showAll    | Boolean    | **true**: ignore state; **false**: consider state |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id": String,
        	"name": String,
        	"avatar": String,
        	"state": Integer
    	},
    	{
    		"id": String,
        	"name": String,
        	"avatar": String,
        	"state": Integer
		}
    }
}
```



## 18. searchFriend API

API URL: /friend/search

corresponding method: FriendController -> searchFriend()

request type: GET

request params:

| param name | param type | description      |
| ---------- | ---------- | ---------------- |
| input      | String     | input            |
| pageSize   | int        | page size        |
| pageNum    | int        | current page num |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id": String,
        	"uname": String,
        	"avatar": String,
        	"state": Integer
    	},
    	{
    		"id": String,
        	"uname": String,
        	"avatar": String,
        	"state": Integer
		}
    }
}
```



## 19. showFriendRequests API

API URL: /friend/reqlist

corresponding method: FriendController -> showReqList()

request type: GET

request params:

| param name | param type | description      |
| ---------- | ---------- | ---------------- |
| pageSize   | int        | page size        |
| pageNum    | int        | current page num |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id": String,
        	"friendId": String,
        	"msg": String,
        	"friendName": String,
        	"friendAvatar": String,
    	},
    	{
        	"id": String,
    		"friendId": String,
        	"msg": String,
        	"friendName": String,
        	"friendAvatar": String,
    	}
    }
}
```



## 20. hideFriend API

API URL: /friend/state

corresponding method: FriendController -> changeFriendState()

request type: PUT

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| friendId   | String     | friend's id |
| state      | int        | 1:hide      |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 21. muteFriend API

API URL: /friend/mute

corresponding method: FriendController -> changeFriendState()

request type: PUT

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| friendId   | String     | friend's id |
| state      | int        | 2:mute      |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 22. deleteFriend API

API URL: /friend/del

corresponding method: FriendController -> delFriend()

request type: DELETE

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| friendId   | String     | friend's id |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 23. searchNewFriend API

API URL: /friend/searchNew

corresponding method: FriendController -> searchNew()

request type: GET

request params:

| param name                | param type | description      |
| ------------------------- | ---------- | ---------------- |
| input (email/phone/uname) | String     | group's id       |
| pageSize                  | int        | page size        |
| pageNum                   | int        | current page num |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id":int,
        	"uname":String,
        	"avatar":String
    	},
    	{
        	"id":int,
        	"uname":String,
        	"avatar":String
    	}
    }
}
```



## 24. sendFriendRequest API

API URL: /friend/send

corresponding method: FriendController ->sendFriendReq()

request type: POST

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| receiverId | String     |             |
| msg        | String     |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 25. responseFriendReq API

API URL: /friend/resp

corresponding method: FriendController -> respFriendReq()

request type: POST

request params:

| param name | param type | description  |
| ---------- | ---------- | ------------ |
| requestId  | String     |              |
| agree      | Boolean    | agree/reject |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



# Status&Comment Operation



## 26. uploadPic API

API URL: /upload

corresponding method: UploadController -> uploadPic()

request type: POST

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| pic        | file       | file name   |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": "https://static.picAddress.com/xx.png"
}
```



## 27. postStatus API

API URL: /status/post

corresponding method:  StatusController -> postStatus()

request type: POST

request params:

| param name | param type | description                        |
| ---------- | ---------- | ---------------------------------- |
| msg        | String     | status message(maybe null)         |
| pics       | String     | picture addresses, separate by `;` |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 28. showStatusList API

API URL: /status/list

corresponding method:  StatusController -> showStatusList()

request type: GET

request params:

| param name | param type | description             |
| ---------- | ---------- | ----------------------- |
| type       | String     | my status or all status |
| pageSize   | int        | page size               |
| pageNum    | int        | current page num        |

return data:(in descending chronological order)

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"uid": String,
        	"uname": String,
            "avatar": String,
            "statusId": String,
            "createDate": {
                "year":int,
                "month":int,
                "day": int,
                "hour": int,
                "min": int
            },
    		"likes": int,
    		"liked": Boolean,
            "msg": String,
            "pics": String,
            "comments":{
				{
                	"commentId": String,
                	"uname":String,
                	"msg": String,
                	"commentDate": {
                        "year":int,
                        "month":int,
                        "day": int,
                        "hour": int,
                        "min": int
                    }
            	},
    			{
                    "commentId": String,
                	"uname":String,
                	"msg": String,
                    "commentDate": {
                        "year":int,
                        "month":int,
                        "day": int,
                        "hour": int,
                        "min": int
                    },
				}
            }
    	},
		{
            "uid": String,
        	"uname": String,
            "avatar": String,
            "statusId": String,
            "createDate": {
                "year":int,
                "month":int,
                "day": int,
                "hour": int,
                "min": int
            },
    		"likes": int,
            "liked": Boolean,
            "msg": String,
            "pics": String,
            "comments":{
				{
                	"commentId": String,
                	"uname":String,
                	"msg": String,
                	"commentDate": {
                        "year":int,
                        "month":int,
                        "day": int,
                        "hour": int,
                        "min": int
                    },
            	},
    			{
                    "commentId": String,
                	"uname":String,
                	"msg": String,
                    "commentDate": {
                        "year":int,
                        "month":int,
                        "day": int,
                        "hour": int,
                        "min": int
                    },
				}
            }
    	}
    }
}
```



## 29. deleteStatus API

API URL: /status/del

corresponding method:  StatusController -> delStatus()

request type: DELETE

request params:

| param name | param type | description   |
| ---------- | ---------- | ------------- |
| statusId   | String     | a status's id |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 30. likeStatus API

API URL: /status/like

corresponding method:  StatusController -> likeStatus()

request type: PUT

request params:

| param name | param type | description   |
| ---------- | ---------- | ------------- |
| statusId   | String     | a status's id |
| like       | Boolean    | true          |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 31. dislikeStatus API

API URL: /status/like

corresponding method:  StatusController -> likeStatus()

request type: PUT

request params:

| param name | param type | description   |
| ---------- | ---------- | ------------- |
| statusId   | String     | a status's id |
| like       | Boolean    | false         |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 32. showCommentList API

API URL: /comment/list

corresponding method:  CommentController -> showCommentList()

request type: GET

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| statusId   | String     |             |

return data:(in descending chronological order)

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
            "commentId": String,
            "uname":String,
            "msg": String,
            "commentDate": {
                "year":int,
                "month":int,
                "day": int,
                "hour": int,
                "min": int
            }
		},
    	{
            "commentId": String,
            "uname":String,
            "msg": String,
            "commentDate": {
                "year":int,
                "month":int,
                "day": int,
                "hour": int,
                "min": int
            }
		}
    }
}
```



## 33. postComment API

API URL: /comment/post

corresponding method:  CommentController -> postComment()

request type: POST

request params:

| param name | param type | description |
| ---------- | ---------- | ----------- |
| statusId   | String     |             |
| msg        | String     | a text      |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



# Chat Operation



## 34. showHistoryList API

API URL: /chat/history

corresponding method:  CommentController -> showHistory()

request type: GET

request params:

| param name | param type | description                                        |
| ---------- | ---------- | -------------------------------------------------- |
| chatId     | String     | a friend / group id                                |
| type       | String     | **friend**: friend id   \|   **group**: a group id |

return data: (in descending chronological order)

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"msgId": String,
        	"senderId": String,
        	"senderName": String,
        	"senderAvatar": String,
        	"sentDate": {
                "year":int,
                "month":int,
                "day": int,
                "hour": int,
                "min": int,
            },
    		"msg": String,
            "msgType": String
    	},
		{
        	"msgId": String,
        	"senderId": String,
        	"senderName": String,
        	"senderAvatar": String,
        	"sentDate": {
                "year":int,
                "month":int,
                "day": int,
                "hour": int,
                "min": int,
            },
    		"msg": String,
            "msgType": String
    	},
    }
}
```



## 35. sendMsg  \*\*\*\*API

API URL: /chat/send

request type: POST

request params:

| param name | param type | description                                     |
| ---------- | ---------- | ----------------------------------------------- |
| chatId     | String     | a user/ a group's id                            |
| type       | String     | **friend**: friend id   \|**group**: a group id |
| msg        | String     | chat message                                    |
| msgType    | String     | message type (text/picture)                     |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 36. receiveDetailMsg \*\*\*\*API

API URL: /chat/receiveDetail

request type: GET

request params:

| param name | param type | description                        |
| ---------- | ---------- | ---------------------------------- |
| chatId     | String     | a friend chat/ group chat id       |
| receiverId | String     | a user/ a group's id               |
| type       | String     | the receiver is a group or a user? |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        "senderId": String,
        "senderAvatar": String,
        "senderName": String,
        "sentDate": {
            "year":int,
            "month":int,
            "day": int,
            "hour": int,
            "min": int
        },
        "msg": String,
        "msgType": String
    }
}
```

## 37. receiveNotice  \*\*\*\*API

API URL: /chat/receiveNotice

request type: GET

request params:

| param name | param type | description                        |
| ---------- | ---------- | ---------------------------------- |
| chatId     | String     | a friend/ group id                 |
| type       | String     | the receiver is a group or a user? |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": Boolean
}
```
