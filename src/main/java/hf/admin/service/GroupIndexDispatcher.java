package hf.admin.service;

import hf.admin.model.UserGroupDto;
import hf.admin.model.UserGroupRequest;
import hf.admin.rpc.AdminClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.UserGroup;
import hf.base.utils.Pagenation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class GroupIndexDispatcher implements Dispatcher {
    @Autowired
    private AdminClient adminClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        String companyId = request.getSession().getAttribute("groupId").toString();
        UserGroupRequest userGroupRequest = new UserGroupRequest();
        userGroupRequest.setCompanyId(companyId);
        Pagenation<UserGroup> pagenation = adminClient.getUserGroupList(userGroupRequest);
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        dispatchResult.addObject("pageInfo",pagenation);
        return dispatchResult;
    }
}
