package com.nart.util;

import com.nart.pojo.Friend;
import com.nart.pojo.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class UserThreadLocalTest {

    @Test
    void testPut() {
        // Setup
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("name");
        user.setTel("tel");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));

        // Run the test
        UserThreadLocal.put(user);

        // Verify the results
    }

    @Test
    void testGet() {
        // Setup
        testPut();
        final User expectedResult = new User();
        expectedResult.setId("id");
        expectedResult.setPwd("pwd");
        expectedResult.setTpwd("tpwd");
        expectedResult.setSalt("salt");
        expectedResult.setAvatar("avatar");
        expectedResult.setName("name");
        expectedResult.setTel("tel");
        expectedResult.setAddress("address");
        expectedResult.setEmail("email");
        expectedResult.setAge("age");
        expectedResult.setUserOnline(0);
        expectedResult.setPower(0);
        expectedResult.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        expectedResult.setFriendList(Arrays.asList(friend));

        // Run the test
        final User result = UserThreadLocal.get();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testRemove() {
        // Setup
        // Run the test
        UserThreadLocal.remove();

        // Verify the results
    }
}
