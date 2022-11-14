# Vue Component # 

## ScrollPage

1. Direction [up  (history) & down (search friend and check status)]
2. Scroll 需要传一个load function
3. 需要一个loading 判定是否已经发送请求了
4. 需要一个no data 判定已经没有新数据了， 停止接收请求
5. Footer 
   1. LOAD MORE 小三角

## ResultItem

1. Avatar 
2. User Name
3. div
   1. button label
   2. button 

4. Close Logo

## AcceptableItem 

1. Avatar 
2. Name
3. Message
4. Accept Button
5. Reject Button
6. Dividing Line

## StatusItem 

1. StatusId
1. Avatar 
1. Uname
2. Message (nullable)
3. Pictures (nullable)
4. Heart 
4. HeartNum
5. Input Bar 
6. Table
   1. CommentItem * n
7. Dividing Line

## CommentItem 

1. username
2. `:`
3. message 
3. date

## MyStatusItem

1. Message (nullable)
2. Pictures (nullable)
3. Delete Button (boolean  isMine)

## InfoItem

1. Label
2. Dividing Line 
3. Value (hide)
4. Change Button (hide) (label v-if  isPwd)
   1. Pop Window 
      1. Input Old Pwd 
      2. Input New Pwd
      3. Confirm New Pwd
      4. Confirm Button

## ChatInputBox

1. textArea(Say something here...)
2. add photo button
3. send button

## ChatMessage

1. Avatar
2. Name
3. div
   1. slot
4. time

# Views

## MainPage   App.vue 

1. Top fix bar

   1. Logo 

   2. Search 

   3. Avatar 

      1. Post My Status

         - router-> PostMyStatus

      2. Check My Status

         - router-> CheckAllMyStatus

      3. Show All Friends

      4. Show All Groups 

      5. Edit My Info

         - router-> EditMyInfo

      6. Log Out

         - Alert

         - Log Out Succeed Page 

2. Navigation Bar

   1. Friend Logo 
   2. Group Logo
   3. Find Something New 
      - router-> FindSthNew
   4. Friends Requests 
      - router-> FriendRequests
   5. Group Invitations
      - router-> GroupInvitations
   6. Status
      - router-> Status

3. Friend List 

   1. Friend1 

      - router-> FriendChat/{friendId}

      1. Friend Name
      2. Hide
      3. Mute
      4. Delete

   2. Friend2 

   3. ...

4. Group List

   1. Group1 

      - router->GroupChat/{groupId}

      1. Group Name
      2. Hide 
      3. Mute 
      4. Leave 

   2. Group2

   3. ...

5. <router-view>

## FindSthNew

1. Header 
   1.  Search Bar 
   2. Add New Friend Button (Search Bar Is Not Empty)
      - router->AddNewFriend
   3. Create New Group
      - router->CreateNewGroup
   4. Dividing Line
2. <router-view>

## AddNewFriend 

```vue
<table>
    <ScrollPage>
        <div  v-for="user in users">
            <ResultItem :avatar="user.avatar"
                        :username="user.uname"
                        :buttonLabel="Add"
                        :button="addFun"
                        :key="user.id"></ResultItem>
        </div>
	</ScrollPage>
</table>
```

## CreateNewGroup

1. Header 

   1. Avatar
   2. Add Avatar Button
   3. Group Name
   4. Input Bar

2. Table

   1. Item * n
      1. Avatar
      2. User Name

3. Add Member Button 

   1. Pop Out Window

      1. Search Bar

      2. ````vue
         <table>
             <ScrollPage>
                 <div v-for="user in users">
                     <ResultItem :avatar="user.avatar"
                                 :username="user.uname"
                                 :buttonLabel="Invite"
                                 :button="inviteFun"
                                 :key="user.id"></ResultItem>
                 </div>
         	</ScrollPage>
         </table>
         ````

## FriendRequests 

1. Header (Dividing Line)

2. ```vue
   <table>
       <ScrollPage>
           <div v-for="req in requests">
               <AcceptableItem :avatar="req.avatar"
                           :name="req.name"
                           :message="req.msg"
                           :acceptfun="acceptFun(req.friendId)"
                           :rejectfun="rejectFun(req.id)"
                           :key="req.id"></ResultItem>
           </div>
   	</ScrollPage>
   </table>
   ```

## GroupInvitations 

1. Header (Dividing Line)

2. ```vue
   <table>
       <ScrollPage>
           <div v-for="invite in invites">
               <AcceptableItem :avatar="invite.groupAvatar"
                           :name="invite.groupName"
                           :senderName="invite.senderName"
                           :message="invite.msg"
                           :acceptfun="acceptFun(invite.groupId)"
                           :rejectfun="rejectFun(invite.id)"
                           :key="invite.id"></ResultItem>
           </div>
   	</ScrollPage>
   </table>
   ```

## Status

```vue
<table>
    <ScrollPage>
        <div v-for="s in status">
            <StatusItem :statusId="s.statusId"
                        :avatar="s.avatar"
                        :uname="s.uname"
                        :message="s.msg"
                        :pictures="s.pics"
                        :heart="s.liked"
                        :heartNum="s.likes"
                        :key="s.statusId">
                <div v-for="comment in s.comments">
                    <CommentItem :username="comment.uname"
                                 :message="comment.msg"
                                 :date="comment.commentDate"></CommentItem>
                </div>
           	</StatusItem>
        </div>
	</ScrollPage>
</table>
```



## PostMyStatus

1. Text Area (500 limit)

2. Message (put pictures here...)

3. ```vue
   <div v-for="name in pics">
       <img :src="name">
   </div>
   ```

4. Add Pictures Button

## CheckAllMyStatus

```vue
<template>
	<el-timeline>
        <div v-for="item in status">
            <el-timeline-item>
                <el-card>
                    <MyStatusItem :message="item.msg"
                                  :pictures="item.pics"
                                  :isMine="true"></MyStatusItem>
                </el-card>
    		</el-timeline-item>
    	</div>
    </el-timeline>
</template>
```

## CheckAllFriendStatus

````vue
<template>
	<el-timeline>
        <div v-for="item in status">
            <el-timeline-item>
                <el-card>
                    <MyStatusItem :message="item.msg"
                                  :pictures="item.pics"
                                  :isMine="false"></MyStatusItem>
                </el-card>
            </el-timeline-item>
    	</div>
    </el-timeline>
</template>
````

## EditMyInfo

1. Avatar

2. Avatar Change Button

3. div

   1. ```vue
      <div>
          <infoItem :Label :Value :HideValue :HideButton></infoItem>
      </div>
      ```

   2. *6

## CheckFriendInfo

1. Avatar

2. div

   1. ```vue
      <div>
          <infoItem :Label :Value :HideValue :HideButton></infoItem>
      </div>
      ```

   2. *2

3. CheckAllFriendStatus

## GroupInfo

1. Label (AllGroupMembers)

2. ```vue
   <div v-for="member in group">
       <div :key="member.id">
           <img :src="member.avatar">
           <p>{{member.uname}}</p>
       </div>
   </div>
   ```

3. Invite Button

4. Show More Button (count click time firstTime+ 10  2ndTime show all) 

5. Label (GroupName)

6. Group Name 

7. Label (Important Notice)

8. Notice Message

## FriendChat

```vue
<div>
    <ScrollPage>
        <div v-for="msg in msgs">
            <ChatMessage 
                         :avatar="msg.senderAvatar" 
                         :name="msg.senderName" 
                         :time="msg.sentDate" 
                         :isMe="this.userId == msg.senderId"
                         :key="sentDate">
                	<div v-if="msg.msgType == 'text'">
                    	{{msg.msg}}    
                	</div>
                	<div v-else>
						<img :src="msg.msg">                        
                	</div>
            </ChatMessage>
        </div>
    </ScrollPage>
</div>
<div>       
    <ChatInputBox :sendfun="sendfun"></ChatInputBox>
</div>
```

## GroupChat

```vue
<div>
    <router-link href="GroupInfo"></router-link>
    <ScrollPage>
        <div v-for="msg in msgs">
            <ChatMessage 
                         :avatar="msg.senderAvatar" 
                         :name="msg.senderName" 
                         :time="msg.sentDate" 
                         :isMe="this.userId == msg.senderId"
                         :key="sentDate">
                	<div v-if="msg.msgType == 'text'">
                    	{{msg.msg}}    
                	</div>
                	<div v-else>
						<img :src="msg.msg">                        
                	</div>
            </ChatMessage>
        </div>
    </ScrollPage>
</div>
<div>       
    <ChatInputBox :sendfun="sendfun"></ChatInputBox>
</div>
```
