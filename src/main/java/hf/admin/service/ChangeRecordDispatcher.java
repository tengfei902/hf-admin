package hf.admin.service;

import hf.base.client.DefaultClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.AccountOprInfo;
import hf.base.model.AccountOprRequest;
import hf.base.utils.Pagenation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ChangeRecordDispatcher implements Dispatcher {
    @Autowired
    private DefaultClient client;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        Long groupId = Long.parseLong(request.getSession().getAttribute("groupId").toString());
        AccountOprRequest accountOprRequest = new AccountOprRequest();
        accountOprRequest.setGroupId(groupId);

        Integer currentPage = 1;
        if(StringUtils.isNotEmpty(request.getParameter("currentPage"))) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }

        accountOprRequest.setCurrentPage(currentPage);
        accountOprRequest.setPageSize(15);
        Pagenation<AccountOprInfo> pagenation = client.getAccountOprLogList(accountOprRequest);

        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        dispatchResult.addObject("pageInfo",pagenation);
        dispatchResult.addObject("requestInfo",accountOprRequest);
        return dispatchResult;
    }
}
