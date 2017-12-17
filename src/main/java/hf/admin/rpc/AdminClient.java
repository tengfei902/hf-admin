package hf.admin.rpc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hf.admin.model.*;
import hf.base.client.BaseClient;
import hf.base.exceptions.BizFailException;
import hf.base.model.*;
import hf.base.utils.Pagenation;
import hf.base.utils.ResponseResult;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 * Created by tengfei on 2017/11/11.
 */
public class AdminClient extends BaseClient {

    private String url;

    private static final String HF_APP_URL = "/user/get_user_list";
    private static final String GET_USER_GROUP_LIST = "/user/get_user_group_list";
    private static final String GET_USER_INFO = "/user/get_user_info";
    private static final String EDIT_PASSWORD = "/user/edit_password";
    private static final String GET_CHANNEL_LIST = "/user/get_channel_list";
    private static final String GET_AVA_CHANNEL_LIST = "/user/get_ava_channel_list";
    private static final String GET_AUTHORIZED_LIST = "/admin/get_authorized_list";
    private static final String GET_USER_CHANNEL_LIST = "/user/get_user_channel_list";
    private static final String SAVE_CHANNEL = "/user/save_channel";
    private static final String SAVE_USER_CHANNEL = "/user/save_user_channel";
    private static final String GET_ADMIN_BANK_CARD = "/user/get_admin_bank_card";
    private static final String GET_ADMIN_BANK_CARD_LIST = "/user/get_admin_bank_card_list";
    private static final String GET_ADMIN_BANK_CARD_BY_ID = "/user/get_admin_bank_card_by_id";
    private static final String SAVE_ADMIN_BANK_CARD = "/user/save_admin_bank_card";
    private static final String USER_TURN_BACK = "/user/user_turn_back";
    private static final String USER_PASS = "/user/user_pass";
    private static final String SAVE_USER_INFO = "/user/save_user_info";
    private static final String SAVE_USER_GROUP = "/user/save_user_group";
    private static final String FINISH_WITH_DRAW = "/settle/finish_with_draw";
    private static final String WITH_DRAW_FAILED = "/settle/with_draw_failed";

    public AdminClient(String url) {
        this.url = url;
    }

    public List<UserInfoDto> getUserListForAdmin(UserInfoRequest request) {
        RemoteParams params = new RemoteParams(url).withPath(HF_APP_URL).withObject(request);
        String result = super.post(params);
        ResponseResult<List<UserInfoDto>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<List<UserInfoDto>>>(){}.getType());
        return response.getData();
    }

    public Pagenation<UserGroup> getUserGroupList(UserGroupRequest request) {
        RemoteParams params = new RemoteParams(url).withPath(GET_USER_GROUP_LIST).withObject(request);
        String result = super.post(params);
        ResponseResult<Pagenation<UserGroup>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<Pagenation<UserGroup>>>(){}.getType());
        return response.getData();
    }

    public UserInfo getUserInfo(String loginId, String password, int userType) {
        RemoteParams params = new RemoteParams(url).withPath(GET_USER_INFO).withParam("loginId",loginId).withParam("password",password).withParam("userType",userType);

        String result = super.post(params);
        ResponseResult<UserInfo> response = new Gson().fromJson(result,new TypeToken<ResponseResult<UserInfo>>(){}.getType());
        UserInfo userInfo = response.getData();
        if(userInfo == null || userInfo.getId() == null) {
            return null;
        }

        return userInfo;
    }

    public boolean editPassword(String userId,String ypassword,String newpassword,String newpasswordok) {
        RemoteParams params = new RemoteParams(url).withPath(EDIT_PASSWORD)
                .withParam("userId",userId)
                .withParam("ypassword",ypassword)
                .withParam("newpassword",newpassword)
                .withParam("newpasswordok",newpasswordok);

        String result = super.post(params);
        ResponseResult<Boolean> response = new Gson().fromJson(result,new TypeToken<ResponseResult<Boolean>>(){}.getType());
        return response.getData();
    }

    public List<Channel> getAvaChannelList() {
        RemoteParams params = new RemoteParams(url).withPath(GET_AVA_CHANNEL_LIST);
        String result = super.get(params,String.class);
        ResponseResult<List<Channel>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<List<Channel>>>(){}.getType());
        return response.getData();
    }

    public List<Channel> getChannelList() {
        RemoteParams params = new RemoteParams(url).withPath(GET_CHANNEL_LIST);
        String result = super.get(params,String.class);
        ResponseResult<List<Channel>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<List<Channel>>>(){}.getType());
        return response.getData();
    }

    public List<UserGroup> getAuthorizedList(Long companyId) {
        RemoteParams params = new RemoteParams(url).withPath(GET_AUTHORIZED_LIST).withParam("companyId",companyId);
        String result = super.post(params);
        ResponseResult<List<UserGroup>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<List<UserGroup>>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }

    public List<UserChannel> getUserChannelList(String groupId) {
        RemoteParams params = new RemoteParams(url).withPath(GET_USER_CHANNEL_LIST).withParam("groupId",groupId);
        String result = super.post(params);
        ResponseResult<List<UserChannel>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<List<UserChannel>>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }

    public boolean saveChannel(Map<String,Object> params) {
        RemoteParams remoteParams = new RemoteParams(url).withPath(SAVE_CHANNEL).withParams(params);
        String result =  super.post(remoteParams);
        ResponseResult<Boolean> response = new Gson().fromJson(result,new TypeToken<ResponseResult<Boolean>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }

    public boolean saveUserChannel(Map<String,Object> params) {
        RemoteParams remoteParams = new RemoteParams(url).withPath(SAVE_USER_CHANNEL).withParams(params);
        String result = super.post(remoteParams);
        ResponseResult<Boolean> response = new Gson().fromJson(result,new TypeToken<ResponseResult<Boolean>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }

    public AdminBankCard getAdminBankCard(String groupId,String companyId) {
        RemoteParams params = new RemoteParams(url).withPath(GET_ADMIN_BANK_CARD).withParam("groupId",groupId).withParam("companyId",companyId);
        String result = super.post(params);
        ResponseResult<AdminBankCard> response = new Gson().fromJson(result,new TypeToken<ResponseResult<AdminBankCard>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }

    public List<AdminBankCard> getAdminBankCardList(Long companyId,String channelNo) {
        RemoteParams params = new RemoteParams(url).withPath(GET_ADMIN_BANK_CARD_LIST).withParam("companyId",companyId).withParam("channelNo",channelNo);
        String result = super.post(params);
        ResponseResult<List<AdminBankCard>> response = new Gson().fromJson(result,new TypeToken<ResponseResult<List<AdminBankCard>>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }

    public boolean saveAdminBankCard(Map<String,Object> data) {
        RemoteParams params = new RemoteParams(url).withPath(SAVE_ADMIN_BANK_CARD).withParams(data);
        String result = super.post(params);
        return parseResult(result);
    }

    public Boolean userTurnBack(Map<String,Object> data) {
        RemoteParams params = new RemoteParams(url).withPath(USER_TURN_BACK).withParams(data);
        String result = super.post(params);
        return parseResult(result);
    }

    public ResponseResult<Boolean> memberPass(String id) {
        RemoteParams params = new RemoteParams(url).withPath(USER_PASS).withParam("id",id);
        String result = super.post(params);
        ResponseResult<Boolean> response = new Gson().fromJson(result,new TypeToken<ResponseResult<Boolean>>(){}.getType());
        return response;
    }

    public Boolean saveUserInfo(Map<String,Object> params) {
        RemoteParams remoteParams = new RemoteParams(url).withPath(SAVE_USER_INFO).withParams(params);
        String result = super.post(remoteParams);
        return parseResult(result);
    }

    public Boolean saveUserGroup(Map<String,Object> params) {
        RemoteParams remoteParams = new RemoteParams(url).withPath(SAVE_USER_GROUP).withParams(params);
        String result = super.post(remoteParams);
        return parseResult(result);
    }

    public Boolean finishWithDraw(Long id) {
        RemoteParams remoteParams = new RemoteParams(url).withPath(FINISH_WITH_DRAW).withParam("id",id);
        String result = super.post(remoteParams);
        return parseResult(result);
    }

    public Boolean withDrawFailed(Long id) {
        RemoteParams remoteParams = new RemoteParams(url).withPath(WITH_DRAW_FAILED).withParam("id",id);
        String result = super.post(remoteParams);
        return parseResult(result);
    }

    public UserChannel getUserChannelById(String id) {
        RemoteParams remoteParams = new RemoteParams(url).withPath("/user/get_user_channel_by_id").withParam("id",id);
        String result = super.post(remoteParams);
        ResponseResult<UserChannel> response = new Gson().fromJson(result,new TypeToken<ResponseResult<UserChannel>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }

    public AdminBankCard getAdminBankCardById(String id) {
        RemoteParams remoteParams = new RemoteParams(url).withPath(GET_ADMIN_BANK_CARD_BY_ID).withParam("id",id);
        String result = super.post(remoteParams);
        ResponseResult<AdminBankCard> response = new Gson().fromJson(result,new TypeToken<ResponseResult<AdminBankCard>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }

    private Boolean parseResult(String result) {
        ResponseResult<Boolean> response = new Gson().fromJson(result,new TypeToken<ResponseResult<Boolean>>(){}.getType());
        if(response.isSuccess()) {
            return response.getData();
        }
        throw new BizFailException(response.getCode(),response.getMsg());
    }
}