# HTTP Request Structure

> All contain Request Body except for HTTP GET

## HTTP GET

> Retrieving non-confidential data

 ```apl
 HTTP GET http://www.appdomain.com/users
 HTTP GET http://www.appdomain.com/users?size=20&page=5
 HTTP GET http://www.appdomain.com/users/123
 HTTP GET http://www.appdomain.com/users/123/address
 ```

## HTTP POST

> Creating new subordinate resources or,
>
> Retrieving confidential data

```apl
HTTP POST http://www.appdomain.com/users
HTTP POST http://www.appdomain.com/users/123/accounts
```

## HTTP PUT

> Updating existing resources

```apl
HTTP PUT http://www.appdomain.com/users/123
HTTP PUT http://www.appdomain.com/users/123/accounts/456
```

## HTTP DELETE

> Delete resources

```apl
HTTP DELETE http://www.appdomain.com/users/123
HTTP DELETE http://www.appdomain.com/users/123/accounts/456
```



# HTTP Response Structure

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": data
}
```

| Parameter Name | Parameter description                                        |
| -------------- | ------------------------------------------------------------ |
| success        | State whether the corresponding request is success.<br/>return `true` if success, otherwise return `false` |
| code           | Error Code<br/>return `200` if success, otherwise return a five digit integer:<br/>For example, `10101` |
| msg            | Error Message<br/>return `success` if success, otherwise return failing detail:<br/>For example, `Registration Fail, Password Not Match` |
| data           | Payload<br/>The returning data of the response corresponds to a request.<br/>Various Data Structure: `String`, `Integer`, `Object`, `Array`, `Array of Object` |

# Client API

# User Operation

## 1. sign in API

API URL: /user/login

corresponding method: UserController -> login()

request type: PUT

request params:

| param name | param type           | description |
| ---------- | -------------------- | ----------- |
| uname      | String (RequestBody) | username    |
| pwd        | String (RequestBody) | password    |

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

| param name | param type           | description   |
| ---------- | -------------------- | ------------- |
| uname      | String (RequestBody) | username      |
| pwd        | String (RequestBody) | password      |
| email      | String (RequestBody) | email address |

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

| param name | param type            | description                     |
| ---------- | --------------------- | ------------------------------- |
| id         | String (PathVariable) | user id or -1: the current user |

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

| param name | param type | description (if null, ignore)                |
| ---------- | ---------- | -------------------------------------------- |
| avatar     | String     |                                              |
| uname      | String     |                                              |
| oldPwd     | String     | compare with the user old pwd                |
| pwd        | String     | user new pwd, only change when old pwd match |
| email      | String     |                                              |
| birthday   | String     |                                              |
| phone      | String     |                                              |
| address    | String     |                                              |

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

request type: POST

request params:

| param name | param type        | description      |
| ---------- | ----------------- | ---------------- |
| pageSize   | int (RequestBody) | page size        |
| pageNum    | int (RequestBody) | current page num |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id": String,
        	"state": int, // 1: hide; 2: mute; 0: normal
        	"newMsg": Boolean, // if there are any new msg in the group after user's leave_time
        	"avatar": String,
        	"name": String,
        	"notice": String
    	},
    	{
    		"id": String,
        	"state": int,
        	"newMsg": Boolean,
        	"avatar": String,
        	"name": String,
        	"notice": String
		}
    }
}
```



## 8. showGroupInvitions API

API URL: /group/inviteList

corresponding method: GroupController -> showInviteList()

request type: POST

request params:

| param name | param type        | description      |
| ---------- | ----------------- | ---------------- |
| pageSize   | int (RequestBody) | page size        |
| pageNum    | int (RequestBody) | current page num |

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

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| groupId    | String (PathVariable) | group's id  |
| state      | int (PathVariable)    | 1: hide     |

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

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| groupId    | String (PathVariable) | group's id  |
| state      | int (PathVariable)    | 2: mute     |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 11. unsetGroup API

API URL: /group/state

corresponding method: GroupController -> changeGroupState()

request type: PUT

request params:

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| groupId    | String (PathVariable) | group's id  |
| state      | int (PathVariable)    | 0: normal   |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 12. leaveGroup API

API URL: /group/del

corresponding method: GroupController -> leaveGroup()

request type: DELETE

request params:

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| groupId    | String (PathVariable) | group's id  |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 13. showMemberList API

API URL: /group/memberList

corresponding method: GroupController -> showGroupMemberList()

request type: GET

request params:

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| groupId    | String (PathVariable) | group's id  |



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



## 14. createNewGroup API

API URL: /group/create

corresponding method: GroupController -> createGroup()

request type: POST

request params:

| param name | param type            | description                    |
| ---------- | --------------------- | ------------------------------ |
| groupName  | String (PathVariable) | group's name, -1 means no name |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": groupId
}
```



## 15. changeGroupInfo API

API URL: /group/changeInfo

corresponding method: GroupController -> changeGroupInfo()

request type: PUT

request params:

| param name  | param type           | description |
| ----------- | -------------------- | ----------- |
| groupId     | String (RequestBody) | group's id  |
| groupName   | String (RequestBody) |             |
| groupAvatar | String (RequestBody) |             |
| notice      | String (RequestBody) |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 16. sendGroupInvite API

API URL: /group/send

corresponding method: GroupController ->sendInvite()

request type: POST

request params:

| param name | param type           | description   |
| ---------- | -------------------- | ------------- |
| groupId    | String (RequestBody) | group's id    |
| receiverId | String (RequestBody) | receiver's id |
| msg        | String (RequestBody) | sent msg      |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 17. responseGroupInvite API

API URL: /group/resp

corresponding method: GroupController -> respGroupInvite()

request type: PUT

request params:

| param name | param type             | description           |
| ---------- | ---------------------- | --------------------- |
| inviteId   | String (PathVariable)  | group invitation's id |
| agree      | Boolean (PathVariable) | accept/reject         |

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

## 18. showFriendList API

API URL: /friend/list

corresponding method: FriendController -> showFriendList()

request type: POST

request params:

| param name | param type        | description      |
| ---------- | ----------------- | ---------------- |
| pageSize   | int (RequestBody) | page size        |
| pageNum    | int (RequestBody) | current page num |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        {
        	"id": String,
        	"state": int, 
        	"newMsg": Boolean,
        	"name": String,
        	"avatar": String,
        	"state": Integer
    	},
    	{
    		"id": String,
        	"state": int, 
        	"newMsg": Boolean,
        	"name": String,
        	"avatar": String,
        	"state": Integer
		}
    }
}
```



## 19. searchFriend API

API URL: /friend/search

corresponding method: FriendController -> searchFriend()

request type: POST

request params:

| param name | param type            | description      |
| ---------- | --------------------- | ---------------- |
| input      | String (PathVariable) | input            |
| pageSize   | int (RequestBody)     | page size        |
| pageNum    | int (RequestBody)     | current page num |

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



## 20. showFriendRequests API

API URL: /friend/reqlist

corresponding method: FriendController -> showReqList()

request type: POST

request params:

| param name | param type        | description      |
| ---------- | ----------------- | ---------------- |
| pageSize   | int (RequestBody) | page size        |
| pageNum    | int (RequestBody) | current page num |

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



## 21. hideFriend API

API URL: /friend/state

corresponding method: FriendController -> changeFriendState()

request type: PUT

request params:

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| friendId   | String (PathVariable) | friend's id |
| state      | int (PathVariable)    | 1:hide      |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 22. muteFriend API

API URL: /friend/state

corresponding method: FriendController -> changeFriendState()

request type: PUT

request params:

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| friendId   | String (PathVariable) | friend's id |
| state      | int (PathVariable)    | 2:mute      |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 23. unsetFriend API

API URL: /friend/state

corresponding method: FriendController -> changeFriendState()

request type: PUT

request params:

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| friendId   | String (PathVariable) | friend's id |
| state      | int (PathVariable)    | 0:normal    |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 24. deleteFriend API

API URL: /friend/del

corresponding method: FriendController -> delFriend()

request type: DELETE

request params:

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| friendId   | String (PathVariable) | friend's id |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 25. searchNewFriend API

API URL: /friend/searchNew

corresponding method: FriendController -> searchNew()

request type: POST

request params:

| param name                | param type            | description      |
| ------------------------- | --------------------- | ---------------- |
| input (email/phone/uname) | String (PathVariable) | group's id       |
| pageSize                  | int (RequestBody)     | page size        |
| pageNum                   | int (RequestBody)     | current page num |

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



## 26. sendFriendRequest API

API URL: /friend/send

corresponding method: FriendController ->sendFriendReq()

request type: POST

request params:

| param name | param type           | description |
| ---------- | -------------------- | ----------- |
| receiverId | String (RequestBody) |             |
| msg        | String (RequestBody) |             |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 27. responseFriendReq API

API URL: /friend/resp

corresponding method: FriendController -> respFriendReq()

request type: POST

request params:

| param name | param type             | description  |
| ---------- | ---------------------- | ------------ |
| requestId  | String (PathVariable)  |              |
| agree      | Boolean (PathVariable) | agree/reject |

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

## 28. uploadPic API

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



## 29. postStatus API

API URL: /status/post

corresponding method:  StatusController -> postStatus()

request type: POST

request params:

| param name | param type           | description                        |
| ---------- | -------------------- | ---------------------------------- |
| msg        | String (RequestBody) | status message(maybe null)         |
| pics       | String (RequestBody) | picture addresses, separate by `;` |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 30. showStatusList API

API URL: /status/list

corresponding method:  StatusController -> showStatusList()

request type: POST

request params:

| param name | param type            | description         |
| ---------- | --------------------- | ------------------- |
| type       | String (PathVariable) | "my" / "all"        |
| uid        | String (PathVariable) | user id; -1: ignore |
| pageSize   | int (RequestBody)     | page size           |
| pageNum    | int (RequestBody)     | current page num    |

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
            "pics": Array<String>,
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
            "pics": Array<String>,
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



## 31. deleteStatus API

API URL: /status/del

corresponding method:  StatusController -> delStatus()

request type: DELETE

request params:

| param name | param type            | description   |
| ---------- | --------------------- | ------------- |
| statusId   | String (PathVariable) | a status's id |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 32. likeStatus API

API URL: /status/like

corresponding method:  StatusController -> likeStatus()

request type: PUT

request params:

| param name | param type             | description   |
| ---------- | ---------------------- | ------------- |
| statusId   | String (PathVariable)  | a status's id |
| like       | Boolean (PathVariable) | true          |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 33. dislikeStatus API

API URL: /status/like

corresponding method:  StatusController -> likeStatus()

request type: PUT

request params:

| param name | param type             | description   |
| ---------- | ---------------------- | ------------- |
| statusId   | String (PathVariable)  | a status's id |
| like       | Boolean (PathVariable) | false         |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 34. showCommentList API

API URL: /Comment/list

corresponding method:  CommentController -> showCommentList()

request type: GET

request params:

| param name | param type            | description |
| ---------- | --------------------- | ----------- |
| statusId   | String (PathVariable) |             |

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



## 35. postComment API

API URL: /comment/post

corresponding method:  CommentController -> postComment()

request type: POST

request params:

| param name | param type           | description |
| ---------- | -------------------- | ----------- |
| statusId   | String (RequestBody) |             |
| msg        | String (RequestBody) | a text      |

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

## 36. showFriendChatHistory API

API URL: /chat/history/\${type}/\${chatId}

corresponding method:  CommentController -> showHistory()

request type: POST

request params:

| param name | param type            | description      |
| ---------- | --------------------- | ---------------- |
| chatId     | String (PathVariable) | a friend id      |
| type       | String (PathVariable) | **"friend"**     |
| pageNum    | int (RequestBody)     | current page num |
| pageSize   | int (RequestBody)     | page size        |

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
            "msgType": String, // "text" / "picture"
    		"isMe": Boolean,
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
            "msgType": String, // "text" / "picture"
    		"isMe": Boolean,
    	},
    }
}
```



## 37. showGroupChatHistory API

API URL: /chat/history/\${type}/\${chatId}

corresponding method:  CommentController -> showHistory()

request type: POST

request params:

| param name | param type            | description      |
| ---------- | --------------------- | ---------------- |
| chatId     | String (PathVariable) | a group id       |
| type       | String (PathVariable) | **"group"**      |
| pageNum    | int (RequestBody)     | current page num |
| pageSize   | int (RequestBody)     | page size        |

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
            "msgType": String, // "text" / "picture"
    		"isMe": Boolean,
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
            "msgType": String, // "text" / "picture"
    		"isMe": Boolean,
    	},
    }
}
```



## 38. sendFriendMsg  API

API URL: /chat/send

request type: POST

request params:

| param name | param type           | description                     |
| ---------- | -------------------- | ------------------------------- |
| chatId     | String (RequestBody) | a user's id                     |
| type       | String (RequestBody) | **"friend"**                    |
| msg        | String (RequestBody) | chat message                    |
| msgType    | String (RequestBody) | message type ("text"/"picture") |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 39. sendGroupMsg  API

API URL: /chat/send

request type: POST

request params:

| param name | param type           | description                     |
| ---------- | -------------------- | ------------------------------- |
| chatId     | String (RequestBody) | a group's id                    |
| type       | String (RequestBody) | **"group"**                     |
| msg        | String (RequestBody) | chat message                    |
| msgType    | String (RequestBody) | message type ("text"/"picture") |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



## 40. leaveRoom API

API URL: /chat/leaveRoom/\${roomId}/\${isFriend}

request type: PUT

request params:

| param name | param type             | description                |
| ---------- | ---------------------- | -------------------------- |
| roomId     | String (PathVariable)  | a friend/group's id        |
| isFriend   | Boolean (PathVariable) | is a friend or not (group) |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```



# Admin API

## 41. showUserNum API

API URL: /admin/userNum

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
    "data": 372  //total user amount 
}
```



## 42. showOnlineUserNum API

API URL: /admin/onlineUserNum

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
    "data": 73  //online user amount 
}
```



## 43. showStatusNum API

API URL: /admin/statusNum

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
    "data": 73  // total status amount 
}
```



## 44. showCommentNum API

API URL: /admin/commentNum

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
    "data": 73  //total comments amount 
}
```



## 45. showMessageNum API

API URL: /admin/msgNum

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
    "data": 73  //total chat message sent amount 
}
```



## 46. searchUser API

API URL: /admin/searchUser

request type: POST

request params:

| param name | param type           | description                            |
| ---------- | -------------------- | -------------------------------------- |
| username   | String (RequestBody) | The searched user name, -1 if show all |
| pageSize   | int (RequestBody)    | page size                              |
| pageNum    | int (RequestBody)    | current page num                       |

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
        	'blocked': int
    	},
    	{
        	"id": String,
        	"name": String,
        	"avatar": String,
        	'blocked': int
    	}
    } 
}
```



## 47. blockUser API

API URL: /admin/block/\${userId}/${isBlock}

request type: PUT

request params:

| param name | param type            | description                             |
| ---------- | --------------------- | --------------------------------------- |
| userId     | string (PathVariable) | The user's id whose going to be blocked |
| isBlock    | int (PathVariable)    | 1: Block user; 0: Unblock user          |

return data:

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```
