package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.client.DefaultClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.SalesManDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class AdminSalesIndexDispatcher implements Dispatcher {
    @Autowired
    private AdminClient adminClient;
    @Autowired
    private DefaultClient defaultClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        String groupId = request.getSession().getAttribute("groupId").toString();
        List<SalesManDto> list = adminClient.getSalesList(groupId);
        DispatchResult dispatchResult=new DispatchResult();
        dispatchResult.setPage(page);
        dispatchResult.addObject("salesinfo",list);

        List<Map<String,Object>> subGroups = defaultClient.getSubGroups(groupId);
        dispatchResult.addObject("subGroups",subGroups);

        return  dispatchResult;
    }
}
