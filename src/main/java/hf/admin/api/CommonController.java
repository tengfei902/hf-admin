package hf.admin.api;

import hf.admin.model.*;
import hf.admin.rpc.HfClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private HfClient hfClient;

    @RequestMapping(value="/{page}")
    public ModelAndView dispatch(HttpServletRequest request, @PathVariable("page")String page) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(String.format("/%s",page));

        if(StringUtils.equals(page,"index")) {
            Map<String,Object> map = (Map<String,Object>)request.getSession().getAttribute(Constants.USER_LOGIN_INFO);
            modelAndView.addObject("name",map.get("name"));
        }
        if(StringUtils.equals(page,"admin_user_index")) {
            String groupId = String.valueOf(request.getSession().getAttribute("groupId"));
            UserGroupRequest userGroupRequest = new UserGroupRequest();
            userGroupRequest.setCompanyId(groupId);
            List<UserGroupDto> list = hfClient.getUserGroupList(userGroupRequest);

            modelAndView = new ModelAndView("admin_user_index");
            modelAndView.addObject("users",list);
            return modelAndView;
        }

        if(StringUtils.equals(page,"admin_user_index2")) {
            String groupId = String.valueOf(request.getSession().getAttribute("groupId"));
            UserInfoRequest userInfoRequest = new UserInfoRequest();
            userInfoRequest.setAdminId(groupId);
            List<UserInfoDto> list = hfClient.getUserListForAdmin(userInfoRequest);
            modelAndView.addObject("users",list);
        }

        return modelAndView;
    }
}
