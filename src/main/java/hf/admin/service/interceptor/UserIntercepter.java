package hf.admin.service.interceptor;

import hf.admin.service.common.CacheService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

import static hf.admin.model.Constants.*;

/**
 * Created by tengfei on 2017/11/11.
 */
public class UserIntercepter implements HandlerInterceptor {
    @Autowired
    private CacheService cacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String loginId = request.getParameter("loginId");
        if(StringUtils.isEmpty(loginId) && request.getRequestURI().contains("login")) {
            response.sendRedirect("/admin/login.jsp");
            return false;
        }

        String sessionId = request.getSession().getId();
        if(Objects.isNull(request.getSession().getAttribute(USER_LOGIN_INFO))) {
            response.sendRedirect("/admin/login.jsp");
            return false;
        }
        Map<String,Object> map = (Map<String,Object>)request.getSession().getAttribute(USER_LOGIN_INFO);

        if(MapUtils.isEmpty(map)) {
            response.sendRedirect("/admin/login.jsp");
            return false;
        }

        if(request.getRequestURI().contains("login") && !StringUtils.equals(loginId,String.valueOf(map.get("loginId")))) {
            response.sendRedirect("/admin/login.jsp");
            return false;
        }

        String userId = ((Map<String,Object>)request.getSession().getAttribute(USER_LOGIN_INFO)).get("id").toString();
        if(!cacheService.checkLogin(userId,sessionId)) {
            response.sendRedirect("/admin/login.jsp");
            return false;
        }

        request.setAttribute(CURRENT_USER_ID,userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
