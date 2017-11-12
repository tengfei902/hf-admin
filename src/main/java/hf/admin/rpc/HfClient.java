package hf.admin.rpc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hf.admin.model.*;
import hf.admin.utils.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * Created by tengfei on 2017/11/11.
 */
@Component
public class HfClient extends BaseClient {

    private static final String HF_APP_URL = "http://127.0.0.1:8080/jh/user/get_user_list";
    private static final String GET_USER_GROUP_LIST = "http://127.0.0.1:8080/jh/user/get_user_group_list";
    private static final String GET_USER_INFO = "http://127.0.0.1:8080/jh/user/get_user_info";
    private static final String EDIT_PASSWORD = "http://127.0.0.1:8080/jh/user/edit_password";

    public List<UserInfoDto> getUserListForAdmin(UserInfoRequest request) {
        RemoteParams params = new RemoteParams().withPath(HF_APP_URL).withObject(request);
        String result = super.post(params);
        ResponseResult<List<UserInfoDto>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<List<UserInfoDto>>>(){}.getType());
        return response.getData();
    }

    public List<UserGroupDto> getUserGroupList(UserGroupRequest request) {
        RemoteParams params = new RemoteParams().withPath(GET_USER_GROUP_LIST).withObject(request);
        String result = super.post(params);
        ResponseResult<List<UserGroupDto>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<List<UserGroupDto>>>(){}.getType());
        return response.getData();
    }

    public UserInfo getUserInfo(String loginId,String password,int userType) {
        RemoteParams params = new RemoteParams().withPath(GET_USER_INFO).withParam("loginId",loginId).withParam("password",password).withParam("userType",userType);

        String result = super.post(params);
        ResponseResult<UserInfo> response = new Gson().fromJson(result,new TypeToken<ResponseResult<UserInfo>>(){}.getType());
        UserInfo userInfo = response.getData();
        if(userInfo == null || userInfo.getId() == null) {
            return null;
        }

        return userInfo;
    }

    public boolean editPassword(String userId,String ypassword,String newpassword,String newpasswordok) {
        RemoteParams params = new RemoteParams().withPath(EDIT_PASSWORD)
                .withParam("userId",userId)
                .withParam("ypassword",ypassword)
                .withParam("newpassword",newpassword)
                .withParam("newpasswordok",newpasswordok);

        String result = super.post(params);
        ResponseResult<Boolean> response = new Gson().fromJson(result,new TypeToken<ResponseResult<Boolean>>(){}.getType());
        return response.getData();
    }
}
