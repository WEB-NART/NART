
package com.nart.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserGroupTest {

    private UserGroup userGroupUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        userGroupUnderTest = new UserGroup();
    }

    @Test
    void testEquals() throws Exception {
        assertThat(userGroupUnderTest.equals("o")).isFalse();
        assertThat(userGroupUnderTest.equals(new UserGroup())).isTrue();
    }

    @Test
    void testCanEqual() throws Exception {
        assertThat(userGroupUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() throws Exception {
        assertThat(userGroupUnderTest.hashCode()).isNotZero();
    }

    @Test
    void testToString() throws Exception {
        assertThat(userGroupUnderTest.toString()).isEqualTo("UserGroup(id=null, uid=null, gid=null, state=0, joinLevel=0, userLevelTime=null)");
    }
}
