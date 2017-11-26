package hf.admin.service;

import hf.base.client.DefaultClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AdminAddChannelDispatcher implements Dispatcher {
    @Autowired
    private DefaultClient defaultClient;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        String channelId = request.getParameter("channelId");
        Channel channel = defaultClient.getChannelById(channelId);
        dispatchResult.addObject("channel",channel);
        return dispatchResult;
    }
}
