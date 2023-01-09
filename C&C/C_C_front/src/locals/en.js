/*
  * @FileDescription: English language
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:15
  * @LastEditor: Shizhong Shang
  * @LastEditTime: 2023/01/07 16:13
*/
export default{
    changeLang: "中文",
    main: {
        menu: {
            item1: 'post my status',
            item2: 'check all my status',
            item3: 'show all friends',
            item3_hide: 'keep hide friends',
            item4: 'show all groups',
            item4_hide: 'keep hide groups',
            item5: 'edit my info',
            item6: 'log out'
        },
        bar: {
            item1: 'Friends',
            item2: 'Groups',
            item3: 'Find Something New',
            item4: 'Friend Requests',
            item5: 'Group Invitations',
            item6: 'Status'
        }
    },
    contactList: {
        hide:"hide",
        mute:"mute",
        leave:"leave",
        del: "delete",
        unset:"unset",
        hideErr: "fail to hide",
        muteErr: "fail to mute",
        unsetErr: "fail to unset",
        delErr: "fail to delete",
        loadError: "fail to load",
        friend: {
            ListError: "fail to load Friend List",
            hideError: "fail to hide Friend",
            muteError: "fail to mute Friend",
            delError: "fail to delete Friend",
            unsetError: "fail to set Friend to normal",
            del: "Are you sure you want to delete ",
            title: "Delete Friend",
            loadErr: "fail to load list",
        },
        group: {
            ListError: "fail to load Group List",
            hideError: "fail to hide Group",
            muteError: "fail to mute Group",
            delError: "fail to leave Group",
            unsetError: "fail to set Group to normal"
        }
    },
    findSthNew: {
        search: "add new friend",
        create: "create new group",
        placeholder: "Search",
    },
    createGroup: {
        avatarLabel: "avatar",
        addAvatar: "add Avatar",
        groupName: "Group Name",
        groupNameHolder: "Please Input Group Name Here",
        addMember: "Add Member",
        create: 'Create Group',
        createError: "Fail to create a group",
        inviteError: "Fail to invite"

    },
    statusItem: {
        commentPlaceHolder: "make a comment",
    },
    chatInputBox: {
        placeHolder: 'Please Input',
        uploadPicError: 'Upload Picture Fail',
        send: 'Send',
        chat: 'Say something here...',

    },
    reqList: {
        loadError: "fail to load friend requests",
        acceptError: "fail to accept the request!",
        rejectError: "fail to reject the request!",
        reject: "Reject",
        accept: "Accept",
    },
    inviteList: {
        loadError: "fail to load group invitations",
        acceptError: "fail to accept the invitation!",
        rejectError: "fail to reject the invitation!",
        reject: "Reject",
        accept: "Accept",
    },
    statusList: {
        loadError: "fail to load statuses",
        toEnd: "This is the End!"
    },
    myStatusList: {
        loadError: "fail to load statuses",
        toEnd: "This is the End!",
        delError: "Fail to delete the status"
    },
    infoItem: {
        title: "Changing Password",
        oldPwd: "Input Old Password",
        newPwd: "Input New Password",
        newPwd2: "Input New Password Again",
        userName: "Username",
        email: "Email",
        birthday: "Birthday",
        password: "Password",
        phone: "Phone",
        address: "Address",
        notMatch: "New Password not match!",
    },
    newFriendList: {
        loadError: "fail to load new friend search result",
        addSuccess: "send a request successfully",
        addError: "fail to send a request",
        add: "Add",
        inputMsg: "Input Invite Message",
        msg: "Message"
    },
    groupSetting: {
        getMemberError: "Not able to get members in group",
        changeName: "Group name change successfully",
        changeNameError: "Group name changed failed",
        changeNotice: "Group notice change successfully",
        changeNoticeError: "Group notice changed failed",
        changeAvatar: "Group Avatar change successfully",
        changeAvatarError: "Group Avatar changed failed",
        addAvatar: "change Avatar",
        groupAvatar: "Group Avatar",
        groupMember: "Group Member",
        groupName: "Group Name",
        importantNotice: "Important Notice",
        changeSucceed: "Info changed succeed",
        changeFailed: "Info changed failed",
    },
    friendIHave: {
        chat: "Chat",
        searchFriend: "Search Your Friend here",
        searchError: "Search friend failed",
        loadError: "Fail to load Friends",
    },
    welcome: {
        login: 'Login',
        register: 'Register',
        uname: "Username",
        email: "Email",
        pwd: 'Password',
        pwd2: 'Password Again',
        toRegister: 'register here!',
        toLogin: 'login here!',
        err: "Page Not Found",
        unameError: 'Username must consist of 3 to 16 letters, numbers, _ or -',
        pwdError: 'Password must consist of 6 to 18 letters, numbers, _ or -',
        emailError: 'email format incorrect!',
        addressError: 'address format incorrect!',
        phoneError: 'phone number format incorrect!',
        birthdayError: 'birthday format incorrect!',
    },
    user: {
        loginError: 'fail to login',
        mainError: 'fail to get user avatar and username',
        infoError: 'fail to get user detailed information',
        changeInfoError: 'fail to change user information',
        logoutError: 'fail to logout',
        registerError: 'fail to register',
        searchFriend: "Search Your Friend here",
        searchError: "Search friend failed",
        loadError: "Fail to load Friends",
    },
    chatRoom: {
        friendLoadError: "Load friend failed",
        groupLoadError: "Load group failed",
        roomTypeError: "Get room type failed",
        LoadError: "Loading failed",
        sendError: "sending message failed",
        leaveError: "updating leave time failed"
    },
    memberList: {
        add: "New Member Adding",
    },
    popWin: {
        AddFriend: "Add New Members",

    },
    postStatus: {
        putPic: "put photo here...",
        post: "Post",
        placeHolder: "How's your day?",
        err: "Post Failed",
        succeed: "Post succeed",
    },
    buttons: {
        cancel: "Cancel",
        confirm: "Confirm",
        change: "Change",
        picInfo: "jpg/png files with a size less than 500kb",
    },
    postComment: {
        succeed: "post comment succeed",
        fail: "post comment failed",
        like: "like succeed",
        likeFail: "like failed",
        dislike: "dislike succeed",
        dislikeFail: "dislike failed",
    }
}
