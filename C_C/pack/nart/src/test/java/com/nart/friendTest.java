//package com.nart;
//
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class friendTest {
////    @Test
////    void contextLoads() {
////    }
////    @Autowired
////    private FriendService friendService;
////
////    @Test
////    public void showReqList(){
////        IPage<Friend> page=new Page<>(1,10);
////        List<RequestVo> requestVos = friendService.showReqList(page, "1574989636705591298");
////        System.out.println(requestVos);
////    }
////
////    @Test
////    public void searchFriend(){
////        IPage<Friend> page=new Page<>(1,1);
////        List<UserVo> userVos = friendService.searchFriend("c", page);
////        System.out.println(userVos);
////    }
//
////    @Test
////    public void TestshowFriendList(){
////        IPage<Friend> page=new Page<>(1,2);
////        List<Friend> Friends = friendService.showFriendList(page, "1");
////        System.out.println(Friends);
////    }
////
////    @Test
////    public void TestsearchFriend(){
////        IPage<Friend> page=new Page<>(1,2);
////        List<User> liu = friendService.searchFriend("liu", page);
////        System.out.println(liu);
////    }
////
////    @Test
////    public void TestdelFriend(){
////        boolean b = friendService.delFriend("2", "1");
////        System.out.println(b);
////    }
////
////    @Test
////    public void TestchangeFriendState(){
////        boolean b = friendService.changeFriendState("2", "1", 1);
////        System.out.println(b);
////    }
////
////    @Test
////    public void TestshowReqList(){
////        IPage<FriendReq> page = new Page<>(1,2);
////        List<FriendReq> FriendReqs = friendService.showReqList(page, "1");
////        System.out.println(FriendReqs);
////    }
////
////    @Test
////    public void TestsendFriendReq(){
////        boolean qqqqq = friendService.sendFriendReq("2", "1", "qqqqq");
////        System.out.println(qqqqq);
////    }
////
////    @Test
////    public void TestrespFriendReq(){
////        boolean b = friendService.respFriendReq("1", true);
////        System.out.println(b);
////    }
//
//}
