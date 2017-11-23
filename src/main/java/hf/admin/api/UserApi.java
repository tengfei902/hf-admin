package hf.admin.api;

import hf.admin.enums.UserType;
import hf.admin.model.*;
import hf.admin.rpc.AdminClient;
import hf.admin.rpc.UserClient;
import hf.admin.utils.MapUtils;
import hf.base.biz.CacheService;
import hf.base.client.DefaultClient;
import hf.base.contants.CodeManager;
import hf.base.model.UserGroup;
import hf.base.model.UserInfo;
import hf.base.utils.Pagenation;
import hf.base.utils.ResponseResult;
import hf.base.utils.Utils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static hf.admin.model.Constants.USER_LOGIN_INFO;

@Controller
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private AdminClient adminClient;
    @Autowired
    private DefaultClient client;
    @Autowired
    private UserClient userClient;
    /**
     * 登陆
     * @param loginId
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView doLogin(HttpServletRequest request, String loginId,String password) {
        UserInfo userInfo = client.getUserInfo(loginId,password, hf.base.contants.Constants.GROUP_TYPE_COMPANY);
        ModelAndView modelAndView = new ModelAndView();

        if(Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            modelAndView.setViewName("redirect:/login.jsp");
            return modelAndView;
        }

        request.getSession().setAttribute(USER_LOGIN_INFO, MapUtils.buildMap("id",userInfo.getId(),"name",userInfo.getName(),"loginId",userInfo.getLoginId(),"userType",UserType.parse(userInfo.getType()).getDesc(),"groupId",userInfo.getGroupId()));
        request.getSession().setAttribute("userId",userInfo.getId());
        request.getSession().setAttribute("groupId",userInfo.getGroupId());
        cacheService.login(userInfo.getId().toString(),request.getSession().getId());

        modelAndView.setViewName("redirect:/common/index");
        return modelAndView;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.USER_LOGIN_INFO);
        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("groupId");
        ModelAndView modelAndView = new ModelAndView("redirect:/login.jsp");
        return modelAndView;
    }

    @RequestMapping(value = "/get_user_list",method = RequestMethod.GET)
    public ModelAndView getUserList(HttpServletRequest request) {
        String userId = String.valueOf(request.getSession().getAttribute("userId"));
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setUser(request.getParameter("user"));
        userInfoRequest.setAgent(request.getParameter("agent"));

        if(!StringUtils.isEmpty(request.getParameter("status"))) {
            userInfoRequest.setStatus(Integer.parseInt(request.getParameter("status")));
        }

        if(!StringUtils.isEmpty(request.getParameter("type"))) {
            userInfoRequest.setStatus(Integer.parseInt(request.getParameter("type")));
        }

        userInfoRequest.setAdminId(userId);

        List<UserInfoDto> list = adminClient.getUserListForAdmin(userInfoRequest);

        ModelAndView modelAndView = new ModelAndView("admin_user_index");
        modelAndView.addObject("users",list);
        return modelAndView;
    }

    @RequestMapping(value = "/get_user_group_list",method = RequestMethod.GET)
    public ModelAndView getUserGroupList(HttpServletRequest request) {
        String groupId = String.valueOf(request.getSession().getAttribute("groupId"));
        UserGroupRequest userGroupRequest = new UserGroupRequest();
        userGroupRequest.setUser(request.getParameter("user"));
        userGroupRequest.setAgent(request.getParameter("agent"));

        if(!StringUtils.isEmpty(request.getParameter("status"))) {
            userGroupRequest.setStatus(Integer.parseInt(request.getParameter("status")));
        }

        if(!StringUtils.isEmpty(request.getParameter("type"))) {
            userGroupRequest.setType(Integer.parseInt(request.getParameter("type")));
        }

        userGroupRequest.setCompanyId(groupId);

        Pagenation<UserGroup> pagenation = adminClient.getUserGroupList(userGroupRequest);

        ModelAndView modelAndView = new ModelAndView("admin_user_authorized");
        modelAndView.addObject("pageInfo",pagenation);
        modelAndView.addObject("userGroupRequest",userGroupRequest);
        return modelAndView;
    }

    @RequestMapping(value = "/edit_password",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> editPassword(HttpServletRequest request) {
        String userId = String.valueOf(request.getSession().getAttribute("userId"));
        String ypassword = request.getParameter("ypassword");
        String newpassword = request.getParameter("newpassword");
        String newpasswordok = request.getParameter("newpasswordok");

        boolean result = adminClient.editPassword(userId,ypassword,newpassword,newpasswordok);
        return MapUtils.buildMap("status",result);
    }

    @RequestMapping(value = "/get_channel_info",method = RequestMethod.GET)
    public Map<String,Object> getChannelInfo(Long channelId,Long groupId) {
        return null;
    }

    @RequestMapping(value = "/save_channel",method = RequestMethod.POST)
    public ModelAndView saveChannel(HttpServletRequest request) {
        String channelName = request.getParameter("channelName");
        String channelCode = request.getParameter("channelCode");
        String feeRate = request.getParameter("feeRate");
        String url = request.getParameter("url");
        String status = request.getParameter("status");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/common/admin_channel_index");

        if(!NumberUtils.isNumber(feeRate)) {
            return modelAndView;
        }

        adminClient.saveChannel(MapUtils.buildMap("channelName",channelName,"channelCode",channelCode,"feeRate",feeRate,"url",url,"status",status));

        return modelAndView;
    }

    @RequestMapping(value = "/save_user_channel",method = RequestMethod.POST)
    public @ResponseBody Map<String,Boolean> saveUserChannel(HttpServletRequest request) {
        String groupId = request.getParameter("group");
        String channelId = request.getParameter("channelId");
        String feeRate = request.getParameter("feeRate");
        String mchId = request.getParameter("mchId");
        String cipherCode = request.getParameter("cipherCode");
        String callbackUrl = request.getParameter("callbackUrl");

        boolean result = adminClient.saveUserChannel(MapUtils.buildMap("groupId",groupId,"channelId",channelId,"feeRate",feeRate,"mchId",mchId,"cipherCode",cipherCode,"callbackUrl",callbackUrl));
        return MapUtils.buildMap("status",result);
    }

    @RequestMapping(value = "/save_admin_bank_card",method = RequestMethod.POST)
    public @ResponseBody Map<String,Boolean> saveAdminBankCard(HttpServletRequest request) {
        String id = request.getParameter("id");
        String bank = request.getParameter("bank");
        String deposit = request.getParameter("deposit");
        String owner = request.getParameter("owner");
        String bankNo = request.getParameter("bankNo");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String companyId = request.getSession().getAttribute("groupId").toString();
        String groupId = request.getParameter("group");

        boolean status = adminClient.saveAdminBankCard(MapUtils.buildMap("id",id,"bank",bank,"deposit",deposit,"owner",owner,
                "bankNo",bankNo,"province",province,"city",city,"companyId",companyId,"groupId",groupId));

        return MapUtils.buildMap("status",status);
    }

    @RequestMapping(value = "/user_member_back",method = RequestMethod.POST)
    public @ResponseBody Map<String,Boolean> memberBack(HttpServletRequest request) {
        String id = request.getParameter("uid");
        String groupId = request.getSession().getAttribute("groupId").toString();
        String userId = request.getSession().getAttribute("userId").toString();

        boolean result = adminClient.userTurnBack(MapUtils.buildMap("id",id,"groupId",groupId,"userId",userId));
        return MapUtils.buildMap("status",result);
    }

    @RequestMapping(value = "/user_member_pass",method = RequestMethod.POST)
    public @ResponseBody Map<String,Boolean> memberPass(HttpServletRequest request) {
        String id = request.getParameter("uid");
        ResponseResult<Boolean> response = adminClient.memberPass(id);
        if(response.isSuccess()) {
            return MapUtils.buildMap("status",true);
        } else {
            return MapUtils.buildMap("status",false,"msg",response.getMsg());
        }
    }

    @RequestMapping(value = "/save_bank_card",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveBankCard(HttpServletRequest request, HttpServletResponse response) {
        String groupId = request.getParameter("groupId");
        String id = request.getParameter("id");
        String bank = request.getParameter("bank");
        String bankNo = request.getParameter("bankNo");
        String deposit = request.getParameter("deposit");
        String owner = request.getParameter("owner");
        String province = request.getParameter("province");
        String city = request.getParameter("city");

        ResponseResult<Boolean> responseResult = userClient.saveBankCard(
                hf.base.utils.MapUtils.buildMap("groupId",groupId,
                        "bank",bank,
                        "bankNo",bankNo,
                        "deposit",deposit,
                        "owner",owner,
                        "province",province,
                        "city",city,
                        "id",id));

        if(responseResult.isSuccess()) {
            return hf.base.utils.MapUtils.buildMap("status",true);
        }
        return hf.base.utils.MapUtils.buildMap("status",false);
    }

    @RequestMapping(value = "/save_user_info",method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public ModelAndView saveUserInfo(@RequestBody Map<String,String> params) {
        String loginId = params.get("loginId");
        String password = params.get("password");
        String name = params.get("name");
        String idCard = params.get("idCard");
        String tel = params.get("tel");
        String qq = params.get("qq");
        String address = params.get("address");
        String groupId = params.get("userGroup");
        String id = params.get("id");

        Map<String,Object> map = Utils.buildMap("loginId",loginId,
                "password",password,
                "name",name,
                "idCard",idCard,
                "tel",tel,
                "qq",qq,
                "address",address,
                "groupId",groupId,
                "id",id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("group_user_list");
        return modelAndView;
    }
}
