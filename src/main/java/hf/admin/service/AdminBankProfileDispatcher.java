package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.contants.Constants;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.AdminBankCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AdminBankProfileDispatcher implements Dispatcher {
    @Autowired
    private AdminClient adminClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        String groupId = request.getParameter("id");
        String companyId = request.getSession().getAttribute("groupId").toString();
        AdminBankCard adminBankCard = adminClient.getAdminBankCard(groupId,companyId);
        dispatchResult.addObject("card",adminBankCard);
        dispatchResult.addObject("bankNames",Constants.bankNames);
        dispatchResult.addObject("group",groupId);
        return dispatchResult;
    }
}
