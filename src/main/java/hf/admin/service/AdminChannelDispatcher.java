package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.enums.ChannelCode;
import hf.base.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdminChannelDispatcher implements Dispatcher {
    @Autowired
    private AdminClient adminClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);

        List<Channel> channelList = adminClient.getChannelList();
        channelList.parallelStream().forEach(channel -> {
            ChannelCode channelCode = ChannelCode.parseFromCode(channel.getCode());
            channel.setCode(channelCode.getDesc());
        });
        dispatchResult.addObject("channels",channelList);
        return dispatchResult;
    }
}
