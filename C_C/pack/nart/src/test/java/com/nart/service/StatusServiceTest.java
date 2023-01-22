package com.nart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nart.pojo.Comment;
import com.nart.pojo.Status;
import com.nart.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Rollback
@Transactional
class StatusServiceTest {

    @Autowired
    private StatusService statusService;

    @Test
    void showAllStatusList() {
        IPage<User> page = new Page<>(1, 1);
        List<Status> statuses = statusService.showAllStatusList("1574989639444471809", page);
        System.out.println(statuses);
        Status status = new Status();
        status.setId("1574989660160139265");
        status.setSenderId("1574989639444471809");
        status.setText("The fool doth think he is wise, but the wise man knows himself to be a fool.");
        status.setPics("http://lorempixel.com/g/1920/1200/business/;http://lorempixel.com/g/720/348/fashion/;");
        status.setCreateDate(1654207321171L);
        status.setLikes(0);
        status.setUserLike(null);

        Comment comment1 = new Comment();
        comment1.setId("1606768208255660033");
        comment1.setMsg("justtry");
        comment1.setStatusId("1574989660160139265");
        comment1.setCreateDate(1671918367723L);
        comment1.setUserId("1574989632599367682");
        comment1.setUname("apiu");

        Comment comment2 = new Comment();
        comment2.setId("1583886742891556866");
        comment2.setMsg("yes!");
        comment2.setStatusId("1574989660160139265");
        comment2.setCreateDate(1666463001216L);
        comment2.setUserId("1574989632599367682");
        comment2.setUname("apiu");

        Comment comment3 = new Comment();
        comment3.setId("1575505862766678017");
        comment3.setMsg("For you and I are past our dancing days.");
        comment3.setStatusId("1574989660160139265");
        comment3.setCreateDate(1629882116332L);
        comment3.setUserId("1574989636311326722");
        comment3.setUname("carlee.ullrich");

        Comment comment4 = new Comment();
        comment4.setId("1575505862372413441");
        comment4.setMsg("O! she doth teach the torches to burn bright.");
        comment4.setStatusId("1574989660160139265");
        comment4.setCreateDate(1597068532628L);
        comment4.setUserId("1574989636705591298");
        comment4.setUname("eun.beatty");

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);
        commentList.add(comment4);
        status.setCommentList(commentList);
        List<Status> statuses1 = new ArrayList<>();
        statuses1.add(status);
        assertEquals(statuses1,statuses);
    }

    @Test
    void showStatusList() {
        IPage<User> page = new Page<>(1, 1);
        List<Status> statuses = statusService.showStatusList("1574989639444471809", page);
        System.out.println(statuses);
        Status status = new Status();
        status.setId("1574989660160139265");
        status.setSenderId("1574989639444471809");
        status.setText("The fool doth think he is wise, but the wise man knows himself to be a fool.");
        status.setPics("http://lorempixel.com/g/1920/1200/business/;http://lorempixel.com/g/720/348/fashion/;");
        status.setCreateDate(1654207321171L);
        status.setLikes(0);
        status.setUserLike(null);

        Comment comment1 = new Comment();
        comment1.setId("1606768208255660033");
        comment1.setMsg("justtry");
        comment1.setStatusId("1574989660160139265");
        comment1.setCreateDate(1671918367723L);
        comment1.setUserId("1574989632599367682");
        comment1.setUname("apiu");

        Comment comment2 = new Comment();
        comment2.setId("1583886742891556866");
        comment2.setMsg("yes!");
        comment2.setStatusId("1574989660160139265");
        comment2.setCreateDate(1666463001216L);
        comment2.setUserId("1574989632599367682");
        comment2.setUname("apiu");

        Comment comment3 = new Comment();
        comment3.setId("1575505862766678017");
        comment3.setMsg("For you and I are past our dancing days.");
        comment3.setStatusId("1574989660160139265");
        comment3.setCreateDate(1629882116332L);
        comment3.setUserId("1574989636311326722");
        comment3.setUname("carlee.ullrich");

        Comment comment4 = new Comment();
        comment4.setId("1575505862372413441");
        comment4.setMsg("O! she doth teach the torches to burn bright.");
        comment4.setStatusId("1574989660160139265");
        comment4.setCreateDate(1597068532628L);
        comment4.setUserId("1574989636705591298");
        comment4.setUname("eun.beatty");

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);
        commentList.add(comment4);
        status.setCommentList(commentList);
        List<Status> statuses1 = new ArrayList<>();
        statuses1.add(status);
        assertEquals(statuses1,statuses);
    }

    @Test
    void postStatus() {
        Status status = new Status();
        status.setSenderId("1606447871244648449");
        status.setText("test");
        status.setPics("https://s1.ax1x.com/2023/01/06/pSEBpxP.png");
        status.setCreateDate(111111111L);
        boolean b = statusService.postStatus(status);
//        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void delStatus() {
        boolean b = statusService.delStatus("1606766924161118210");
        System.out.println(b);
        assertEquals(true,b);
    }

    @Test
    void likeStatus() {
        boolean b = statusService.likeStatus("1611370361452507138", true);
        System.out.println(b);
        assertEquals(true,b);
    }
}