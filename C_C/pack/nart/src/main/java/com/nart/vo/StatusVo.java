package com.nart.vo;

//import com.nart.dao.UserDao;

import com.nart.pojo.Comment;
import com.nart.pojo.Status;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


@Service
@Data
public class StatusVo {
    private String uid;
    private String uname;
    private String avatar;
    private String statusId;
    private DateVo createDate;
    private int likes;
    private Boolean liked;
    private String msg;
    private List<String> pics;
    private List<CommentVo> comments;

//    @Autowired
//    private UserDao userDao;

    public StatusVo transfer(Status status){
        StatusVo statusVo = new StatusVo();
        statusVo.setStatusId(status.getId());
        statusVo.setUid(status.getSenderId());

        String senderId = status.getSenderId();
//        System.out.println(senderId);
//        User user = userDao.selectById(senderId);
//        System.out.println(user);
//        statusVo.setUname(user.getName());
//        statusVo.setAvatar(user.getAvatar());

        Long createDate = status.getCreateDate();
        String dateToString = getDateToString(createDate);
        DateVo dateVo = new DateVo();

        DateVo dateVo1 = createDateVo(dateToString, dateVo);
        statusVo.setCreateDate(dateVo1);

        statusVo.setLikes(status.getLikes());
        statusVo.setLiked(status.getUserLike());
        statusVo.setMsg(status.getText());

        // statusVo pics
        String pics = status.getPics();
        if (pics != null) {
            List<String> p = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(pics,";");
            while(st.hasMoreElements()){
                p.add(st.nextToken());
            }
            statusVo.setPics(p);
        }

        List<Comment> commentList = status.getCommentList();
//        List<CommentVo> comments = statusVo.getComments();
        if(commentList == null) {
            return statusVo;
        }
        List<CommentVo> comments = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentVo commentVo = new CommentVo();
            commentVo.setStatusId(comment.getStatusId());

            Long createDate1 = comment.getCreateDate();
            String dateToString1 = getDateToString(createDate1);
            DateVo date = new DateVo();
            DateVo dateVo2 = createDateVo(dateToString1, date);
            commentVo.setCreateDate(dateVo2);
            commentVo.setMsg(comment.getMsg());
            commentVo.setUname(comment.getUname());
            comments.add(commentVo);
        }


        statusVo.setComments(comments);

        return statusVo;
    }

    public String getDateToString(long time) {

        Date d = new Date(time);
        //sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = sf.format(d);
//        System.out.println(format);
        return format;
    }

    public DateVo createDateVo(String dateToString, DateVo dateVo){
        String nian = dateToString.substring(0, 4);
        int Nian = Integer.parseInt(nian);
//        System.out.println(Nian);
        dateVo.setYear(Nian);


        String yue = dateToString.substring(5, 7);
        int YUE = Integer.parseInt(yue);
//        System.out.println(YUE);
        dateVo.setMonth(YUE);

        String ri = dateToString.substring(8,10);
        int RI = Integer.parseInt(ri);
//        System.out.println(RI);
        dateVo.setDay(RI);

        String xiaoshi = dateToString.substring(11,13);
        int XIAOSHI = Integer.parseInt(xiaoshi);
//        System.out.println(XIAOSHI);
        dateVo.setHour(XIAOSHI);

        String fenzhong = dateToString.substring(14);
        int Fen = Integer.parseInt(fenzhong);
//        System.out.println(Fen);
        dateVo.setMin(Fen);

        return dateVo;
    }

}
