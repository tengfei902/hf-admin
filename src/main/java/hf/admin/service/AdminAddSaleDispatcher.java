package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.client.DefaultClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.SalesManDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class AdminAddSaleDispatcher implements Dispatcher {
    @Autowired
    private DefaultClient defaultClient;
    @Autowired
    private AdminClient adminClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);

        String groupId = request.getParameter("groupId");
        if(!StringUtils.isEmpty(groupId)) {
//            SalesManDto salesManDto = adminClient.getSaleInfo(groupId);
//            dispatchResult.addObject("sale",salesManDto);
        }

        List<Map<String,Object>> subGroups = defaultClient.getSubGroups(groupId);
        dispatchResult.addObject("subGroups",subGroups);
        return dispatchResult;
    }
}
