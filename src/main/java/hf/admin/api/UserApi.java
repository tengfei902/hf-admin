package hf.admin.api;

import hf.admin.enums.UserType;
import hf.admin.model.*;
import hf.admin.rpc.HfClient;
import hf.admin.service.UserService;
import hf.admin.service.common.CacheService;
import hf.admin.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static hf.admin.model.Constants.USER_LOGIN_INFO;

@Controller
@RequestMapping("/user")
public class UserApi extends BaseController {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserService userService;
    @Autowired
    private HfClient hfClient;
    /**
     * 登陆
     * @param loginId
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView doLogin(HttpServletRequest request, String loginId,String password) {
        UserInfo userInfo = userService.getUserInfo(loginId,password);

        ModelAndView modelAndView = new ModelAndView();

        if(Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            modelAndView.setViewName("redirect:/login.jsp");
            return modelAndView;
        }

        if(userInfo.getType() == UserType.ADMIN.getValue() || userInfo.getType() == UserType.SUPER_ADMIN.getValue()) {
            request.getSession().setAttribute(USER_LOGIN_INFO, MapUtils.buildMap("id",userInfo.getId(),"name",userInfo.getName(),"loginId",userInfo.getLoginId(),"userType",UserType.parse(userInfo.getType()).getDesc(),"groupId",userInfo.getGroupId()));
            request.getSession().setAttribute("userId",userInfo.getId());
            request.getSession().setAttribute("groupId",userInfo.getGroupId());
            cacheService.login(userInfo.getId().toString(),request.getSession().getId());

            modelAndView.setViewName("redirect:/common/index");
            modelAndView.addObject("userInfo",userInfo);
            modelAndView.addObject("userId",userInfo.getId());
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/login.jsp");
        return modelAndView;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.USER_LOGIN_INFO);
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

        List<UserInfoDto> list = hfClient.getUserListForAdmin(userInfoRequest);

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

        List<UserGroupDto> list = hfClient.getUserGroupList(userGroupRequest);

        ModelAndView modelAndView = new ModelAndView("admin_user_index");
        modelAndView.addObject("users",list);
        return modelAndView;
    }

    @RequestMapping(value = "/edit_password",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> editPassword(HttpServletRequest request) {
        String userId = String.valueOf(request.getSession().getAttribute("userId"));
        String ypassword = request.getParameter("ypassword");
        String newpassword = request.getParameter("newpassword");
        String newpasswordok = request.getParameter("newpasswordok");

        boolean result = hfClient.editPassword(userId,ypassword,newpassword,newpasswordok);
        return MapUtils.buildMap("status",result);
    }
}
