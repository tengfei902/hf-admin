package hf.admin;

import hf.admin.model.UserInfo;
import hf.admin.model.UserInfoDto;
import hf.admin.model.UserInfoRequest;
import hf.admin.service.UserService;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void testGetUserList() {
    }
}
