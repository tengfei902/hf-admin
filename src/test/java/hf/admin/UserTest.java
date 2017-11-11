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
        UserInfoRequest request = new UserInfoRequest();
        request.setAdminId("12457");
        List<UserInfoDto> list = userService.getUserListForAdmin(request);
        Assert.assertTrue(list.size() == 120);
        for(UserInfoDto userInfo:list) {
            Assert.assertEquals(userInfo.getAdminId().intValue(),12457);
        }

        request.setStatus(1);

        list = userService.getUserListForAdmin(request);

        Assert.assertEquals(list.size(),0);

        request.setStatus(0);
        request.setUser("张三_2_0_1");

        list = userService.getUserListForAdmin(request);

        for(UserInfoDto userInfo:list) {
            System.out.println(userInfo.getName());
            System.out.println(userInfo.getUserNo());
            Assert.assertTrue(userInfo.getName().contains("张三_2_0_1") || userInfo.getUserNo().contains("张三_2_0_1"));
        }

        request = new UserInfoRequest();
        request.setAdminId("1");

        list = userService.getUserListForAdmin(request);
        Assert.assertEquals(list.size(),12101);
    }
}
