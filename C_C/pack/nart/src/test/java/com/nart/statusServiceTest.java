//package com.nart;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.nart.pojo.Status;
//import com.nart.pojo.User;
//import com.nart.service.StatusService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//public class statusServiceTest {
//    @Autowired
//    private StatusService statusService;
//
//
//    @Test
//    public void showAllStatusList(){
//        IPage<User> page = new Page<>(1, 5);
//        List<Status> statuses = statusService.showAllStatusList("1574989636705591298", page);
//        System.out.println(statuses);
//    }
//
////    @Test
////    void contextLoads() {
////    }
////
////    @Test
////    public void sshowAllStatusList(){
////        List<Status> statuses = StatusService.showAllStatusList("1574989632599367682");
////        long time = new Date().getTime();
////        System.out.println(time);
////        System.out.println(statuses);
////    }
////
////
////    @Test
////    public void showStatusListTest(){
////        IPage<Status> page=new Page<Status>(1,2);
////        List<Status> Statuses = StatusService.showStatusList("1574989632599367682", page);
////        System.out.println(Statuses);
////    }
////
////    @Test
////    public void TestpostStatus(){
////        Status status = new Status();
////        status.setSenderId("1");
////        status.setText("erfwf");
////        status.setPics("1122323");
////        status.setCreateDate(13232344L);
////        boolean b = StatusService.postStatus(status);
////        System.out.println(b);
////    }
////
////    @Test
////    public void TestdelStatus(){
////        boolean b = StatusService.delStatus("1564436341058641922");
////        System.out.println(b);
////    }
////
////    @Test
////    public void TestlikeStatus(){
////        boolean b = StatusService.likeStatus("1", true);
////        System.out.println(b);
////    }
//}
