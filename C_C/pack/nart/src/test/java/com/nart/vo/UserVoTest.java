
package com.nart.vo;

import com.nart.pojo.Friend;
import com.nart.pojo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class UserVoTest {

    private UserVo userVoUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        userVoUnderTest = new UserVo();
    }

    @Test
    void testTransfer() {
        // Setup
        final User user = new User();
        user.setId("id");
        user.setPwd("pwd");
        user.setTpwd("tpwd");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setName("name");
        user.setTel("phone");
        user.setAddress("address");
        user.setEmail("email");
        user.setAge("age");
        user.setUserOnline(0);
        user.setPower(0);
        user.setState(0);
        final Friend friend = new Friend();
        friend.setId("id");
        user.setFriendList(Arrays.asList(friend));

        final UserVo expectedResult = new UserVo();
        expectedResult.setId("id");
        expectedResult.setAvatar("avatar");
        expectedResult.setUname("name");
        expectedResult.setPwd("pwd");
        expectedResult.setEmail("email");
        expectedResult.setBirthday("age");
        expectedResult.setPhone("phone");
        expectedResult.setAddress("address");
        expectedResult.setPower(0);
        expectedResult.setLock(0);

        // Run the test
        final UserVo result = userVoUnderTest.transfer(user);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToUser() {
        // Setup
        final User expectedResult = new User();
        expectedResult.setId("id");
        expectedResult.setPwd("pwd");
        expectedResult.setAvatar("avatar");
        expectedResult.setName("name");
        expectedResult.setTel("phone");
        expectedResult.setAddress("address");
        expectedResult.setEmail("email");
        expectedResult.setAge("age");
        expectedResult.setUserOnline(null);
        expectedResult.setPower(0);
        expectedResult.setState(null);
//        final Friend friend = new Friend();
//        friend.setId("id");
//        expectedResult.setFriendList(Arrays.asList(friend));

        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("avatar");
        userVo.setUname("name");
        userVo.setPwd("pwd");
        userVo.setEmail("email");
        userVo.setBirthday("age");
        userVo.setPhone("phone");
        userVo.setAddress("address");
        userVo.setPower(0);
        userVo.setLock(0);

        // Run the test
        final User result = userVo.toUser();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEquals() throws Exception {
        assertThat(userVoUnderTest.equals("o")).isFalse();
        assertThat(userVoUnderTest.equals(new UserVo())).isTrue();
    }

    @Test
    void testCanEqual() throws Exception {
        assertThat(userVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() throws Exception {
        assertThat(userVoUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() throws Exception {
        assertThat(userVoUnderTest.toString()).isEqualTo("UserVo(id=null, avatar=null, uname=null, pwd=null, email=null, birthday=null, phone=null, address=null, power=null, lock=0)");
    }
}
