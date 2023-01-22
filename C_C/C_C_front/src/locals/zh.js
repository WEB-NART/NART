/*
  * @FileDescription: Chinese language
  * @Author: Zirui Qiao
  * @Date: 2022/12/25 14:16
  * @LastEditor: Shizhong Shang
  * @LastEditTime: 2023/01/07 15:26
*/
export default{
    changeLang: "English",
    main: {
        menu: {
            item1: '发新状态',
            item2: '查看所有状态',
            item3: '查看所有好友',
            item3_hide: '继续隐藏好友',
            item4: '查看所有群组',
            item4_hide: '继续隐藏群组',
            item5: '编辑个人信息',
            item6: '退出'
        },
        bar: {
            item1: '朋友',
            item2: '群组',
            item3: '新鲜事',
            item4: '好友申请',
            item5: '群组邀请',
            item6: '状态'
        }
    },
    contactList: {
        hide:"隐藏",
        mute:"静音",
        leave:"离开",
        del: "删除",
        unset:"解除",
        hideErr: "隐藏失败",
        muteErr: "静音失败",
        unsetErr: "解除失败",
        delErr: "删除失败",
        friend: {
            ListError: "加载好友列表失败",
            hideError: "隐藏好友失败",
            muteError: "静音好友失败",
            delError: "删除好友失败",
            unsetError: "恢复好友正常状态失败",
            del: "请确定你将要删除好友：",
            title: "删除好友",
            loadErr: "加载好友列表失败",
        },
        group: {
            ListError: "加载群组列表失败",
            hideError: "隐藏群组失败",
            muteError: "静音群组失败",
            delError: "离开群组失败",
            unsetError: "恢复群组正常状态失败"
        }
    },
    findSthNew: {
        search: "添加新朋友",
        create: "创建新群组",
        placeholder: "搜素",
    },
    createGroup: {
        avatarLabel: "头像",
        addAvatar: "添加头像",
        groupName: "群组名称",
        groupNameHolder: "请在这里输入群组名称",
        addMember: "添加成员",
        create: '创建群组',
        createError: "创建群组失败",
        inviteError: "邀请成员失败"

    },
    statusItem: {
        commentPlaceHolder: "说点什么...",
    },
    chatInputBox: {
        placeHolder: '请在这里输入',


        uploadPicError: '上传图片失败',
        send: '发送',
        chat: '请输入...',

    },
    reqList: {
        loadError: "加载好友申请失败",
        acceptError: "接受好友申请失败",
        rejectError: "拒绝好友申请失败",
        reject: "拒绝",
        accept: "同意",
    },
    inviteList: {
        loadError: "加载群组邀请失败",
        acceptError: "接受群组邀请失败",
        rejectError: "拒绝群组邀请失败",
        reject: "拒绝",
        accept: "同意",
    },
    statusList: {
        loadError: "加载动态失败",
        toEnd: "没有更多"
    },
    myStatusList: {
        loadError: "加载动态失败",
        toEnd: "没有更多",
        delError: "删除动态失败"
    },
    infoItem: {
        title: "更改密码",
        oldPwd: "请输入旧密码",
        newPwd: "请输入新密码",
        newPwd2: "再次输入新密码",
        userName: "用户名",
        email: "邮箱",
        birthday: "生日",
        password: "密码",
        phone: "手机",
        address: "地址",
        notMatch: "两次新密码不相同",
    },
    newFriendList: {
        loadError: "加载搜素好友结果不正确",
        addSuccess: "请求发送成功",
        addError: "请求发送失败",
        add: "添加",
        inputMsg: "请输入邀请信息",
        msg: "请输入验证信息"
    },
    groupSetting: {
        getMemberError: "无法添加好友进入群组",
        changeName: "更改群名成功",
        changeNameError: "更改群名失败",
        changeNotice: "更改群组通知成功",
        changeNoticeError: "更改群组通知失败",
        changeAvatar: "更改群组头像成功",
        changeAvatarError: "更改群组头像失败",
        addAvatar: "更改头像",
        groupAvatar: "群组头像",
        groupMember: "群组成员",
        groupName: "群组名称",
        importantNotice: "群组通知",
        changeSucceed: "群组信息更改成功",
        changeFailed: "群组信息更改失败",
    },
    friendIHave: {
        chat: "聊聊",
        searchFriend: "搜素好友",
        searchError: "查找好友失败",
        loadError: "加载好友失败",
    },
    welcome: {
        login: '登录',
        register: '注册',
        uname: "用户名",
        email: "邮箱",
        pwd: '密码',
        pwd2: '请再次输入密码',
        toRegister: '点击此处注册',
        toLogin: '点击此处登录',
        err: "页面找不到",
        unameError: '用户名必须包含 3 到 16 个字母, 数字, _ 或 -',
        pwdError: '密码必须包含 6 到 18 个字母, 数字, _ 或 -',
        emailError: '邮箱格式不正确',
        addressError: '地址格式不正确',
        phoneError: '手机号格式不正确',
        birthdayError: '生日格式不正确',
    },
    user: {
        loginError: '登录失败',
        mainError: '加载用户名称和头像失败',
        infoError: '加载用户信息失败',
        changeInfoError: '更改用户信息失败',
        logoutError: '退出登录失败',
        registerError: '注册失败',
        searchFriend: "搜索好友",
        searchError: "搜索好友失败",
        loadError: "加载好友失败",
    },
    chatRoom: {
        friendLoadError: "加载好友失败",
        groupLoadError: "加载群组失败",
        roomTypeError: "判定聊天室类型失败",
        LoadError: "加载失败",
        sendError: "发送信息失败",
        leaveError: "更新离开时间失败"
    },
    memberList: {
        add: "添加新朋友",
    },
    popWin: {
        AddFriend: "添加新组员",

    },
    postStatus: {
        putPic: "照片放这里",
        post: "发布",
        placeHolder: "说说这一天",
        err: "发布失败",
        succeed: "发布成功",
    },
    buttons: {
        cancel: "取消",
        confirm: "确定",
        change: "更改",
        picInfo: "500kb 以内 jpg/png 文件 ",
    },
    postComment: {
        succeed: "发布评论成功",
        fail: "发布评论失败",
        like: "点赞成功",
        likeFail: "点赞失败",
        dislike: "取消点赞成功",
        dislikeFail: "取消点赞失败",
    }
}
