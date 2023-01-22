//package com.nart;
//
//import com.nart.pojo.Comment;
//import com.nart.service.CommentService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class commentTest {
//
//    @Autowired
//    private CommentService commentService;
////
////    @Test
////    void contextLoads() {
////    }
////
//    @Test
//    public void testshowCommentList(){
//        List<Comment> Comments = commentService.showCommentList("1574989638660136961");
//        System.out.println(Comments);
//    }
////
////    @Test
////    public void testpostComment(){
////        boolean a = commentService.postComment("1","ddwdwdw","1");
////        System.out.println(a);
////    }
////
////
////    // vo
////    @Test
////    public void time(){
////        Date d = new Date();
////        SimpleDateFormat  sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
////        String format = sf.format(d);
////        System.out.println(format);
////    }
////
////    public static String getCurrentDate() {
////        Date d = new Date();
////        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        return sf.format(d);
////    }
////
////    @Test
////    public void getTimeStamp() {
////        String currentDate = getCurrentDate();
////        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date date = new Date();
////        try {
////            date = sf.parse(currentDate);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////        String s = String.valueOf(date.getTime());
////        System.out.println(s);
////    }
////
////
////    @Test
////    public void getDateToString() {
////        DateVo dateVo = new DateVo();
////        long time = 1662267059000L;
////        Date d = new Date(time);
////        //sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
////        String format = sf.format(d);
////        System.out.println(format);
////
////
////        String nian = format.substring(0, 4);
////        int Nian = Integer.parseInt(nian);
////        System.out.println(Nian);
////        dateVo.setYear(Nian);
////
////        String yue = format.substring(5, 7);
////        int YUE = Integer.parseInt(yue);
////        System.out.println(YUE);
////        dateVo.setMonth(YUE);
////
////        String ri = format.substring(8,10);
////        int RI = Integer.parseInt(ri);
////        System.out.println(RI);
////        dateVo.setDay(RI);
////
////
////        String xiaoshi = format.substring(11,13);
////        int XIAOSHI = Integer.parseInt(xiaoshi);
////        System.out.println(XIAOSHI);
////        dateVo.setHour(XIAOSHI);
////
////        String fenzhong = format.substring(14);
////        int Fen = Integer.parseInt(fenzhong);
////        System.out.println(Fen);
////        dateVo.setMin(Fen);
////        System.out.println(dateVo);
////    }
////
////    @Test
////    public void status(){
////        String pics = "ewewdwd;ewewewew1";
////        List<String> p = new ArrayList<>();
////        StringTokenizer st = new StringTokenizer(pics,";");
////        while(st.hasMoreElements()){
////            p.add(st.nextToken());
////
////        }
////        System.out.println(p);
////    }
//}
