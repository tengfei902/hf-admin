package hf.admin.service;

import com.google.common.collect.Lists;
import hf.admin.model.Constants;
import hf.admin.rpc.AdminClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.UserGroup;
import hf.base.utils.Pagenation;
import hf.base.utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthorizedDispatcher implements Dispatcher {
    @Autowired
    private AdminClient adminClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        int currentPage = 1;
        if(!StringUtils.isEmpty(request.getParameter("currentPage"))) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"))<=0?currentPage:Integer.parseInt(request.getParameter("currentPage"));
        }

        Long groupId = Long.parseLong(request.getSession().getAttribute("groupId").toString());
        List<UserGroup> list = adminClient.getAuthorizedList(groupId);

        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);

        Pagenation<UserGroup> pagenation = new Pagenation<UserGroup>(list, currentPage,hf.base.contants.Constants.pageSize);
        dispatchResult.addObject("pageInfo",pagenation);

        return dispatchResult;
    }
}
