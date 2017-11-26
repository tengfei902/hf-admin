package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.client.DefaultClient;
import hf.base.contants.Constants;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.Channel;
import hf.base.model.TradeRequest;
import hf.base.model.TradeRequestDto;
import hf.base.utils.Pagenation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdminOrderDispatcher implements Dispatcher {
    @Autowired
    private DefaultClient client;
    @Autowired
    private AdminClient adminClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        Long groupId = Long.parseLong(request.getSession().getAttribute("groupId").toString());
        int currentPage = 1;
        String pageIndex = request.getParameter("currentPage");
        if(!StringUtils.isEmpty(pageIndex)) {
            currentPage = Integer.parseInt(pageIndex);
        }
        TradeRequest tradeRequest = new TradeRequest();
        tradeRequest.setCurrentPage(1);
        tradeRequest.setGroupId(groupId);
        tradeRequest.setCurrentPage(currentPage);
        tradeRequest.setPageSize(15);
        Pagenation<TradeRequestDto> pagenation = client.getTradeList(tradeRequest);

        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        dispatchResult.addObject("pageInfo",pagenation);
        dispatchResult.addObject("requestInfo",tradeRequest);

        List<Channel> channels = adminClient.getChannelList();
        dispatchResult.addObject("channels",channels);

        return dispatchResult;
    }
}
