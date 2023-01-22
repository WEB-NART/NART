package com.nart.service;

import com.nart.pojo.Comment;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
//@Resource
@Rollback
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    void showCommentList() {

        List<Comment> comments = commentService.showCommentList("1574989661011582978");
        System.out.println(comments);

        List<Comment> C = new ArrayList<>();
        Comment comment = new Comment();
        comment.setId("1574990086246948865");
        comment.setMsg("It is the east, and Juliet is the sun.");
        comment.setStatusId("1574989661011582978");
        comment.setCreateDate(Long.valueOf("1607006977551"));
        comment.setUserId("1574989638660136961");
        comment.setUname("kaye.conroy");
        C.add(comment);
        assertEquals(C,comments);

    }

    @Test
    void postComment() {
        boolean a = commentService.postComment("1574989660554403841","unit test","1574989636311326722");
        System.out.println("postComment: "+ a);
        assertEquals(true,a);
    }



    @Test
    void delComment(){
        boolean b = commentService.delComment("1574989660554403841");
        assertEquals(true,b);
    }

}