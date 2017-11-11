package hf.admin.service;

import hf.admin.enums.UserType;
import hf.admin.model.UserInfo;
import hf.admin.model.UserInfoDto;
import hf.admin.model.UserInfoRequest;
import hf.admin.rpc.HfClient;
import hf.admin.service.common.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private HfClient hfClient;
    @Autowired
    private CacheService cacheService;

    public List<UserInfoDto> getUserListForAdmin(UserInfoRequest request) {
        return hfClient.getUserListForAdmin(request);
    }

    public UserInfo getUserInfo(String loginId, String password,UserType userType) {
        UserInfo userInfo = hfClient.getUserInfo(loginId,password,userType.getValue());
        return userInfo;
    }

//    public boolean editPassword(String userId,String ypassword,String newpassword,String newpasswordok) {
//
//    }
}
