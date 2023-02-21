package com.nart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nart.pojo.Group;
import com.nart.pojo.GroupInvite;
import com.nart.pojo.User;
import com.nart.service.GroupService;
import com.nart.service.LoginService;
import com.nart.util.GsonFormatter;
import com.nart.util.Result;
import com.nart.vo.GroupVo;
import com.nart.vo.InviteVo;
import com.nart.vo.UserVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GroupController.class)
@AutoConfigureMybatis
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService mockGroupService;

    @MockBean
    private LoginService mockLoginService;

    private final String successListToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":[]}";
    private final String successNullToString = "{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":null}";

    @BeforeEach
    void setup() {
        User user = new User();
        user.setId("id");
        when(mockLoginService.checkToken("token")).thenReturn(user);
    }

    @Test
    void testShowGroupMemberList() throws Exception {
        // Setup
        // Configure GroupService.showGroupMebList(...).
        final UserVo userVo = new UserVo();
        userVo.setId("id");
        userVo.setAvatar("avatar");
        userVo.setUname("uname");
        userVo.setPwd("pwd");
        userVo.setEmail("email");
        userVo.setBirthday("birthday");
        userVo.setPhone("phone");
        userVo.setAddress("address");
        userVo.setPower(0);
        userVo.setLock(0);
        final List<UserVo> userVos = Arrays.asList(userVo);
        when(mockGroupService.showGroupMebList("gid")).thenReturn(userVos, null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/group/memberList/{groupId}", "gid")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                        "[{\"id\":\"id\",\"avatar\":\"avatar\",\"uname\":\"uname\",\"pwd\":\"pwd\"," +
                        "\"email\":\"email\",\"birthday\":\"birthday\",\"phone\":\"phone\"," +
                        "\"address\":\"address\",\"power\":0,\"lock\":0}]}");
    }

    @Test
    void testShowGroupMemberList_GroupServiceReturnsNull() throws Exception {
        // Setup
        when(mockGroupService.showGroupMebList("gid")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/group/memberList/{groupId}", "gid")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testShowGroupMemberList_GroupServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockGroupService.showGroupMebList("gid")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                get("/group/memberList/{groupId}", "gid")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testChangeGroupInfo() throws Exception {
        // Setup
        when(mockGroupService.changeGroupInfo(any(Group.class))).thenReturn(false, true);

        Group group = new Group();
        group.setId("gid");
        group.setGroupName("gName");
        group.setAvatar("gAvatar");
        group.setNotice("gNotice");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/group/changeInfo")
                        .content(GsonFormatter.toJsonString(group))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":30902," +
                "\"msg\":\"change group information failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response3 = mockMvc.perform(put("/group/changeInfo")
                        .content(GsonFormatter.toJsonString(group))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response3.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response3.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testShowGroupList() throws Exception {
        // Setup
        // Configure GroupService.showGroupList(...).
        final GroupVo groupVo = new GroupVo();
        groupVo.setId("id");
        groupVo.setName("groupName");
        groupVo.setAvatar("avatar");
        groupVo.setNotice("notice");
        groupVo.setState(0);
        groupVo.setNewMsg(false);
        final List<GroupVo> groupVos = Arrays.asList(groupVo);
        when(mockGroupService.showGroupList(any(IPage.class))).thenReturn(groupVos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/group/list")
                        .content("{\"pageSize\":1,\"pageNum\":1}").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                        "[{\"id\":\"id\",\"name\":\"groupName\",\"avatar\":\"avatar\"," +
                        "\"notice\":\"notice\",\"state\":0,\"newMsg\":false}]}");
    }

    @Test
    void testShowGroupList_GroupServiceReturnsNull() throws Exception {
        // Setup
        when(mockGroupService.showGroupList(any(IPage.class))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/group/list")
                        .content("{\"pageSize\":1,\"pageNum\":1}").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":30903," +
                "\"msg\":\"show group list failed: unknown reason\",\"data\":null}");
    }

    @Test
    void testShowGroupList_GroupServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockGroupService.showGroupList(any(IPage.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/group/list")
                        .content("{\"pageSize\":1,\"pageNum\":1}").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testLeaveGroup() throws Exception {
        // Setup
        when(mockGroupService.leaveGroup("gid", "id")).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                delete("/group/del/{groupId}", "gid")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":30904," +
                "\"msg\":\"leave group failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        delete("/group/del/{groupId}", "gid")
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testChangeGroupState() throws Exception {
        // Setup
        when(mockGroupService.changeGroupState("gid", "id", 0)).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                put("/group/state/{groupId}/{state}", "gid", 0)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":30905," +
                "\"msg\":\"change group state failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        put("/group/state/{groupId}/{state}", "gid", 0)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testShowInviteList() throws Exception {
        // Setup
        // Configure GroupService.showInviteList(...).
        final InviteVo inviteVo = new InviteVo();
        inviteVo.setId("id");
        inviteVo.setGroupId("groupId");
        inviteVo.setMsg("msg");
        inviteVo.setGroupName("groupName");
        inviteVo.setGroupAvatar("groupAvatar");
        inviteVo.setSenderName("senderName");
        inviteVo.setReceiverId("receiverId");
        final List<InviteVo> inviteVos = Arrays.asList(inviteVo);
        when(mockGroupService.showInviteList(any(IPage.class))).thenReturn(inviteVos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/group/inviteList")
                        .content("{\"pageSize\":1,\"pageNum\":1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"success\":true,\"code\":200,\"msg\":\"success\",\"data\":" +
                    "[{\"id\":\"id\",\"groupId\":\"groupId\",\"msg\":\"msg\"," +
                        "\"groupName\":\"groupName\",\"groupAvatar\":\"groupAvatar\"," +
                        "\"senderName\":\"senderName\",\"receiverId\":\"receiverId\"}]}");
    }

    @Test
    void testShowInviteList_GroupServiceReturnsNull() throws Exception {
        // Setup
        when(mockGroupService.showInviteList(any(IPage.class))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/group/inviteList")
                        .content("{\"pageSize\":1,\"pageNum\":1}").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":30906," +
                "\"msg\":\"show group invite list failed: unknown, reason\",\"data\":null}");
    }

    @Test
    void testShowInviteList_GroupServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockGroupService.showInviteList(any(IPage.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/group/inviteList")
                        .content("{\"pageSize\":1,\"pageNum\":1}").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(successListToString);
    }

    @Test
    void testSendInvite() throws Exception {
        // Setup
        when(mockGroupService.sendInvite(new GroupInvite())).thenReturn(false, true);

        GroupInvite groupInvite = new GroupInvite();

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/group/send")
                        .content(GsonFormatter.toJsonString(groupInvite)).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":30907," +
                "\"msg\":\"send group invite failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(post("/group/send")
                        .content(GsonFormatter.toJsonString(groupInvite)).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testRespGroupInvite() throws Exception {
        // Setup
        when(mockGroupService.respGroupInvite("inviteId", false)).thenReturn(false, true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                put("/group/resp/{inviteId}/{agree}", "inviteId", false)
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":30908," +
                "\"msg\":\"response to group invite failed: unknown reason\",\"data\":null}");

        final MockHttpServletResponse response2 = mockMvc.perform(
                        put("/group/resp/{inviteId}/{agree}", "inviteId", false)
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(successNullToString);
    }

    @Test
    void testCreateGroup() throws Exception {
        // Setup
        when(mockGroupService.createGroup("groupName", "id")).thenReturn(false, true);
        when(mockGroupService.findGroup("groupName")).thenReturn(new Group("gid"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                post("/group/create/{groupName}", "groupName")
                        .header("Authorization", "token")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":false,\"code\":30909," +
                "\"msg\":\"create group failed: unknown reason\",\"data\":null}");

        // Run the test
        final MockHttpServletResponse response2 = mockMvc.perform(
                        post("/group/create/{groupName}", "groupName")
                                .header("Authorization", "token")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response2.getContentAsString()).isEqualTo(GsonFormatter.toJsonString(Result.success("gid")));
    }
}
