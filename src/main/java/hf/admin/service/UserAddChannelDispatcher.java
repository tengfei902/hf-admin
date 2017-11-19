package hf.admin.service;

import hf.admin.rpc.AdminClient;
import hf.base.dispatcher.DispatchResult;
import hf.base.dispatcher.Dispatcher;
import hf.base.model.Channel;
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
        List<Channel> channels = client.getAvaChannelList();

        DispatchResult dispatchResult = new DispatchResult();
        dispatchResult.setPage(page);
        dispatchResult.addObject("channels",channels);

        String groupId = request.getParameter("groupId");
        dispatchResult.addObject("group",groupId);
        return dispatchResult;
    }
}
