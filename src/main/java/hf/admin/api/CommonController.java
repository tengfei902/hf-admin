package hf.admin.api;

import hf.admin.model.Constants;
import hf.admin.model.UserInfoDto;
import hf.admin.model.UserInfoRequest;
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
import java.util.Objects;

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

            modelAndView.addObject("users",list);
        }

        return modelAndView;
    }
}
