package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.client.DefaultClient;
import hf.base.contants.Constants;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.AdminBankCard;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AdminBankProfileDispatcher implements Dispatcher {
    @Autowired
    private AdminClient adminClient;
    @Autowired
    private DefaultClient defaultClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        String id = request.getParameter("id");
//        String companyId = request.getSession().getAttribute("groupId").toString();
        if(StringUtils.isNotEmpty(id)) {
            AdminBankCard adminBankCard = adminClient.getAdminBankCardById(id);
            dispatchResult.addObject("card",adminBankCard);
        }
        dispatchResult.addObject("bankNames",Constants.bankNames);
        return dispatchResult;
    }
}
