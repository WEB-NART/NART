package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User userUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        userUnderTest = new User();
    }

    @Test
    void testEquals() throws Exception {
        assertThat(userUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() throws Exception {
        assertThat(userUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() throws Exception {
        assertThat(userUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() throws Exception {
        assertThat(userUnderTest.toString()).isEqualTo("User(id=null, pwd=null, tpwd=null, salt=null, avatar=null, name=null, tel=null, address=null, email=null, age=null, userOnline=null, power=null, state=null, friendList=null, requestList=null, groupList=null, GroupInvites=null, statusList=null)");
    }
}
