package hf.admin;

import hf.admin.model.UserInfoRequest;
import hf.admin.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void testGetUserList() {
        UserInfoRequest request = new UserInfoRequest();
        request.setName("test");
        request.setStatus(0);
        request.setType(0);
        request.setUserId(1000L);
        userService.getUserListForAdmin(request);
    }
}
