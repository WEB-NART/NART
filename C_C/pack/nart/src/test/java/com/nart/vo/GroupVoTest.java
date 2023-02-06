package com.nart.vo;

import com.nart.pojo.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GroupVoTest {

    private GroupVo groupVoUnderTest;

    @BeforeEach
    void setUp() {
        groupVoUnderTest = new GroupVo();
        groupVoUnderTest.setId("id");
    }

    @Test
    void testTransfer() {
        // Setup
        final Group group = new Group("id");
        final GroupVo expectedResult = new GroupVo();
        expectedResult.setId("id");
        expectedResult.setState(0);

        // Run the test
        final GroupVo result = groupVoUnderTest.transfer(group);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToGroup() {
        // Setup
        final Group expectedResult = new Group("id");


        // Run the test
        final Group result = groupVoUnderTest.toGroup();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEquals() {
        assertThat(groupVoUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(groupVoUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(groupVoUnderTest.hashCode()).isNotZero();;
    }

    @Test
    void testToString() {
        assertThat(groupVoUnderTest.toString()).isEqualTo("GroupVo(id=id, name=null, avatar=null, notice=null, state=null, newMsg=null)");
    }
}
