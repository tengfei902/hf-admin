package hf.admin.service;

import hf.admin.utils.MapUtils;
import hf.base.client.DefaultClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.enums.ChannelCode;
import hf.base.enums.ChannelProvider;
import hf.base.model.Channel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminAddChannelDispatcher implements Dispatcher {
    @Autowired
    private DefaultClient defaultClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        String channelId = request.getParameter("channelId");
        if(!StringUtils.isEmpty(channelId)) {
            Channel channel = defaultClient.getChannelById(channelId);
            dispatchResult.addObject("channel",channel);
        }

        List<Map<String,String>> providers = new ArrayList<>();
        for(ChannelProvider channelProvider:ChannelProvider.values()) {
            providers.add(MapUtils.buildMap("code",channelProvider.getCode(),"name",channelProvider.getName()));
        }
        dispatchResult.addObject("providers",providers);

        List<Map<String,String>> channelNoMap = new ArrayList<>();
        for(ChannelCode channelCode:ChannelCode.values()) {
            channelNoMap.add(MapUtils.buildMap("code",channelCode.getCode(),"name",channelCode.getDesc()));
        }
        dispatchResult.addObject("channelNos",channelNoMap);

        return dispatchResult;
    }
}
