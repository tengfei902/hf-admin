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
        tradeRequest.setGroupId(groupId);
        tradeRequest.setCurrentPage(currentPage);
        tradeRequest.setPageSize(15);

        if(StringUtils.isNotEmpty(request.getParameter("mchId"))) {
            tradeRequest.setMchId(request.getParameter("mchId"));
        }

        if(StringUtils.isNotEmpty(request.getParameter("orderid"))) {
            tradeRequest.setOutTradeNo(request.getParameter("orderid"));
        }

        if(StringUtils.isNotEmpty(request.getParameter("channelCode"))) {
            tradeRequest.setChannelCode(request.getParameter("channelCode"));
        }

        if(StringUtils.isNotEmpty(request.getParameter("status"))) {
            tradeRequest.setStatus(Integer.parseInt(request.getParameter("status")));
        }

        if(StringUtils.isNotEmpty(request.getParameter("type"))) {
            tradeRequest.setType(Integer.parseInt(request.getParameter("type")));
        }

        Pagenation<TradeRequestDto> pagenation = client.getTradeList(tradeRequest);

        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        dispatchResult.addObject("pageInfo",pagenation);
        dispatchResult.addObject("requestInfo",tradeRequest);

        List<Channel> channels = adminClient.getChannelList();
        dispatchResult.addObject("channels",channels);
        dispatchResult.addObject("urlParams",String.format("mchId=%s&orderid=%s&channelCode=%s&status=%s&type=%s",
                StringUtils.isEmpty(tradeRequest.getMchId())?"":tradeRequest.getMchId(),
                StringUtils.isEmpty(tradeRequest.getOutTradeNo())?"":tradeRequest.getOutTradeNo(),
                StringUtils.isEmpty(tradeRequest.getChannelCode())?"":tradeRequest.getChannelCode(),
                tradeRequest.getStatus()==null?"":tradeRequest.getStatus(),
                tradeRequest.getType()==null?"":tradeRequest.getType()));
        return dispatchResult;
    }
}
