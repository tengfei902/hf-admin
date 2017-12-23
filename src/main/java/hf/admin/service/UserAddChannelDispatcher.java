package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.Channel;
import hf.base.model.UserChannel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserAddChannelDispatcher implements Dispatcher {
    @Autowired
    private AdminClient client;

    @Override
    public DispatchResult dispatch(HttpServletRequest request, String page) {
        String providerCode = request.getParameter("provider");
        List<Channel> channels = client.getProviderChannelList(providerCode);

        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        dispatchResult.addObject("channels",channels);
        dispatchResult.addObject("providerCode",providerCode);

        String groupId = request.getParameter("groupId");
        String id = request.getParameter("id");
        if(StringUtils.isNotEmpty(id)) {
            UserChannel userChannel = client.getUserChannelById(id);
            dispatchResult.addObject("userChannel",userChannel);
        }
        dispatchResult.addObject("group",groupId);
        return dispatchResult;
    }
}
